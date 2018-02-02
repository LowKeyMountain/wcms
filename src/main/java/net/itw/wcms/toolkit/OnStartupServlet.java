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
	private DataSyncStepA dataSyncStepA;
	@Autowired
	private DataSyncStepB dataSyncStepB;

	public void init() {
		if (initialized) {
			return;
		}
		initialized = true;
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					dataSyncStepA.start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 1000);
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					dataSyncStepB.start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 1000);
	}

	public void dispose() {
	}

	public OnStartupServlet() {
	}
}
