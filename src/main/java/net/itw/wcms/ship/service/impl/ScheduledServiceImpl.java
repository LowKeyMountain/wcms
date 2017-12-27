package net.itw.wcms.ship.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import net.itw.wcms.ship.service.IScheduledService;


@Component
public class ScheduledServiceImpl implements IScheduledService {

	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次 
    @Override
	public void ImportAllUnloaderData() {
		
        System.out.println("进入测试");
	}
}
