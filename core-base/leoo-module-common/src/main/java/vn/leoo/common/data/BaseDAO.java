/*
 * package vn.leoo.common.data;
 * 
 * import java.io.Serializable; import java.lang.reflect.Constructor; import
 * java.lang.reflect.Field; import java.util.ArrayList; import java.util.List;
 * import java.util.Objects;
 * 
 * import javax.persistence.Column; import javax.persistence.EntityManager;
 * import javax.persistence.Query; import javax.persistence.TypedQuery; import
 * javax.persistence.criteria.CriteriaBuilder; import
 * javax.persistence.criteria.CriteriaQuery; import
 * javax.persistence.criteria.Expression; import
 * javax.persistence.criteria.Predicate; import javax.persistence.criteria.Root;
 * 
 * import vn.leoo.common.data.mapper.JpaResultMapper; import
 * vn.leoo.common.dto.PaginationDTO; import vn.leoo.common.util.PagingUtils;
 * import vn.leoo.common.util.StringUtils;
 * 
 * public class BaseDAO<T, ID extends Serializable> { private Class<T>
 * _entityClass; private EntityManager _entityManager;
 * 
 * public BaseDAO(EntityManager entityManager, Class<T> entityClass) {
 * this._entityManager = entityManager; this._entityClass = entityClass; }
 * 
 * protected Class<T> getEntityClass() { return _entityClass; }
 * 
 * protected CriteriaBuilder criteriaBuilder() { return
 * _entityManager.getCriteriaBuilder(); }
 * 
 * public List<T> findAll(PaginationDTO paginationDTO) throws Exception {
 * PagingUtils pageUtil = new PagingUtils(paginationDTO);
 * 
 * CriteriaBuilder cb = criteriaBuilder(); CriteriaQuery<T> cq =
 * cb.createQuery(_entityClass); Root<T> root = cq.from(_entityClass);
 * 
 * if (StringUtils.isNotEmpty(pageUtil.getSortField())) { String order =
 * pageUtil.getOrder(); order = order == null ? "asc" : order.toLowerCase();
 * cq.orderBy(Objects.equals("desc", order) ?
 * cb.desc(root.get(pageUtil.getSortField())) :
 * cb.asc(root.get(pageUtil.getSortField()))); } cq.select(root);
 * 
 * TypedQuery<T> query = _entityManager.createQuery(cq); if
 * (pageUtil.getPageSize() > 0) { query.setFirstResult(pageUtil.getOffset());
 * query.setMaxResults(pageUtil.getPageSize()); } return
 * (List<T>)query.getResultList(); }
 * 
 * public List<?> findByNativeQuery(String sql, final Object[] params,
 * PaginationDTO paginationDTO, Class<?> clazz) throws Exception { PagingUtils
 * pageUtil = new PagingUtils(paginationDTO);
 * if(StringUtils.isNotEmpty(pageUtil.getSortField())) { sql += " order by " +
 * pageUtil.getSortField() + " " + pageUtil.getOrder(); } Query query =
 * _entityManager.createNativeQuery(sql); for (int i=0; params!=null &&
 * i<params.length; i++) { query.setParameter(i+1, params[i]); } if
 * (pageUtil.getPageSize() > 0) { query.setFirstResult(pageUtil.getOffset());
 * query.setMaxResults(pageUtil.getPageSize()); } JpaResultMapper
 * jpaResultMapper = new JpaResultMapper(); return jpaResultMapper.list(query,
 * clazz); }
 * 
 * public Long countByNativeQuery(String sql, final Object[] params) throws
 * Exception { Query query = _entityManager.createNativeQuery(sql); for (int
 * i=0; params!=null && i<params.length; i++) { query.setParameter(i+1,
 * params[i]); } return (Long)query.getSingleResult(); }
 * 
 * public List<T> findByQuery(String sql, final Object[] params, PaginationDTO
 * paginationDTO) throws Exception { PagingUtils pageUtil = new
 * PagingUtils(paginationDTO);
 * if(StringUtils.isNotEmpty(pageUtil.getSortField())) { sql += " order by " +
 * pageUtil.getSortField() + " " + pageUtil.getOrder(); } TypedQuery<T> query =
 * _entityManager.createQuery(sql, _entityClass); if (params != null) { for (int
 * i = 0; i < params.length; i++) { query.setParameter(i + 1, params[i]); } } if
 * (pageUtil.getPageSize() > 0) { query.setFirstResult(pageUtil.getOffset());
 * query.setMaxResults(pageUtil.getPageSize()); }
 * 
 * return query.getResultList(); }
 * 
 * public Long countByQuery(String sql, final Object[] params) throws Exception
 * { TypedQuery<Long> query = _entityManager.createQuery(sql, Long.class); for
 * (int i=0; params!=null && i<params.length; i++) { query.setParameter(i+1,
 * params[i]); } return query.getSingleResult(); }
 * 
 * public List<T> findByCriteriaQuery(SearchCriteria cri, PaginationDTO
 * paginationDTO) throws Exception{ PagingUtils pageUtil = new
 * PagingUtils(paginationDTO); List<T> retval = new ArrayList<>();
 * CriteriaBuilder builder = criteriaBuilder(); CriteriaQuery<T> criteria =
 * builder.createQuery(_entityClass); Root<T> root =
 * criteria.from(_entityClass); Predicate predicateAll = getPredicate(cri,
 * builder, root); if (predicateAll != null) { criteria.where(predicateAll); }
 * //Them phan order List<javax.persistence.criteria.Order> orderList = new
 * ArrayList<>(); if (cri.getOrderList() != null && cri.getOrderList().size() >
 * 0) { for (int k = 0; k < cri.getOrderList().size(); k++) {
 * javax.persistence.criteria.Order condition = null; SearchOperation oper =
 * cri.getOrderOperation().get(k); switch (oper) { case ORDER_DESC: condition =
 * builder.desc(root.get((String) cri.getOrderList().get(k))); break; default:
 * condition = builder.asc(root.get((String) cri.getOrderList().get(k))); break;
 * } orderList.add(condition); } criteria.orderBy(orderList); }
 * criteria.select(root);
 * 
 * TypedQuery<T> query = _entityManager.createQuery(criteria); if
 * (pageUtil.getPageSize() > 0) { query.setFirstResult(pageUtil.getOffset());
 * query.setMaxResults(pageUtil.getPageSize()); } retval =
 * query.getResultList(); return retval; }
 * 
 * public Long countByCriteriaQuery(SearchCriteria cri) throws Exception{ Long
 * retval = 0L; CriteriaBuilder builder = criteriaBuilder(); CriteriaQuery<Long>
 * criteria = builder.createQuery(Long.class); Root<T> root =
 * criteria.from(_entityClass); Predicate predicateAll =
 * getPredicate(cri,builder,root); if (predicateAll != null) {
 * criteria.where(predicateAll); } criteria.select(builder.count(root));
 * //criteria.select(root); retval =
 * _entityManager.createQuery(criteria).getSingleResult();
 * 
 * return retval; }
 * 
 * private Predicate getPredicate(SearchCriteria cri, CriteriaBuilder builder,
 * Root<T> root) throws Exception{ Predicate predicateAll = null; if (cri ==
 * null) { throw new NullPointerException("input value is null"); } String
 * strColumNameDb = "~"; Class<?> clazz = cri.getClass(); Constructor<?> ctor =
 * clazz.getConstructor(); Object obj = ctor.newInstance();
 * 
 * for (Field field : obj.getClass().getDeclaredFields()) {
 * field.setAccessible(true); String name = field.getName(); Column a =
 * field.getAnnotation(Column.class); if (a != null) strColumNameDb =
 * strColumNameDb + name + "~"; } List<String> pName = cri.getPropertyName();
 * List<Object> pValue = cri.getPropertyValue(); List<SearchOperation> operation
 * = cri.getOperation(); int size = pName.size(); List<Predicate> criteriaList =
 * new ArrayList<>(); Predicate predicate = null; for (int i = 0; i < size; i++)
 * { Predicate condition = null; String name = (String) pName.get(i); if
 * (!StringUtils.isNull(strColumNameDb) && strColumNameDb.indexOf("~" + name +
 * "~") == -1) { continue; } if (name.equals("serialVersionUID")) { continue; }
 * Object value = pValue.get(i); if (StringUtils.isNull(String.valueOf(value))
 * || String.valueOf(value).equals("null")) { continue; } SearchOperation oper =
 * operation.get(i); Expression<String> filterKeyExp =
 * root.get(name).as(String.class); switch (oper) { case OP_MATCH: condition =
 * builder.like(filterKeyExp, "%" + String.valueOf(value) + "%"); break;
 * default: condition = builder.equal(filterKeyExp, String.valueOf(value));
 * break; } criteriaList.add(condition); } if (criteriaList != null &&
 * criteriaList.size() > 0) { if (cri.isKeyWord()) predicate =
 * builder.or(criteriaList.toArray(new Predicate[0])); else predicate =
 * builder.and(criteriaList.toArray(new Predicate[0])); } predicateAll =
 * predicate;
 * 
 * return predicateAll; } }
 */