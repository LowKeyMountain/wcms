package net.itw.wcms.toolkit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * Description: 自动创建数据库表
 * 
 * @author Michael 27 Jan 2018 14:40:20
 */
@Service
@Transactional
public class AutoCreateDBTable {

	private static SqlMap sqlMap;
	private Map<String, String> cache = new HashMap<>();
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./create_table.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建表TAB_TEMP_B、TAB_TEMP_C
	 * 
	 * @param taskId
	 * @throws SQLException
	 */
	public void createTable(Integer taskId) throws SQLException {
		if (cache.containsKey(taskId)) {
			return;
		}
		String tabName_b = "tab_temp_b_" + taskId;
		if (!isExistsTable(tabName_b)) {
			this.jdbcTemplate.update(sqlMap.getSql("TAB_B", tabName_b));
		}
		String tabName_c = "tab_temp_c_" + taskId;
		if (!isExistsTable(tabName_c)) {
			this.jdbcTemplate.update(sqlMap.getSql("TAB_C", tabName_c));
		}
		cache.put(taskId+"", (tabName_b + "," + tabName_c));
	}
	
	/**
	 * 检查表是否存在
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isExistsTable(String tableName) {
		List<String> list = this.jdbcTemplate.queryForList(sqlMap.getSql("checkTable"), String.class,"%" + tableName + "%");
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 程序入口
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		if (ctx != null) {
			AutoCreateDBTable helper = (AutoCreateDBTable) ctx.getBean("autoCreateDBTable");
			helper.createTable(null);
		}
	}
	
}
