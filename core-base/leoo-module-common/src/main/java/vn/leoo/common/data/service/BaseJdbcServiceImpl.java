/*
 * package vn.leoo.common.data.service;
 * 
 * import java.io.Serializable; import java.math.BigDecimal; import
 * java.sql.SQLException; import java.text.SimpleDateFormat; import
 * java.util.Date; import java.util.List; import java.util.Map;
 * 
 * import javax.persistence.EntityManager; import javax.sql.DataSource;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.jdbc.core.namedparam.MapSqlParameterSource; import
 * org.springframework.jdbc.core.namedparam.SqlParameterSource; import
 * org.springframework.jdbc.core.simple.SimpleJdbcCall; import
 * org.springframework.jdbc.datasource.DriverManagerDataSource; import
 * org.springframework.util.CollectionUtils;
 * 
 * import vn.leoo.common.constants.ErrorCode; import
 * vn.leoo.common.constants.Constants.ORACLE_PO; import
 * vn.leoo.common.data.BaseDAO; import vn.leoo.common.data.BaseJdbcDAO; import
 * vn.leoo.common.data.SearchCriteria; import vn.leoo.common.dto.ListResponse;
 * import vn.leoo.common.dto.PaginationDTO; import
 * vn.leoo.common.exception.BaseException; import
 * vn.leoo.common.util.ColumnRowMapper;
 * 
 * 
 * //@Transactional public class BaseJdbcServiceImpl<T, ID extends Serializable>
 * implements BaseJdbcService<T, ID> { private BaseDAO<T, ID> baseDAO; private
 * BaseJdbcDAO baseJdbcDAO;
 * 
 * @Autowired private DataSource dataSource;
 * 
 * public BaseJdbcServiceImpl(DataSource dataSource, EntityManager
 * entityManager, Class<T> entityClass){ baseJdbcDAO = new
 * BaseJdbcDAO(dataSource); baseDAO = new BaseDAO<>(entityManager, entityClass);
 * }
 * 
 * @Override public List<T> findAll(PaginationDTO paginationDTO) throws
 * Exception { try { return baseDAO.findAll(paginationDTO); } catch (Exception
 * e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public List<?> findByNativeQuery(String sql, final Object[] params,
 * PaginationDTO paginationDTO, Class<?> clazz) throws Exception{ try { return
 * baseDAO.findByNativeQuery(sql, params, paginationDTO, clazz); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public Long countByNativeQuery(String sql, final Object[] params)
 * throws Exception { try { return baseDAO.countByNativeQuery(sql, params); }
 * catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public List<T> findByQuery(String sql, final Object[] params,
 * PaginationDTO paginationDTO) throws Exception { try { return
 * baseDAO.findByQuery(sql, params, paginationDTO); } catch (Exception e) {
 * throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public Long countByQuery(String sql, final Object[] params) throws
 * Exception { try { return baseDAO.countByQuery(sql, params); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public List<T> findByCriteriaQuery(SearchCriteria cri,
 * PaginationDTO paginationDTO) throws Exception { try { return
 * baseDAO.findByCriteriaQuery(cri, paginationDTO); } catch (Exception e) {
 * throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public Long countByCriteriaQuery(SearchCriteria cri) throws
 * Exception { try { return baseDAO.countByCriteriaQuery(cri); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public <S> S FindById(String sql, Class<S> clazz, Object id) throws
 * BaseException{ try { return baseJdbcDAO.findById(sql, clazz, id); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public <S> S FindById(String sql, S entity, Object id) throws
 * BaseException{ try { return baseJdbcDAO.findById(sql, entity, id); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public <S> List<S> FindByExample(String sql, S entity, Object...
 * objs) throws BaseException{ try { return baseJdbcDAO.findByExample(sql,
 * entity, objs); } catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public <S> List<S> FindByExample(String sql, Class<S> clazz,
 * Object... objs) throws BaseException{ try { return
 * baseJdbcDAO.findByExample(sql, clazz, objs); } catch (Exception e) { throw
 * new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public List<Map<String, Object>> FindByExample(String sql,
 * Object... objs) throws BaseException{ try { return
 * baseJdbcDAO.findByExample(sql, objs); } catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public <S> List<S> FindAll(String sql, S entity) throws
 * BaseException{ try { return baseJdbcDAO.findAll(sql, entity); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public <S> List<S> FindAll(String sql, Class<S> clazz) throws
 * BaseException{ try { return baseJdbcDAO.findAll(sql, clazz); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override // @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
 * Exception.class) public void update(String sql, Object... obj) throws
 * BaseException{ try { baseJdbcDAO.update(sql, obj); } catch (Exception e) {
 * throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public Map<String,Object> callStoreProcedure(String catalogName,
 * String spName, Object... params) throws BaseException { try{ return
 * baseJdbcDAO.callStoreProcedure(catalogName, spName, params); }
 * catch(Exception e){ throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public Object callStoreProcedure(String catalogName, String spName,
 * Map<String, Object> mapParams) throws BaseException { try { return
 * baseJdbcDAO.callStoreProcedure(catalogName, spName, mapParams); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public Object callStoreProcedureWithAList(String catalogName,
 * String spName, String resultMapping, Map<String, Object> mapParams) throws
 * BaseException { try { return
 * baseJdbcDAO.callStoreProcedureWithAList(catalogName, spName, resultMapping,
 * mapParams); } catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public Object callStoreProcedureWithAListNCount(String catalogName,
 * String spName, String resultMapping, Map<String, Object> mapParams) throws
 * BaseException { try { return
 * baseJdbcDAO.callStoreProcedureWithAListNCount(catalogName, spName,
 * resultMapping, mapParams); } catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public Map<String,Object> callFunction(String catalogName, String
 * fnName, Object... params) throws BaseException { try { return
 * baseJdbcDAO.callFunction(catalogName, fnName, params); } catch (Exception e)
 * { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public Map<String,Object> callFunction(String catalogName, String
 * fnName, Map<String, Object> params) throws BaseException { try { return
 * baseJdbcDAO.callFunction(catalogName, fnName, params); } catch (Exception e)
 * { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public int insert(String tableName, Map<String, Object> columns,
 * String genColumn) throws Exception { try { return
 * baseJdbcDAO.insert(tableName, columns, genColumn); } catch (Exception e) {
 * throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public ListResponse findAll(String catalogName, String spName,
 * Map<String, Object> params, Class<?> responseType) throws BaseException { try
 * { return baseJdbcDAO.findAll(catalogName, spName, params, responseType); }
 * catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public Object saveChange(String catalogName, String spName,
 * Map<String, Object> params, Class<?> responseType) throws BaseException { try
 * { return baseJdbcDAO.saveChange( catalogName, spName, params, responseType);
 * } catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public Object findById(String catalogName, String spName,
 * Map<String, Object> params, Class<?> responseType) throws BaseException { try
 * { return baseJdbcDAO.findById( catalogName, spName, params, responseType); }
 * catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * @Override public Object getObject(String sqlText, Object... params) throws
 * BaseException { try { return baseJdbcDAO.getObject(sqlText, params); } catch
 * (Exception e) { throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage()); } }
 * 
 * @Override public Long getLongValue(String sqlText, Object... params) throws
 * BaseException { try { return baseJdbcDAO.getLongValue(sqlText, params); }
 * catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * 
 * 
 * 
 * }
 */