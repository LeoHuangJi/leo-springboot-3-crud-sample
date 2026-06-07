package vn.leoo.shopli.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.leoo.entity.CitizenEntity;
import vn.leoo.shopli.dto.dlsfilter.BuildResult;
import vn.leoo.shopli.dto.dlsfilter.DynamicFilterEngine;
import vn.leoo.shopli.dto.dlsfilter.DynamicFilterRequest;

import java.util.List;
import java.util.Map;
@Repository
@RequiredArgsConstructor
public class CitizenDynamicRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final DynamicFilterEngine engine;

    public Page<CitizenEntity> search(
            DynamicFilterRequest req){

        BuildResult build =
                engine.build(req);

        String baseSql =
                """
                FROM CITIZEN
                """;

        if(!build.getWhere().isBlank()){

            baseSql +=
                    " WHERE "
                            + build.getWhere();

        }

        /*
         * query data
         */

        Query dataQuery =
                entityManager
                        .createNativeQuery(
                                "SELECT * " + baseSql,
                                CitizenEntity.class
                        );

        build.getParams()
                .forEach(
                        dataQuery::setParameter
                );

        dataQuery.setFirstResult(
                req.getPage()
                        * req.getSize()
        );

        dataQuery.setMaxResults(
                req.getSize()
        );

        List<CitizenEntity> data =
                dataQuery.getResultList();

        /*
         * query count
         */

        Query countQuery =
                entityManager
                        .createNativeQuery(
                                "SELECT COUNT(*) "
                                        + baseSql
                        );

        build.getParams()
                .forEach(
                        countQuery::setParameter
                );

        Number total =
                (Number)
                        countQuery
                                .getSingleResult();

        return new PageImpl<>(
                data,
                PageRequest.of(
                        req.getPage(),
                        req.getSize()
                ),
                total.longValue()
        );

    }

}