package vn.leoo.audit.log.publish;

import vn.leoo.audit.log.context.AuditLogContext;


public interface AuditEventPublisher {
    void publish(AuditLogContext context);
}