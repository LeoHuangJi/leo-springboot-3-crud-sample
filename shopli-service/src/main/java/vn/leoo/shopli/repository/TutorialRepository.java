package vn.leoo.shopli.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import vn.leoo.entity.TutorialEntity;

import java.sql.Timestamp;

public interface TutorialRepository extends JpaRepository<TutorialEntity, String>,QuerydslPredicateExecutor<TutorialEntity> {
	List<TutorialEntity> findByPublished(boolean published);

	List<TutorialEntity> findByTitleContaining(String title);

	@Query(value = "SELECT count(1) from TUTORIALS where code=?", nativeQuery = true)
	long findByCode(@Param("code") String code);

	@Query(value = "SELECT * from TUTORIALS where CREATEDDATE >=:createdDate and CREATEDDATE<=:createdDateTo OFFSET :skip ROWS FETCH NEXT :take ROWS ONLY ", nativeQuery = true)
	List<TutorialEntity> findAlll(Timestamp createdDate, Timestamp createdDateTo, int skip, int take);

	@Query(value = "SELECT * FROM TUTORIALS WHERE title = :Title ORDER BY created_at DESC LIMIT :limit OFFSET :offset", countQuery = "SELECT count(*) FROM TUTORIALS WHERE title = :title", nativeQuery = true)
	Page<TutorialEntity> findNativeTitle(@Param("Title") String title, Pageable pageable);
}
