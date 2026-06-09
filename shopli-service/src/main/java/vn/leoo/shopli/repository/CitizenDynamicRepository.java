package vn.leoo.shopli.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.leoo.common.util.ResultSetUtil;
import vn.leoo.entity.CitizenEntity;
import vn.leoo.shopli.dto.dlsfilter.BuildResult;
import vn.leoo.shopli.dto.dlsfilter.CitizenResultDTO;
import vn.leoo.shopli.dto.dlsfilter.DynamicFilterEngine;
import vn.leoo.shopli.dto.dlsfilter.DynamicFilterRequest;
import vn.leoo.shopli.dto.tutorial.TutorialResultDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CitizenDynamicRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final DynamicFilterEngine engine;

    public Page<CitizenResultDTO> search(
            DynamicFilterRequest req) throws ParseException {
        BuildResult build =
                engine.build(req);
        String baseSql =
                """
                        FROM CITIZEN
                        """;
        if (!build.getWhere().isBlank()) {
            baseSql +=
                    " WHERE "
                            + build.getWhere();
        }
        NativeQuery<?> dataQuery =
                entityManager
                        .createNativeQuery(
                                "SELECT * " + baseSql
                        )
                        .unwrap(NativeQuery.class);

        build.getParams()
                .forEach(
                        dataQuery::setParameter
                );
        if (Objects.equals(req.getPage(), 0)) {
            req.setPage(1);
        }
        int skip = (req.getPage()-1)
                * req.getSize();

        dataQuery.setFirstResult(skip);
        dataQuery.setMaxResults(req.getSize());
        dataQuery.setTupleTransformer(
                AliasToEntityMapResultTransformer.INSTANCE
        );
        List<Map<String, Object>> rows =
                (List<Map<String, Object>>) dataQuery.getResultList();
        List<CitizenResultDTO> lst = ResultSetUtil.resultSetToList(rows, CitizenResultDTO.class);
        NativeQuery countQuery =
                entityManager
                        .createNativeQuery(
                                "SELECT COUNT(*) "
                                        + baseSql
                        ).unwrap(NativeQuery.class);
        build.getParams()
                .forEach(
                        countQuery::setParameter
                );
        Number total =
                (Number)
                        countQuery
                                .getSingleResult();
        return new PageImpl<>(
                lst,
                PageRequest.of(
                        req.getPage(),
                        req.getSize()
                ),
                total.longValue()
        );

    }

}