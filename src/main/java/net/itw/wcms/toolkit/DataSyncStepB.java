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

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤B(将临时表数据计算后同步到各船舶任务子表)
 * 
 * @author Michael 29 Jan 2018 21:47:16
 */
@Service
@Transactional
public class DataSyncStepB{

	private final Logger log = Logger.getLogger("dataSyncInfo");

	private static SqlMap sqlMap;
	public static boolean isBreak = false;
	@Autowired
	private AutoCreateDBTable autoCreateDBTable;
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./DataSyncB.xml"));
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
						sync();
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
		log.info("同步工具：步骤B 已重启。");
	}

	/**
	 * 关闭
	 * 
	 */
	public void stop() {
		isBreak = true;
		log.info("同步工具：步骤B 已关闭。");
	}

	/**
	 * 将临时表数据计算后同步到各船舶任务子表
	 * 
	 */
	@Transactional
	public void sync() {
		try {
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlMap.getSql("01"));
			for (Map<String, Object> map : list) {

				int operationType = (int) map.get("operationType");

				int taskId = 0;
				int cabinId = 0;
				int groupId = 0;
				Date time = (Date) map.get("Time");
				int id = (int) map.get("id");
				String cmsid = (String) map.get("Cmsid");
				Double unloaderMove = (Double) map.get("unloaderMove");

				// 查询卸船机作业数据任务ID、船舱ID
				Object[] args = new Object[] { unloaderMove, unloaderMove, time };
				List<Map<String, Object>> cabinNums = this.jdbcTemplate.queryForList(sqlMap.getSql("02"), args);
				String dbTime = this.jdbcTemplate.queryForObject(" select now() time ", String.class);

				if (cabinNums == null || cabinNums.isEmpty()) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 未找到船舱信息！入参数据：位移|" + unloaderMove + "、操作时间|"
							+ time + "、当前时间|" + dbTime);
					args = new Object[] { unloaderMove, unloaderMove, time, time };
					List<Map<String, Object>> tasks = this.jdbcTemplate.queryForList(sqlMap.getSql("18"), args);
					if (tasks != null && !tasks.isEmpty()) {
						String sql = "";
						taskId = (int) tasks.get(0).get("taskId");
						try {
							// 自动创建任务子表
							autoCreateDBTable.createTable(taskId);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						List<Map<String, Object>> list2 = this.jdbcTemplate
								.queryForList(sqlMap.getSql("19", taskId), cmsid);
						for (Map<String, Object> map2 : list2) {
							int id2 = (int) map2.get("id");
							sql = sqlMap.getSql("setFinishTicket", taskId);
							args = new Object[] { id2 };
							this.jdbcTemplate.update(sql, args);
						}
					}

					// 删除临时表作业数据
					args = new Object[] { id, cmsid };
					this.jdbcTemplate.update(sqlMap.getSql("04"), args);
					continue;
				}

				if (cabinNums.size() > 1) {
					// log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "]
					// 匹配到多个船舱信息！");
				}
				cabinId = (int) cabinNums.get(0).get("id");
				taskId = (int) cabinNums.get(0).get("taskId");

				try {
					// 自动创建任务子表
					autoCreateDBTable.createTable(taskId);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// 更新表b数据
				try {
					// 【任务子表】将临时表作业信息插入子表
					if (1 == operationType) {

						// 查询临时表数据是否存在
						args = new Object[] { id, cmsid };
						List<?> list2 = this.jdbcTemplate.queryForList(sqlMap.getSql("03-1", taskId), args);

						if (list2.isEmpty()) {
							// 获取组编号
							groupId = calc(taskId, cabinId, cmsid, operationType, time);

							// 维护开工时间（由系统自动计算，以船舶的靠泊时间为起始点，判断卸船机第一斗的时间为开工时间）
							String beginTime = this.jdbcTemplate.queryForObject(
									" SELECT t.begin_time from tab_task t WHERE t.id = ? ", String.class, taskId);
							if (beginTime == null) {
								this.jdbcTemplate.update("UPDATE tab_task t SET t.begin_time = ? WHERE t.id = ?",
										new Object[] { time, taskId });
							}

							args = new Object[] { groupId, id, cmsid };
							this.jdbcTemplate.update(sqlMap.getSql("03", taskId), args);
						}

					}

					// 删除临时表作业数据
					args = new Object[] { id, cmsid };
					this.jdbcTemplate.update(sqlMap.getSql("04"), args);
					log.info("数据编号[" + id + "]|卸船机编号[" + cmsid + "] 数据已计算组信息！入参数据：位移|" + unloaderMove + "、操作时间|" + time
							+ "、当前时间|" + dbTime);
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
	int calc(int taskId, int cabinId, String cmsid, int operationType, Date time) {
		int groupId = 0;
		String sql = "";
		Object[] args = new Object[0];
		// 查询组信息，根据舱ID、 卸船机ID
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlMap.getSql("05", taskId), cabinId,
				cmsid);
		int conut = list.size();

		if (conut == 0) {
			// 新建组信息
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String inster_sql = "";
					if (0 == operationType) {
						inster_sql = sqlMap.getSql("createTicketByDisplacementInfo", taskId);
					} else if (1 == operationType) {
						inster_sql = sqlMap.getSql("createTicketByJobInfo", taskId);
					}
					PreparedStatement ps = connection.prepareStatement(inster_sql, Statement.RETURN_GENERATED_KEYS);
					if (0 == operationType) {
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) time);
						ps.setTimestamp(4, (Timestamp) time);
						ps.setInt(5, 0);
					} else if (1 == operationType) {
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) time);
						ps.setTimestamp(4, (Timestamp) time);
						ps.setTimestamp(5, (Timestamp) time);
						ps.setTimestamp(6, (Timestamp) time);
						ps.setInt(7, 0);
					}
					return ps;
				}
			}, keyHolder);
			Long generatenKey = (Long) keyHolder.getKeys().get("GENERATED_KEY");
			if (generatenKey != null) {
				groupId = generatenKey.intValue();
			}

			// 维护上一组结束时间
