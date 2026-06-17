package vn.leoo.audit.log.builder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.leoo.audit.log.config.ActorInfoResolver;
import vn.leoo.audit.log.config.DiffOptions;
import vn.leoo.audit.log.context.AuditLogContext;
import vn.leoo.audit.log.util.Helper;
import vn.leoo.audit.log.util.ListDiffUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AuditLogContextBuilder {
    private final ActorInfoResolver actorInfoResolver;
    private final ObjectMapper objectMapper;
    private final ListDiffUtil listDiffUtil;
    public Builder newLog(String module, String action, String rootType, String rootId) {
        return new Builder(module, action, rootType, rootId,objectMapper, listDiffUtil, actorInfoResolver ).actor();
    }

    public static class Builder {
        private final ObjectMapper      objectMapper;
        private final ListDiffUtil      listDiffUtil;
        private final ActorInfoResolver actorInfoResolver;
        private final String module;
        private final String action;
        private final String rootType;
        private final String rootId;
        private String description;
        private AuditLogContext.ActorInfo actorInfo;
        private final List<AuditLogContext.DetailContext> details = new ArrayList<>();

        private Builder(String module, String action, String rootType, String rootId,  ObjectMapper objectMapper,
                        ListDiffUtil listDiffUtil,
                        ActorInfoResolver actorInfoResolve) {
            this.module = module;
            this.action = action;
            this.rootType = rootType;
            this.rootId   = rootId;
            this.objectMapper      = objectMapper;
            this.listDiffUtil      = listDiffUtil;
            this.actorInfoResolver = actorInfoResolve;
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
        public Builder actor() {
            this.actorInfo = actorInfoResolver.resolve();
            return this;
        }

        // Tự động lấy actor từ SecurityContext
        public Builder actorFromSecurityContext(
                jakarta.servlet.http.HttpServletRequest request) {
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

        // UPDATE — dùng options mặc định
        public Builder addUpdate(String objectType, String objectId,
                                 Object oldObj, Object newObj) {
            return addUpdate(objectType, objectId, oldObj, newObj, DiffOptions.defaults());
        }

        // UPDATE — dùng options tuỳ chỉnh
        public Builder addUpdate(String objectType, String objectId,
                                 Object oldObj, Object newObj, DiffOptions options) {
            Map<String, AuditLogContext.FieldChange> diff = computeDiff(oldObj, newObj, options);
            if (!diff.isEmpty()) {
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
        // LIST — dùng options mặc định
        public <T> Builder addList(String objectType,
                                   Function<T, String> idExtractor,
                                   List<T> oldList,
                                   List<T> newList) {
            return addList(objectType, idExtractor, oldList, newList, DiffOptions.defaults());
        }

        // LIST — dùng options tuỳ chỉnh
        public <T> Builder addList(String objectType,
                                   Function<T, String> idExtractor,
                                   List<T> oldList,
                                   List<T> newList,
                                   DiffOptions options) {

            ListDiffUtil.ListDiff<T> diff = listDiffUtil.diff(oldList, newList, idExtractor);

            diff.getToInsert().forEach(obj ->
                    addInsert(objectType, idExtractor.apply(obj), obj)
            );

            diff.getToUpdate().forEach(pair ->
                    addUpdate(objectType,
                            idExtractor.apply(pair.getNewObj()),
                            pair.getOldObj(),
                            pair.getNewObj(),
                            options)
            );

            diff.getToDelete().forEach(obj ->
                    addDelete(objectType, idExtractor.apply(obj), obj)
            );

            return this;
        }

        public AuditLogContext build() {
            return AuditLogContext.builder()
                    .traceId(MDC.get("traceId"))  // Lấy từ MDC của request
                    .module(module)
                    .action(action)
                    .actionTime(LocalDateTime.now())
                    .rootType(rootType)
                    .rootId(rootId)
                    .description(description)
                    .actorInfo(actorInfo)
                    .details(details)
                    .endpoint(Helper.getEndpoint())
                    .machine(Helper.getMachineId())
                    .userAgent(Helper.getUserAgent())
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
        private Map<String, AuditLogContext.FieldChange> computeDiff(Object oldObj, Object newObj,
                                                                     DiffOptions options) {
            Map<String, Object> oldMap = toFlatMap(oldObj);
            Map<String, Object> newMap = toFlatMap(newObj);
            Map<String, AuditLogContext.FieldChange> diff = new LinkedHashMap<>();

            newMap.forEach((field, newVal) -> {
                if (options.getIgnoreFields().contains(field)) return;
                if (options.isIgnoreNewField() && !oldMap.containsKey(field)) return;

                Object oldVal = oldMap.get(field);
                if (!isEqual(oldVal, newVal, options)) {
                    diff.put(field, new AuditLogContext.FieldChange(oldVal, newVal));
                }
            });

            return diff;
        }

        private boolean isEqual(Object oldVal, Object newVal, DiffOptions options) {
            if (Objects.equals(oldVal, newVal)) return true;

            if (options.isTreatNullAsEmpty()
                    && isNullOrEmpty(oldVal)
                    && isNullOrEmpty(newVal)) return true;

            if (options.isCompareAsString()) {
                return String.valueOf(oldVal).equals(String.valueOf(newVal));
            }

            return false;
        }

        private boolean isNullOrEmpty(Object val) {
            if (val == null) return true;
            if (val instanceof String s) return s.isBlank();
            if (val instanceof Collection<?> c) return c.isEmpty();
            return false;
        }

        private Map<String, Object> toFlatMap(Object obj) {
            try {
                return objectMapper.convertValue(obj, new TypeReference<>() {});
            } catch (Exception e) {
                return Collections.emptyMap();
            }
        }

        private String extractIp(HttpServletRequest request) {
            String ip = request.getHeader("X-Forwarded-For");
            return ip != null ? ip.split(",")[0].trim() : request.getRemoteAddr();
        }
    }
}