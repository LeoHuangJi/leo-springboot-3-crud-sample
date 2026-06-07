package vn.leoo.shopli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.leoo.entity.CitizenEntity;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, String>,QuerydslPredicateExecutor<CitizenEntity> {


}