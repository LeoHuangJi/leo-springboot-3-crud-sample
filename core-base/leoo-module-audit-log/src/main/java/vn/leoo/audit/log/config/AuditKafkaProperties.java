package vn.leoo.audit.log.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cấu hình cho {@link }.
 *
 * <pre>{@code
 * # Dùng Spring Event (default)
 * audit:
 *   publisher:
 *     type: spring
 *
 * # Dùng Kafka
 * audit:
 *   publisher:
 *     type: kafka
 *     kafka:
 *       topic: audit-log-topic
 * }</pre>
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "audit.kafka")
public class AuditKafkaProperties {
    private String topic;
    private String groupId;
}