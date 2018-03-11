package net.itw.wcms.toolkit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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
			Properties pro = new Properties();
			pro.load(getClass().getResourceAsStream("/wcms.properties"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		dataSyncStepA.start();
		dataSyncStepB.start();
	}
	
	public void dispose() {
	}

	public OnStartupServlet() {
	}
}
