package vn.leoo.audit.log.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import vn.leoo.audit.log.context.AuditLogContext;

@Configuration
@ConditionalOnProperty(
        name = "audit.mode",
        havingValue = "kafka"
)
@EnableKafka
public class _KafkaConfig {

   /* @Bean
    public ProducerFactory<String, AuditLogContext> producerFactory() {

    }

    @Bean
    public KafkaTemplate<String, AuditLogContext> kafkaTemplate() {
    }*/
}