package net.itw.wcms.ship.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.repository.CabinRepository;
import net.itw.wcms.ship.repository.CargoRepository;
import net.itw.wcms.ship.repository.ShipRepository;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.service.ITaskShipService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;

/**
 * 船舶作业信息业务类
 * 
 * @author Michael 9 Dec 2017 07:09:01
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
	private CabinRepository cabinRepository;
	@Autowired
	private CargoRepository cargoRepository;

	/**
	 * 设置船舱位置
	 * 
	 * @param taskId
	 * @param userName
	 * @param list
	 * @return
	 */
	@Override
	public MessageOption setCabinPosition(String taskId, String userName, List<Map<String, Object>> list) {

		// 需求：设定船舱位置功能。
		// 前置条件：
		// 检查当前作业船舶状态，"预靠船舶|0、作业船舶|1"状态可设置舱位，"离港船舶|2"不能设置舱位；
		// 处理流程：
		// 修改船舱位置；
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 前置条件验证
			// 1. "离港船舶|2"不能设置舱位；
			Integer status = task.getStatus();
			if (2 == status) {
				throw new X27Exception("操作失败： 离港船舶不能设置舱位！");
			}

			Map<String, Cabin> cabins = new HashMap<>();
			if (task.getCabins() != null && task.getCabins().size() > 0) {
				for (Cabin e : task.getCabins()) {
					cabins.put(e.getCabinNo() + "", e);
				}
			} else {
				Integer num = task.getShip().getCabinNum();
				while (num > 0) {
					Cabin c = new Cabin();
					c.setCabinNo(num);
					c.setTask(task);
					cabins.put(c.getCabinNo() + "", c);
					num--;
				}
			}

			// 修改船舱位置
			for (Map<String, Object> map : list) {
				String cabinNo = map.get("cabinNo").toString();
				Cabin cabin = cabins.get(cabinNo);
				if (cabin == null) {
					continue;
				}
				cabin.setUpdateTime(new Date());
				cabin.setUpdateUser(operator.getUserName());
				cabin.setEndPosition(Float.parseFloat(map.get("endPosition").toString()));
				cabin.setStartPosition(Float.parseFloat(map.get("startPosition").toString()));
			}

			cabinRepository.save(cabins.values());

		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

	/**
	 * 开始卸船
	 * 
	 * @param taskId
	 * @param userName
	 * @return
	 */
	@Override
	public MessageOption beginShipUnload(String taskId, String userName) {
		// 需求：作业船舶开始卸船功能。
		// 前置条件：
		// 1. 检查当前作业船舶状态，只有"预靠船舶|0"状态才能开始卸船，其他状态不接受开始卸船请求；
		// 2. 检查作业船舶是否设置舱位；
		// 处理流程：
		// 1. 将作业船舶状态改为 : 作业船舶|1；
		// 2. 设置作业船舶开始卸货时间；
		// 3. 设置作业船舶关联的卸船机开始作业时间；
		// [作业船舶状态 : 卸货中|2]
		// 1. 设置作业船舶开始卸货时间；
		// 2. 设置作业船舶关联的卸船机开始作业时间；
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		// try {
		//
		// User operator = userService.getUserByUserName(userName);
		// Task task = taskRepository.findOne(Integer.parseInt(taskId));
		//
		// if (task == null) {
		// throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
		// }
		//
		// // 前置条件验证
		// // 1. 检查当前作业船舶状态，只有"预卸货|1、 卸货中|2"状态才能开始卸船，其他状态不接受开始卸船请求；
		// Integer taskStatus = task.getStatus();
		// if (!(Task.TaskStatus_PreDischarge == taskStatus ||
		// Task.TaskStatus_InDischarge == taskStatus)) {
		// throw new X27Exception("操作失败： 作业船舶只在预卸货、 卸货中状态时才可以进行开始卸船！");
		// }
		// // 2. 检查作业船舶是否有绑定泊位；
		// boolean isBind = false;
		// Set<TaskBerth> taskBerths = task.getTaskBerths();
		// if (taskBerths != null) {
		// for (TaskBerth e : taskBerths) {
		// if (Berth.BerthStatus_Occupied == e.getBerth().getStatus()) {
		// isBind = true;
		// }
		// }
		// }
		// if (!isBind) {
		// throw new X27Exception("操作失败： 作业船舶未绑定泊位！");
		// }
		// // 3.检查作业船舶是否设置舱位；
		// boolean isSet = false;
		// if (taskBerths != null) {
		// for (TaskBerth e : taskBerths) {
		// if (Berth.BerthStatus_Occupied == e.getBerth().getStatus()) {
		// Set<TaskCabinPositionDetail> taskCabinPositionDetails =
		// e.getTaskCabinPositionDetails();
		// if (taskCabinPositionDetails != null
		// && taskCabinPositionDetails.size() == task.getShip().getCabinNum()) {
		// isSet = true;
		// }
		// }
		// }
		// }
		// if (!isSet) {
		// throw new X27Exception("操作失败： 当前作业船舶未设置舱位！");
		// }
		//
		// switch (taskStatus) {
		// case 1:
		// // [作业船舶状态 : 预卸货|1]
		// // 1. 将作业船舶状态改为 : 卸货中|2；
		// // 2. 设置作业船舶开始卸货时间；
		// // 3. 设置作业船舶关联的卸船机开始作业时间；
		// task.setStatus(2);
		// task.setBeginTime(new Date());
		// // 设置作业船舶关联的卸船机开始作业时间；
		// for (TaskBerth tb : task.getTaskBerths()) {
		// for (TaskUnloadShipDetail e : tb.getTaskUnloadShipDetails()) {
		// e.setStartTime(new Date());
		// }
		// }
		// task.setUpdateTime(new Date());
		// task.setUpdateUser(operator.getUserName());
		// taskRepository.saveAndFlush(task);
		// break;
		// case 2:
		// // [作业船舶状态 : 卸货中|2]
		// // 1. 设置作业船舶开始卸货时间；
		// // 2. 设置作业船舶关联的卸船机开始作业时间；
		// task.setBeginTime(new Date());
		// // 设置作业船舶关联的卸船机开始作业时间；
		// for (TaskBerth tb : task.getTaskBerths()) {
		// for (TaskUnloadShipDetail e : tb.getTaskUnloadShipDetails()) {
		// e.setStartTime(new Date());
		// }
		// }
		// task.setUpdateTime(new Date());
		// task.setUpdateUser(operator.getUserName());
		// taskRepository.saveAndFlush(task);
		// break;
		// default:
		// break;
		// }
		//
		// } catch (Exception e) {
		// mo.msg = e.getMessage();
		// mo.code = ConstantUtil.FailInt;
		// }
		return mo;
	}

	/**
	 * 设置清舱
	 * 
	 * @param taskId
	 * @param cabinNo
	 * @param userName
	 * @return
	 */
	@Override
	public MessageOption setClearCabin(String taskId, String cabinNo, String userName) {
		// 需求：作业船舶设置清舱功能。
		// 前置条件：
		// 1. 检查该船舱货物是否快卸完；
		// 2. 检查当前作业船舶状态，只有"卸货中|2"状态才能清舱，其他状态不接受清舱请求；
		// 处理流程：
		// [作业船舶状态 : 卸货中|2]
		// 1. 设置作业船舶指定舱位状态为：清舱状态；
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		// try {
		//
		// User operator = userService.getUserByUserName(userName);
		// Task task = taskRepository.findOne(Integer.parseInt(taskId));
		//
		// if (task == null) {
		// throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
		// }
		//
		// // 前置条件验证
		// // 1. 检查该船舱货物是否快卸完；
		// // TODO:??
		// // 2. 检查当前作业船舶状态，只有"卸货中|2"状态才能清舱，其他状态不接受清舱请求；
		// Integer taskStatus = task.getStatus();
		// if (!(Task.TaskStatus_InDischarge == taskStatus)) {
		// throw new X27Exception("操作失败： 作业船舶只卸货中状态时才可以设置清舱状态！");
		// }
		//
		// // [作业船舶状态 : 卸货中|2]
		// // 1. 设置作业船舶指定舱位状态为：清舱状态；
		// for (TaskCabinDetail cabin : task.getTaskCabinDetails()) {
		// if (cabin.getId() == Integer.parseInt(cabinNo)) {
		// cabin.setIsFinish(true);
		// cabin.setUpdateTime(new Date());
		// cabin.setUpdateUser(operator.getUserName());
		// }
		// }
		// task.setUpdateTime(new Date());
		// task.setUpdateUser(operator.getUserName());
		// taskRepository.saveAndFlush(task);
		// } catch (Exception e) {
		// mo.msg = e.getMessage();
		// mo.code = ConstantUtil.FailInt;
		// }
		return mo;
	}

	/**
	 * 完成卸船
	 * 
	 * @param taskId
	 * @param userName
	 * @return
	 */
	@Override
	public MessageOption finishedShipUnload(String taskId, String userName) {

		// 需求：设置船舶完成卸货状态
		// 前置条件：
		// 1.检查当前船舶状态，只有“卸货中|2”状态才能设置“完成卸船”，其它状态不能进行设定；
		// 2.检查各船舱是否为清舱状态,各舱均为清舱状态时才可设置完成卸船；
		// 处理流程：
		// 【作业船舶状态：卸货中|2】
		// 分析 ：
		// 1.查询各船舱是否全部为清舱状态，如果全部清舱则变更船舶状态为“完成卸船”

		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		// try {
		//
		// User operator = userService.getUserByUserName(userName);
		// Task task = taskRepository.findOne(Integer.parseInt(taskId));
		//
		// if (task == null) {
		// throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
		// }
		//
		// // 作业状态 （已入港|0、 预卸货|1、 卸货中|2、 完成卸船|3、 已离港|4）
		// // 1.检查当前船舶状态，只有“卸货中|2”状态才能设置“完成卸船”，其它状态不能进行设定；
		// Integer status = task.getStatus();
		// switch (status) {
		// case 0:
		// mo.msg = "操作失败: 当前船舶为绑定泊位或设置舱位！";
		// mo.code = ConstantUtil.FailInt;
		// break;
		// case 1:
		// mo.msg = "操作失败: 当前还未开始卸货！";
		// mo.code = ConstantUtil.FailInt;
		// break;
		// case 2:
		// // 2.检查各船舱是否为清舱状态,各舱均为清舱状态时才可设置完成卸船；
		// for (TaskCabinDetail cabin : task.getTaskCabinDetails()) {
		// if (cabin.getIsFinish() == true) {
		// continue;
		// } else {
		// mo.msg = "操作失败：当前船舶存在未清舱的船舱，船舱编号：" + cabin.getCabinNo();
		// mo.code = ConstantUtil.FailInt;
		// return mo;
		// }
		// }
		// task.setStatus(3);
		// task.setEndTime(new Date());
		// task.setUpdateTime(new Date());
		// task.setUpdateUser(operator.getUserName());
		// taskRepository.saveAndFlush(task);
		// break;
		// case 3:
		// mo.msg = "操作失败: 当前船舶已完成卸船！";
		// mo.code = ConstantUtil.FailInt;
		// break;
		// case 4:
		// mo.msg = "操作失败: 当前船舶已离港！";
		// mo.code = ConstantUtil.FailInt;
		// break;
		// default:
		// break;
		// }
		//
		// } catch (Exception e) {
		// mo.msg = e.getMessage();
		// mo.code = ConstantUtil.FailInt;
		// }

		return mo;
	}
	
	/**
	 * 修改船舱状态
	 * 
	 * @param taskId
	 * @param cabinNo
	 * @param userName
	 * @return
	 */
	@Override
	public MessageOption updateCabinStatus(String taskId, String userName, String cabinNo, String status) {
		// 需求：作业船舶设置船舱状态（0|卸货;1|清舱;2|完成）
		// 前置条件：
		// 1. 检查该船舱货物是否快卸完；
		// 2. 检查当前作业船舶状态，只有"作业船舶|1"状态才能清舱，其他状态不接受清舱请求；
		// 处理流程：
		// 作业船舶|1
		// 1. 设置作业船舶指定舱位状态为：清舱或完成状态；
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 前置条件验证
			// 1. 检查该船舱货物是否快卸完；
			// TODO:??
			// 2. 检查当前作业船舶状态，只有"作业船舶|1"状态才能设置清舱或完成，其他状态不允许设置；
			Integer taskStatus = task.getStatus();
			if (taskStatus != 1) {
				throw new X27Exception("操作失败： 船舶只有在作业时才能修改船舱状态！");
			}
			if (Integer.parseInt(status) == 1 || Integer.parseInt(status) == 2) {
				// 1. 设置作业船舶指定舱位状态为：清舱或完成状态；
				for (Cabin cabin : task.getCabins()) {
					if (cabin.getCabinNo() == Integer.parseInt(cabinNo)) {
						cabin.setStatus(Integer.parseInt(status));
						cabin.setUpdateTime(new Date());
						cabin.setUpdateUser(operator.getUserName());
					}
				}
			} else {
				throw new X27Exception("操作失败： 船舱状态参数异常！");
			}
			task.setUpdateTime(new Date());
			task.setUpdateUser(operator.getUserName());
			taskRepository.saveAndFlush(task);
		}catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		
		return mo;
	}

	
	/**
	 * 设置船舶状态
	 * 
	 * @param taskId
	 * @param userName
	 * @param status(0|开始卸船、1|结束卸船)
	 * @return
	 */
	@Override
	@Transactional
	public MessageOption updateShipStatus(String taskId, String userName, String status) {

		// 需求：设置船舶状态
		// 前置条件：
		// 【开始卸船】
		// 1.检查当前船舶状态，只有“预靠船舶|0”状态才能设置“开始卸船”，其它状态不能进行设定；
		// 【结束卸船】
		// 1.检查当前船舶状态，只有“作业船舶|1”状态才能设置“结束卸船”，其它状态不能进行设定；
		// 2.检查各船舱是否为完成状态,各舱均为完成状态时才可设置"结束卸船"；

		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));
			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 作业状态 （预靠船舶|0、 作业船舶|1、离港船舶|2）
			// 1.检查当前船舶状态，只有“作业船舶|1”状态才能设置“结束卸船”，其它状态不能进行设定；
			Integer shipStatus = task.getStatus();
			switch (shipStatus) {
			case 0:
				if ("0".equals(status)) {
					// 开始卸船
					task.setStatus(1);
					task.setBeginTime(new Date());
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					taskRepository.saveAndFlush(task);
					// TODO:更新所有船舱状态为卸货|0

				} else if ("1".equals(status)) {
					throw new X27Exception("操作失败: 当前船舶为预靠状态！");
				} else {
					throw new X27Exception("操作失败: 船舶状态参数有误！");
				}
				break;
			case 1:
				if ("0".equals(status)) {
					throw new X27Exception("操作失败: 当前船舶为作业状态！");
				} else if ("1".equals(status)) {
					// 检查各船舱是否为完成状态,各舱均为完成状态时才可设置结束卸船；
					for (Cabin cabin : task.getCabins()) {
						if (cabin.getStatus() == 2) {
							continue;
						} else {
							throw new X27Exception("操作失败：当前船舶存在未完成卸货的船舱!");
						}
					}
					// 结束卸船
					task.setStatus(2);
					task.setEndTime(new Date());
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					taskRepository.saveAndFlush(task);
				} else {
					throw new X27Exception("操作失败: 船舶状态参数有误！");
				}
				break;
			case 2:
				throw new X27Exception("操作失败: 当前船舶已进入离港状态！");
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
