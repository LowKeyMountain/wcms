package net.itw.wcms.toolkit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤B(将临时表数据计算后同步到各船舶任务子表)
 * 
 * @author Michael 29 Jan 2018 21:47:16
 */
public class DataSyncStepB extends JdbcDaoSupport {

	private final Logger log = Logger.getLogger("DataSyncStepB");

	private static SqlMap sqlMap;
	public static boolean isContinue = false;
	@Autowired
	private AutoCreateDBTable autoCreateDBTable;

	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./DataSyncB.xml"));
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
				log.info("同步工具：步骤B 运行中...");
				sync();
				Thread.sleep(1000);
			}
		}
	}

	/**
	 * 启动(按照卸船机分别同步)
	 * 
	 * @param cmsNo
	 * @throws InterruptedException
	 */
	public void start(Integer cmsNo) throws InterruptedException {
		while (true) {
			if (!isContinue) {
				log.info("同步工具：步骤B 运行中...");
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
		log.info("同步工具：步骤B 重启中...");
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
		log.info("同步工具：步骤B 已关闭...");
	}

	/**
	 * 将临时表数据计算后同步到各船舶任务子表
	 * 
	 * @param cmsNo
	 */
	public void sync() {
		sync(null);
	}

	/**
	 * 将临时表数据计算后同步到各船舶任务子表
	 * 
	 * @param cmsNo
	 */
	@Transactional
	public void sync(Integer cmsNo) {
		int num = 0;
		try {
			List<Map<String, Object>> list = null;
			if (cmsNo == null) {
				// 查询临时表所有卸船机待处理数据
				list = this.getJdbcTemplate().queryForList(sqlMap.getSql("01"));
			} else {
				// 查询临时表指定卸船机待处理数据
				list = this.getJdbcTemplate().queryForList(sqlMap.getSql("17"), "ABB_GSU_" + cmsNo);
			}
			log.info("待计算数据" + list.size() + "条。");
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");
				// 过滤卸船机在线状态数据
				if (2 == operationType) {
					continue;
				}

				Integer taskId = 0;
				Integer cabinId = 0;
				Integer groupId = 0;
				Date time = (Date) map.get("Time");
				Integer id = (Integer) map.get("id");
				String cmsid = (String) map.get("Cmsid");
				Double unloaderMove = (Double) map.get("unloaderMove");

				// 查询卸船机作业数据任务ID、船舱ID
				Object[] args = new Object[] { unloaderMove, unloaderMove, time, time };
				List<Map<String, Object>> cabinNums = this.getJdbcTemplate().queryForList(sqlMap.getSql("02"), args);

				if (cabinNums == null || cabinNums.isEmpty()) {
					try {
						args = new Object[] { id, cmsid };
						this.getJdbcTemplate().update(sqlMap.getSql("15"), args);
						log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 未找到船舱信息！");
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
					continue;
				}

				if (cabinNums.size() > 1) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 匹配到多个船舱信息！");
				}
				cabinId = (Integer) cabinNums.get(0).get("id");
				taskId = (Integer) cabinNums.get(0).get("taskId");

				try {
					// 自动创建任务子表
					autoCreateDBTable.createTable(taskId);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				groupId = calc(taskId, cabinId, cmsid, operationType, time);

				// 更新表b数据
				try {
					// 【任务子表】将临时表作业数据插入子表
					args = new Object[] { groupId, id, cmsid };
					this.getJdbcTemplate().update(sqlMap.getSql("03", taskId), args);
					// 删除临时表作业数据
					args = new Object[] { id, cmsid };
					this.getJdbcTemplate().update(sqlMap.getSql("04"), args);
					num++;
					log.info("数据编号[" + id + "]|卸船机编号[" + cmsid + "] 数据已计算组信息！");
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
					continue;
				} finally {
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			log.info("数据已处理" + num + "条。");
		}
	}

	/**
	 * 重新同步任务子表数据
	 * 
	 * @param tid
	 */
	@Transactional
	public void resyncByTaskId(Integer tid) {
		int num = 0;
		try {
			// 查询临时表待处理数据
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("16"), tid);
			log.info("待计算数据" + list.size() + "条。");
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");
				// 过滤卸船机在线状态数据
				if (2 == operationType) {
					continue;
				}

				Integer taskId = 0;
				Integer cabinId = 0;
				Integer groupId = 0;
				Date time = (Date) map.get("Time");
				Integer id = (Integer) map.get("id");
				String cmsid = (String) map.get("Cmsid");
				Double unloaderMove = (Double) map.get("unloaderMove");

				// 查询卸船机作业数据任务ID、船舱ID
				Object[] args = new Object[] { unloaderMove, unloaderMove, time, time };
				List<Map<String, Object>> cabinNums = this.getJdbcTemplate().queryForList(sqlMap.getSql("02"), args);

				if (cabinNums == null || cabinNums.isEmpty()) {
					try {
						args = new Object[] { id, cmsid };
						this.getJdbcTemplate().update(sqlMap.getSql("15"), args);
						log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 未找到船舱信息！");
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
					continue;
				}

				if (cabinNums.size() > 1) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 匹配到多个船舱信息！");
				}
				cabinId = (Integer) cabinNums.get(0).get("id");
				taskId = (Integer) cabinNums.get(0).get("taskId");

				try {
					// 自动创建任务子表
					autoCreateDBTable.createTable(taskId);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				groupId = calc(taskId, cabinId, cmsid, operationType, time);

				// 更新表b数据
				try {
					// 【任务子表】将临时表作业数据插入子表
					args = new Object[] { groupId, id, cmsid };
					this.getJdbcTemplate().update(sqlMap.getSql("03", taskId), args);
					// 删除临时表作业数据
					args = new Object[] { id, cmsid };
					this.getJdbcTemplate().update(sqlMap.getSql("04"), args);
					num++;
					log.info("数据编号[" + id + "]|卸船机编号[" + cmsid + "] 数据已计算组信息！");
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
					continue;
				} finally {
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			log.info("数据已处理" + num + "条。");
		}
	}

	/**
	 * 计算组编号
	 * 
	 * @param taskId
	 *            任务ID
	 * @param cabinId
	 *            船舱ID
	 * @param cmsid
	 *            卸船机编号
	 * @param operationType
	 *            数据类型
	 * @param time
	 *            操作时间
	 * @return
	 */
	private Integer calc(Integer taskId, Integer cabinId, String cmsid, int operationType, Date time) {
		int groupId = 0;
		String sql = "";
		Object[] args = new Object[0];
		// 查询组信息，根据舱ID、 卸船机ID
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("05", taskId), cabinId,
				cmsid);
		int conut = list.size();

		if (conut == 0) {
			// 新建组信息
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.getJdbcTemplate().update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String inster_sql = "";
					if (0 == operationType) {
						inster_sql = sqlMap.getSql("06", taskId);
					} else if (1 == operationType) {
						inster_sql = sqlMap.getSql("07", taskId);
					}
					PreparedStatement ps = connection.prepareStatement(inster_sql, Statement.RETURN_GENERATED_KEYS);
					if (0 == operationType) {
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) time);
						ps.setInt(4, 0);
					} else if (1 == operationType) {
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) time);
						ps.setTimestamp(4, (Timestamp) time);
						ps.setTimestamp(5, (Timestamp) time);
						ps.setInt(6, 0);
					}
					return ps;
				}
			}, keyHolder);
			Long generatenKey = (Long) keyHolder.getKeys().get("GENERATED_KEY");
			if (generatenKey != null) {
				groupId = generatenKey.intValue();
			}

			// 维护上一组结束时间
			List<Map<String, Object>> groups = this.getJdbcTemplate().queryForList(sqlMap.getSql("08", taskId), cmsid,
					groupId);
			if (groups != null) {
				for (Map<String, Object> m : groups) {
					Integer id = (Integer) m.get("id");
					if (0 == operationType) {
						sql = sqlMap.getSql("09", taskId);
						args = new Object[] { time, id };
					} else if (1 == operationType) {
						sql = sqlMap.getSql("10", taskId);
						args = new Object[] { time, time, id };
					}
					this.getJdbcTemplate().update(sql, args);
				}
			}
		} else if (conut == 1) {
			Map<String, Object> map = list.get(0);
			groupId = (Integer) map.get("id");
			Date firstTime = (Date) map.get("firstTime");
			switch (operationType) {
			case 0:
				break;
			case 1:
				if (firstTime == null) {
					sql = sqlMap.getSql("11", taskId);
					args = new Object[] { time, time, groupId };
				} else {
					sql = sqlMap.getSql("12", taskId);
					args = new Object[] { time, groupId };
				}
				this.getJdbcTemplate().update(sql, args);
				break;
			default:
				break;
			}
		} else {
			Map<String, Object> map = list.get(0);
			groupId = (Integer) map.get("id");
			Date firstTime = (Date) map.get("firstTime");
			switch (operationType) {
			case 0:
				break;
			case 1:
				if (firstTime == null) {
					sql = sqlMap.getSql("11", taskId);
					args = new Object[] { time, time, groupId };
				} else {
					sql = sqlMap.getSql("12", taskId);
					args = new Object[] { time, groupId };
				}
				this.getJdbcTemplate().update(sql, args);
				break;
			default:
				break;
			}
			log.error("数据异常：船舱编号[" + cabinId + "]|卸船机编号[" + cmsid + "] >> 查到多条未关闭的组信息！");
		}

		return groupId;
	}

	/**
	 * 更新组结束时间
	 * 
	 * @param taskId
	 * @return
	 */
	public void updateGroupEndTime(Integer taskId) {
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("13", taskId), taskId);
		for (Map<String, Object> m : list) {
			Integer groupId = (Integer) m.get("id");
			Date startTime = (Date) m.get("startTime");
			Date lastTime = (Date) m.get("lastTime");
			Date endTime = lastTime != null ? lastTime : (startTime != null ? startTime : new Date());
			Object[] args = new Object[] { endTime, 1, groupId };
			this.getJdbcTemplate().update(sqlMap.getSql("14", taskId), args);
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
			DataSyncStepB helper = (DataSyncStepB) ctx.getBean("dataSyncStepB");
			helper.start();
		}
	}

}