//			List<Map<String, Object>> groups = this.jdbcTemplate.queryForList(sqlMap.getSql("08", taskId), cmsid,
//					groupId);
//			if (groups != null) {
//				for (Map<String, Object> m : groups) {
//					int id = (int) m.get("id");
//					sql = sqlMap.getSql("setFinishTicket", taskId);
//					args = new Object[] { id };
//					this.jdbcTemplate.update(sql, args);
//				}
//			}
			
			List<Map<String, Object>> tasks = this.jdbcTemplate.queryForList(sqlMap.getSql("20"));
			for (Map<String, Object> task : tasks) {
				int tId = (int) task.get("id");
				List<Map<String, Object>> groups = null;
				if (tId == taskId) {
					groups = this.jdbcTemplate.queryForList(sqlMap.getSql("08", tId), cmsid, groupId);
				} else {
					groups = this.jdbcTemplate.queryForList(sqlMap.getSql("08_2", tId), cmsid);
				}
				if (groups != null) {
					for (Map<String, Object> m : groups) {
						int id = (int) m.get("id");
						sql = sqlMap.getSql("setFinishTicket", tId);
						args = new Object[] { id };
						this.jdbcTemplate.update(sql, args);
					}
				}
			}
		} else {
			Map<String, Object> map = list.get(0);
			groupId = (int) map.get("id");
			Date firstTime = (Date) map.get("firstTime");
			switch (operationType) {
			case 0:
				sql = sqlMap.getSql("updateTicketByDisplacementInfo", taskId);
				args = new Object[] { time, groupId };
				this.jdbcTemplate.update(sql, args);
				break;
			case 1:
				if (firstTime == null) {
					sql = sqlMap.getSql("updateTicketByJobInfoForFirstTimeIsNull", taskId);
					args = new Object[] { time, time, time, groupId };
				} else {
					sql = sqlMap.getSql("updateTicketByJobInfo", taskId);
					args = new Object[] { time, time, groupId };
				}
				this.jdbcTemplate.update(sql, args);
				break;
			default:
				break;
			}
			// log.error("数据异常：船舱编号[" + cabinId + "]|卸船机编号[" + cmsid + "] >>
			// 查到多条未关闭的组信息！");
		}

		return groupId;
	}
	
	/**
	 * 计算组编号(重新同步时使用)
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
	int resyncCalc(int taskId, int cabinId, String cmsid, int operationType, Date time) {
		int groupId = 0;
		String sql = "";
		Object[] args = new Object[0];
		// 查询组信息，根据舱ID、 卸船机ID
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlMap.getSql("05", taskId), cabinId,
				cmsid);
		int conut = list.size();

		if (conut == 0) {
			// 新建组信息
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String inster_sql = "";
					if (0 == operationType) {
						inster_sql = sqlMap.getSql("createTicketByDisplacementInfo", taskId);
					} else if (1 == operationType) {
						inster_sql = sqlMap.getSql("createTicketByJobInfo", taskId);
					}
					PreparedStatement ps = connection.prepareStatement(inster_sql, Statement.RETURN_GENERATED_KEYS);
					if (0 == operationType) {
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) time);
						ps.setTimestamp(4, (Timestamp) time);
						ps.setInt(5, 0);
					} else if (1 == operationType) {
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) time);
						ps.setTimestamp(4, (Timestamp) time);
						ps.setTimestamp(5, (Timestamp) time);
						ps.setTimestamp(6, (Timestamp) time);
						ps.setInt(7, 0);
					}
					return ps;
				}
			}, keyHolder);
			Long generatenKey = (Long) keyHolder.getKeys().get("GENERATED_KEY");
			if (generatenKey != null) {
				groupId = generatenKey.intValue();
			}

			// 维护上一组结束时间
			List<Map<String, Object>> groups = this.jdbcTemplate.queryForList(sqlMap.getSql("08", taskId), cmsid,
					groupId);
			if (groups != null) {
				for (Map<String, Object> m : groups) {
					int id = (int) m.get("id");
					sql = sqlMap.getSql("setFinishTicket", taskId);
					args = new Object[] { id };
					this.jdbcTemplate.update(sql, args);
				}
			}
		} else {
			Map<String, Object> map = list.get(0);
			groupId = (int) map.get("id");
			Date firstTime = (Date) map.get("firstTime");
			switch (operationType) {
			case 0:
				sql = sqlMap.getSql("updateTicketByDisplacementInfo", taskId);
				args = new Object[] { time, groupId };
				this.jdbcTemplate.update(sql, args);
				break;
			case 1:
				if (firstTime == null) {
					sql = sqlMap.getSql("updateTicketByJobInfoForFirstTimeIsNull", taskId);
					args = new Object[] { time, time, time, groupId };
				} else {
					sql = sqlMap.getSql("updateTicketByJobInfo", taskId);
					args = new Object[] { time, time, groupId };
				}
				this.jdbcTemplate.update(sql, args);
				break;
			default:
				break;
			}
			// log.error("数据异常：船舱编号[" + cabinId + "]|卸船机编号[" + cmsid + "] >>
			// 查到多条未关闭的组信息！");
		}

		return groupId;
	}
	
	/**
	 * 更新组结束时间
	 * 
	 * @param taskId
	 * @return
	 */
	public void updateGroupEndTime(int taskId) {
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlMap.getSql("13", taskId), taskId);
		for (Map<String, Object> m : list) {
			int groupId = (int) m.get("id");
			Object[] args = new Object[] { groupId };
			this.jdbcTemplate.update(sqlMap.getSql("setFinishTicket", taskId), args);
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
			// helper.start();
		}
	}

}
