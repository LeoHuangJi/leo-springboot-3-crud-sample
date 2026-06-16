package vn.leoo.audit.log.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import vn.leoo.audit.log.context.AuditLogContext;
import vn.leoo.audit.log.service.AuditLogService;
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "audit.mode",
        havingValue = "event"
)
public class SpringEventListener {
    // Gọi từ nghiệp vụ — non-blocking

    private final AuditLogService auditLogService;
    @Async("auditLogExecutor")
    @TransactionalEventListener(phase = org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT)
    public void saveAsync(AuditLogContext context) {
        try {
            auditLogService.save(context);
        } catch (Exception e) {
            // Fallback: ghi file, không để mất log
            log.error("[AUDIT-FALLBACK] Lỗi ghi audit log: module={}, action={}, trace={}",
                    context.getModule(), context.getAction(), context.getTraceId(), e);
            auditLogService.writeFallbackLog(context);
        }
    }

}
