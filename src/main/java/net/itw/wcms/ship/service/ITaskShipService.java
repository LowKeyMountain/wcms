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
	 * 修改船舶状态
	 * 
	 * @param taskId
	 * @param userName
	 * @param status
	 * @return
	 */
	MessageOption updateShipStatus(String params, String userName, String status);
	
	/**
	 * 修改船舶状态（用于APP端因误操作或忘记操作时的船舶状态维护）
	 * 
	 * @param params
	 * @param userName
	 * @return
	 */	
	MessageOption remedyShipStatus(Map<String, String> params, String userName);	
	
	/**
	 * 修改船舱状态
	 * 
	 * @param taskId
	 * @param cabinNo
	 * @param status
	 * @return
	 */
	MessageOption updateCabinStatus(String taskId, String userName, String cabinNo, String status);
	
	/**
	 * 货物信息查询
	 * 
	 * @param taskId
	 * @param cabinNo
	 * @return
	 */
	Map<String, Object> doGetCargoDetail(Integer taskId, Integer cabinNo);

	/**
	 * 查询卸船情况信息（以卸船机为维度）
	 * 
	 * @param parseInt
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Map<String, Object> doGetUnloaderUnshipInfo(int parseInt, String startTime, String endTime);
	
	/**
	 * 统计卸船机信息
	 * @param args
	 * @return
	 */
	Map<String, Object> doUnloaderInfoStatistics(Map<String, Object> args);

	/**
	 * 查询卸船机卸船明细列表信息（以卸船机为维度）
	 * 
	 * @param parseInt
	 * @param unloaderId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Map<String, Object> doGetUnloaderUnshipDetailList(int parseInt, String unloaderId, String startTime,
			String endTime);

	/**
	 * 获取指定船舱的货物信息, 根据货物ID
	 * @param cargoId
	 * @return
	 */
	Map<String, Object> doGetCargoDetailById(Integer cargoId);
	
	/**
	 * 修改船舱状态（用于APP端因误操作或忘记操作时的船舱状态维护）
	 * 
	 * @param params
	 * @param userName
	 * @return
	 */
	MessageOption remendyCabinStatus(String taskId, String userName, String cabinNo, String status);

	/**
	 * 统计飘到舱外的作业量统计
	 * @param args
	 * @return
	 */
	Map<String, Object> doOutboardInfoStatistics(Map<String, Object> args);

	
}
