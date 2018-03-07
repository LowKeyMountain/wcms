package net.itw.wcms.toolkit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤C(将船舶任务子表数据同步到临时表)
 * 
 * @author Michael 29 Jan 2018 22:42:25
 */
public class DataSyncStepC extends JdbcDaoSupport {

	private final Logger log = Logger.getLogger("dataSyncInfo");

	private static SqlMap sqlMap;
	private static Map<Object, Object> taskCache = new HashMap<>();

	/**
	 * 是否启动步骤C <br>
	 * 
	 * 启动步骤C时，步骤A、B需暂停；步骤C执行完后，重启步骤A、B
	 * 
	 * @return
	 */
	public static boolean isStartStepC() {
		if (taskCache.isEmpty()) {
			return false;
		}
		return true;
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

	@Autowired
	private DataSyncStepB dataSyncStepB;
	@Autowired
	private AutoCreateDBTable autoCreateDBTable;

	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return this.getJdbcTemplate().queryForList(sql, args);
	}

	/**
	 * 启动
	 * 
	 * @param taskId
	 * @throws Exception
	 */
	public void start(Integer taskId) throws Exception {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				taskCache.put(taskId, taskId);
				try {
					// 自动创建任务子表
					autoCreateDBTable.createTable(taskId);
					delete(taskId);
					resync(taskId);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					taskCache.remove(taskId);
				}
			}
		}, 1000);
	}

	private void delete(Integer taskId) {
		try {
			// 【任务子表】删除任务子表：卸船作业信息
			this.getJdbcTemplate().update(sqlMap.getSql("02", "tab_temp_b_" + taskId));
			// 【任务子表】删除任务子表：组信息
			this.getJdbcTemplate().update(sqlMap.getSql("03", "tab_temp_c_" + taskId));
			// 删除任务表开工时间
			this.getJdbcTemplate().update("UPDATE tab_task t SET t.begin_time = null WHERE t.id = ?",
					new Object[] { taskId });
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 重新同步任务子表数据
	 * 
	 * @param tid
	 */
	private void resync(Integer tid) {
		int num = 0;
		try {
			// 查询临时表待处理数据
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("04"), tid, tid, tid,
					tid);
			for (Map<String, Object> map : list) {

				Integer operationType = (Integer) map.get("operationType");

				Integer taskId = 0;
				Integer cabinId = 0;
				Integer groupId = 0;
				Date time = (Date) map.get("Time");
				Integer id = (Integer) map.get("uid");
				String cmsid = (String) map.get("Cmsid");
				// Double unloaderMove = (Double) map.get("unloaderMove") + 7;
				Double unloaderMove = (Double) map.get("unloaderMove");

				// 查询卸船机作业数据任务ID、船舱ID
				Object[] args = new Object[] { unloaderMove, unloaderMove, time, time };
				List<Map<String, Object>> cabinNums = this.getJdbcTemplate().queryForList(sqlMap.getSql("06"), args);

				if (cabinNums == null || cabinNums.isEmpty()) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 未找到船舱信息！");
					List<Map<String, Object>> tasks = this.getJdbcTemplate().queryForList(sqlMap.getSql("07"), args);
					if (tasks != null && !tasks.isEmpty()) {
						String sql = "";
						taskId = (Integer) tasks.get(0).get("taskId");
						if (taskId != tid) {
							continue;
						}
						List<Map<String, Object>> list2 = this.getJdbcTemplate()
								.queryForList(dataSyncStepB.getSqlMap().getSql("19", taskId), cmsid);
						for (Map<String, Object> map2 : list2) {
							Integer id2 = (Integer) map2.get("id");
							sql = dataSyncStepB.getSqlMap().getSql("setFinishTicket", taskId);
							args = new Object[] { id2 };
							this.getJdbcTemplate().update(sql, args);
						}
					}
					continue;
				}

				if (cabinNums.size() > 1) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 匹配到多个船舱信息！");
				}
				cabinId = (Integer) cabinNums.get(0).get("id");
				taskId = (Integer) cabinNums.get(0).get("taskId");

				if (taskId != tid) {
					continue;
				}

				// 更新表b数据
				try {
					// 【任务子表】将临时表作业数据插入子表
					if (1 == operationType) {
						
						// 查询临时表数据是否存在
						args = new Object[] { id, cmsid };
						List<?> list2 = this.getJdbcTemplate().queryForList(sqlMap.getSql("05-1", taskId), args);
						if (!list2.isEmpty()) {
							continue;
						}
						
						// 获取组编号
						groupId = dataSyncStepB.calc(taskId, cabinId, cmsid, operationType, time);
						
						// 维护开工时间（由系统自动计算，以船舶的靠泊时间为起始点，判断卸船机第一斗的时间为开工时间）
						String beginTime = this.getJdbcTemplate().queryForObject(
								" SELECT t.begin_time from tab_task t WHERE t.id = ? ", String.class, taskId);
						if (beginTime == null) {
							this.getJdbcTemplate().update("UPDATE tab_task t SET t.begin_time = ? WHERE t.id = ?",
									new Object[] { time, taskId });
						}
						this.getJdbcTemplate().update(sqlMap.getSql("05", taskId), groupId, id, cmsid);
						num++;
						
					}
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
