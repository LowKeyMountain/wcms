package net.itw.wcms.ship.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Berth;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.entity.TaskBerth;
import net.itw.wcms.ship.entity.TaskCabinDetail;
import net.itw.wcms.ship.entity.TaskCabinPositionDetail;
import net.itw.wcms.ship.repository.BerthRepository;
import net.itw.wcms.ship.repository.ShipRepository;
import net.itw.wcms.ship.repository.ShipUnloaderRepository;
import net.itw.wcms.ship.repository.TaskBerthRepository;
import net.itw.wcms.ship.repository.TaskCabinDetailRepository;
import net.itw.wcms.ship.repository.TaskCabinPositionDetailRepository;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.repository.TaskUnloadShipDetailRepository;
import net.itw.wcms.ship.service.ITaskShipService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;

/**
 * 
 * Description: 作业船舶业务处理类
 * 
 * @author Michael 4 Dec 2017 19:58:33
 */
@Service
@Transactional
public class TaskShipServiceImpl implements ITaskShipService {

	@Autowired
	private IUserService userService;
	@Autowired
	private ShipRepository shipRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private BerthRepository berthRepository;
	@Autowired
	private TaskBerthRepository taskBerthRepository;
	@Autowired
	private ShipUnloaderRepository shipUnloaderRepository;
	@Autowired
	private TaskCabinDetailRepository taskCabinDetailRepository;
	@Autowired
	private TaskCabinPositionDetailRepository taskCabinPositionDetailRepository;
	@Autowired
	private TaskUnloadShipDetailRepository taskUnloadShipDetailRepository;

	public MessageOption bindBerth(String taskId, String berthId, String userName) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));
			Berth berth = berthRepository.findOne(Integer.parseInt(berthId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}
			if (berth == null) {
				throw new X27Exception("操作失败：[berthId]未找到指定泊位 ！");
			}
			if (berth.getStatus() == 1) {
				throw new X27Exception("操作失败：当前泊位已被占用 ！");
			}

			TaskBerth taskBerth = new TaskBerth();
			taskBerth.setTask(task);
			berth.setStatus(1); 
			taskBerth.setBerth(berth);
			taskBerth.setIsSetPosition(false);
			taskBerth.setUpdateTime(new Date());
			taskBerth.setUpdateUser(operator.getUserName());
			taskBerth.setRemarks(berth.getBerthName());
			taskBerthRepository.save(taskBerth);

		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

	@Override
	public MessageOption beginShipUnload(String taskId, String userName) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 检查开始卸货前置条件
			// ??

			// 作业状态 （已入港|0、 预卸货|1、 卸货中|2、 完成卸船|3、 已离港|4）
			Integer status = task.getStatus();
			switch (status) {
			case 0:
				mo.msg = "操作失败: 当前船舶为绑定泊位或设置舱位！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 1:
//				if (task.getBeginTime() == null) {
//					task.setBeginTime(new Date());
//				}
//				task.setStatus(2);
//				for (TaskBerth tb: task.getTaskBerths()) {
//					if (tb.getBerth().getStatus() == 1) { // 操作当前停靠的船舶 
//						tb.getTaskUnloadShipDetails();
//					}
//				}
				task.setStatus(2);
				if (task.getBeginTime() == null) {
					task.setBeginTime(new Date());
				}
				task.setUpdateTime(new Date());
				task.setUpdateUser(operator.getUserName());
				taskRepository.saveAndFlush(task);
				break;
			case 2:
				mo.msg = "操作失败: 当前船舶正在卸货中！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 3:
				mo.msg = "操作失败: 当前船舶已完成卸船！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 4:
				mo.msg = "操作失败: 当前船舶已离港！";
				mo.code = ConstantUtil.FailInt;
				break;
			default:
				break;
			}

		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

	@Override
	public MessageOption setClearCabin(String taskId, String cabinNo, String userName) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 检查设置清舱前置条件
			// ??

			for (TaskCabinDetail cabin : task.getTaskCabinDetails()) {
				if (!StringUtils.equalsIgnoreCase(cabin.getCabinNo(), cabinNo)) {
					continue;
				}
				cabin.setIsFinish(true);
			}
			task.setUpdateTime(new Date());
			task.setUpdateUser(operator.getUserName());
			taskRepository.saveAndFlush(task);
		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

	@Override
	public MessageOption finishedShipUnload(String taskId, String userName) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 检查开始卸货前置条件
			// ??

			// 作业状态 （已入港|0、 预卸货|1、 卸货中|2、 完成卸船|3、 已离港|4）
			Integer status = task.getStatus();
			switch (status) {
			case 0:
				mo.msg = "操作失败: 当前船舶为绑定泊位或设置舱位！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 1:
				mo.msg = "操作失败: 当前还未开始卸货！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 2:
				task.setStatus(3);
				task.setEndTime(new Date());
				task.setUpdateTime(new Date());
				task.setUpdateUser(operator.getUserName());
				taskRepository.saveAndFlush(task);
				break;
			case 3:
				mo.msg = "操作失败: 当前船舶已完成卸船！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 4:
				mo.msg = "操作失败: 当前船舶已离港！";
				mo.code = ConstantUtil.FailInt;
				break;
			default:
				break;
			}

		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

	@Override
	public MessageOption setCabinPosition(String taskId, String userName, List<Map<String, Object>> list) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 检查开始卸货前置条件
			// ??

			// 作业状态 （已入港|0、 预卸货|1、 卸货中|2、 完成卸船|3、 已离港|4）
			Integer status = task.getStatus();
			switch (status) {
			case 0:
				mo.msg = "操作失败: 当前船舶还未绑定泊位！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 1:
				Set<TaskBerth> set = task.getTaskBerths();
				if (set==null || set.isEmpty()) {
					mo.msg = "操作失败: 当前船舶还未绑定泊位！";
					mo.code = ConstantUtil.FailInt;
					break;
				}
				
				TaskBerth taskBerth = null;
				for (TaskBerth tb : task.getTaskBerths()) {
					taskBerth = tb;
					break;
				}
				
				Map<String, TaskCabinPositionDetail> maps = new HashMap<>();
				for (TaskCabinPositionDetail position : taskBerth.getTaskCabinPositionDetails()) {
					maps.put(position.getCabinNo(), position);
				}
				
				for (Map<String, Object> map : list) {
					String cabinNo = (String)map.get("cabinNo");
					Double endPosition = (Double)map.get("endPosition");
					Double startPosition = (Double)map.get("startPosition");
					TaskCabinPositionDetail position = maps.get(cabinNo);
					position.setEndPosition(endPosition);
					position.setStartPosition(startPosition);
				}
				
				
				if (!taskBerth.getIsSetPosition()) {
					taskBerth.setIsSetPosition(true);
				}
				taskBerthRepository.saveAndFlush(taskBerth);
				
				task.setStatus(1);
				taskRepository.saveAndFlush(task);
				
				break;
			case 2:
				mo.msg = "操作失败: 当前船舶正在卸货中！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 3:
				mo.msg = "操作失败: 当前船舶已完成卸船！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 4:
				mo.msg = "操作失败: 当前船舶已离港！";
				mo.code = ConstantUtil.FailInt;
				break;
			default:
				break;
			}

		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

}
