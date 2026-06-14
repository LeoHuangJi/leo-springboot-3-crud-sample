package vn.leoo.audit.log.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import vn.leoo.audit.log.dto.AuditLogContext;
import vn.leoo.audit.log.entity.AuditLog;
import vn.leoo.audit.log.entity.AuditLogDetail;
import vn.leoo.audit.log.repo.AuditLogDetailRepository;
import vn.leoo.audit.log.repo.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogDetailRepository auditLogDetailRepository;
    private final ObjectMapper objectMapper;

    // Gọi từ nghiệp vụ — non-blocking
    @Async("auditLogExecutor")
    @TransactionalEventListener(phase = org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT)
    public void saveAsync(AuditLogContext context) {
        try {
            save(context);
        } catch (Exception e) {
            // Fallback: ghi file, không để mất log
            log.error("[AUDIT-FALLBACK] Lỗi ghi audit log: module={}, action={}, trace={}",
                    context.getModule(), context.getAction(), context.getTraceId(), e);
            writeFallbackLog(context);
        }
    }

   @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(AuditLogContext context) {
        // Map context → entity
        AuditLog auditLog = AuditLog.builder()
                .traceId(context.getTraceId())
                .module(context.getModule())
                .action(context.getAction())
                .rootType(context.getRootType())
                .rootId(context.getRootId())
                .description(context.getDescription())
                .actorInfo(toJson(context.getActorInfo()))
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);

        // Lưu details
        if (context.getDetails() != null) {
            List<AuditLogDetail> details = context.getDetails().stream()
                    .map(d -> AuditLogDetail.builder()
                            .auditLog(auditLog)
                            .objectType(d.getObjectType())
                            .objectId(d.getObjectId())
                            .action(d.getAction())
                            .changedData(toJson(d.getChangedData()))
                            .createdAt(LocalDateTime.now())
                            .build())
                    .collect(Collectors.toList());

            auditLogDetailRepository.saveAll(details);
        }
    }

    private void writeFallbackLog(AuditLogContext context) {
        try {
            // Ghi ra file riêng để không mất
            log.error("[AUDIT-DATA] {}", toJson(context));
        } catch (Exception ex) {
            log.error("[AUDIT-FALLBACK] Không thể ghi fallback log", ex);
        }
    }

    private String toJson(Object obj) {
        try {
            return obj == null ? null : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj);
        }
    }
}