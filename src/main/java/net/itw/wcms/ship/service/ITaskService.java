package net.itw.wcms.ship.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.x27.entity.User;

/**
 * Description: 船舶作业表相关操作
 *
 * @author sparking 2017-11-17 下午10:20:35
 *
 */
public interface ITaskService {
	
	/**
	 * 获取船舶列表信息
	 * @param pageable
	 * @param status
	 * @param params
	 * @return
	 */
	Map<String, Object> getTaskList(Pageable pageable, Integer status, Map<String, String> params);
	
	/**
	 * 根据船舶状态，返回船舶列表
	 * 
	 * @param pageable
	 * @return
	 */
	List<Task> getTaskByStatus(String status);
	
	/**
	 * 根据船舶id，返回船舶作业信息
	 * 
	 * @param pageable
	 * @return
	 */	
	Task getTaskById(Integer id);

	/**
	 * 设置船舶完成状态(00|未开始,01|卸货中,02|已完成)
	 * 
	 * @param taskId
	 * @return
	 */
	
	Integer updateShipStatusByid(Integer taskId, String userId,  String status);
	
	/**
	 * 新增船舶作业
	 * 
	 * @param task
	 * @param operator
	 * @return
	 */
	Integer createTask(Task task, User operator);
	
}
