package vn.leoo.audit.log.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

// Dùng để truyền data giữa các layer, tránh lazy load issue khi async
@Getter
@Builder
public class AuditLogContext {

    private String traceId;
    private String module;
    private String action;
    private String description;
    private ActorInfo actorInfo;
    private List<DetailContext> details;

    @Getter
    @Builder
    public static class ActorInfo {
        private String username;
        private String role;
        private String ip;
        private String sessionId;
    }

    @Getter
    @Builder
    public static class DetailContext {
        private String objectType;
        private String objectId;
        private String action;         // INSERT / UPDATE / DELETE
        private Map<String, FieldChange> changedData;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FieldChange {
        private Object oldValue;
        private Object newValue;
    }
}