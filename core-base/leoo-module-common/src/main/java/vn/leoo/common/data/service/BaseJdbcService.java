/*
 * package vn.leoo.common.data.service;
 * 
 * import java.io.Serializable; import java.util.List; import java.util.Map;
 * 
 * import org.springframework.jdbc.core.namedparam.SqlParameterSource; import
 * org.springframework.jdbc.core.simple.SimpleJdbcCall;
 * 
 * import vn.leoo.common.data.SearchCriteria; import
 * vn.leoo.common.dto.ListResponse; import vn.leoo.common.dto.PaginationDTO;
 * import vn.leoo.common.exception.BaseException;
 * 
 * public interface BaseJdbcService<T, ID extends Serializable> { List<T>
 * findAll(PaginationDTO paginationDTO) throws Exception;
 * 
 * List<?> findByNativeQuery(String sql, final Object[] params, PaginationDTO
 * paginationDTO, Class<?> clazz) throws Exception;
 * 
 * Long countByNativeQuery(String sql, final Object[] params) throws Exception;
 * 
 * List<T> findByQuery(String sql, final Object[] params, PaginationDTO
 * paginationDTO) throws Exception;
 * 
 * Long countByQuery(String sql, final Object[] params) throws Exception;
 * 
 * List<T> findByCriteriaQuery(SearchCriteria cri, PaginationDTO paginationDTO)
 * throws Exception;
 * 
 * Long countByCriteriaQuery(SearchCriteria cri) throws Exception;
 * 
 * <S> S FindById(String sql, Class<S> clazz, Object id) throws BaseException;
 * 
 * <S> S FindById(String sql, S entity, Object id) throws BaseException;
 * 
 * <S> List<S> FindByExample(String sql, S entity, Object... objs) throws
 * BaseException;
 * 
 * <S> List<S> FindByExample(String sql, Class<S> clazz, Object... objs) throws
 * BaseException;
 * 
 * List<Map<String, Object>> FindByExample(String sql, Object... objs) throws
 * BaseException;
 * 
 * <S> List<S> FindAll(String sql, S entity) throws BaseException;
 * 
 * <S> List<S> FindAll(String sql, Class<S> clazz) throws BaseException;
 * 
 * void update(String sql, Object... obj) throws BaseException;
 * 
 * Map<String,Object> callStoreProcedure(String catalogName, String spName,
 * Object... params) throws BaseException;
 * 
 * Object callStoreProcedure(String catalogName, String spName, Map<String,
 * Object> mapParams) throws BaseException;
 * 
 * Object callStoreProcedureWithAList(String catalogName, String spName, String
 * resultMapping, Map<String, Object> mapParams) throws BaseException;
 * 
 * Object callStoreProcedureWithAListNCount(String catalogName, String spName,
 * String resultMapping, Map<String, Object> mapParams) throws BaseException;
 * 
 * Map<String,Object> callFunction(String catalogName, String fnName, Object...
 * params) throws BaseException;
 * 
 * Map<String,Object> callFunction(String catalogName, String fnName,
 * Map<String, Object> params) throws BaseException;
 * 
 * int insert(String tableName, Map<String, Object> columns, String genColumn)
 * throws Exception; ListResponse findAll(String catalogName, String spName,
 * Map<String, Object> params, Class<?> responseType) throws BaseException;
 * Object saveChange(String catalogName, String spName, Map<String, Object>
 * params, Class<?> responseType) throws BaseException; Object findById(String
 * catalogName, String spName, Map<String, Object> params, Class<?>
 * responseType) throws BaseException; Object getObject(String sqlText,
 * Object... params) throws BaseException; Long getLongValue(String sqlText,
 * Object... params) throws BaseException;
 * 
 * }
 */