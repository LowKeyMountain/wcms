package net.itw.wcms.toolkit;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步   > 步骤A(将卸船机原始数据同步到临时表)
 * 
 * @author Michael 29 Jan 2018 21:45:54
 */
public class DataSyncStepA extends JdbcDaoSupport {

	private final Logger log = Logger.getLogger("DataSyncStepA");

	private static SqlMap sqlMap;
	public static boolean isContinue;

	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./DataSyncA.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		isContinue = false;
		log.info("初始化同步工具：步骤A ...");
	}

	/**
	 * 启动
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		while (!isContinue) {
			log.info("同步工具：步骤A 运行中...");
			for (int i = 1; i <= 6; i++) {
				sync(i);
			}
			Thread.sleep(1000);
		}
	}

	/**
	 * 重启
	 * 
	 * @throws InterruptedException
	 */
	public void restart() throws InterruptedException {
		log.info("同步工具：步骤A 重启中...");
		if (isContinue) {
			isContinue = false;
		}
		start();
	}

	/**
	 * 关闭
	 * 
	 */
	public void stop() {
		isContinue = true;
		log.info("同步工具：步骤A 已关闭...");
	}

	/**
	 * 将卸船机原始数据同步临时表
	 * 
	 * @param cmsNum
	 *            卸船机编号
	 */
	private void sync(int cmsNum) {
		int max = 0;
		boolean isUpdateData = false;
		try {
			// 查询卸船机增量数据
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("01"),
					new Object[] { cmsNum, cmsNum });
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");
				// 过滤卸船机在线状态数据
				if (2 == operationType) {
					continue;
				}

				Integer id = (Integer) map.get("id");
				String cmsid = (String) map.get("Cmsid");

				// 检查数据是否存在
				List<?> list2 = this.getJdbcTemplate().queryForList(sqlMap.getSql("02"), id, cmsid);
				if (!list2.isEmpty()) {
					continue;
				}

				isUpdateData = true;
				Date time = (Date) map.get("Time");
				Date pushTime = (Date) map.get("PushTime");
				Double oneTask = (Double) map.get("OneTask");
				String direction = (String) map.get("direction");
				Double unloaderMove = (Double) map.get("unloaderMove");

				// 卸船机数据插入表b
				try {
					Object[] args = new Object[] { id, time, cmsid, pushTime, oneTask, direction, unloaderMove,
							operationType, 0, 0 };
					this.getJdbcTemplate().update(sqlMap.getSql("03"), args);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
					continue;
				}
				max = id; // 记录增量标书
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			// 记录卸船机数据增量标识
			if (isUpdateData && max > 0) {
				this.getJdbcTemplate().update(sqlMap.getSql("04"), new Object[] { max, cmsNum });
			}
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
			DataSyncStepA helper = (DataSyncStepA) ctx.getBean("dataSyncStepA");
			helper.start();
		}
	}

}
