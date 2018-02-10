package net.itw.wcms.toolkit;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤A(将卸船机原始数据同步到临时表)
 * 
 * @author Michael 29 Jan 2018 21:45:54
 */
public class DataSyncStepA extends JdbcDaoSupport {

	private final Logger log = Logger.getLogger("dataSyncInfo");

	private static SqlMap sqlMap;
	public static boolean isContinue = false;

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
	 * 运行数据同步任务
	 * 
	 * @param pattern
	 *            模式 true|多线程同步、false|单线程同步（默认）
	 */
	public void runTask(Boolean pattern) {
		if (pattern) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						start(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 1000);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						start(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 1000);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						start(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 1000);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						start(4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 1000);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						start(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 1000);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						start(6);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 1000);
		} else {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						start();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	}

	/**
	 * 启动
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		while (true) {
			if (!isContinue) {
				log.info("同步工具：步骤A 运行中...");
				for (int i = 1; i <= 6; i++) {
					sync(i);
				}
				Thread.sleep(1000);
			}
		}
	}

	/**
	 * 启动指定指定卸船机同步器
	 * 
	 * @param cmsNo
	 * @throws InterruptedException
	 */
	public void start(Integer cmsNo) throws InterruptedException {
		while (true) {
			if (!isContinue) {
				log.info("同步工具：步骤A 运行中...");
				sync(cmsNo);
				Thread.sleep(1000);
			}
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
	@Transactional
	private void sync(int cmsNum) {
		int max = 0;
		boolean isUpdateData = false;
		try {
			// 查询卸船机增量数据
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("01", cmsNum),
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
				this.getJdbcTemplate().update(sqlMap.getSql("04"), new Object[] { max, "ABB_GSU_" + cmsNum });
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
