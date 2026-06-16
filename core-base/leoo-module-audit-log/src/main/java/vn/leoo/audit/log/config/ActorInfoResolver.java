package vn.leoo.audit.log.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.leoo.audit.log.context.AuditLogContext;

@Component
public class ActorInfoResolver {


    public AuditLogContext.ActorInfo resolve() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();

        return AuditLogContext.ActorInfo.builder()
                .username(extractUsername(auth))
                .role(extractRole(auth))
                .ip(extractIp(request))
                .sessionId(extractSessionId(request))
                .build();
    }

    /**
     * Gọi ngoài HTTP scope (batch job, scheduled task...).
     * Trả về actor mặc định thay vì throw exception.
     *
     * @param systemName tên hệ thống thực hiện (VD: "BATCH_JOB", "SCHEDULER")
     */
    public AuditLogContext.ActorInfo resolveSystem(String systemName) {
        return AuditLogContext.ActorInfo.builder()
                .username(systemName)
                .role("SYSTEM")
                .ip("internal")
                .sessionId(null)
                .build();
    }

    private String extractUsername(Authentication auth) {
        return auth != null ? auth.getName() : "ANONYMOUS";
    }

    private String extractRole(Authentication auth) {
        if (auth == null) return null;
        return auth.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
    }

    private String extractIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return ip != null ? ip.split(",")[0].trim() : request.getRemoteAddr();
    }

    private String extractSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? session.getId() : null;
    }
}