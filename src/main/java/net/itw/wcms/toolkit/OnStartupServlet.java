package net.itw.wcms.toolkit;

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
		try {
			dataSyncStepA.init();
			dataSyncStepB.init();
			dataSyncStepA.start();
			dataSyncStepB.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
	}

	public OnStartupServlet() {
	}
}
