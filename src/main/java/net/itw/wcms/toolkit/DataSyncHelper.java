package net.itw.wcms.toolkit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;

import net.itw.wcms.toolkit.jdbc.DBUtil;

/**
 * 
 * Description: 数据同步工具
 * 
 * @author Michael 25 Dec 2017 23:03:21
 */
public class DataSyncHelper {

	private static Connection conn;

	static {
		try {
			conn = DBUtil.openConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动Task数据同步
	 */
	private void start() {
		for (int i = 1; i >= 6; i++) {
			syncShipUnloaderData(i);
		}
	}

	/**
	 * 同步数据 -> 表tab_temp_b
	 * 
	 * @param num
	 */
	private void syncShipUnloaderData(int num) {

		int max = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			// 查询增量数据
			String sql = " select * from tab_unloader_" + num
					+ "t where t.id > ( select t.id from tab_temp_d t where t.unloaderId = 'ABB_GSU_" + num
					+ "' ) ORDER BY id ASC ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Integer operationType = rs.getInt("operationType");
				if (2 == operationType) {
					continue;
				}

				Integer id = rs.getInt("id");

				try {
					// 将数据插入到表B
					String isql = "insert into test (id, Time, Cmsid, PushTime, OneTask, direction, unloaderMove, operationType, groupId) values(?,?,?,?,?,?,?,?,?)";
					PreparedStatement ips = conn.prepareStatement(isql);

					Date time = rs.getDate("Time");
					String cmsid = rs.getString("Cmsid");
					Date pushTime = rs.getDate("PushTime");
					Double oneTask = rs.getDouble("OneTask");
					String direction = rs.getString("direction");
					Double unloaderMove = rs.getDouble("unloaderMove");

					ps.setInt(0, id);
					ps.setTime(1, new Time(time.getTime()));
					ps.setString(2, cmsid);
					ps.setTime(3, new Time(pushTime.getTime()));
					ps.setDouble(4, oneTask);
					ps.setString(5, direction);
					ps.setDouble(6, unloaderMove);
					ps.setInt(7, operationType);
					ps.setInt(8, getGroupId(id, cmsid, operationType, pushTime, unloaderMove));

					ips.execute();
					ips.close();

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
					continue;
				}

				max = id; // 记录增量标书
			}
			ps.close();
			rs.close();

			// 处理逻辑

			// 更新增量标识
			sql = " UPDATE tab_temp_d t SET t.id = " + max + " WHERE t.unloaderId = 'ABB_GSU_" + num + "'";
			ps = conn.prepareStatement(sql);
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取组ID
	 * 
	 * @param id 数据Id
	 * 
	 * @param cmsid
	 *            卸船机编号
	 * @param operationType
	 *            数据类型
	 * @param time
	 *            操作时间
	 * @param unloaderMove
	 *            位置
	 * @return
	 * @throws SQLException
	 */
	private Integer getGroupId(Integer id, String cmsid, int operationType, Date time, Double unloaderMove) throws SQLException {
		int groupId = 0;
		int cabinId = 0;
		
		// 查询卸船机操作船舱ID
		String sql = " "
				+ "SELECT cabin.id FROM tab_cabin cabin "
				+ "LEFT JOIN ( SELECT c.*, task.`status`, task.begin_time, "
				+ "CASE WHEN task.end_time IS NULL THEN CURRENT_TIMESTAMP () "
				+ "ELSE task.end_time END AS 'end_time' FROM tab_cargo c LEFT JOIN "
				+ "tab_task task ON c.task_id = task.id ) t ON cabin.cargo_id = t.id "
				+ "WHERE  t.`status` = 1 AND cabin.start_position <= ? "
				+ "AND cabin.end_position >= ? AND t.begin_time <= ? AND t.end_time <= ? ";
		
		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setDouble(0, unloaderMove);
		ps.setDouble(1, unloaderMove);
		ps.setTime(2, new Time(time.getTime()));
		ps.setTime(3, new Time(time.getTime()));
		ResultSet rs = ps.getResultSet();
		if (rs.getMetaData().getColumnCount() == 0) {
			System.out.println(id + "|" + cmsid + "未找到船舱信息！");
			return groupId;
		}
		
		cabinId = rs.getInt("id");
		
		// 查询组信息，根据舱ID、 卸船机ID
		sql = " select * from tab_temp_c t where t.`status` = 0 and t.cabinId = ? AND t.Cmsid = ? ";
		ps = conn.prepareStatement(sql);
		ps.setInt(0, cabinId);
		ps.setString(1, cmsid);
		rs = ps.getResultSet();
		int conut = rs.getMetaData().getColumnCount();
		if (conut == 0) {
			
			// 新建组信息
			sql = "insert into tab_temp_c (cabinId, Cmsid, startTime, status) values(?,?,?,?)";
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			ps.setInt(0, cabinId);
			ps.setString(1, cmsid);
			ps.setTime(2, new Time(time.getTime()));
			ps.setInt(3, 0);
			ps.executeUpdate();
			
			ResultSet irs = ps.getGeneratedKeys();
            if(rs.next()) {
            	groupId = irs.getInt(1);
            }
            
            // 维护上一组结束时间
            
			
			// 更新第一次抓钩作业时间
    		sql = " select * from tab_temp_c t where t.`status` = 0 and t.cabinId != ? AND t.Cmsid = ? ";
    		ps = conn.prepareStatement(sql);
    		ps.setInt(0, cabinId);
    		ps.setString(1, cmsid);
    		rs = ps.getResultSet();
    		
//    		if ()
            
		} else if (conut == 1) {
			groupId = rs.getInt("id");
			
			// 更新第一次抓钩作业时间
			if (rs.getDate("firstTime") == null && operationType == 1) { 
				sql = "update tab_temp_c t SET t.firstTime = ? WHERE t.id = ?";
				ps = conn.prepareStatement(sql);
				ps.setTime(0, new Time(time.getTime()));
				ps.setInt(1, groupId);
				ps.executeUpdate();
			}
			
		} else {
			// TODO: ???
			groupId = rs.getInt("id");
			System.out.println(id + "|" + cmsid + " >> 查到多条组信息！");
		}
		
		ps.close();
		rs.close();
		
		return groupId;
	}

	/**
	 * 程序入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
