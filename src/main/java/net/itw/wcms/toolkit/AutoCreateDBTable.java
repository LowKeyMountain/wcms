package net.itw.wcms.toolkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import net.itw.wcms.ship.entity.Task;

/**
 * Description: 自动创建数据库表
 * 
 * @author Michael 27 Jan 2018 14:40:20
 */
public class AutoCreateDBTable extends JdbcDaoSupport {

	private static String SQL_B;
	private static String SQL_C;

	static {
		try {
			SQL_B = loadSqlFile("./tab_temp_b.sql");
			SQL_C = loadSqlFile("./tab_temp_c.sql");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载sql文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static String loadSqlFile(String path) throws IOException {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(AutoCreateDBTable.class.getResourceAsStream(path)));
		StringBuffer buf = new StringBuffer();
		String line = " ";
		while ((line = in.readLine()) != null) {
			buf.append(line);
		}
		return buf.toString();
	}

	/**
	 * 创建表B、表C
	 * 
	 * @param task
	 * @throws SQLException
	 */
	public void createTable(Task task) throws SQLException {
		String tableName_B = "tab_temp_b_" + task.getId();
		List<String> list = this.getJdbcTemplate().queryForList(" SHOW TABLES LIKE '%" + tableName_B + "%' ",
				String.class);
		if (list.isEmpty()) {
			this.getJdbcTemplate().execute(SQL_B.replace("tab_temp_b", tableName_B));
		}
		String tableName_C = "tab_temp_c_" + task.getId();
		list = this.getJdbcTemplate().queryForList(" SHOW TABLES LIKE '%" + tableName_C + "%' ", String.class);
		if (list.isEmpty()) {
			this.getJdbcTemplate().execute(SQL_C.replace("tab_temp_c", tableName_C));
		}
	}

}
