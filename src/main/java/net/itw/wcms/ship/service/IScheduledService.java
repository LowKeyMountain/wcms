package net.itw.wcms.ship.service;

/**
 * Description: 调试线程实现把6台卸船机的增量数据统一导出至指定表，并计算当前作业任务对应的船舱及任务组
 *
 * @author sparking 2017-12-21 下午21:40:10
 *
 */
public interface IScheduledService {

	/**
	 * 
	 * @param 
	 * @return
	 */
	
	public void ImportAllUnloaderData();
	
	
}
