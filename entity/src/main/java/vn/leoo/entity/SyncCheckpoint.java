package vn.leoo.entity;

import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "sync_checkpoint")
@Data // Tự động generate getter, setter, toString, equals, hashCode
public class SyncCheckpoint {

    @Id
    @Column(name = "job_name", length = 100)
    private String jobName;

    @Column(name = "last_sort_value", length = 500)
    private String lastSortValue;
    @Column(name = "pit_id", length = 500)
    private String pitId;

    @Column(name = "total_synced", precision = 19)
    private Long totalSynced = 0L;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SyncCheckpoint() {

    }
}