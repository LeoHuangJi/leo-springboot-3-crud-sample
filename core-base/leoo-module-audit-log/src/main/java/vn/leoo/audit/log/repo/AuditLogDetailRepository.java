package vn.leoo.audit.log.repo;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.leoo.audit.log.entity.AuditLogDetail;

import java.util.List;

@Repository
public interface AuditLogDetailRepository extends JpaRepository<AuditLogDetail, String> {

   /* List<AuditLogDetail> findByAuditLogId(Long auditLogId);

    List<AuditLogDetail> findByObjectTypeAndObjectId(String objectType, String objectId);

    @Query("""
        SELECT d FROM AuditLogDetail d
        WHERE d.objectType = :objectType
          AND d.objectId   = :objectId
        ORDER BY d.createdAt DESC
        """)
    List<AuditLogDetail> findHistoryByObject(
            @Param("objectType") String objectType,
            @Param("objectId") String objectId
    );*/
}