package vn.leoo.audit.log.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.leoo.audit.log.dto.AuditLogContext;

import java.util.*;

@Component
@RequiredArgsConstructor
public class AuditLogContextBuilder {

    private final ObjectMapper objectMapper;

    public Builder newLog(String module, String action, String rootType, String rootId) {
        return new Builder(module, action, rootType, rootId);
    }

    public class Builder {

        private final String module;
        private final String action;
        private final String rootType;
        private final String rootId;
        private String description;
        private AuditLogContext.ActorInfo actorInfo;
        private final List<AuditLogContext.DetailContext> details = new ArrayList<>();

        private Builder(String module, String action, String rootType, String rootId) {
            this.module = module;
            this.action = action;
            this.rootType = rootType;
            this.rootId   = rootId;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder actor(String username, String role, String ip) {
            this.actorInfo = AuditLogContext.ActorInfo.builder()
                    .username(username)
                    .role(role)
                    .ip(ip)
                    .build();
            return this;
        }

        // Tự động lấy actor từ SecurityContext
        public Builder actorFromSecurityContext(HttpServletRequest request) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.actorInfo = AuditLogContext.ActorInfo.builder()
                    .username(auth != null ? auth.getName() : "SYSTEM")
                    .role(extractRole(auth))
                    .ip(extractIp(request))
                    .sessionId(request.getSession(false) != null
                            ? request.getSession().getId() : null)
                    .build();
            return this;
        }

        // INSERT — chỉ có new
        public Builder addInsert(String objectType, String objectId, Object newObj) {
            details.add(AuditLogContext.DetailContext.builder()
                    .objectType(objectType)
                    .objectId(objectId)
                    .action("INSERT")
                    .changedData(buildInsertDiff(newObj))
                    .build());
            return this;
        }

        // UPDATE — tính diff old vs new
        public Builder addUpdate(String objectType, String objectId,
                                 Object oldObj, Object newObj) {
            Map<String, AuditLogContext.FieldChange> diff = computeDiff(oldObj, newObj);
            if (!diff.isEmpty()) {  // Chỉ log nếu thực sự có thay đổi
                details.add(AuditLogContext.DetailContext.builder()
                        .objectType(objectType)
                        .objectId(objectId)
                        .action("UPDATE")
                        .changedData(diff)
                        .build());
            }
            return this;
        }

        // DELETE — chỉ có old
        public Builder addDelete(String objectType, String objectId, Object oldObj) {
            details.add(AuditLogContext.DetailContext.builder()
                    .objectType(objectType)
                    .objectId(objectId)
                    .action("DELETE")
                    .changedData(buildDeleteDiff(oldObj))
                    .build());
            return this;
        }

        public AuditLogContext build() {
            return AuditLogContext.builder()
                    .traceId(MDC.get("traceId"))  // Lấy từ MDC của request
                    .module(module)
                    .action(action)
                    .rootType(rootType)
                    .rootId(rootId)
                    .description(description)
                    .actorInfo(actorInfo)
                    .details(details)
                    .build();
        }

        // Tính diff: chỉ lấy field thay đổi
        private Map<String, AuditLogContext.FieldChange> computeDiff(Object oldObj, Object newObj) {
            Map<String, Object> oldMap = objectMapper.convertValue(oldObj, Map.class);
            Map<String, Object> newMap = objectMapper.convertValue(newObj, Map.class);

            Map<String, AuditLogContext.FieldChange> diff = new LinkedHashMap<>();
            newMap.forEach((field, newVal) -> {
                Object oldVal = oldMap.get(field);
                if (!Objects.equals(oldVal, newVal)) {
                    diff.put(field, new AuditLogContext.FieldChange(oldVal, newVal));
                }
            });
            return diff;
        }

        private Map<String, AuditLogContext.FieldChange> buildInsertDiff(Object newObj) {
            Map<String, Object> newMap = objectMapper.convertValue(newObj, Map.class);
            Map<String, AuditLogContext.FieldChange> diff = new LinkedHashMap<>();
            newMap.forEach((field, val) ->
                    diff.put(field, new AuditLogContext.FieldChange(null, val)));
            return diff;
        }

        private Map<String, AuditLogContext.FieldChange> buildDeleteDiff(Object oldObj) {
            Map<String, Object> oldMap = objectMapper.convertValue(oldObj, Map.class);
            Map<String, AuditLogContext.FieldChange> diff = new LinkedHashMap<>();
            oldMap.forEach((field, val) ->
                    diff.put(field, new AuditLogContext.FieldChange(val, null)));
            return diff;
        }

        private String extractRole(Authentication auth) {
            if (auth == null) return null;
            return auth.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority())
                    .orElse(null);
        }

        private String extractIp(HttpServletRequest request) {
            String ip = request.getHeader("X-Forwarded-For");
            return ip != null ? ip.split(",")[0].trim() : request.getRemoteAddr();
        }
    }
}