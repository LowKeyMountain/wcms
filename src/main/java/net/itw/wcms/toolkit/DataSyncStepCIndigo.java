package net.itw.wcms.toolkit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ICabinService;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.toolkit.sql.SqlMap;

/**
 * 数据同步 > 步骤C(将船舶任务子表数据同步到临时表)实现类
 * 
 * @author Michael 29 Jan 2018 22:42:25
 */
@Service("dataSyncStepCIndigo")
@Transactional
public class DataSyncStepCIndigo implements DataSyncStepC {

	private final Logger log = Logger.getLogger("dataSyncInfo");

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

	private void delete(int taskId) {
		try {
			// 【任务子表】删除任务子表：卸船作业信息
			jdbcTemplate.update(sqlMap.getSql("02", "tab_temp_b_" + taskId));
			// 【任务子表】删除任务子表：组信息
			jdbcTemplate.update(sqlMap.getSql("03", "tab_temp_c_" + taskId));
			// 删除任务表开工时间
			jdbcTemplate.update(sqlMap.getSql("06"), new Object[] { taskId });
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
		final Date currentTime = new Date();
		Task task = taskService.getTaskById(taskId);
		List<Cabin> cabins = cabinService.findAllByTaskId(taskId);
		// 升序排列
		Collections.sort(cabins, new Comparator<Cabin>() {
			public int compare(Cabin c1, Cabin c2) {
				if (c1.getCabinNo() == null || c2.getCabinNo() == null) {
					return 0;
				}
				return c1.getCabinNo().compareTo(c2.getCabinNo());
			}
		});
		for (int i = 0; i < cabins.size(); i++) {
			Cabin cabin = cabins.get(i);
			// 获取上一舱
			Cabin up_cabin = null;
			if (i != 0) {
				int up_index = i - 1;
				up_cabin = cabins.get(up_index);
			}
			// 获取下一舱
			Cabin down_cabin = null;
			if (i != cabins.size() - 1) {
				int down_index = i + 1;
				down_cabin = cabins.get(down_index);
			}
			Date startTime = task.getEnterPortTime();
			Date endTime = task.getEndTime() == null ? currentTime : task.getEndTime();
			Float p1 = up_cabin != null ? up_cabin.getStartPosition() : 0;
			Float p2 = cabin.getStartPosition();
			Float p3 = cabin.getEndPosition();
			Float p4 = down_cabin != null ? down_cabin.getEndPosition() : 0;
			for (int cmsNo = 1; cmsNo <= 6; cmsNo++) {
				calcGroup(taskId, cabin.getId(), "ABB_GSU_" + cmsNo, startTime, endTime, p1, p2, p3, p4);
			}
		}

		// 批量更新任务子表数据
		List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sqlMap.getSql("11", taskId));
		log.info("[" + taskId + "] 共创建组 " + result.size() + "条");
		System.out.println("[" + taskId + "] 共创建组 " + result.size() + "条");
		for (Map<String, Object> m : result) {
			int groupId = (int) m.get("id");
			String cmsid = (String) m.get("Cmsid");
			int cabinId = (int) m.get("cabinId");
			Date g_startTime = (Date) m.get("startTime");
			Date g_endTime = (Date) m.get("endTime");
			g_endTime = g_endTime == null ? currentTime : g_endTime;
			Cabin cabin = cabinService.findOne(cabinId);

			List<Object[]> batchArgs = new ArrayList<Object[]>();
			Object[] args = new Object[] { groupId, cmsid, g_startTime, g_endTime, cabin.getStartPosition(),
					cabin.getEndPosition() };
			batchArgs.add(args);
			
			// 更新组最后一次作业时间、组结束时间
			List<Map<String, Object>> result1 = this.jdbcTemplate.queryForList(sqlMap.getSql("12"), args);
			if (!result1.isEmpty()) {
				Date time = (Date) result1.get(0).get("Time");
				this.jdbcTemplate.update(sqlMap.getSql("13", taskId), new Object[] { time, time, groupId });
			}
			// 从卸船机总表批量拷贝作业信息到任务子表
			int [] batchResult = this.jdbcTemplate.batchUpdate(sqlMap.getSql("07", taskId), batchArgs);
			log.info("[" + taskId + "] ["+groupId+"]组 更新数据 " + batchResult[0] + "条");
			System.out.println("[" + taskId + "] ["+groupId+"]组 更新数据 " + batchResult[0] + "条");
		}
	}

	private void calcGroup(int taskId, int cabinId, String cmsid, Date startTime, Date endTime, Float p1,
			Float p2, Float p3, Float p4) {

		Object[] args = new Object[] { cmsid, startTime, endTime, p2, p3 };
		List<Map<String, Object>> result1 = this.jdbcTemplate.queryForList(sqlMap.getSql("04"), args);

		if (!result1.isEmpty()) {
			Date newStartTime = (Date) result1.get(0).get("Time");
			args = new Object[] { cmsid, newStartTime, endTime, p1, p2, p3, p4 };
			List<Map<String, Object>> result2 = this.jdbcTemplate.queryForList(sqlMap.getSql("05"), args);
			
			// 维护开工时间（由系统自动计算，以船舶的靠泊时间为起始点，判断卸船机第一斗的时间为开工时间）
			String beginTime = this.jdbcTemplate.queryForObject(sqlMap.getSql("08"), String.class, taskId);
			if (beginTime == null) {
				this.jdbcTemplate.update(sqlMap.getSql("09"), new Object[] { newStartTime, taskId });
			}
			
			if (result2.isEmpty()) {
				KeyHolder keyHolder = new GeneratedKeyHolder();
				this.jdbcTemplate.update(new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(sqlMap.getSql("14", taskId),
								Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) newStartTime);
						ps.setTimestamp(4, (Timestamp) newStartTime);
						ps.setInt(5, 0);
						return ps;
					}
				}, keyHolder);
			} else {
				Date newEndTime = (Date) result2.get(0).get("Time");

				// 新建组信息
				KeyHolder keyHolder = new GeneratedKeyHolder();
				this.jdbcTemplate.update(new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(sqlMap.getSql("10", taskId),
								Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, cabinId);
						ps.setString(2, cmsid);
						ps.setTimestamp(3, (Timestamp) newStartTime);
						ps.setTimestamp(4, (Timestamp) newStartTime);
						ps.setTimestamp(5, (Timestamp) newEndTime);
						ps.setInt(6, 1);
						return ps;
					}
				}, keyHolder);
				calcGroup(taskId, cabinId, cmsid, newEndTime, endTime, p1, p2, p3, p4);
			}
		}
	}

}
