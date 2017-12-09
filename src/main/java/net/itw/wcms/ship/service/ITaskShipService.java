package net.itw.wcms.ship.service;

import java.util.List;
import java.util.Map;

import net.itw.wcms.toolkit.MessageOption;

/**
 * 
 * Description:作业船舶相关操作
 * 
 * @author Michael 4 Dec 2017 18:52:15
 */
public interface ITaskShipService {
	
	/**
	 * 设置舱位
	 * 
	 * @param taskId
	 * @param userName
	 * @param list
	 * @return
	 */
	MessageOption setCabinPosition(String taskId, String userName, List<Map<String, Object>> list);

	/**
	 * 开始卸船
	 * 
	 * @param taskId
	 * @param userName
	 * @return
	 */
	MessageOption beginShipUnload(String taskId, String userName);

	/**
	 * 设置清舱
	 * 
	 * @param taskId
	 * @param cabinNo
	 * @param userName
	 * @return
	 */
	MessageOption setClearCabin(String taskId, String cabinNo, String userName);

	/**
	 * 完成卸船
	 * 
	 * @param taskId
	 * @param userName
	 * @return
	 */
	MessageOption finishedShipUnload(String taskId, String userName);

}
