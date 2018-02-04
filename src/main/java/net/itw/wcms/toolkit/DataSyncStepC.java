package net.itw.wcms.toolkit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤C(将船舶任务子表数据同步到临时表)
 * 
 * @author Michael 29 Jan 2018 22:42:25
 */
public class DataSyncStepC extends JdbcDaoSupport {

	private final Logger log = Logger.getLogger("DataSyncStepC");

	private static SqlMap sqlMap;
	@Autowired
	private DataSyncStepA dataSyncStepA;
	@Autowired
	private DataSyncStepB dataSyncStepB;
	
	public List<Map<String, Object>> queryForList(String sql, Object... args){
		return this.getJdbcTemplate().queryForList(sql, args);
	}
	
	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./DataSyncC.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动
	 * 
	 * @param taskId
	 * @throws Exception
	 */
	public void start(Integer taskId) throws Exception {
		log.info("同步工具：步骤C 开始...");
		dataSyncStepA.stop();
		dataSyncStepB.stop();

		sync(taskId);
		// 重新同步任务子表数据
		dataSyncStepB.resyncByTaskId(taskId);

		dataSyncStepA.restart();
		dataSyncStepB.restart();
		log.info("同步工具：步骤C 结束...");
	}

	/**
	 * 将船舶任务子表数据同步到临时表
	 * 
	 * @param taskId
	 */
	@Transactional
	public void sync(Integer taskId) throws Exception {
		try {
			// 将船舶任务子表数据同步到临时表
			this.getJdbcTemplate().update(sqlMap.getSql("01", "tab_temp_b_" + taskId), 0);
			// 【任务子表】删除任务子表：卸船作业信息
			this.getJdbcTemplate().update(sqlMap.getSql("02", "tab_temp_b_" + taskId));
			// 【任务子表】删除任务子表：组信息
			this.getJdbcTemplate().update(sqlMap.getSql("03", "tab_temp_c_" + taskId));
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
		}
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
			DataSyncStepC helper = (DataSyncStepC) ctx.getBean("dataSyncStepC");
			helper.start(48);
			// DataSyncStepB helper = (DataSyncStepB)
			// ctx.getBean("dataSyncStepB");
			// helper.resyncByTaskId(48);
		}
	}

}
