package vn.leoo.audit.log.repo;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.leoo.audit.log.entity.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {

    /*List<AuditLog> findByTraceId(String traceId);

    List<AuditLog> findByModuleAndAction(String module, String action);*/

 /*   @Query("""
        SELECT l FROM AuditLog l
        WHERE l.module = :module
          AND l.createdAt BETWEEN :from AND :to
        ORDER BY l.createdAt DESC
        """)
    List<AuditLog> findByModuleAndDateRange(
            @Param("module") String module,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );*/
}