package net.itw.wcms.toolkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 卸船机数据同步工具
 * 
 * @author Michael 30 Dec 2017 15:02:25
 */
public class DataSyncHelper extends JdbcDaoSupport {

	public static boolean isBreakRun;
	private final Logger log = Logger.getLogger("dataSyncInfo");

	/**
	 * 初始化（同步卸船机数据工具）
	 * 
	 */
	public void init() {
		isBreakRun = false;
		log.info("初始化同步工具...");
	}

	/**
	 * 启动（同步卸船机数据工具）
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		log.info("同步工具成功...");
		while (!isBreakRun) {
			log.info("同步工具运行中...");
			for (int i = 1; i <= 6; i++) {
				sync(i);
			}
			calcGroup();
			log.info("同步工具休眠5秒...");
			Thread.sleep(5000);
		}
	}

	/**
	 * 重启（同步卸船机数据工具）
	 * 
	 * @throws InterruptedException
	 */
	public void restart() throws InterruptedException {
		log.info("同步工具重启中...");
		if (isBreakRun) {
			isBreakRun = false;
		}
		start();
	}

	/**
	 * 关闭（同步卸船机数据工具）
	 * 
	 */
	public void stop() {
		isBreakRun = true;
		log.info("同步工具已关闭...");
	}

	private void sync(int num) {
		int max = 0;
		boolean isUpdateData = false;
		try {
			// 查询卸船机增量数据
			String sql = " select * from tab_unloader_" + num
					+ " t where t.id > ( select t.id from tab_temp_d t where t.unloaderId = 'ABB_GSU_" + num
					+ "' ) ORDER BY id ASC ";
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");
				// 过滤卸船机在线状态数据
				if (2 == operationType) {
					continue;
				}

				Integer id = (Integer) map.get("id");
				String cmsid = (String) map.get("Cmsid");

				// 检查数据是否存在
				sql = " select  * from tab_temp_b t where t.id = ? AND t.Cmsid = ? ";
				List<?> list2 = this.getJdbcTemplate().queryForList(sql, id, cmsid);
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
					sql = " " + "insert into tab_temp_b "
							+ " (id, Time, Cmsid, PushTime, OneTask, direction, unloaderMove, operationType, offset, groupId) "
							+ "values(?,?,?,?,?,?,?,?,?,?) ";

					Object[] args = new Object[] { id, time, cmsid, pushTime, oneTask, direction, unloaderMove,
							operationType, 0, 0 };
					this.getJdbcTemplate().update(sql, args);
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
				String sql = " UPDATE tab_temp_d t SET t.id = " + max + " WHERE t.unloaderId = 'ABB_GSU_" + num + "'";
				this.getJdbcTemplate().update(sql);
			}
		}
	}

	/**
	 * 创建组并返回组编号
	 *
	 * @param cabinId
	 *            船舱ID
	 * @param cmsid
	 *            卸船机编号
	 * @param operationType
	 *            数据类型
	 * @param time
	 *            操作时间
	 * @param unloaderMove
	 *            位置
	 * @return
	 */
	private Integer createGroup(Integer cabinId, String cmsid, int operationType, Date time, Double unloaderMove) {
		int groupId = 0;
		Object[] args = null;
		// 查询组信息，根据舱ID、 卸船机ID
		String sql = " select * from tab_temp_c t where t.`status` = 0 and t.cabinId = ? AND t.Cmsid = ? ";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql, cabinId, cmsid);
		int conut = list.size();

		if (conut == 0) {
			// 新建组信息
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.getJdbcTemplate().update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String inster_sql = "";
					if (0 == operationType) {
						inster_sql = " insert into tab_temp_c (cabinId, Cmsid, startTime, status)  values(?,?,?,?)";
					} else if (1 == operationType) {
						inster_sql = " insert into tab_temp_c (cabinId, Cmsid, startTime, firstTime, lastTime, status)  values(?,?,?,?,?,?)";
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
			sql = " select * from tab_temp_c t where t.`status` = 0 AND t.Cmsid = ? AND t.id != ? ";
			List<Map<String, Object>> groups = this.getJdbcTemplate().queryForList(sql, cmsid, groupId);
			if (groups != null) {
				for (Map<String, Object> m : groups) {
					Integer id = (Integer) m.get("id");
					if (0 == operationType) {
						sql = " UPDATE tab_temp_c t SET t.endTime = ?, t.status = 1 WHERE t.id = ? ";
						args = new Object[] { time, id };
					} else if (1 == operationType) {
						sql = " UPDATE tab_temp_c t SET t.endTime = ?, t.lastTime = ?, t.status = 1 WHERE t.id = ? ";
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
					sql = " UPDATE tab_temp_c t SET t.firstTime = ?, t.lastTime = ?  WHERE t.id = ? ";
					args = new Object[] { time, time, groupId };
				} else {
					sql = " UPDATE tab_temp_c t SET t.lastTime = ?  WHERE t.id = ? ";
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
					sql = " UPDATE tab_temp_c t SET t.firstTime = ?, t.lastTime = ?  WHERE t.id = ? ";
					args = new Object[] { time, time, groupId };
				} else {
					sql = " UPDATE tab_temp_c t SET t.lastTime = ?  WHERE t.id = ? ";
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
		String sql = " select c2.id from tab_temp_c c2 LEFT JOIN v_cabin_info c1 ON c1.cabin_id = c2.cabinId where  c2.`status` = 0 AND c1.task_id = ? ";
		List<Integer> list = this.getJdbcTemplate().queryForList(sql, Integer.class, taskId);
		for (Integer groupId : list) {
			sql = " UPDATE tab_temp_c t SET t.endTime = ?, t.status = ? WHERE t.id = ? ";
			Object[] args = new Object[] { new Date(), 1, groupId };
			this.getJdbcTemplate().update(sql, args);
		}
	}

	/**
	 * 计算卸船机推送数据组信息
	 * 
	 */
	public void calcGroup() {
		int num = 0;
		try {
			String sql = "  select * from tab_temp_b b where b.groupId = 0 ORDER BY b.Cmsid, id ASC  ";
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
			log.info("待计算数据" + list.size() + "条。");
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");
				// 过滤卸船机在线状态数据
				if (2 == operationType) {
					continue;
				}

				Integer cabinId = 0;
				Integer groupId = 0;
				Double offset = 0.0; // 船舶位置偏移量
				Date time = (Date) map.get("Time");
				Integer id = (Integer) map.get("id");
				String cmsid = (String) map.get("Cmsid");
				Double unloaderMove = (Double) map.get("unloaderMove");

				// 查询船舱ID
				sql = " SELECT cabin.id, t.offset FROM tab_cabin cabin "
						+ "LEFT JOIN ( SELECT c.*, task.`offset`, task.`status`, task.begin_time, "
						+ "CASE WHEN task.end_time IS NULL THEN CURRENT_TIMESTAMP () "
						+ "ELSE task.end_time END AS 'end_time' FROM tab_cargo c LEFT JOIN "
						+ "tab_task task ON c.task_id = task.id ) t ON cabin.cargo_id = t.id "
						+ "WHERE t.`status` = 1 AND cabin.start_position <= ? "
						+ "AND cabin.end_position >= ? AND t.begin_time <= ? AND t.end_time >= ? ";

				Object[] args = new Object[] { unloaderMove, unloaderMove, time, time };

				List<Map<String, Object>> cabinNums = this.getJdbcTemplate().queryForList(sql, args);

				if (cabinNums == null || cabinNums.isEmpty()) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 未找到船舱信息！");
					sql = " select * from tab_temp_c t where t.`status` = 0 AND t.Cmsid = ? ";
					List<Map<String, Object>> list2 = this.getJdbcTemplate().queryForList(sql, cmsid);
					for (Map<String, Object> map2 : list2) {
						Integer id2 = (Integer) map2.get("id");
						if (0 == operationType) {
							sql = " UPDATE tab_temp_c t SET t.endTime = ?, t.status = 1 WHERE t.id = ? ";
							args = new Object[] { time, id2 };
						} else if (1 == operationType) {
							sql = " UPDATE tab_temp_c t SET t.endTime = ?, t.lastTime = ?, t.status = 1 WHERE t.id = ? ";
							args = new Object[] { time, time, id2 };
						}
						this.getJdbcTemplate().update(sql, args);
					}
					continue;
				} else {
					if (cabinNums.size() > 1) {
						log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 匹配到多个船舱信息！");
					}
					cabinId = (Integer) cabinNums.get(0).get("id");
					offset = (Double) cabinNums.get(0).get("offset");
					if (offset == null) {
						offset = 0.0;
					}
					groupId = createGroup(cabinId, cmsid, operationType, time, unloaderMove);
				}

				// 更新表b数据
				try {
					sql = " update tab_temp_b b set b.groupId = ?, b.offset = ? where b.id = ? and b.cmsid = ? ";
					args = new Object[] { groupId, offset, id, cmsid };
					this.getJdbcTemplate().update(sql, args);
					num++;
					log.info("数据编号[" + id + "]|卸船机编号[" + cmsid + "] 数据已计算组信息！");
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
					continue;
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
	 * 修改舱位时，需重新计算相关数据组信息
	 * 
	 * @param taskId
	 */
	public void modifyCabinPosition(Integer taskId) {
		this.stop();
		try {
			String sql = " UPDATE tab_temp_b b SET b.groupId = 0 where b.groupId in "
					+ " ( select c2.id from tab_temp_c c2 LEFT JOIN v_cabin_info c1 "
					+ "ON c1.cabin_id = c2.cabinId where c1.task_id = ? )";
			this.getJdbcTemplate().update(sql, taskId);

			sql = " DELETE FROM tab_temp_c t where t.id in "
					+ "( select c2.id from tab_temp_c c2 LEFT JOIN v_cabin_info c1 ON "
					+ " c1.cabin_id = c2.cabinId where c1.task_id = ?  )";
			this.getJdbcTemplate().execute(sql);
			calcGroup();
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			try {
				this.restart();
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage());
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
			DataSyncHelper helper = (DataSyncHelper) ctx.getBean("dataSyncHelper");
			helper.start();
		}
	}

}
