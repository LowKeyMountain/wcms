package net.itw.wcms.ship.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import net.itw.wcms.ship.entity.Berth;
import net.itw.wcms.ship.entity.ShipUnloader;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.entity.TaskBerth;
import net.itw.wcms.ship.entity.TaskCabinDetail;
import net.itw.wcms.ship.entity.TaskCabinPositionDetail;
import net.itw.wcms.ship.entity.TaskUnloadShipDetail;
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

	/**
	 * JpaTransactionManager事务管理 .
	 */
	@Resource(name = "transactionManager")
	JpaTransactionManager tm;
	
	/**
	 * 绑定泊位
	 * 
	 * @param taskId
	 * @param berthId
	 * @param userName
	 * @return
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public MessageOption bindBerth(String taskId, String berthId, String userName) {
		
		// 需求：作业船舶绑定泊位功能。
		// 前置条件：
		// 1. 检查待绑定泊位是否为空闲状态；
		// 2. 检查当前作业船舶状态，只有"已入港|0、 预卸货|1、 卸货中|2"状态才能绑定泊位，"完成卸船|3、
		// 已离港|4"状态不能绑定泊位；
		// 处理流程：
		// [作业船舶状态 : 已入港|0]
		// 分析：该状态下进行泊位绑定通常为第一次绑定；
		// 1. 将作业船舶与待绑定泊位进行关联，该泊位状态改为：使用中；
		// 2. 初始化船舱位置；
		// 3. 将作业船舶状态改为： 预卸货|1；
		// [作业船舶状态 : 预卸货|1]
		// 分析：该状态下进行泊位绑定通常是进行变更泊位，需要将已绑定的泊位进行解绑；
		// 1. 查询已绑定泊位，进行解除绑定操作；也就是将已绑定的泊位状态改为：未使用；
		// 2. 将作业船舶与待绑定泊位进行关联，该泊位状态改为：使用中；
		// [作业船舶状态 : 卸货中|2]
		// 分析：该状态下进行泊位绑定通常是进行变更泊位，需要将已绑定的泊位进行解绑；
		// 1. 查询已绑定泊位，进行解除绑定操作；也就是将已绑定的泊位状态改为：未使用；
		// 2. 查询与已绑定泊位关联的卸船机，设置卸船机作业结束时间；
		// 3. 将作业船舶与待绑定泊位进行关联，该泊位状态改为：使用中；
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

			// 1、前置条件验证

			// 1.1 检查待绑定泊位是否为空闲状态；
			if (Berth.BerthStatus_Occupied == berth.getStatus()) {
				throw new X27Exception("操作失败：待绑定泊位已被占用 ！");
			}
			// 1.2 检查当前作业船舶状态，只有"已入港|0、 预卸货|1、 卸货中|2"状态才能绑定泊位，"完成卸船|3、
			// 已离港|4"状态不能绑定泊位；
			Integer taskStatus = task.getStatus();
			if (Task.TaskStatus_Leave == taskStatus || Task.TaskStatus_Finished == taskStatus) {
				throw new X27Exception("操作失败：作业船舶已完成卸船、已离港 ！");
			}

			// 2、处理流程：
			switch (taskStatus) {
			case 0:
				// 2.1 [作业船舶状态 : 已入港|0]
				// 分析：该状态下进行泊位绑定通常为第一次绑定；
				// 1. 将作业船舶与待绑定泊位进行关联，该泊位状态改为：使用中；
				// 2. 初始化船舱位置；
				// 3. 将作业船舶状态改为： 预卸货|1；
				TaskBerth taskBerth = new TaskBerth();

				task.setStatus(task.TaskStatus_PreDischarge);
				taskBerth.setTask(task);
				berth.setStatus(Berth.BerthStatus_Occupied);
				taskBerth.setBerth(berth);
				taskBerth.setIsSetPosition(false);
				taskBerth.setUpdateTime(new Date());
				taskBerth.setUpdateUser(operator.getUserName());
				taskBerth.setRemarks(berth.getBerthName());
				
				//事务开始
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);  
				def.setTimeout(30);

				int i = 0;
				Set<TaskCabinPositionDetail> taskCabinPositionDetails = taskBerth.getTaskCabinPositionDetails();
				while (i < task.getShip().getCabinNum()) {
					TaskCabinPositionDetail position = new TaskCabinPositionDetail();
					position.setCabinNo(i + 1 + "#舱");
					position.setTaskBerth(taskBerth);
					position.setUpdateTime(new Date());
					position.setUpdateUser(operator.getUserName());
					taskCabinPositionDetails.add(position);
					i++;
				}
				
				//事务状态
				TransactionStatus status2 = tm.getTransaction(def); 
				try {
				    //此处写持久层逻辑
					taskCabinPositionDetailRepository.save(taskCabinPositionDetails);
				    tm.commit(status2);
				} catch (Exception e) {
				    e.printStackTrace();
				    if(!status2.isCompleted()){
				        tm.rollback(status2);
				    }
				    throw new Exception();
				}
				
				//事务状态
				TransactionStatus status = tm.getTransaction(def); 
				try {
				    //此处写持久层逻辑
					taskBerthRepository.save(taskBerth);
				    tm.commit(status);
				} catch (Exception e) {
					e.printStackTrace();
					if(!status.isCompleted()){
				        tm.rollback(status);
				    }
				    throw new Exception();
				}
				
				break;
			case 1:
				// 2.2 [作业船舶状态 : 预卸货|1]
				// 分析：该状态下进行泊位绑定通常是进行变更泊位，需要将已绑定的泊位进行解绑；
				// 1. 查询已绑定泊位，进行解除绑定操作；也就是将已绑定的泊位状态改为：未使用；
				// 2. 将作业船舶与待绑定泊位进行关联，该泊位状态改为：使用中；
			case 2:
				// [作业船舶状态 : 卸货中|2]
				// 分析：该状态下进行泊位绑定通常是进行变更泊位，需要将已绑定的泊位进行解绑；
				// 1. 将解绑泊位上的卸船机，状态、作业完成时间进行设置；
				// 2. 查询已绑定泊位，进行解除绑定操作；也就是将已绑定的泊位状态改为：未使用；
				// 3. 将作业船舶与待绑定泊位进行关联，该泊位状态改为：使用中；
				Set<TaskBerth> taskBerths = task.getTaskBerths();
				if (taskBerths != null && !taskBerths.isEmpty()) {
					for (TaskBerth tb : taskBerths) {
						// 1. 将解绑泊位上的卸船机，状态、作业完成时间进行设置；
						Set<TaskUnloadShipDetail> taskUnloadShipDetails = tb.getTaskUnloadShipDetails();
						if (taskUnloadShipDetails != null) {
							for (TaskUnloadShipDetail e : taskUnloadShipDetails) {
								ShipUnloader shipUnloader = e.getShipUnloader();
								if (shipUnloader != null) {
									shipUnloader.setStatus(ShipUnloader.ShipUnloaderStatus_Leisure);
								}
								e.setEndTime(new Date());
							}
						}
						// 2. 查询已绑定泊位，进行解除绑定操作；也就是将已绑定的泊位状态改为：未使用；
						Berth b = tb.getBerth();
						if (b != null) {
							b.setStatus(Berth.BerthStatus_Leisure);
						}
						taskBerthRepository.save(tb);
					}
				}
				// 3. 将作业船舶与待绑定泊位进行关联，该泊位状态改为：使用中；
				taskBerth = new TaskBerth();
				taskBerth.setTask(task);
				berth.setStatus(Berth.BerthStatus_Occupied);
				taskBerth.setBerth(berth);
				taskBerth.setIsSetPosition(false);
				taskBerth.setUpdateTime(new Date());
				taskBerth.setUpdateUser(operator.getUserName());
				taskBerth.setRemarks(berth.getBerthName());
				taskBerthRepository.save(taskBerth);
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
		// 1. 检查当前作业船舶状态，只有"预卸货|1、 卸货中|2"状态才能开始卸船，其他状态不接受开始卸船请求；
		// 2. 检查作业船舶是否有绑定泊位；
		// 3. 检查作业船舶是否设置舱位；
		// 处理流程：
		// [作业船舶状态 : 预卸货|1]
		// 1. 将作业船舶状态改为 : 卸货中|2；
		// 2. 设置作业船舶开始卸货时间；
		// 3. 设置作业船舶关联的卸船机开始作业时间；
		// [作业船舶状态 : 卸货中|2]
		// 1. 设置作业船舶开始卸货时间；
		// 2. 设置作业船舶关联的卸船机开始作业时间；
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 前置条件验证
			// 1. 检查当前作业船舶状态，只有"预卸货|1、 卸货中|2"状态才能开始卸船，其他状态不接受开始卸船请求；
			Integer taskStatus = task.getStatus();
			if (!(Task.TaskStatus_PreDischarge == taskStatus || Task.TaskStatus_InDischarge == taskStatus)) {
				throw new X27Exception("操作失败： 作业船舶只在预卸货、 卸货中状态时才可以进行开始卸船！");
			}
			// 2. 检查作业船舶是否有绑定泊位；
			boolean isBind = false;
			Set<TaskBerth> taskBerths = task.getTaskBerths();
			if (taskBerths != null) {
				for (TaskBerth e : taskBerths) {
					if (Berth.BerthStatus_Occupied == e.getBerth().getStatus()) {
						isBind = true;
					}
				}
			}
			if (!isBind) {
				throw new X27Exception("操作失败： 作业船舶未绑定泊位！");
			}
			// 3.检查作业船舶是否设置舱位；
			boolean isSet = false;
			if (taskBerths != null) {
				for (TaskBerth e : taskBerths) {
					if (Berth.BerthStatus_Occupied == e.getBerth().getStatus()) {
						Set<TaskCabinPositionDetail> taskCabinPositionDetails = e.getTaskCabinPositionDetails();
						if (taskCabinPositionDetails != null
								&& taskCabinPositionDetails.size() == task.getShip().getCabinNum()) {
							isSet = true;
						}
					}
				}
			}
			if (!isSet) {
				throw new X27Exception("操作失败： 当前作业船舶未设置舱位！");
			}

			switch (taskStatus) {
			case 1:
				// [作业船舶状态 : 预卸货|1]
				// 1. 将作业船舶状态改为 : 卸货中|2；
				// 2. 设置作业船舶开始卸货时间；
				// 3. 设置作业船舶关联的卸船机开始作业时间；
				task.setStatus(2);
				task.setBeginTime(new Date());
				// 设置作业船舶关联的卸船机开始作业时间；
				for (TaskBerth tb : task.getTaskBerths()) {
					for (TaskUnloadShipDetail e : tb.getTaskUnloadShipDetails()) {
						e.setStartTime(new Date());
					}
				}
				task.setUpdateTime(new Date());
				task.setUpdateUser(operator.getUserName());
				taskRepository.saveAndFlush(task);
				break;
			case 2:
				// [作业船舶状态 : 卸货中|2]
				// 1. 设置作业船舶开始卸货时间；
				// 2. 设置作业船舶关联的卸船机开始作业时间；
				task.setBeginTime(new Date());
				// 设置作业船舶关联的卸船机开始作业时间；
				for (TaskBerth tb : task.getTaskBerths()) {
					for (TaskUnloadShipDetail e : tb.getTaskUnloadShipDetails()) {
						e.setStartTime(new Date());
					}
				}
				task.setUpdateTime(new Date());
				task.setUpdateUser(operator.getUserName());
				taskRepository.saveAndFlush(task);
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
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 前置条件验证
			// 1. 检查该船舱货物是否快卸完；
			// TODO:??
			// 2. 检查当前作业船舶状态，只有"卸货中|2"状态才能清舱，其他状态不接受清舱请求；
			Integer taskStatus = task.getStatus();
			if (!(Task.TaskStatus_InDischarge == taskStatus)) {
				throw new X27Exception("操作失败： 作业船舶只卸货中状态时才可以设置清舱状态！");
			}

			// [作业船舶状态 : 卸货中|2]
			// 1. 设置作业船舶指定舱位状态为：清舱状态；
			for (TaskCabinDetail cabin : task.getTaskCabinDetails()) {
				if (cabin.getId() == Integer.parseInt(cabinNo)) {
					cabin.setIsFinish(true);
					cabin.setUpdateTime(new Date());
					cabin.setUpdateUser(operator.getUserName());
				}
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

	/**
	 * 完成卸船
	 * 
	 * @param taskId
	 * @param userName
	 * @return
	 */
	@Override
	public MessageOption finishedShipUnload(String taskId, String userName) {
		
		/*	需求：设置船舶完成卸货状态
		前置条件：
			1.检查当前船舶状态，只有“卸货中|2”状态才能设置“完成卸船”，其它状态不能进行设定；
			2.检查各船舱是否为清舱状态,各舱均为清舱状态时才可设置完成卸船；
		处理流程：
			【作业船舶状态：卸货中|2】
			分析 ：
			1.查询各船舱是否全部为清舱状态，如果全部清舱则变更船舶状态为“完成卸船”
		*/
		
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 作业状态 （已入港|0、 预卸货|1、 卸货中|2、 完成卸船|3、 已离港|4）
			//1.检查当前船舶状态，只有“卸货中|2”状态才能设置“完成卸船”，其它状态不能进行设定；
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
				//2.检查各船舱是否为清舱状态,各舱均为清舱状态时才可设置完成卸船；
				for (TaskCabinDetail cabin : task.getTaskCabinDetails()) {
					if (cabin.getIsFinish() == true) {
						continue;
					} else {
						mo.msg = "操作失败：当前船舶存在未清舱的船舱，船舱编号：" + cabin.getCabinNo();
						mo.code = ConstantUtil.FailInt;
						return mo;
					}
				}
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
		
/*		 需求：设定船舱位置功能。
		 前置条件：
		 1. 检查当前作业船舶状态，只有"预卸货|1"状态才能设置船舱位置，其他状态均不允许设置；
		 2. 检查作业船舶是否有绑定泊位；
		 处理流程：
		 [作业船舶状态 : 预卸货|1]
		 1. 检查作业船舶是否有绑定泊位；
		 2. 修改船舱位置；
		 3. 修改船舶所在泊位为已设置舱位状态；
		*/
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {

			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));

			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 作业状态 （已入港|0、 预卸货|1、 卸货中|2、 完成卸船|3、 已离港|4）
			//1. 检查当前作业船舶状态，只有"预卸货|1"状态才能设置船舱位置，其他状态均不允许设置；
			Integer status = task.getStatus();
			switch (status) {
			case 0:
				mo.msg = "操作失败: 当前船舶还未绑定泊位！";
				mo.code = ConstantUtil.FailInt;
				break;
			case 1:
				//2.检查作业船舶是否有绑定泊位；
				Set<TaskBerth> set = task.getTaskBerths();
				if (set == null || set.isEmpty()) {
					mo.msg = "操作失败: 当前船舶还未绑定泊位！";
					mo.code = ConstantUtil.FailInt;
					break;
				}

				TaskBerth taskBerth = null;
				for (TaskBerth tb : task.getTaskBerths()) {
					taskBerth = tb;
					break;
				}

				Map<Integer, TaskCabinPositionDetail> maps = new HashMap<>();
				for (TaskCabinPositionDetail position : taskBerth.getTaskCabinPositionDetails()) {
					maps.put(position.getId(), position);
				}

				//修改船舱位置
				for (Map<String, Object> map : list) {
					int cabinNo = Integer.parseInt(map.get("cabinNo").toString());
					Double endPosition = Double.parseDouble(map.get("endPosition").toString());
					Double startPosition = Double.parseDouble(map.get("startPosition").toString());
					TaskCabinPositionDetail position = maps.get(cabinNo);
					position.setEndPosition(endPosition);
					position.setStartPosition(startPosition);
					position.setUpdateTime(new Date());
					position.setUpdateUser(operator.getUserName());
				}

				//设置船舱状态为已设置舱位
				if (!taskBerth.getIsSetPosition()) {
					taskBerth.setIsSetPosition(true);
				}
				taskBerthRepository.saveAndFlush(taskBerth);

				task.setStatus(1);//设置预卸货状态
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
