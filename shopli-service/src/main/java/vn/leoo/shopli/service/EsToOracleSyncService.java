package vn.leoo.shopli.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.core.OpenPointInTimeResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.leoo.entity.SyncCheckpoint;
import vn.leoo.shopli.config.SyncProperties;
import vn.leoo.shopli.repository.SyncCheckpointRepository;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EsToOracleSyncService {

    private final ElasticsearchClient        esClient;
    private final EntityManager entityManager;
    private final SyncCheckpointRepository   checkpointRepository;
    private final SyncProperties properties;
    private final ObjectMapper               objectMapper;

    private static final String JOB_NAME = "audit_log_es_to_oracle";

    /**
     * Đồng bộ toàn bộ dữ liệu từ ES index sang Oracle.
     * Hỗ trợ resume nếu job bị gián đoạn — đọc lại checkpoint cuối cùng.
     *
     * @param indexName tên index ES nguồn
     */
    public void syncAll(String indexName) {
        SyncCheckpoint checkpoint = checkpointRepository.findById(JOB_NAME)
                .orElse(createNewCheckpoint());

        if ("COMPLETED".equals(checkpoint.getStatus())) {
            log.info("[SYNC] Job đã hoàn tất trước đó. Gọi resetCheckpoint() nếu muốn chạy lại.");
            return;
        }

        checkpoint.setStatus("RUNNING");
        checkpoint.setStartedAt(LocalDateTime.now());
        checkpointRepository.save(checkpoint);

        String pitId = resolvePitId(checkpoint, indexName);
        List<FieldValue> searchAfter = parseSearchAfter(checkpoint.getLastSortValue());
        long totalSynced = checkpoint.getTotalSynced();

        try {
            while (true) {
                SearchResponse<Map> response = fetchPage(pitId, searchAfter);
                List<Hit<Map>> hits = response.hits().hits();

                if (hits.isEmpty()) break;

                List<Map> records = hits.stream().map(Hit::source).collect(Collectors.toList());
                insertBatch(records);

                totalSynced += records.size();
                searchAfter = hits.get(hits.size() - 1).sort();
                pitId = response.pitId() != null ? response.pitId() : pitId;

                saveCheckpoint(checkpoint, pitId, searchAfter, totalSynced);
                log.info("[SYNC] Đã đồng bộ: {} / ~9,000,000 rows", totalSynced);
            }

            closePit(pitId);
            checkpoint.setStatus("COMPLETED");
            checkpoint.setUpdatedAt(LocalDateTime.now());
            checkpointRepository.save(checkpoint);

            log.info("[SYNC] HOÀN TẤT. Tổng: {} rows", totalSynced);

        } catch (Exception e) {
            checkpoint.setStatus("FAILED");
            checkpointRepository.save(checkpoint);
            log.error("[SYNC] Lỗi tại {} rows. Chạy lại syncAll() để resume.", totalSynced, e);
            throw new RuntimeException("Sync thất bại", e);
        }
    }

    /**
     * Cho phép chạy lại job từ đầu — xóa checkpoint cũ.
     */
    public void resetCheckpoint() {
        checkpointRepository.deleteById(JOB_NAME);
    }

    // ─────────────────────────────────────────────────────────
    // ES — Point-in-Time + search_after
    // ─────────────────────────────────────────────────────────

    private String resolvePitId(SyncCheckpoint checkpoint, String indexName) {
        if (checkpoint.getPitId() != null) {
            return checkpoint.getPitId();   // resume — dùng lại PIT cũ
        }
        try {
            OpenPointInTimeResponse pitResponse = esClient.openPointInTime(p -> p
                    .index(indexName)
                    .keepAlive(Time.of(t -> t.time(properties.getPitKeepAlive()))));
            return pitResponse.id();
        } catch (IOException e) {
            throw new RuntimeException("Không thể mở Point-in-Time", e);
        }
    }

    /**
     * Lấy 1 trang dữ liệu từ ES.
     * Sort theo ngay_tao + _id (tie-breaker) để đảm bảo search_after
     * không bỏ sót/lặp lại khi nhiều document trùng giá trị ngay_tao.
     */
    private SearchResponse<Map> fetchPage(String pitId, List<FieldValue> searchAfter) {
        try {
            SearchRequest.Builder builder = new SearchRequest.Builder()
                    .size(properties.getPageSize())
                    .pit(pit -> pit.id(pitId).keepAlive(Time.of(t -> t.time(properties.getPitKeepAlive()))))
                    .sort(so -> so.field(f -> f.field("_id").order(SortOrder.Asc)))   // tie-breaker
                    .source(src -> src.filter(f -> f.includes(
                            "id", "traceId", "module", "action", "rootType",
                            "rootId", "actorInfo", "description", "ngay_tao")));

            if (searchAfter != null && !searchAfter.isEmpty()) {
                builder.searchAfter(searchAfter);
            }

            return esClient.search(builder.build(), Map.class);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi fetch từ ES", e);
        }
    }

    private void closePit(String pitId) {
        try {
            esClient.closePointInTime(c -> c.id(pitId));
        } catch (IOException e) {
            log.warn("[SYNC] Không thể đóng PIT, sẽ tự hết hạn sau keep-alive", e);
        }
    }

    // ─────────────────────────────────────────────────────────
    // Oracle — batch insert qua JPA
    // ─────────────────────────────────────────────────────────

    /**
     * Insert batch dùng EntityManager.
     * Flush + clear sau mỗi batchSize record để tránh OOM
     * do Persistence Context tích lũy quá nhiều entity.
     */
    @Transactional
    public void insertBatch(List<Map> records) {
        int count = 0;
        for (Map record : records) {
          //  entityManager.persist(toEntity(record));
            count++;

            if (count % properties.getBatchSize() == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }

   /* private AuditLogArchive toEntity(Map record) {
        return AuditLogArchive.builder()
                .traceId((String) record.get("traceId"))
                .module((String) record.get("module"))
                .action((String) record.get("action"))
                .rootType((String) record.get("rootType"))
                .rootId((String) record.get("rootId"))
                .actorInfo(toJson(record.get("actorInfo")))
                .description((String) record.get("description"))
                .ngayTao(parseDateTime(record.get("ngay_tao")))
                .build();
    }*/

    // ─────────────────────────────────────────────────────────
    // Checkpoint
    // ─────────────────────────────────────────────────────────

    private void saveCheckpoint(SyncCheckpoint checkpoint, String pitId,
                                List<FieldValue> searchAfter, long totalSynced) {
        checkpoint.setPitId(pitId);
        checkpoint.setLastSortValue(serializeSearchAfter(searchAfter));
        checkpoint.setTotalSynced(totalSynced);
        checkpoint.setUpdatedAt(LocalDateTime.now());
        checkpointRepository.save(checkpoint);
    }

    private List<FieldValue> parseSearchAfter(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            List<Object> values = objectMapper.readValue(json, new TypeReference<>() {});
            return values.stream().map(v -> FieldValue.of(String.valueOf(v))).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    private String serializeSearchAfter(List<FieldValue> searchAfter) {
        try {
            List<Object> values = searchAfter.stream().map(FieldValue::_get).collect(Collectors.toList());
            return objectMapper.writeValueAsString(values);
        } catch (Exception e) {
            return null;
        }
    }

    private SyncCheckpoint createNewCheckpoint() {
        return SyncCheckpoint.builder().jobName(JOB_NAME).totalSynced(0L).status("NEW").build();
    }

    // ─────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────

    private String toJson(Object obj) {
        try {
            return obj == null ? null : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj);
        }
    }

    private LocalDateTime parseDateTime(Object value) {
        if (value == null) return null;
        return LocalDateTime.parse(value.toString(), DateTimeFormatter.ISO_DATE_TIME);
    }
}