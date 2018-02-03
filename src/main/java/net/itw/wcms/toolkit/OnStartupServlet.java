package net.itw.wcms.toolkit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * 
 * Description: 加载系统启动项
 * 
 * @author Michael 31 Dec 2017 09:50:24
 */
public class OnStartupServlet {

	private boolean initialized;
	@Autowired
	private DataSyncStepA dataSyncStepA;
	@Autowired
	private DataSyncStepB dataSyncStepB;

	public void init() {
		if (initialized) {
			return;
		}
		initialized = true;
		
		boolean pattern = false;
		try {
			Properties pro = new Properties();
			pro.load(getClass().getResourceAsStream("/wcms.properties"));
			String str = (String) pro.get("unloaderDataSyn.runTask.pattern");
			pattern = StringUtils.isEmpty(str) ? false : Boolean.parseBoolean(str);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		dataSyncStepA.runTask(pattern);
		dataSyncStepB.runTask(pattern);
	}
	
	public void dispose() {
	}

	public OnStartupServlet() {
	}
}
