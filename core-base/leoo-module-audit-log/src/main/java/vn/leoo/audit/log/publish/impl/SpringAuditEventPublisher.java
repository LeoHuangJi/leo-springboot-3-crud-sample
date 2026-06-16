package vn.leoo.audit.log.publish.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.leoo.audit.log.context.AuditLogContext;
import vn.leoo.audit.log.publish.AuditEventPublisher;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "audit.mode",
        havingValue = "event"
)
public class SpringAuditEventPublisher implements AuditEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(AuditLogContext context) {
        eventPublisher.publishEvent(context);
    }
}