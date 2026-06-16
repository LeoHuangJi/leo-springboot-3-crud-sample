package vn.leoo.audit.log.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log_detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDetail {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_log_id", nullable = false)
    private AuditLog auditLog;

    @Column(name = "object_type")
    private String objectType;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "action")
    private String action;  // INSERT / UPDATE / DELETE

    @Column(name = "changed_data", columnDefinition = "CLOB")
    private String changedData;  // JSON: {field: {old, new}}

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
