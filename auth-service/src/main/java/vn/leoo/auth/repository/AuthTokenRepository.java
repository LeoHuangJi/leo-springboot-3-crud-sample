package vn.leoo.auth.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.leoo.auth.entity.AuthTokenEntity;

public interface AuthTokenRepository extends JpaRepository<AuthTokenEntity, String> {

	Optional<AuthTokenEntity> findByRefreshToken(String refreshToken);

	@Query("SELECT a FROM AuthTokenEntity a WHERE a.refreshToken = :token AND a.revoked = false AND a.expiresAt > :now")
	Optional<AuthTokenEntity> findValidToken(@Param("token") String token, @Param("now") Instant now);

	void deleteAllByUserId(String userId);
	
	
	@Query(value = """
		    SELECT *
		    FROM (
		        SELECT t.*,
		               ROW_NUMBER() OVER (
		                   PARTITION BY t.user_agent, t.ip_address
		                   ORDER BY t.issued_at DESC
		               ) AS rn
		        FROM auth_tokens t
		        WHERE t.user_id = :userId
		          AND t.revoked = 0
		          AND t.expires_at > SYSTIMESTAMP
		    )
		    WHERE rn = 1
		    ORDER BY issued_at DESC
		    """, nativeQuery = true)
	List<AuthTokenEntity> findLatestSessionsPerDeviceRaw(@Param("userId") String userId);
}
