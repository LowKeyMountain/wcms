package net.itw.wcms.toolkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步日志帮助类
 * 
 * @author Michael 4 Mar 2018 15:52:26
 */
@Service
@Transactional
public class DataSyncLogsHelper {

	private static SqlMap sqlMap;
	private Map<String, String> cache = new HashMap<>();
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AutoCreateDBTable autoCreateDBTable;
	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./DataSyncLogsHelper.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public SqlMap getSqlMap() {
		return sqlMap;
	}

	/**
	 * DataSyncStepB日志记录
	 * 
	 * @param type
	 *            操作类型
	 * @param args
	 *            sql参数
	 */
	public void dataSyncStepbLogs(Integer type, Object... args) {
		// 自动创建日志表
		String tabName = "tab_dataSyncStepB_logs";
		if (!cache.containsKey(tabName)) {
			if (!autoCreateDBTable.isExistsTable(tabName)) {
				this.jdbcTemplate.update(sqlMap.getSql("C_01"));
				cache.put(tabName, tabName);
			}
		}
		
		switch (type) {
		case 0: // 插入数据
			this.jdbcTemplate.update(sqlMap.getSql("I_01"), args);
			break;
		case 1: // 找到所属船舶
			this.jdbcTemplate.update(sqlMap.getSql("U_01"), args);
			break;
		case 2: // 未找到所属船舶
			this.jdbcTemplate.update(sqlMap.getSql("U_02"), args);
			break;
		default:
			break;
		}

	}

	/**
	 * 程序入口
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		if (ctx != null) {
			// DataSyncLogsHelper helper = (DataSyncLogsHelper)
			// ctx.getBean("dataSyncStepB");
			// helper.start();
		}
	}

}
