package net.itw.wcms.toolkit;

import java.util.Timer;
import java.util.TimerTask;

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

	public void init() {
		if (initialized)
			return;
		initialized = true;
		// 启动卸船机数据同步功能
		dataSyncHelper.init();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					dataSyncHelper.start();
				} catch (InterruptedException e) {
					e.printStackTrace();
					dataSyncHelper.stop();
				}
			}
		}, 1000);
	}

	public void dispose() {
	}

	public OnStartupServlet() {
	}
}
