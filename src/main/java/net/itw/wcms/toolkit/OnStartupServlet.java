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
	private DataSyncHelper dataSyncHelper;
	@Autowired
	private DataSyncHelperV6 dataSyncHelperV6;

	public void init() {
		if (initialized)
			return;
		initialized = true;

		boolean isAllowStartup = false;
		try {
			Properties pro = new Properties();
			pro.load(getClass().getResourceAsStream("/wcms.properties"));
			String str = (String) pro.get("unloaderDataSyn.isAllowStartup");
			isAllowStartup = StringUtils.isEmpty(str) ? true : Boolean.parseBoolean(str);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (isAllowStartup) {
			// 启动卸船机数据同步功能
			dataSyncHelperV6.init();
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						dataSyncHelperV6.start();
					} catch (InterruptedException e) {
						e.printStackTrace();
						dataSyncHelperV6.stop();
					}
				}
			}, 1000);
		}

	}

	public void dispose() {
	}

	public OnStartupServlet() {
	}
}
