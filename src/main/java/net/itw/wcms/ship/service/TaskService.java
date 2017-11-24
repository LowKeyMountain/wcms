package net.itw.wcms.ship.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.x27.entity.User;

/**
 * Description: 船舶作业表相关操作
 *
 * @author sparking 2017-11-17 下午10:20:35
 *
 */
public interface TaskService {
	
	/**
	 * 根据船舶状态，返回船舶Json数据格式
	 * 
	 * @param pageable
	 * @return
	 */
	String getTaskList(Task task, Pageable pageable, String status);

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
