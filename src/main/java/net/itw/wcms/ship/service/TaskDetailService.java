package net.itw.wcms.ship.service;

import java.util.List;
import net.itw.wcms.ship.entity.TaskDetail;

/**
 * Description: 船舶作业详情表相关操作
 *
 * @author sparking 2017-11-17 下午4:40:10
 *
 */
public interface TaskDetailService {

	
	/**
	 * 根据作业id,返回船舶作业详情
	 * 
	 * @param taskId
	 * @return
	 */
	List<TaskDetail> getTaskDetailByTaskId(Integer taskId);
	
	/**
	 * 根据作业详情id,获取船舶某一船舱的详细信息
	 * 
	 * @param detailId
	 * @return
	 */
	TaskDetail getTaskDetailById(Integer detailId);
	
	/**
	 * 设置船舱清仓/完成状态(00|未开始,01|卸货中,02|清仓)
	 * 
	 * @param detailId
	 * @return
	 */
	
	Integer updateCabinStatusByid(Integer detailId, String userId,  String status);
	
	/**
	 * 设置船舱位置
	 * 
	 * @param detailId
	 * @return
	 */
	
	Integer updateCabinPositionByid(List<TaskDetail> taskDetail, String userId);
}
