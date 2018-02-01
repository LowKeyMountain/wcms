package net.itw.wcms.toolkit.sql;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

@XmlRootElement
public class SqlMap {

	Map<String, String> sqls = new HashMap<>();

	public Map<String, String> getSqls() {
		return sqls;
	}

	public void setSqls(Map<String, String> sqls) {
		this.sqls = sqls;
	}

	public String getSql(String name) {
		return sqls.get(name);
	}
	
	public String getSql(String name, Object... args) {
		String sql = sqls.get(name);
		return String.format(sql, args);
	}
	
	public static SqlMap load(String name) throws JAXBException, UnsupportedEncodingException {
		SqlMap sqlMap = null;
		if (StringUtils.isNotEmpty(name)) {
			JAXBContext context = JAXBContext.newInstance(SqlMap.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Reader reader = new InputStreamReader(SqlMap.class.getResourceAsStream(name), "UTF-8");
			sqlMap = (SqlMap) unmarshaller.unmarshal(reader);
		} else {
			sqlMap = new SqlMap();
		}
		return sqlMap;
	}

	public static SqlMap load(InputStream in) throws JAXBException, UnsupportedEncodingException {
		SqlMap sqlMap = null;
		if (in != null) {
			JAXBContext context = JAXBContext.newInstance(SqlMap.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Reader reader = new InputStreamReader(in, "UTF-8");
			sqlMap = (SqlMap) unmarshaller.unmarshal(reader);
		} else {
			sqlMap = new SqlMap();
		}
		return sqlMap;
	}

}
