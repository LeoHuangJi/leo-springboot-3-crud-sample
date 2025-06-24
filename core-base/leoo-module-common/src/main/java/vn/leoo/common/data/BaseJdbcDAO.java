/*
 * package vn.leoo.common.data;
 * 
 * import org.springframework.beans.factory.InitializingBean; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.dao.DataAccessException; import
 * org.springframework.jdbc.core.BeanPropertyRowMapper; import
 * org.springframework.jdbc.core.JdbcTemplate; import
 * org.springframework.jdbc.core.namedparam.MapSqlParameterSource; import
 * org.springframework.jdbc.core.simple.SimpleJdbcCall; import
 * org.springframework.jdbc.core.simple.SimpleJdbcInsert; import
 * org.springframework.jdbc.datasource.DriverManagerDataSource; import
 * org.springframework.util.Assert;
 * 
 * import com.google.gson.Gson; import
 * com.nimbusds.oauth2.sdk.util.CollectionUtils;
 * 
 * import vn.leoo.common.constants.ErrorCode; import
 * vn.leoo.common.constants.Constants.ORACLE_PO; import
 * vn.leoo.common.dto.ListResponse; import
 * vn.leoo.common.exception.BaseException; import
 * vn.leoo.common.util.ColumnRowMapper;
 * 
 * import java.math.BigDecimal; import java.sql.SQLException; import
 * java.text.SimpleDateFormat; import java.util.ArrayList; import
 * java.util.Date; import java.util.HashMap; import java.util.List; import
 * java.util.Map; import java.util.logging.Logger;
 * 
 * import javax.sql.DataSource;
 * 
 * 
 * public class BaseJdbcDAO implements InitializingBean {
 * 
 * private static JdbcTemplate jdbcTemp; private static SimpleJdbcCall jdbcCall;
 * private static SimpleJdbcInsert JdbcInsert;
 * 
 * private DataSource dataSource;
 * 
 * public BaseJdbcDAO() { }
 * 
 * public BaseJdbcDAO(DataSource dataSource) { this.dataSource = dataSource; }
 * 
 * public DataSource getDataSource() { return dataSource; }
 * 
 * public void setDataSource(DataSource dataSource) { this.dataSource =
 * dataSource; }
 * 
 * protected synchronized JdbcTemplate getJdbcTemplate() { //if (jdbcTemp ==
 * null) { // Assert.notNull(dataSource, "dataSource must not be null");
 * jdbcTemp = new JdbcTemplate(dataSource); // } return jdbcTemp; }
 * 
 * protected synchronized SimpleJdbcCall getJdbcCall() { //if (jdbcCall == null)
 * { // Assert.notNull(dataSource, "dataSource must not be null"); jdbcCall =
 * new SimpleJdbcCall(dataSource); //} //List<SimpleJdbcCall> db= new
 * ArrayList<>(); //for(int i=1;i<=500;i++) { //db.add(new
 * SimpleJdbcCall(dataSource)) ; // System.out.println(db.size()); // }
 * 
 * return jdbcCall; }
 * 
 * protected synchronized SimpleJdbcInsert getJdbcInsert() { if (JdbcInsert ==
 * null) { Assert.notNull(dataSource, "dataSource must not be null"); JdbcInsert
 * = new SimpleJdbcInsert(dataSource); } return JdbcInsert; }
 * 
 * @Override public void afterPropertiesSet() { getJdbcTemplate(); }
 * 
 * @SuppressWarnings("unchecked") public <T> T findById(String sql, T entity,
 * Object id) throws Exception{ entity =
 * this.getJdbcTemplate().queryForObject(sql, new
 * BeanPropertyRowMapper<>((Class<T>) entity.getClass()), id);
 * 
 * return entity; }
 * 
 * public <T> T findById(String sql, Class<T> clazz, Object id) throws
 * Exception{ return this.getJdbcTemplate().queryForObject(sql,
 * BeanPropertyRowMapper.newInstance(clazz), id); }
 * 
 * @SuppressWarnings("unchecked") public <T> List<T> findByExample(String sql, T
 * entity, Object... objs) throws Exception{ return
 * this.getJdbcTemplate().query(sql,
 * BeanPropertyRowMapper.newInstance((Class<T>) entity.getClass()), objs); }
 * 
 * public <T> List<T> findByExample(String sql, Class<T> clazz, Object... objs)
 * throws Exception{ return this.getJdbcTemplate().query(sql,
 * BeanPropertyRowMapper.newInstance(clazz), objs); }
 * 
 * public List<Map<String, Object>> findByExample(String sql, Object... objs)
 * throws Exception{ return this.getJdbcTemplate().queryForList(sql, objs); }
 * 
 * public <T> List<T> findAll(String sql, T entity) throws Exception{ return
 * this.findByExample(sql, entity, (Object[]) null); }
 * 
 * public <T> List<T> findAll(String sql, Class<T> clazz) throws Exception{
 * 
 * return this.findByExample(sql, clazz); }
 * 
 * public void update(String sql, Object... obj) throws Exception{
 * this.getJdbcTemplate().update(sql, obj); }
 * 
 * public Map<String, Object> callStoreProcedure(String catalogName, String
 * spName, Object... params) throws Exception { Map<String, Object> result =
 * this.getJdbcCall().withCatalogName(catalogName).withProcedureName(spName).
 * execute(params);
 * 
 * return result; }
 * 
 * public Object callStoreProcedure(String catalogName, String spName,
 * Map<String, Object> mapParams) throws Exception { return toLowerInMap(
 * this.getJdbcCall().withCatalogName(catalogName).withProcedureName(spName).
 * execute(mapParams)); }
 * 
 * @SuppressWarnings("unchecked") public Object
 * callStoreProcedureWithAList(String catalogName, String spName, String
 * resultMapping, Map<String, Object> mapParams) throws Exception {
 * Map<String,Object> raw_map =
 * this.getJdbcCall().withCatalogName(catalogName).withProcedureName(spName).
 * execute(mapParams);
 * 
 * return
 * toLowerInMapForList((List<Map<String,Object>>)raw_map.get(resultMapping)); }
 * 
 * @SuppressWarnings("unchecked") public Object
 * callStoreProcedureWithAListNCount(String catalogName, String spName, String
 * resultMapping, Map<String, Object> mapParams) throws Exception {
 * Map<String,Object> outMap = new HashMap<>(); Map<String,Object> raw_map =
 * this.getJdbcCall().withCatalogName(catalogName).withProcedureName(spName).
 * execute(mapParams);
 * 
 * if(raw_map.get(resultMapping)!=null) { outMap.put("list",
 * toLowerInMapForList((List<Map<String,Object>>)raw_map.get(resultMapping))); }
 * if(raw_map.get("PO_TOTALROW")!=null) { outMap.put("count",
 * Integer.parseInt((String)raw_map.get("PO_TOTALROW"))); }
 * if(raw_map.get("PO_ERRORCODE")!=null) { outMap.put("po_errorcode",
 * raw_map.get("PO_ERRORCODE")); } if(raw_map.get("PO_ERRORDESC")!=null) {
 * outMap.put("po_errordesc", raw_map.get("PO_ERRORDESC")); } return outMap; }
 * 
 * public Map<String,Object> callFunction(String catalogName, String fnName,
 * Object... params) throws Exception { return
 * this.getJdbcCall().withCatalogName(catalogName).withFunctionName(fnName).
 * execute(params); }
 * 
 * public Map<String,Object> callFunction(String catalogName, String fnName,
 * Map<String, Object> params) throws Exception { return
 * this.getJdbcCall().withCatalogName(catalogName).withFunctionName(fnName).
 * execute(params); }
 * 
 * public int insert(String tableName, Map<String, Object> columns, String
 * genColumn) throws Exception { if (genColumn == null || "".equals(genColumn)){
 * return this.getJdbcInsert().withTableName(tableName).execute(columns); }
 * return
 * this.getJdbcInsert().withTableName(tableName).usingGeneratedKeyColumns(
 * genColumn).executeAndReturnKey(columns).intValue(); }
 * 
 * private Map<String, Object> toLowerInMap(Map<String, Object> inMaps) {
 * Map<String, Object> outMap = new HashMap<String, Object>();
 * 
 * if(inMaps == null) { return null; }
 * 
 * for(String key:inMaps.keySet()) { outMap.put(key.toLowerCase(),
 * inMaps.get(key)); } return outMap;
 * 
 * }
 * 
 * private List<Map<String,Object>> toLowerInMapForList(List<Map<String,Object>>
 * inList) { List<Map<String,Object>> outList = new ArrayList<>();//Map<String,
 * Object>();
 * 
 * if(inList == null) { return null; }
 * 
 * for(Map<String,Object> mapItem:inList) { outList.add(toLowerInMap(mapItem));
 * }
 * 
 * return outList;
 * 
 * } public ListResponse findAll(String catalogName, String spName, Map<String,
 * Object> params, Class<?> responseType) throws BaseException {
 * 
 * try { SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
 * log.info("----------------------------------Execute package "+spName+
 * "----------------------------------step 1: "+ fm.format(new Date()));
 * System.out.println(); Map<String, Object> out = ExecuteQuery(catalogName,
 * spName, params, responseType);
 * log.info("----------------------------------Execute package "+spName+
 * "----------------------------------step 2: "+ fm.format(new Date())); Object
 * errorMsg = out.get(ORACLE_PO.ERROR_MSG); if (errorMsg !=null) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value()); }
 * 
 * @SuppressWarnings("unchecked") List<Object> result = (List<Object>)
 * out.get(ORACLE_PO.REF_CURSOR); if
 * (org.springframework.util.CollectionUtils.isEmpty(result)) return new
 * vn.leoo.common.dto.ListResponse();
 * 
 * return new vn.leoo.common.dto.ListResponse(result, ((BigDecimal)
 * out.get(ORACLE_PO.TOTAL)).longValue());
 * 
 * } catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage());
 * 
 * 
 * }
 * 
 * }
 * 
 * public Object saveChange(String catalogName, String spName, Map<String,
 * Object> params, Class<?> responseType) throws BaseException { try {
 * 
 * Map<String, Object> out = ExecuteQuery(catalogName, spName, params,
 * responseType); Object statusCode = out.get(ORACLE_PO.REF_CURSOR); if
 * (!statusCode.equals("OK")) { return out.get(ORACLE_PO.ERROR_MSG); }
 * 
 * @SuppressWarnings("unchecked") List<Object> result = (List<Object>)
 * out.get(ORACLE_PO.REF_CURSOR); if
 * (org.springframework.util.CollectionUtils.isEmpty(result)) { return null; }
 * 
 * return result.get(0);
 * 
 * } catch (Exception e) {
 * 
 * if (logConsole) { log.info("callStoreProcedure - #event:getList, error:" +
 * e.getMessage()); } if
 * (e.getMessage().toLowerCase().contains("integrity constraint")) { throw new
 * BaseException(ErrorConst.ERR_SQL_CONSTRAINT, e.getMessage()); }
 * 
 * throw new BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(),
 * e.getMessage());
 * 
 * } } private Logger log = Logger.getLogger("BaseJdbcDAO"); private Map<String,
 * Object> ExecuteQuery(String catalogName, String spName, Map<String, Object>
 * params, Class<?> responseType) throws SQLException {
 * 
 * log.info("ExecuteQuery payload#2 - catalogName:" + catalogName + ", spName:"
 * + spName + ", params:"+ params.toString());
 * 
 * SimpleJdbcCall scall =
 * this.getJdbcCall().withProcedureName(spName).returningResultSet(ORACLE_PO.
 * REF_CURSOR, ColumnRowMapper.newInstance(responseType));
 * 
 * 
 * String[] cataLog = catalogName.split("\\."); if (cataLog.length > 1) {
 * scall.withSchemaName(cataLog[0]); scall.withCatalogName(cataLog[1]); } else {
 * 
 * scall.withCatalogName(catalogName); }
 * 
 * MapSqlParameterSource parameters = new MapSqlParameterSource(); if (params !=
 * null) { for (Map.Entry<String, Object> param : params.entrySet()) {
 * 
 * parameters.addValue(param.getKey(), param.getValue()); } }
 * 
 * //Connection conn = dataSource.getConnection();
 * //log.info("----------------------------------connection status "+dataSource.
 * getConnection().isClosed()+"----------------------------"); return
 * scall.execute(parameters);
 * 
 * }
 * 
 * public Object findById(String catalogName, String spName, Map<String, Object>
 * params, Class<?> responseType) throws BaseException{
 * 
 * try {
 * 
 * Map<String, Object> out = ExecuteQuery(catalogName, spName, params,
 * responseType); Object statusCode = out.get(ORACLE_PO.ERROR_MSG); if
 * (!statusCode.equals("OK")) { return out.get(ORACLE_PO.ERROR_MSG); }
 * 
 * @SuppressWarnings("unchecked") List<Object> result = (List<Object>)
 * out.get(ORACLE_PO.REF_CURSOR); if (CollectionUtils.isEmpty(result)) return
 * null; return result.get(0);
 * 
 * } catch (Exception e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * public Long getLongValue(String sqlText, Object... params) throws
 * BaseException { JdbcTemplate jdbcTemplate = new
 * JdbcTemplate(this.dataSource); try { Long longValue =
 * jdbcTemplate.queryForObject(sqlText, params, Long.class); return longValue; }
 * catch (DataAccessException e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } }
 * 
 * 
 * public Object getObject(String sqlText, Object... params) throws
 * BaseException { JdbcTemplate jdbcTemplate = new
 * JdbcTemplate(this.dataSource); try { Object value =
 * jdbcTemplate.queryForObject(sqlText, params, Object.class); return value; }
 * catch (DataAccessException e) { throw new
 * BaseException(ErrorCode.APP_SQL_ERROR_EXCE.value(), e.getMessage()); } } }
 */