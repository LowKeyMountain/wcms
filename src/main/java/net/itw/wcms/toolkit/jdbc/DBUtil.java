package net.itw.wcms.toolkit.jdbc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class DBUtil {

	private static Connection conn;

	public static Connection openConnection() throws SQLException, ClassNotFoundException, IOException {
		if (null == conn || conn.isClosed()) {
			Properties p = new Properties();
			p.load(DBUtil.class.getResourceAsStream("/db.properties"));
			Class.forName(p.getProperty("jdbc.driverClass"));
			conn = DriverManager.getConnection(p.getProperty("jdbc.jdbcUrl"), p.getProperty("jdbc.username"),
					p.getProperty("jdbc.password"));
		}
		return conn;
	}

	public static void closeConnection() throws SQLException {
		try {
			if (null != conn)
				conn.close();
		} finally {
			conn = null;
			// System.gc();
		}
	}

	public static List<Map<String, Object>> queryMapList(Connection con, String sql)
			throws SQLException, InstantiationException, IllegalAccessException {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Statement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt = con.createStatement();
			rs = preStmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (null != rs && rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String name = rsmd.getColumnName(i + 1);
					Object value = rs.getObject(name);
					map.put(name, value);
				}
				lists.add(map);
			}
		} finally {
			if (null != rs)
				rs.close();
			if (null != preStmt)
				preStmt.close();
			if (null != con)
				con.close();
		}
		return lists;
	}

	public static List<Map<String, Object>> queryMapList(Connection con, String sql, Object... params)
			throws SQLException, InstantiationException, IllegalAccessException {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt = con.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				// if(params[i]!=null){
				preStmt.setObject(i + 1, params[i]);// 下标从1开始
				// }
			}
			rs = preStmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (null != rs && rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String name = rsmd.getColumnName(i + 1);
					Object value = rs.getObject(name);
					if (value == null) {
						value = "";
					}
					map.put(name, value);
				}
				lists.add(map);
			}
		} finally {
			if (null != rs)
				rs.close();
			if (null != preStmt)
				preStmt.close();
		}
		return lists;
	}

	/**
	 * 郭彪 返回2列
	 */
	public static Map<String, Object> queryMap(Connection con, String sql, Object[] params)
			throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			preStmt = con.prepareStatement(sql);
			for (int i = 0; params != null && i < params.length; i++) {
				if (params[i] != null) {
					preStmt.setObject(i + 1, params[i]);// 下标从1开始
				}
			}
			rs = preStmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while (null != rs && rs.next()) {
				String name = rsmd.getColumnName(1);
				Object value = rs.getObject(name);
				if (value == null) {
					value = "";
				}
				String name1 = rsmd.getColumnName(2);
				Object value1 = rs.getObject(name1);
				if (value1 == null) {
					value1 = "";
				}
				map.put(value.toString(), value1);
			}
		} finally {
			if (null != rs)
				rs.close();
			if (null != preStmt)
				preStmt.close();
		}
		return map;
	}

	public static Map<String, Object> queryMap(Connection con, String sql)
			throws SQLException, InstantiationException, IllegalAccessException {
		return queryMap(con, sql, null);
	}

	public static <T> T queryObject(Connection con, String sql, Class<T> objClass)
			throws SQLException, InstantiationException, IllegalAccessException {
		List<T> lists = queryObjectList(con, sql, objClass);
		if (lists.size() == 0) {
			return null;
		} else if (lists.size() > 1) {
			throw new SQLException("SqlError：期待一行返回值，却返回了太多行！");
		}
		return lists.get(0);
	}

	public static <T> List<T> queryObjectList(Connection con, String sql, Class<T> objClass, Object... params)
			throws SQLException, InstantiationException, IllegalAccessException {
		List<T> lists = new ArrayList<T>();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt = con.prepareStatement(sql);
			for (int i = 0; i < params.length; i++)
				preStmt.setObject(i + 1, params[i]);
			rs = preStmt.executeQuery();
			label: while (null != rs && rs.next()) {
				Constructor<?>[] constor = objClass.getConstructors();
				for (Constructor<?> c : constor) {
					Object obj = rs.getObject(1);
					String value = obj != null ? obj.toString() : "";
					try {
						T t = (T) c.newInstance(value);
						lists.add(t);
						continue label;
					} catch (Exception e) {
					}
				}
			}
		} finally {
			if (null != rs)
				rs.close();
			if (null != preStmt)
				preStmt.close();
		}
		return lists;
	}

	public static <T> List<T> queryAppointObjectList(Connection con, String sql, Class<T> objClass,
			Map<String, String> fieldMapping, Object... params)
			throws SQLException, InstantiationException, IllegalAccessException {
		List<T> lists = new ArrayList<T>();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt = con.prepareStatement(sql);
			for (int i = 0; i < params.length; i++)
				preStmt.setObject(i + 1, params[i]);
			rs = preStmt.executeQuery();
			lists = populate(rs, objClass, fieldMapping);
		} finally {
			if (null != rs)
				rs.close();
			if (null != preStmt)
				preStmt.close();
		}
		return lists;
	}

	/*
	 * 将rs结果转换成对象列表
	 * 
	 * @param rs jdbc结果集
	 * 
	 * @param clazz 对象的映射类 return 封装了对象的结果列表
	 */
	public static <T> List<T> populate(ResultSet rs, Class<T> clazz, Map<String, String> fieldMapping)
			throws SQLException, InstantiationException, IllegalAccessException {
		// 结果集的元素对象
		ResultSetMetaData rsmd = rs.getMetaData();
		// 获取结果集的元素个数
		int colCount = rsmd.getColumnCount();
		// System.out.println("#");
		// for(int i = 1;i<=colCount;i++){
		// System.out.println(rsmd.getColumnName(i));
		// System.out.println(rsmd.getColumnClassName(i));
		// System.out.println("#");
		// }
		// 返回结果的列表集合
		List list = new ArrayList();
		// 业务对象的属性数组
		Field[] fields = clazz.getDeclaredFields();
		while (rs.next()) {// 对每一条记录进行操作
			Object obj = clazz.newInstance();// 构造业务对象实体
			// 将每一个字段取出进行赋值
			for (int i = 1; i <= colCount; i++) {
				Object value = rs.getObject(i);
				// 寻找该列对应的对象属性
				for (int j = 0; j < fields.length; j++) {
					Field f = fields[j];
					// 如果匹配进行赋值
					if (fieldMapping != null && !fieldMapping.isEmpty()) {
						String columnName = getIgnoreCase(fieldMapping, rsmd.getColumnName(i));
						if (f.getName().equalsIgnoreCase(columnName)) {
							boolean flag = f.isAccessible();
							f.setAccessible(true);
							f.set(obj, value);
							f.setAccessible(flag);
						}
					} else {
						if (f.getName().equalsIgnoreCase(rsmd.getColumnName(i))) {
							boolean flag = f.isAccessible();
							f.setAccessible(true);
							f.set(obj, value);
							f.setAccessible(flag);
						}
					}
				}
			}
			list.add(obj);
		}
		return list;
	}

	/**
	 * 根据key获取map值(忽略大小写)
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	private static String getIgnoreCase(Map<String, String> map, String key) {
		if (map == null) {
			return "";
		}
		String value = "";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String _k = entry.getKey();
			String _v = entry.getValue();
			if (StringUtils.equalsIgnoreCase(_k, key)) {
				return _v;
			}
		}
		return value;
	}

}
