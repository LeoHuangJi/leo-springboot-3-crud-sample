package vn.leoo.audit.log.publish.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import vn.leoo.audit.log.config.AuditKafkaProperties;
import vn.leoo.audit.log.context.AuditLogContext;
import vn.leoo.audit.log.publish.AuditEventPublisher;

/**
 * Publish audit event qua Kafka.
 *
 * <p>Phù hợp cho:</p>
 * <ul>
 *   <li>Hệ thống microservice, nhiều instance</li>
 *   <li>Cần retry tự động khi consumer gặp lỗi</li>
 *   <li>Audit log lưu ở service riêng biệt</li>
 *   <li>Cần throughput cao</li>
 * </ul>
 *
 * <p>Kích hoạt khi {@code audit.publisher.type=kafka}</p>
 *
 * <pre>{@code
 * audit:
 *   publisher:
 *     type: kafka
 *     kafka:
 *       topic: audit-log-topic
 * }</pre>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
       name = "audit.mode",
        havingValue = "kafka"
)
public class KafkaAuditEventPublisher implements AuditEventPublisher {

    private final KafkaTemplate<String, AuditLogContext> kafkaTemplate;
    private final AuditKafkaProperties properties;

    @Override
    public void publish(AuditLogContext context) {
        String topic = properties.getTopic();
        String key   = context.getRootType() + ":" + context.getRootId();

        kafkaTemplate.send(topic, key, context)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        // Kafka thất bại → fallback ghi log, không mất audit
                        log.error("[AUDIT-KAFKA-FALLBACK] topic={}, key={}, traceId={}",
                                topic, key, context.getTraceId(), ex);
                    }
                });
    }
}