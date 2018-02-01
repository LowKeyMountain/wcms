package net.itw.wcms.interfaceApi.http;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.toolkit.jdbc.DBUtil;
import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 
 * Description: 信息查询接口帮助类
 * 
 * @author Michael 2 Dec 2017 21:34:47
 */
public class InfoQueryHelper {

	private static final SimpleDateFormat Simple_Date_Format = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat Simple_Time_Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SqlMap sqlMap;

	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./queryInterfaceConfig.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据查询接口
	 * 
	 * @param json
	 * @return <b> 入参[数据格式json]: </b> fuctionType :功能类型(功能号) </b><br>
	 * 
	 *         <b> 出参[数据格式json]: </b> code 结果码( 1|成功; 0|失败)、 msg 返回提示信息、data
	 *         返回数据</b>
	 */
	public Map<String, Object> doQueryInfo(JSONObject jsonObject) {
		return doQueryInfo(jsonObject, null);
	}
	
	/**
	 * 数据查询接口
	 * 
	 * @param json
	 * @param options
	 * @return <b> 入参[数据格式json]: </b> fuctionType :功能类型(功能号) </b><br>
	 * 
	 *         <b> 出参[数据格式json]: </b> code 结果码( 1|成功; 0|失败)、 msg 返回提示信息、data
	 *         返回数据</b>
	 */
	public Map<String, Object> doQueryInfo(JSONObject jsonObject, QueryOptions options) {
		long a = System.currentTimeMillis();

		System.out.println("接口[doQueryInfo_" + a + "] start ...");
		System.out.println("接口[doQueryInfo_" + a + "] 入参：" + jsonObject.toJSONString());

		String msg = "操作成功！";
		boolean isSuccess = true;
		Map<String, Object> map = null;
		if (options == null) {
			options = new QueryOptions();
		}
		// 解析入参
		try {
			String fuctionType = (String) jsonObject.get("fuctionType");
			// 参数校验
			if (StringUtils.isBlank(fuctionType)) {
				throw new Exception("接口参数异常[fuctionType]不能为空.");
			}
			map = doQueryInfoSupport(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			msg = "操作失败：" + e.getMessage();
		}

		// 处理返回值
		Map<String, Object> result = new HashMap<String, Object>();
		if (map != null) {
			result.putAll(map);
		}
		result.put("code", isSuccess ? "1" : "0");
		result.put("msg", StringUtils.isBlank(msg) ? "" : msg);

		System.out.println("接口[doQueryInfo_" + a + "] 出参：" + result.toString());
		System.out.println("接口[doQueryInfo_" + a + "] 共耗时：" + (System.currentTimeMillis() - a) / 1000 + "s.");

		return result;
	}

	// ////////////////////// 插口查询通用方法 //////////////////////////////////////

	/**
	 * 查询信息支持函数
	 * 
	 * @param jsonObject
	 * @param options
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> doQueryInfoSupport(JSONObject jsonObject, QueryOptions options) throws Exception {

		// 接口配置数据结构（CDMDC_RPT.QUERY_INTERFACE_CONFIG）
		// FUCTION_TYPE varchar2 100 接口功能号
		// PARAM_CHECK varchar2 500 参数检查，包括入参取值范围、默认值等，如：
		// {"order":"section|asc、desc","sort":"section|XH、ZQGSMC、RZJE","page":"number|1","rows":"number|10"}
		// SQL varchar2 500 接口执行SQL语句
		// EXTEND_CONFIG varchar2 500 查询结果集字段及类型，如：{"isPaging":"0|1",
		// "queryFields":{"XH":"number","ZQGSMC":"string","RZJE":"number"}}
		// NOTES varchar2 2000 备注

		// 获取业务库（即，数据库名：cdmdc_rpt）数据库链接
		Connection conn = DBUtil.openConnection();

		// 获取接口查询配置
		Map<String, String> config = new HashMap<String, String>();

		Map<String, Object> map = null;
		try {
			String sql = " select * from QUERY_INTERFACE_CONFIG t where t.fuction_type = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			// 扩展功能号（function_type）类型, 功能号后加“_” +
			// 序号，符号可以递增，例如：FN_012_1、FN_012_2 ...
			String functionType = jsonObject.getString("fuctionType");
			if (jsonObject.containsKey("type")) {
				int type = (Integer) jsonObject.get("type");
				if (type != 0) {
					functionType += "_" + jsonObject.getString("type");
				}
			}
			ps.setString(1, functionType);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				try {
					String _SQL = "";
					if (options.args != null) {
						_SQL = sqlMap.getSql(rs.getString("FUCTION_TYPE"), options.args);
					}
					if (StringUtils.isNotBlank(options.searchString) && StringUtils.isNotBlank(options.replacement)) {
						if (StringUtils.isBlank(_SQL)) {
							_SQL = sqlMap.getSql(rs.getString("FUCTION_TYPE"));
						}
						_SQL = StringUtils.replace(_SQL, options.searchString, options.replacement);
					}
					if (StringUtils.isBlank(_SQL)) {
						_SQL = rs.getString("SQL");
					}
					config.put("SQL", _SQL);
				} catch (Exception e) {
					e.printStackTrace();
				}
				config.put("PARAM_CHECK", rs.getString("PARAM_CHECK"));
				config.put("FUCTION_TYPE", rs.getString("FUCTION_TYPE"));
				config.put("EXTEND_CONFIG", rs.getString("EXTEND_CONFIG"));
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}

		if (config.isEmpty()) {
			throw new Exception("未找到[" + jsonObject.getString("fuctionType") + "]接口配置信息，请联系接口提供商.");
		}

		// 解析示例： EXTEND_CONFIG varchar2 500 查询结果集字段及类型，如：{"isPaging":"0|1"}
		boolean isPaging = false;
		try {
			JSONObject jbconfig = JSONObject.parseObject(config.get("EXTEND_CONFIG"));
			isPaging = jbconfig.getIntValue("isPaging") == 0 ? true : false;
		} catch (Exception e) {
			throw new Exception("接口配置异常：[" + config.get("FUCTION_TYPE") + "] > [EXTEND_CONFIG] > [isPaging] 配置异常。");
		}

		if (isPaging) {
			map = pagingQuerySupport(jsonObject, options, config);
		} else {
			map = querySupport(jsonObject, options, config);
		}
		return map;
	}

	/**
	 * 分页查询支持
	 * 
	 * @param jsonObject
	 * @param options
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> pagingQuerySupport(JSONObject jsonObject, QueryOptions options,
			Map<String, String> config) throws Exception {
		// 获取数据库链接
		Connection conn = DBUtil.openConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 1. 入参验证
			// 示例描述：PARAM_CHECK varchar2 500 参数检查，包括入参取值范围、默认值等，如：
			// {"order":"section|asc、desc","sort":"section|XH、ZQGSMC、RZJE","page":"number|1","rows":"number|10"}
			JSONObject jbconfig = JSONObject.parseObject(config.get("PARAM_CHECK"));
			for (String param : jbconfig.keySet()) {
				String checkRule = jbconfig.getString(param);
				if (StringUtils.isEmpty(checkRule) || checkRule.split("|").length < 1) {
					throw new Exception(
							"接口配置异常：[" + config.get("FUCTION_TYPE") + "] > [PARAM_CHECK] > [" + param + "] 入参值异常。");
				}

				String[] jbcArray = checkRule.split("\\|");
				if ("section".equalsIgnoreCase(jbcArray[0].toLowerCase())) { // 区间值判断
					Object object = jsonObject.get(param);
					if (object != null) {
						String stringVal = jsonObject.getString(param);
						if (StringUtils.isNotBlank(stringVal)
								&& !Arrays.asList(jbcArray[1].split("、")).contains(stringVal.toUpperCase())) {
							throw new Exception("入参异常，[" + param + "]必须是" + jbcArray[1] + "其中选一！");
						}
						options.setQueryInParamValue(param, stringVal);
					}
				} else if ("number".equalsIgnoreCase(jbcArray[0].toLowerCase())) { // 数值类型验证
					Object object = jsonObject.get(param);
					Integer intVal = -1;
					if (object != null) {
						try {
							intVal = jsonObject.getIntValue(param);
						} catch (Exception e) {
							throw new Exception("入参异常，[" + param + "]必须是数值类型！");
						}
					} else {
						intVal = Integer.valueOf(jbcArray[1]);
					}
					options.setQueryInParamValue(param, intVal);
				}
			}

			// 2. 数据查询
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {

				// 解析示例： EXTEND_CONFIG varchar2 500 查询结果集字段及类型，
				// 如：{"queryFields":{"XH":"number","ZQGSMC":"string","RZJE":"number"}}
				JSONObject queryFieldsJson = null;

				try {
					jbconfig = JSONObject.parseObject(config.get("EXTEND_CONFIG"));
					queryFieldsJson = jbconfig.getJSONObject("queryFields");
				} catch (Exception e) {
					throw new Exception(
							"接口配置异常：[" + config.get("FUCTION_TYPE") + "] > [EXTEND_CONFIG] > [queryFields] 配置异常。");
				}

				// 2.1 分页查询
				StringBuffer buff = new StringBuffer(config.get("SQL"));
				
				if (jsonObject.get("criteria") instanceof JSONObject) {
					JSONObject criteria = (JSONObject) jsonObject.get("criteria");
					if (criteria != null) {
						for (String key : criteria.keySet()) {
							String value = criteria.getString(key);
							if (!queryFieldsJson.containsKey(key) && !key.startsWith("$")) {
								throw new Exception("接口入参异常：[" + config.get("FUCTION_TYPE")
										+ "] > [PARAM_CHECK] > [CRITERIA] > [" + key + "]查询条件不存在，请确认。");
							}
							key = key.startsWith("$") ? key.substring(1, key.length()) : key;
							Object fieldType = queryFieldsJson.getString(key);
							if (fieldType != null && StringUtils.equalsIgnoreCase("string", (String)fieldType)) {
								buff.append(" and " + key + " = '" + value + "'");
							} else {
								buff.append(" and " + key + " = " + value);
							}
						}
					}
				}
				
				if (StringUtils.isNotBlank(options.order) && StringUtils.isNotBlank(options.sort)) {
					buff.append(" order by " + options.sort + " " + options.order);
				}
				String sql = " select * from ( " + buff.toString() + " ) as iooooooi LIMIT ?,? ";

				System.out.println("接口[doQueryInfo] 查询数据 执行SQL：" + sql);

				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, (options.page - 1) * options.rows);
				ps.setInt(2, options.rows);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					for (String param : queryFieldsJson.keySet()) {
						String dataType = queryFieldsJson.getString(param);
						if ("string".equalsIgnoreCase(dataType)) {
							String str = rs.getString(param);
							resultMap.put(param, StringUtils.isNotEmpty(str) ? str : "--");
						} else if ("integer".equalsIgnoreCase(dataType) || "number".equalsIgnoreCase(dataType)) {
							resultMap.put(param, rs.getInt(param));
						} else if ("time".equalsIgnoreCase(dataType)) {
//							Date date = rs.getDate(param);
//							if (date != null) {
//								resultMap.put(param, Simple_Time_Format.format(date));
//							} else {
//								resultMap.put(param, "--");
//							}
							String str = rs.getString(param);
							resultMap.put(param, StringUtils.isNotEmpty(str) ? str.substring(0, str.length() - 2) : "--");
						} else if ("date".equalsIgnoreCase(dataType)) {
							Date date = rs.getDate(param);
							if (date != null) {
								resultMap.put(param, Simple_Date_Format.format(date));
							} else {
								resultMap.put(param, "--");
							}
						} else if ("double".equalsIgnoreCase(dataType)) {
							resultMap.put(param, rs.getDouble(param));
						} else {
							resultMap.put(param, rs.getObject(param));
						}
					}
					list.add(resultMap);
				}

				// 2.2 查询总记录数
				ps = conn.prepareStatement(" select count(*) from ( " + buff.toString() + " ) as iooooooi");
				rs = ps.executeQuery();
				while (rs.next()) {
					options.setTotal(rs.getInt(1));
				}
				rs.close();
				ps.close();
			} catch (Exception e) {
				throw e;
			}

			// 3. 装备返回值
			map.put("data", list);
			map.put("total", options.getTotal());
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 * 查询支持
	 * 
	 * @param jsonObject
	 * @param options
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> querySupport(JSONObject jsonObject, QueryOptions options,
			Map<String, String> config) throws Exception {
		// 获取数据库链接
		Connection conn = DBUtil.openConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 1. 入参验证
			// 示例描述：PARAM_CHECK varchar2 500 参数检查，包括入参取值范围、默认值等，如：
			// {"order":"section|asc、desc","sort":"section|XH、ZQGSMC、RZJE","page":"number|1","rows":"number|10"}
			JSONObject jbconfig = JSONObject.parseObject(config.get("PARAM_CHECK"));
			for (String param : jbconfig.keySet()) {
				String checkRule = jbconfig.getString(param);
				if (StringUtils.isEmpty(checkRule) || checkRule.split("|").length < 1) {
					throw new Exception(
							"接口配置异常：[" + config.get("FUCTION_TYPE") + "] > [PARAM_CHECK] > [" + param + "] 入参值异常。");
				}

				String[] jbcArray = checkRule.split("\\|");
				if ("section".equalsIgnoreCase(jbcArray[0].toLowerCase())) { // 区间值判断
					Object object = jsonObject.get(param);
					if (object != null) {
						String stringVal = jsonObject.getString(param);
						if (StringUtils.isNotBlank(stringVal)
								&& !Arrays.asList(jbcArray[1].split("、")).contains(stringVal.toUpperCase())) {
							throw new Exception("入参异常，[" + param + "]必须是" + jbcArray[1] + "其中选一！");
						}
						options.setQueryInParamValue(param, stringVal);
					}
				} else if ("number".equalsIgnoreCase(jbcArray[0].toLowerCase())) { // 数值类型验证
					Object object = jsonObject.get(param);
					Integer intVal = -1;
					if (object != null) {
						try {
							intVal = jsonObject.getIntValue(param);
						} catch (Exception e) {
							throw new Exception("入参异常，[" + param + "]必须是数值类型！");
						}
					} else {
						intVal = Integer.valueOf(jbcArray[1]);
					}
					options.setQueryInParamValue(param, intVal);
				}
			}

			// 2. 数据查询
			Map<String, Object> resultMap = new HashMap<String, Object>();
			try {

				// 解析示例： EXTEND_CONFIG varchar2 500 查询结果集字段及类型，
				// 如：{"queryFields":{"XH":"number","ZQGSMC":"string","RZJE":"number"}}
				JSONObject queryFieldsJson = null;

				try {
					jbconfig = JSONObject.parseObject(config.get("EXTEND_CONFIG"));
					queryFieldsJson = jbconfig.getJSONObject("queryFields");
				} catch (Exception e) {
					throw new Exception(
							"接口配置异常：[" + config.get("FUCTION_TYPE") + "] > [EXTEND_CONFIG] > [queryFields] 配置异常。");
				}

				// 2.1 查询
				StringBuffer buff = new StringBuffer(config.get("SQL"));
				
				if (jsonObject.get("criteria") instanceof JSONObject) {
					JSONObject criteria = (JSONObject) jsonObject.get("criteria");
					if (criteria != null) {
						for (String key : criteria.keySet()) {
							String value = criteria.getString(key);
							if (!queryFieldsJson.containsKey(key) && !key.startsWith("$")) {
								throw new Exception("接口入参异常：[" + config.get("FUCTION_TYPE")
										+ "] > [PARAM_CHECK] > [CRITERIA] > [" + key + "]查询条件不存在，请确认。");
							}
							key = key.startsWith("$") ? key.substring(1, key.length()) : key;
							Object fieldType = queryFieldsJson.getString(key);
							if (fieldType != null && StringUtils.equalsIgnoreCase("string", (String)fieldType)) {
								buff.append(" and " + key + " = '" + value + "'");
							} else {
								buff.append(" and " + key + " = " + value);
							}
						}
					}
				}
				
				if (StringUtils.isNotBlank(options.order) && StringUtils.isNotBlank(options.sort)) {
					buff.append(" order by " + options.sort + " " + options.order);
				}
				String sql = " " + buff.toString() + " ";

				System.out.println("接口[doQueryInfo] 查询数据 执行SQL：" + sql);

				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					for (String param : queryFieldsJson.keySet()) {
						String dataType = queryFieldsJson.getString(param);
						if ("string".equalsIgnoreCase(dataType)) {
							String str = rs.getString(param);
							resultMap.put(param, StringUtils.isNotEmpty(str) ? str : "--");
						} else if ("integer".equalsIgnoreCase(dataType) || "number".equalsIgnoreCase(dataType)) {
							resultMap.put(param, rs.getInt(param));
						} else if ("time".equalsIgnoreCase(dataType)) {
//							Date date = rs.getDate(param);
//							if (date != null) {
//								resultMap.put(param, Simple_Time_Format.format(date));
//							} else {
//								resultMap.put(param, "--");
//							}
							String str = rs.getString(param);
							resultMap.put(param, StringUtils.isNotEmpty(str) ? str.substring(0, str.length() - 2) : "--");
						} else if ("date".equalsIgnoreCase(dataType)) {
							Date date = rs.getDate(param);
							if (date != null) {
								resultMap.put(param, Simple_Date_Format.format(date));
							} else {
								resultMap.put(param, "--");
							}
						} else if ("double".equalsIgnoreCase(dataType)) {
							resultMap.put(param, rs.getDouble(param));
						} else {
							resultMap.put(param, rs.getObject(param));
						}
					}
				}

			} catch (Exception e) {
				throw e;
			}

			// 3. 装备返回值
			map.put("data", resultMap);
		} finally {
			conn.close();
		}
		return map;
	}
}
