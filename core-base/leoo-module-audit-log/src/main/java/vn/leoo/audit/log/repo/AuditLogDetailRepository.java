package vn.leoo.audit.log.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.leoo.audit.log.entity.AuditLogDetail;

@Repository
public interface AuditLogDetailRepository extends JpaRepository<AuditLogDetail, String> {

}