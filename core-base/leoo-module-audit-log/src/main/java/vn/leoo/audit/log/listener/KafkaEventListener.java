package vn.leoo.audit.log.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import vn.leoo.audit.log.context.AuditLogContext;
import vn.leoo.audit.log.service.AuditLogService;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "audit.mode",
        havingValue = "kafka"
)
public class KafkaEventListener {
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    @KafkaListener(groupId = "{audit.kafka.group-id}", topics = "{audit.kafka.topic}")
    public void auditListener(ConsumerRecord<String, Object> body, Acknowledgment acknowledgment) {
        try {
            log.info("=============================== value: " + body.value());
            AuditLogContext context = modelMapper.map(body, AuditLogContext.class);
            auditLogService.save(context);
        } catch (Exception e) {
            log.info("Exception kafka: " + e.getMessage());
        } finally {
            acknowledgment.acknowledge();
        }
        if (body.value() != null) {

        }


    }

}