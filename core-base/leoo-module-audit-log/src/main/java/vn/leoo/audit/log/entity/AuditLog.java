package vn.leoo.audit.log.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "trace_id", nullable = false)
    private String traceId;

    @Column(name = "module", nullable = false)
    private String module;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "actor_info", columnDefinition = "CLOB")
    private String actorInfo;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "auditLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuditLogDetail> details = new ArrayList<>();
}
