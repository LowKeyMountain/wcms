package net.itw.wcms.toolkit;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ICabinService;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.ship.service.impl.CabinServiceImpl;
import net.itw.wcms.ship.service.impl.TaskServiceImpl;
import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤C(将船舶任务子表数据同步到临时表)快速计算实现类
 * 
 * @author Michael 29 Jan 2018 22:42:25
 */
/**
 * 
 * Description:
 * 
 * @author Michael 20 May 2019 04:01:03
 */
@Service("dataSyncStepCIndigo")
@Transactional
public class DataSyncStepCIndigo implements DataSyncStepC {

	private Logger log = Logger.getLogger("dataSyncInfo");

	private static SqlMap sqlMap;
	private static Map<Object, Object> taskCache = new HashMap<>();

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ITaskService taskService;
	@Autowired
	private ICabinService cabinService;

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
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./DataSyncC_Indigo.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private DataSyncStepA dataSyncStepA;
	@Autowired
	private DataSyncStepB dataSyncStepB;
	@Autowired
	private AutoCreateDBTable autoCreateDBTable;

	/**
	 * 启动
	 * 
	 * @param taskId
	 * @throws Exception
	 */
	public void start(int taskId) throws Exception {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (taskCache.isEmpty()) {
						dataSyncStepA.stop();
						dataSyncStepB.stop();
					}
					taskCache.put(taskId, taskId);
					// 自动创建任务子表
					autoCreateDBTable.createTable(taskId);
					delete(taskId);
					resync(taskId);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					taskCache.remove(taskId);
					if (taskCache.isEmpty()) {
						dataSyncStepA.restart();
						dataSyncStepB.restart();
					}
				}
			}
		}, 1000);
	}

	public void delete(int taskId) {
		try {
			// 【任务子表】删除任务子表：卸船作业信息
			jdbcTemplate.update(" DELETE FROM tab_temp_b_" + taskId);
			// 【任务子表】删除任务子表：组信息
			jdbcTemplate.update(" DELETE FROM tab_temp_c_" + taskId);
			// 删除任务表开工时间
			jdbcTemplate.update(" UPDATE tab_task t SET t.begin_time = null WHERE t.id = ? ", new Object[] { taskId });
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 重新同步任务子表数据
	 * 
	 * @param taskId
	 */
	private void resync(int taskId) {
		long a = System.currentTimeMillis();

		log.info("[taskId:" + taskId + "][" + DateTimeUtils.now2StrDateTime() + "]开始快速计算！");
		System.out.println("[taskId:" + taskId + "][" + DateTimeUtils.now2StrDateTime() + "]开始快速计算！");
		try {
			Task task = taskService.getTaskById(taskId);
			if (task == null) {
				return;
			}
			List<Cabin> cabins = cabinService.findAllByTaskId(taskId);
			// 对船舱进行排序
			cabinSorting(cabins);
			
			// 创建卸船机数据临时表
			jdbcTemplate.update(sqlMap.getSql("04", taskId));
			jdbcTemplate.update(sqlMap.getSql("06", taskId));
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			Object[] args = new Object[] { task.getBerthingTime(), this.getTaskEndTime(task),
					cabins.get(0).getStartPosition(), cabins.get(cabins.size() - 1).getEndPosition() };
			batchArgs.add(args);
			int[] batchResult = this.jdbcTemplate.batchUpdate(sqlMap.getSql("05", taskId), batchArgs);
			log.info("[taskId:" + taskId + "] 本次需要处理数据" + batchResult[0] + "条。");
			System.out.println("[taskId:" + taskId + "] 本次需要处理数据" + batchResult[0] + "条。");

			// 维护开工时间
			makeBeginTime(task, cabins);
			// 遍历每台卸船机，进行快速处理
			for (int i = 1; i <= 6; i++) {
				quickHandle(task, cabins,
						getEnterCabinFirstShovel(task, cabins, getTaskBeginTime(task), "ABB_GSU_" + i));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} finally {
			// 删除卸船机数据临时表
			if (taskId != 0) {
				jdbcTemplate.update("DROP TABLE tab_unloader_all_" + taskId + ";");
			}
		}

		log.info("[taskId:" + taskId + "][" + DateTimeUtils.now2StrDateTime() + "]快速计算结束！");
		System.out.println("[taskId:" + taskId + "][" + DateTimeUtils.now2StrDateTime() + "]快速计算结束！");
		log.info("[taskId:" + taskId + "]快速计算运行消耗:" + (System.currentTimeMillis() - a) / (1000 * 60) + "分钟");
		System.out.println("[taskId:" + taskId + "]快速计算运行消耗:" + (System.currentTimeMillis() - a) / (1000 * 60) + "分钟");
	}

	/**
	 * 维护开工时间（由系统自动计算，以船舶的靠泊时间为起始点，判断卸船机第一斗的时间为开工时间）
	 * 
	 * @param task
	 * @param cabins
	 */
	private void makeBeginTime(Task task, List<Cabin> cabins) {
		Map<String, Object> data = getShipFirstShovel(task, cabins);
		if (data == null) {
			return;
		}
		String beginTime = this.jdbcTemplate.queryForObject(" SELECT t.begin_time from tab_task t WHERE t.id = ? ",
				String.class, task.getId());
		if (beginTime == null) {
			this.jdbcTemplate.update("UPDATE tab_task t SET t.begin_time = ? WHERE t.id = ?",
					new Object[] { (Date) data.get("Time"), task.getId() });
		}
	}

	/**
	 * 对船舱进行排序（按照船舱位置从小到大）
	 * 
	 * @param cabins
	 */
	private void cabinSorting(List<Cabin> cabins) {
		Map<Integer, Cabin> cabinMap = new HashMap<>();
		for (Cabin c : cabins) {
			cabinMap.put(c.getCabinNo(), c);
		}
		int direction = this.getShipDirection(cabinMap);
		Collections.sort(cabins, new Comparator<Cabin>() {
			public int compare(Cabin c1, Cabin c2) {
				if (c1.getCabinNo() == null || c2.getCabinNo() == null) {
					return 0;
				}
				return direction == 1 ? c1.getCabinNo().compareTo(c2.getCabinNo())
						: c2.getCabinNo().compareTo(c1.getCabinNo());
			}
		});
	}

	/**
	 * 数据快速处理
	 * 
	 * @param task
	 * @param cabins
	 * @param enter_data
	 *            卸船机非舱外第一铲数据
	 */
	private void quickHandle(Task task, List<Cabin> cabins, Map<String, Object> enter_data) {

		if (enter_data == null) {
			return;
		}

		
		int taskId = task.getId();

		int enter_id = (int) enter_data.get("id");
		Date enter_time = (Date) enter_data.get("Time");
		String enter_cmsid = (String) enter_data.get("Cmsid");
		int enter_operationType = (int) enter_data.get("operationType");
		double enter_unloaderMove = (Double) enter_data.get("unloaderMove");
		Cabin enter_cabin = this.getCabinByUnloaderMove(cabins, enter_unloaderMove);
		
		// 计算组编号
		int groupId = dataSyncStepB.calc(taskId, enter_cabin.getId(), enter_cmsid, enter_operationType,
				enter_time);
		
		// 查询卸船机出舱第一铲数据
		Map<String, Object> out_data = getOutCabinFirstShovel(task, cabins, enter_data);
		if (out_data == null) {
			// 批量入库
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			Object[] args = new Object[] { groupId, enter_cmsid, enter_time, this.getTaskEndTime(task), enter_cabin.getStartPosition(),
					enter_cabin.getEndPosition() };
			batchArgs.add(args);

			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlMap.getSql("08", taskId), args);
			if (list != null && !list.isEmpty() && enter_id != (int) list.get(0).get("id")) {
				dataSyncStepB.calc(taskId, enter_cabin.getId(), (String) list.get(0).get("Cmsid"),
						(int) list.get(0).get("operationType"), (Date) list.get(0).get("Time"));
			}

			int[] batchResult = this.jdbcTemplate.batchUpdate(sqlMap.getSql("07", taskId, taskId), batchArgs);
			log.info("[taskId:" + taskId + "] [groupId:" + groupId + "] [Cmsid:" + enter_cmsid + "] 最后几铲更新数据 " + batchResult[0] + "条");
			System.out.println("[taskId:" + taskId + "] [groupId:" + groupId + "] [Cmsid:" + enter_cmsid + "] 最后几铲更新数据 " + batchResult[0] + "条");
			return;
		}

		Date out_time = (Date) out_data.get("Time");
		String out_cmsid = (String) out_data.get("Cmsid");
		double out_unloaderMove = (Double) out_data.get("unloaderMove");

		// 批量入库
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		Object[] args = new Object[] { groupId, enter_cmsid, enter_time, out_time, enter_cabin.getStartPosition(),
				enter_cabin.getEndPosition() };
		batchArgs.add(args);

		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlMap.getSql("08", taskId), args);
		if (list != null && !list.isEmpty() && enter_id != (int) list.get(0).get("id")) {
			dataSyncStepB.calc(taskId, enter_cabin.getId(), (String) list.get(0).get("Cmsid"),
					(int) list.get(0).get("operationType"), (Date) list.get(0).get("Time"));
		}

		int[] batchResult = this.jdbcTemplate.batchUpdate(sqlMap.getSql("07", taskId, taskId), batchArgs);
		log.info("[taskId:" + taskId + "] [groupId:" + groupId + "] 更新数据 " + batchResult[0] + "条");
		System.out.println("[taskId:" + taskId + "] [groupId:" + groupId + "] 更新数据 " + batchResult[0] + "条");

		// 获取船舱信息, 根据卸船机位置
		Cabin cabin = this.getCabinByUnloaderMove(cabins, out_unloaderMove);
		if (cabin == null) {// 舱外数据
			String sql = " select * from tab_temp_c_" + taskId + " t where t.`status` = 0 AND t.Cmsid = ? ";
			List<Map<String, Object>> list2 = this.jdbcTemplate.queryForList(sql, out_cmsid);
			for (Map<String, Object> map2 : list2) {
				int id2 = (int) map2.get("id");
				sql = " UPDATE tab_temp_c_" + taskId + "   t SET t.status = 1 WHERE t.id = ?  ";
				this.jdbcTemplate.update(sql, new Object[] { id2 });
			}
			// 查询卸船机第一铲（非舱外数据）
			quickHandle(task, cabins, getEnterCabinFirstShovel(task, cabins, out_time, out_cmsid));
		} else { // 舱内数据
			quickHandle(task, cabins, out_data);
		}
	}

	/**
	 * 查询卸船机出舱第一铲数据
	 * 
	 * @param task
	 * @param cabins
	 * @param data
	 * @return
	 */
	private Map<String, Object> getOutCabinFirstShovel(Task task, List<Cabin> cabins, Map<String, Object> data) {
		Date beginTime = (Date) data.get("Time");
		String cmsid = (String) data.get("Cmsid");
		double unloaderMove = (Double) data.get("unloaderMove");
		Cabin excludeCabin = this.getCabinByUnloaderMove(cabins, unloaderMove);

		String sql = " SELECT * FROM tab_unloader_all_" + task.getId()
				+ " b WHERE (b.operationType = 1) ";
		sql += " AND  b.Time > '" + DateTimeUtils.date2StrDateTime(beginTime) + "' AND b.Time <= '"
				+ DateTimeUtils.date2StrDateTime(getTaskEndTime(task)) + "' AND b.Cmsid = '" + cmsid + "'";
		sql += " AND ( b.unloaderMove < " + excludeCabin.getStartPosition() + " OR b.unloaderMove > "
				+ excludeCabin.getEndPosition() + " ) ";
		sql += " ORDER BY b.Time ASC limit 1 ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);

		return list != null && !list.isEmpty() ? list.get(0) : null;
	}

	/**
	 * 获取改成第一铲数据（非舱外数据）
	 * 
	 * @param task
	 * @param cabins
	 * @return
	 */
	private Map<String, Object> getShipFirstShovel(Task task, List<Cabin> cabins) {
		String sql = "SELECT * FROM tab_unloader_all_" + task.getId() + " b WHERE b.operationType = 1 ";
		sql += "AND  b.Time >= '" + DateTimeUtils.date2StrDateTime(getTaskBeginTime(task)) + "' " + "AND b.Time <= '"
				+ DateTimeUtils.date2StrDateTime(getTaskEndTime(task)) + "' ";
		sql += " AND ( ";
		for (int i = 0; i < cabins.size(); i++) {
			sql += " ( b.unloaderMove >= " + cabins.get(i).getStartPosition() + " AND b.unloaderMove <= "
					+ cabins.get(i).getEndPosition() + " ) ";
			if (i != (cabins.size() - 1)) {
				sql += " OR";
			}
		}
		sql += " )  ORDER BY b.Time ASC limit 1";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 查询卸船机第一铲（非舱外数据）
	 * 
	 * @param task
	 * @param cabins
	 * @param beginTime
	 * @param unloaderName
	 * @return
	 */
	private Map<String, Object> getEnterCabinFirstShovel(Task task, List<Cabin> cabins, Date beginTime,
			String unloaderName) {
		String sql = " SELECT * FROM tab_unloader_all_" + task.getId() + " b WHERE b.operationType = 1 ";
		sql += "AND  b.Time >= '" + DateTimeUtils.date2StrDateTime(beginTime) + "' " + "AND b.Time <= '"
				+ DateTimeUtils.date2StrDateTime(getTaskEndTime(task)) + "' ";
		sql += " AND b.Cmsid = '" + unloaderName + "'  ";
		sql += " AND ( ";
		for (int i = 0; i < cabins.size(); i++) {
			sql += " ( b.unloaderMove >= " + cabins.get(i).getStartPosition() + " AND b.unloaderMove <= "
					+ cabins.get(i).getEndPosition() + " ) ";
			if (i != (cabins.size() - 1)) {
				sql += " OR";
			}
		}
		sql += " ) ORDER BY b.Time ASC limit 1 ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 根据位置获取船舱信息
	 * 
	 * @param cabins
	 * @param unloaderMove
	 * @return
	 */
	private Cabin getCabinByUnloaderMove(List<Cabin> cabins, Double unloaderMove) {
		for (Cabin cabin : cabins) {
			if (cabin.getStartPosition() <= unloaderMove && cabin.getEndPosition() >= unloaderMove) {
				return cabin;
			}
		}
		return null;
	}

	/**
	 * 获取任务开始时间
	 * 
	 * @param task
	 */
	private Date getTaskBeginTime(Task task) {
		return task.getBerthingTime();
	}

	/**
	 * 获取任务结束时间
	 * 
	 * @param task
	 */
	private Date getTaskEndTime(Task task) {
		return task.getEndTime() != null ? task.getEndTime() : new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取船舶方向 1|正、-1|负
	 * 
	 * @param cabinMap
	 * @return
	 */
	private int getShipDirection(Map<Integer, Cabin> cabinMap) {
		final int count = cabinMap.size(); // 船舱数
		Cabin x = null;
		for (int i = 1; i <= count; i++) {
			Cabin cabin = cabinMap.get(i);
			if (cabin.getStartPosition() == 0 && cabin.getEndPosition() == 0) {
				continue;
			}
			if (x == null) {
				x = cabin;
				continue;
			}
			Float value = cabin.getStartPosition() - x.getStartPosition();
			if (value > 0) {
				return 1;
			} else {
				return -1;
			}
		}
		return 1;
	}

	/**
	 * 程序入口
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		if (ctx != null) {
			DataSyncStepCIndigo c = (DataSyncStepCIndigo) ctx.getBean("dataSyncStepCIndigo");
			c.jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
			c.taskService = (ITaskService) ctx.getBean(TaskServiceImpl.class);
			c.cabinService = (ICabinService) ctx.getBean(CabinServiceImpl.class);
			c.dataSyncStepB = (DataSyncStepB) ctx.getBean(DataSyncStepB.class);
			c.dataSyncStepA = (DataSyncStepA) ctx.getBean(DataSyncStepA.class);
			c.autoCreateDBTable = (AutoCreateDBTable) ctx.getBean(AutoCreateDBTable.class);
			c.log = Logger.getLogger("dataSyncInfo");
			c.resync(388);
		}
	}
}
