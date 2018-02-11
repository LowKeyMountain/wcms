package net.itw.wcms.toolkit;

import java.io.IOException;
import java.sql.SQLException;
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
	@Autowired
	private DataSyncStepB dataSyncStepB;
	@Autowired
	private AutoCreateDBTable autoCreateDBTable;

	public List<Map<String, Object>> queryForList(String sql, Object... args) {
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
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				delete(taskId);
				resync(taskId);
			}
		}, 1000);
		log.info("同步工具：步骤C 结束...");
	}

	private void delete(Integer taskId) {
		try {
			// 【任务子表】删除任务子表：卸船作业信息
			this.getJdbcTemplate().update(sqlMap.getSql("02", "tab_temp_b_" + taskId));
			// 【任务子表】删除任务子表：组信息
			this.getJdbcTemplate().update(sqlMap.getSql("03", "tab_temp_c_" + taskId));
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
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlMap.getSql("04"), tid, tid);
			log.info("待计算数据" + list.size() + "条。");
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
				Object[] args = new Object[] { unloaderMove, unloaderMove, time, time };
				List<Map<String, Object>> cabinNums = this.getJdbcTemplate()
						.queryForList(dataSyncStepB.getSqlMap().getSql("02"), args);

				if (cabinNums == null || cabinNums.isEmpty()) {
					log.error("数据异常：数据编号[" + id + "]|卸船机编号[" + cmsid + "] 未找到船舱信息！");
					List<Map<String, Object>> tasks = this.getJdbcTemplate()
							.queryForList(dataSyncStepB.getSqlMap().getSql("18"), args);
					if (tasks != null && !tasks.isEmpty()) {
						String sql = "";
						taskId = (Integer) tasks.get(0).get("taskId");
						if (taskId != tid) {
							continue;
						}
						try {
							// 自动创建任务子表
							autoCreateDBTable.createTable(taskId);
						} catch (SQLException e) {
							e.printStackTrace();
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

				try {
					// 自动创建任务子表
					autoCreateDBTable.createTable(taskId);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				groupId = dataSyncStepB.calc(taskId, cabinId, cmsid, operationType, time);

				// 更新表b数据
				try {
					// 【任务子表】将临时表作业数据插入子表
					if ( 1 == operationType) {
						this.getJdbcTemplate().update(sqlMap.getSql("05", taskId), groupId, id, cmsid);
						num++;
						log.info("数据编号[" + id + "]|卸船机编号[" + cmsid + "] 数据已计算组信息！");
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
			log.info("数据已处理" + num + "条。");
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
