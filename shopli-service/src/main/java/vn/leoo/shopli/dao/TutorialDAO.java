
package vn.leoo.shopli.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.constants.Constants;
import vn.leoo.common.dto.PageResponse;
import vn.leoo.common.exception.ServiceException;
import vn.leoo.common.util.ResultSetUtil;
import vn.leoo.entity.QTutorialEntity;
import vn.leoo.entity.TutorialEntity;
import vn.leoo.shopli.dto.tutorial.TutorialFilterDTO;
import vn.leoo.shopli.dto.tutorial.TutorialQuerydslResultDTO;
import vn.leoo.shopli.dto.tutorial.TutorialResultDTO;

@Repository
public class TutorialDAO {

	private EntityManager entityManager;
	public static final String PACKAGE_NAME = "PKG_TUTORIAL.";

	public TutorialDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	/*
	 * StringBuilder simple
	 */
	public Page<TutorialEntity> searchUsers(String status, String username, LocalDateTime createdFrom,
			LocalDateTime createdTo, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT * FROM TUTORIALS WHERE 1=1 ");
		StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM TUTORIALS WHERE 1=1 ");
		Map<String, Object> params = new HashMap<>();

		if (status != null) {
			sql.append("AND status = :status ");
			countSql.append("AND status = :status ");
			params.put("status", status);
		}
		if (username != null) {
			sql.append("AND username ILIKE :username ");
			countSql.append("AND username ILIKE :username ");
			params.put("username", "%" + username + "%");
		}

		if (createdFrom != null) {
			sql.append("AND created_at >= :createdFrom ");
			countSql.append("AND created_at >= :createdFrom ");
			params.put("createdFrom", createdFrom);
		}

		if (createdTo != null) {
			sql.append("AND created_at <= :createdTo ");
			countSql.append("AND created_at <= :createdTo ");
			params.put("createdTo", createdTo);
		}

		sql.append("ORDER BY created_at DESC ");
		Query query = entityManager.createNativeQuery(sql.toString(), TutorialEntity.class);
		Query countQuery = entityManager.createNativeQuery(countSql.toString());

		params.forEach((k, v) -> {
			query.setParameter(k, v);
			countQuery.setParameter(k, v);
		});

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<TutorialEntity> users = query.getResultList();
		long total = ((Number) countQuery.getSingleResult()).longValue();
		return new PageImpl<>(users, pageable, total);
	}
	/*
	 * Querydsl simple
	 */
	public PageResponse<TutorialQuerydslResultDTO> searchQuerydsl(TutorialFilterDTO filter) {

		QTutorialEntity t = QTutorialEntity.tutorialEntity;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		BooleanBuilder predicate = new BooleanBuilder();

		if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
			predicate.and(t.title.containsIgnoreCase(filter.getTitle()));
		}

		if (filter.getPublished() != null) {
			predicate.and(t.published.eq(true));
		}

		List<TutorialQuerydslResultDTO> content = queryFactory
				.select(Projections.constructor(TutorialQuerydslResultDTO.class, t.id, t.title, t.code, t.description))
				.from(t).where(predicate).offset(filter.getPage()).limit(filter.getSize()).orderBy(t.createdDate.desc())
				.fetch();

		long totals = queryFactory.select(t.count()).from(t).where(predicate).fetchOne();

		return new PageResponse<TutorialQuerydslResultDTO>(content, totals);

	}

	/*
	 * Package simple
	 */
	public PageResponse<TutorialResultDTO> search(TutorialFilterDTO filter) throws SQLException {
		try {

			String orderByQueryBuilder = null;

			if (StringUtils.isNotBlank(filter.getSortColumn()) || StringUtils.isNotBlank(filter.getSortDirection())) {
				Map<String, String> sortColumnMap = Map.of("id", "t.ID", "title", "t.TITLE", "description",
						"t.DESCRIPTION", "published", "t.PUBLISHED", "code", "t.CODE", "categoryId", "t.CATEGORY_ID",
						"parentId", "t.PARENT_ID", "createdDate", "t.CREATEDDATE");

				orderByQueryBuilder = vn.leoo.common.util.StringUtils.buildOrderByClause(filter.getSortColumn(),
						filter.getSortDirection(), sortColumnMap);
			}

			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(PACKAGE_NAME + "search_tutorials")
					.registerStoredProcedureParameter("pi_keyword", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("pi_title", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("pi_description", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("pi_published", Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter("pi_order_by_query_builder", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("pi_page", Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter("pi_size", Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(Constants.ORACLE_PO.REF_CURSOR, void.class,
							ParameterMode.REF_CURSOR)
					.registerStoredProcedureParameter(Constants.ORACLE_PO.TOTAL, Long.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(Constants.ORACLE_PO.ERROR_MSG, String.class, ParameterMode.OUT);
			query.setParameter("pi_keyword", filter.getKeyword());
			query.setParameter("pi_title", filter.getTitle());
			query.setParameter("pi_description", filter.getDescription());
			query.setParameter("pi_published", filter.getKeyword());
			query.setParameter("pi_order_by_query_builder", orderByQueryBuilder);
			query.setParameter("pi_page", filter.getPage());
			query.setParameter("pi_size", filter.getSize());

			String errorMsg = (String) query.getOutputParameterValue(Constants.ORACLE_PO.ERROR_MSG);

			if (errorMsg != null && !errorMsg.trim().isEmpty()) {
				throw new ServiceException(AppErrorCode.INTERNAL_ERROR, errorMsg);
			}

			long totals = (long) query.getOutputParameterValue(Constants.ORACLE_PO.TOTAL);

			ResultSet rs = (ResultSet) query.getOutputParameterValue(Constants.ORACLE_PO.REF_CURSOR);
			List<TutorialResultDTO> lst = ResultSetUtil.resultSetToList(rs, TutorialResultDTO.class);
			rs.close();
			return new PageResponse<TutorialResultDTO>(lst, totals);

		} catch (Exception e) {
			throw new ServiceException(AppErrorCode.INTERNAL_ERROR, e.getMessage());
		} finally {

		}
	}
}
