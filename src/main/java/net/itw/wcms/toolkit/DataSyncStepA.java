package net.itw.wcms.toolkit;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤A(将卸船机原始数据同步到临时表)
 * 
 * @author Michael 29 Jan 2018 21:45:54
 */
@Service
@Transactional
public class DataSyncStepA {

	private final Logger log = Logger.getLogger("dataSyncInfo");

	private static SqlMap sqlMap;
	public static boolean isBreak = false;
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
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
	 * 启动
	 * 
	 */
	public void start() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					while (true) {
						if (isBreak) {
							this.cancel();
							break;
						}
						for (int i = 1; i <= 6; i++) {
							sync(i);
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 1000);
	}

	/**
	 * 重启
	 * 
	 */
	public void restart() {
		isBreak = false;
		start();
		log.info("同步工具：步骤A 已重启。");
	}

	/**
	 * 关闭
	 * 
	 */
	public void stop() {
		isBreak = true;
		log.info("同步工具：步骤A 已关闭。");
	}

	/**
	 * 将卸船机原始数据同步临时表
	 * 
	 * @param cmsNum
	 *            卸船机编号
	 */
	@Transactional
	private void sync(int cmsNum) {
		int max = 0;
		boolean isUpdateData = false;
		try {
			// 查询卸船机增量数据
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlMap.getSql("01", cmsNum),
					"ABB_GSU_" + cmsNum);
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");
				// 过滤卸船机在线状态数据
				if (2 == operationType) {
					continue;
				}

				Integer id = (Integer) map.get("id");
				String cmsid = (String) map.get("Cmsid");

				// 检查数据是否存在
				List<?> list2 = this.jdbcTemplate.queryForList(sqlMap.getSql("02"), id, cmsid);
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
					// Object[] args = new Object[] { id, time, cmsid, pushTime,
					// oneTask, direction, unloaderMove + 7,
					// operationType, 0, 0 };
					Object[] args = new Object[] { id, time, cmsid, pushTime, oneTask, direction, unloaderMove,
							operationType, 0, 0 };
					this.jdbcTemplate.update(sqlMap.getSql("03"), args);
					// dataSyncLogsHelper.dataSyncStepbLogs(0, args);
					log.info("数据编号[" + id + "]|卸船机编号[" + cmsid + "] 数据已插入B表！");
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
				this.jdbcTemplate.update(sqlMap.getSql("04"), new Object[] { max, "ABB_GSU_" + cmsNum });
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
			// helper.start();
		}
	}

}
