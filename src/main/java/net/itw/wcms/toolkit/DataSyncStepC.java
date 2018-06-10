package net.itw.wcms.toolkit;

/**
 * 数据同步 > 步骤C(将船舶任务子表数据同步到临时表)
 * 
 * @author Michael 29 Jan 2018 22:42:25
 */
public interface DataSyncStepC {

	/**
	 * 启动
	 * 
	 * @param taskId
	 * @throws Exception
	 */
	public abstract void start(int taskId) throws Exception;
	
	/**
	 * 启动
	 * 
	 * @param taskId
	 * @throws Exception
	 */
	public void delete(int taskId);

}
