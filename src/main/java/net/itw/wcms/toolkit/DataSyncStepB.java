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

	private final Logger log = Logger.getLogger("dataSyncInfo");

	private static SqlMap sqlMap;
	@Autowired
	private AutoCreateDBTable autoCreateDBTable;
	@Autowired
	private DataSyncLogsHelper dataSyncLogsHelper;

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
	 * 运行数据同步任务
	 * 
	 */
	public void runTask() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					while (true) {
						if (!DataSyncStepC.isStartStepC()) {
							sync();
							Thread.sleep(1000);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 1000);
	}

	/**
	 * 将临时表数据计算后同步到各船舶任务子表
	 * 
	 */
	@Transactional
	public void sync() {
		try {
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("01"));
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");

				Integer taskId = 0;
				Integer cabinId = 0;
				Integer groupId = 0;
				Date time = (Date) map.get("Time");
				Integer id = (Integer) map.get("id");
				String cmsid = (String) map.get("Cmsid");
				Double unloaderMove = (Double) map.get("unloaderMove");

				// 查询卸船机作业数据任务ID、船舱ID
				Object[] args = new Object[] { unloaderMove, unloaderMove, time };
				List<Map<String, Object>> cabinNums = this.getJdbcTemplate().queryForList(sqlMap.getSql("02"), args);

				if (cabinNums == null || cabinNums.isEmpty()) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 未找到船舱信息！");
					args = new Object[] { unloaderMove, unloaderMove, time, time };
					List<Map<String, Object>> tasks = this.getJdbcTemplate().queryForList(sqlMap.getSql("18"), args);
					if (tasks != null && !tasks.isEmpty()) {
						String sql = "";
						taskId = (Integer) tasks.get(0).get("taskId");
						try {
							// 自动创建任务子表
							autoCreateDBTable.createTable(taskId);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						List<Map<String, Object>> list2 = this.getJdbcTemplate()
								.queryForList(sqlMap.getSql("19", taskId), cmsid);
						for (Map<String, Object> map2 : list2) {
							Integer id2 = (Integer) map2.get("id");
							sql = sqlMap.getSql("setFinishTicket", taskId);
							args = new Object[] { id2 };
							this.getJdbcTemplate().update(sql, args);
						}
					}

					args = new Object[] { "未找到所属船舶！", id, cmsid };
					dataSyncLogsHelper.dataSyncStepbLogs(2, args);

					// 删除临时表作业数据
					args = new Object[] { id, cmsid };
					this.getJdbcTemplate().update(sqlMap.getSql("04"), args);
					continue;
				}

				if (cabinNums.size() > 1) {
					// log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "]
					// 匹配到多个船舱信息！");
				}
				cabinId = (Integer) cabinNums.get(0).get("id");
				taskId = (Integer) cabinNums.get(0).get("taskId");

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
						// 获取组编号
						groupId = calc(taskId, cabinId, cmsid, operationType, time);

						// 维护开工时间（由系统自动计算，以船舶的靠泊时间为起始点，判断卸船机第一斗的时间为开工时间）
						String beginTime = this.getJdbcTemplate().queryForObject(
								" SELECT t.begin_time from tab_task t WHERE t.id = ? ", String.class, taskId);
						if (beginTime == null) {
							this.getJdbcTemplate().update("UPDATE tab_task t SET t.begin_time = ? WHERE t.id = ?",
									new Object[] { time, taskId });
						}

						args = new Object[] { groupId, id, cmsid };
						this.getJdbcTemplate().update(sqlMap.getSql("03", taskId), args);
					}

					args = new Object[] { "已找到所属船舶！", groupId, id, cmsid };
					dataSyncLogsHelper.dataSyncStepbLogs(1, args);

					// 删除临时表作业数据
					args = new Object[] { id, cmsid };
					this.getJdbcTemplate().update(sqlMap.getSql("04"), args);
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
	Integer calc(Integer taskId, Integer cabinId, String cmsid, int operationType, Date time) {
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
			List<Map<String, Object>> groups = this.getJdbcTemplate().queryForList(sqlMap.getSql("08", taskId), cmsid,
					groupId);
			if (groups != null) {
				for (Map<String, Object> m : groups) {
					Integer id = (Integer) m.get("id");
					sql = sqlMap.getSql("setFinishTicket", taskId);
					args = new Object[] { id };
					this.getJdbcTemplate().update(sql, args);
				}
			}
		} else {
			Map<String, Object> map = list.get(0);
			groupId = (Integer) map.get("id");
			Date firstTime = (Date) map.get("firstTime");
			switch (operationType) {
			case 0:
				sql = sqlMap.getSql("updateTicketByDisplacementInfo", taskId);
				args = new Object[] { time, groupId };
				this.getJdbcTemplate().update(sql, args);
				break;
			case 1:
				if (firstTime == null) {
					sql = sqlMap.getSql("updateTicketByJobInfoForFirstTimeIsNull", taskId);
					args = new Object[] { time, time, time, groupId };
				} else {
					sql = sqlMap.getSql("updateTicketByJobInfo", taskId);
					args = new Object[] { time, time, groupId };
				}
				this.getJdbcTemplate().update(sql, args);
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
	public void updateGroupEndTime(Integer taskId) {
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("13", taskId), taskId);
		for (Map<String, Object> m : list) {
			Integer groupId = (Integer) m.get("id");
			Object[] args = new Object[] { groupId };
			this.getJdbcTemplate().update(sqlMap.getSql("setFinishTicket", taskId), args);
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
