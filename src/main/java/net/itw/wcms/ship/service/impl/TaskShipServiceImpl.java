package net.itw.wcms.ship.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.repository.CabinRepository;
import net.itw.wcms.ship.repository.CargoRepository;
import net.itw.wcms.ship.repository.ShipRepository;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.service.ITaskShipService;
import net.itw.wcms.toolkit.DataSyncStepB;
import net.itw.wcms.toolkit.DataSyncStepC;
import net.itw.wcms.toolkit.DataSyncStepCIndigo;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.toolkit.sql.SqlMap;
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
	
	@Autowired
	private DataSyncStepB dataSyncStepB;
	@Resource(name="dataSyncStepCImpl")
	private DataSyncStepC dataSyncStepC;
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	private static SqlMap sqlMap;

	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./queryInterfaceConfig.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
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
	@Transactional
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
			
			if (task.getCargos() != null) {
				for (Cargo cargo : task.getCargos()) {
					for (Cabin e : cargo.getCabins()) {
						cabins.put(e.getCabinNo() + "", e);
					}
				}
			} else {
				Integer num = task.getShip().getCabinNum();
				while (num > 0) {
					Cabin c = new Cabin();
					c.setCabinNo(num);
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
			cabinRepository.flush();
			// 将船舶任务子表数据同步到临时表
			dataSyncStepC.start(Integer.parseInt(taskId));
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
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
			int statusCode = Integer.parseInt(status);
			if (statusCode == 1 || statusCode == 2) {
				// 1. 设置作业船舶指定舱位状态为：清舱或完成状态；
				for (Cargo cargo : task.getCargos()) {
					for (Cabin cabin : cargo.getCabins()) {
						if (cabin.getCabinNo() == Integer.parseInt(cabinNo)) {
							cabin.setStatus(statusCode);
							if (statusCode == 1) { // 设置清舱时间
								cabin.setClearTime(new Date());
							}
							cabin.setUpdateTime(new Date());
							cabin.setUpdateUser(operator.getUserName());
						}
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
	 * 修改船舱状态
	 * 
	 * @param taskId
	 * @param cabinNo
	 * @param userName
	 * @return
	 */
	@Override
	public MessageOption remendyCabinStatus(String taskId, String userName, String cabinNo, String status) {
		// 需求：作业船舶设置船舱状态（0|卸货;1|清舱;）
		// 前置条件：
		// 1. 检查该船舱货物是否快卸完；
		// 2. 检查当前作业船舶状态，只有"作业船舶|1"状态才能清舱，其他状态不接受清舱请求；
		// 处理流程：
		// 作业船舶|1
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
			// 2. 检查当前作业船舶状态，只有"作业船舶|1"状态才能设置清舱，其他状态不允许设置；
			Integer taskStatus = task.getStatus();
			if (taskStatus != 1) {
				throw new X27Exception("操作失败： 船舶只有在作业时才能修改船舱状态！");
			}
			int statusCode = Integer.parseInt(status);
			if (statusCode == 0 || statusCode == 1) {
				// 1. 设置作业船舶指定舱位状态为：卸货或清舱状态；
				for (Cargo cargo : task.getCargos()) {
					for (Cabin cabin : cargo.getCabins()) {
						if (cabin.getCabinNo() == Integer.parseInt(cabinNo)) {
							cabin.setStatus(statusCode);
							if (statusCode == 1) { // 设置清舱时间
								cabin.setClearTime(new Date());
							}
							cabin.setUpdateTime(new Date());
							cabin.setUpdateUser(operator.getUserName());
						}
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
					// 检查当前泊位是否被占用
					List<Task> tasks = taskRepository.getTaskByStatus(1);
					for (Task e : tasks) {
						if (task.getBerth() == e.getBerth()) {
							throw new X27Exception("操作失败: " + "矿"
									+ (e.getBerth() == 1 ? "一" : (e.getBerth() == 2 ? "二" : "其他")) + "已被占用！");
						}
					}
					// 开始卸船
					task.setStatus(1);
					task.setBerthingTime(new Date());
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					// 更新所有船舱状态为卸货|0
					for (Cargo cargo : task.getCargos()) {
						for (Cabin cabin : cargo.getCabins()) {
							cabin.setStatus(0);
						}
					}
					taskRepository.saveAndFlush(task);

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
//					List<Cabin> cabins = new ArrayList<>();
//					for (Cargo cargo : task.getCargos()) {
//						for (Cabin cabin : cargo.getCabins()) {
//							cabins.add(cabin);
//						}
//					}
//					for (Cabin cabin : cabins) {
//						if (cabin.getStatus() == 2) {
//							continue;
//						} else {
//							throw new X27Exception("操作失败：当前船舶存在未完成卸货的船舱!");
//						}
//					}
					// 结束卸船
					task.setStatus(2);
					task.setEndTime(new Date());
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					taskRepository.saveAndFlush(task);
					// 更新组结束时间
					dataSyncStepB.updateGroupEndTime(task.getId());
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

	/**
	 * 调整船舶状态
	 * 
	 * @param taskId
	 * @param userName
	 * @return
	 */
	@Override
	@Transactional
	public MessageOption remedyShipStatus(Map<String, String> params, String userName) {

		String taskId = params.get("taskId");
		String status = params.get("status");
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			User operator = userService.getUserByUserName(userName);
			Task task = taskRepository.findOne(Integer.parseInt(taskId));
			if (task == null) {
				throw new X27Exception("操作失败：[taskId]未找到指定作业船舶 ！");
			}

			// 作业状态 （预靠船舶|0、 作业船舶|1、离港船舶|2）
			Integer shipStatus = task.getStatus();
			switch (shipStatus) {
			// 一、船舶为预靠状态；
			case 0:
					// 1.检查当前泊位是否被占用，作业船舶最多两条船矿一、矿二；
					List<Task> tasks = taskRepository.getTaskByStatus(1);
					for (Task e : tasks) {
						if (task.getBerth() == e.getBerth()) {
							throw new X27Exception("操作失败: " + "矿"
									+ (e.getBerth() == 1 ? "一" : (e.getBerth() == 2 ? "二" : "其他")) + "已被占用！");
						}
					}
					// 2.更新靠泊时间、状态改为作业船舶状态
					task.setStatus(1);
					task.setBerthingTime(DateTimeUtils.strDateTime2Date(params.get("berthingTime")));					
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					// 更新所有船舱状态为卸货|0
/*					for (Cargo cargo : task.getCargos()) {
						for (Cabin cabin : cargo.getCabins()) {
							cabin.setStatus(0);
						}
					}*/
					taskRepository.saveAndFlush(task);
					//3.重新计算作业量
					dataSyncStepC.start(Integer.parseInt(taskId));

				break;
			// 二、当前为作业状态时，可以修改状态为预靠或离港状态；
			case 1:
				if ("0".equals(status)) {
					// 1.检查当前泊位是否被占用，预靠船舶最多两条船矿一、矿二
					List<Task> ykTasks = taskRepository.getTaskByStatus(0);
					for (Task e : ykTasks) {
						if (task.getBerth() == e.getBerth()) {
							throw new X27Exception("操作失败: " + "矿"
									+ (e.getBerth() == 1 ? "一" : (e.getBerth() == 2 ? "二" : "其他")) + "已被占用！");
						}
					}					
					// 2.状态改为预靠状态，清空靠泊时间及开工时间
					task.setStatus(0);
					task.setBerthingTime(null);
					task.setBeginTime(null);
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					taskRepository.saveAndFlush(task);
					// 3.清空作业子表数据(子表B、C)
					this.deleteTempData(Integer.parseInt(taskId));
				} else if ("2".equals(status)) {
					// 1.设置完工时间，状态改为离港状态，
					task.setStatus(2);
					task.setEndTime(DateTimeUtils.strDateTime2Date(params.get("endTime")));
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					taskRepository.saveAndFlush(task);
					// 2.重新计算作业量
					dataSyncStepC.start(Integer.parseInt(taskId));
				} else {
					throw new X27Exception("操作失败: 船舶状态参数有误！");
				}
				break;
			// 三、当前为离港状态时，仅可以修改为作业状态
			case 2:
				// 1.检查当前泊位是否被占用，作业船舶最多两条船矿一、矿二；
				List<Task> zytasks = taskRepository.getTaskByStatus(1);
				for (Task e : zytasks) {
					if (task.getBerth() == e.getBerth()) {
						throw new X27Exception("操作失败: " + "矿"
								+ (e.getBerth() == 1 ? "一" : (e.getBerth() == 2 ? "二" : "其他")) + "已被占用！");
					}
				}
				// 2.检查是否最近离港的船舶
				Task latestTask = taskRepository.findFirstByOrderByEndTimeDesc();
				if (latestTask.getId() == task.getId()) {
					// 3.清空完工时间及离泊时间，状态改为作业船舶状态；

					task.setStatus(1);
					task.setEndTime(null);
					task.setDepartureTime(null);
//					task.setBeginTime(DateTimeUtils.strDateTime2Date(params.get("beginTime")));
					task.setUpdateTime(new Date());
					task.setUpdateUser(operator.getUserName());
					taskRepository.saveAndFlush(task);
					// 4.重新计算作业量
					dataSyncStepC.start(Integer.parseInt(taskId));
				} else {
					throw new X27Exception("操作失败: 只能修改最新离港的船舶！");
				}

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
	public Map<String, Object> doGetCargoDetail(Integer taskId, Integer cabinNo) {
		String msg = "操作成功！";
		Integer isSuccess = ConstantUtil.SuccessInt;

		Map<String, Object> result = new HashMap<>();
		try {
			
			Cargo cargo = cargoRepository.getCargoByTaskIdAndCabinNo(taskId, cabinNo);
			if (cargo == null) {
				result.put("code", ConstantUtil.FailInt);
				result.put("msg", "货物信息未找到！");
				return result;
			}

			Map<String, Object> data = new HashMap<>();
			data.put("cargoType", cargo.getCargoType());
			data.put("cargoCategory", cargo.getCargoCategory());
			data.put("loadingPort", cargo.getLoadingPort());
			data.put("quality", cargo.getQuality());
			data.put("moisture", cargo.getMoisture());
			data.put("owner", cargo.getCargoOwner());
			data.put("stowage", cargo.getStowage());
			String warehouse = cargoRepository.getCargoWarehouse(cargo.getId());
			data.put("warehouse", warehouse);
			
			result.put("msg", msg);
			result.put("data", data);
			result.put("code", isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", ConstantUtil.FailInt);
			result.put("msg", e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> doGetCargoDetailById(Integer cargoId) {
		String msg = "操作成功！";
		Integer isSuccess = ConstantUtil.SuccessInt;

		Map<String, Object> result = new HashMap<>();
		try {
			
			Cargo cargo = cargoRepository.findOne(cargoId);
			if (cargo == null) {
				result.put("code", ConstantUtil.FailInt);
				result.put("msg", "货物信息未找到！");
				return result;
			}

			Map<String, Object> data = new HashMap<>();
			data.put("cargoType", cargo.getCargoType());
			data.put("cargoCategory", cargo.getCargoCategory());
			data.put("loadingPort", cargo.getLoadingPort());
			data.put("quality", cargo.getQuality());
			data.put("moisture", cargo.getMoisture());
			data.put("owner", cargo.getCargoOwner());
			data.put("stowage", cargo.getStowage());
			String warehouse = cargoRepository.getCargoWarehouse(cargo.getId());
			data.put("warehouse", warehouse);
			
			result.put("msg", msg);
			result.put("data", data);
			result.put("code", isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", ConstantUtil.FailInt);
			result.put("msg", e.getMessage());
			return result;
		}
		return result;
	}
	
	@Override
	public Map<String, Object> doGetUnloaderUnshipInfo(int taskId, String startTime, String endTime) {
		String msg = "操作成功！";
		Integer isSuccess = ConstantUtil.SuccessInt;
		Map<String, Object> result = new HashMap<>();
		try {
			String sql = "";
			Object[] args = null;
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				sql = sqlMap.getSql("FN_009_2", taskId, taskId);
				args = new Object[] { startTime, endTime, taskId };
				System.out.println(startTime + "|" + endTime + "|" + taskId);
			} else {
				args = new Object[] { taskId };
				sql = sqlMap.getSql("FN_009_1", taskId, taskId);
				System.out.println(taskId);
			}
			List<Map<String, Object>> data = this.jdbcTemplate.queryForList(sql.toString(), args);
			System.out.println(sql.toString());
			result.put("msg", msg);
			result.put("data", data);
			result.put("code", isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", ConstantUtil.FailInt);
			result.put("msg", e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> doGetUnloaderUnshipDetailList(int taskId, String unloaderId, String startTime,
			String endTime) {
		String msg = "操作成功！";
		Integer isSuccess = ConstantUtil.SuccessInt;
		Map<String, Object> result = new HashMap<>();
		try {
			String sql = "";
			Object[] args = null;
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				sql = sqlMap.getSql("FN_010_2");
				sql = StringUtils.replace(sql, "%#", taskId + "");
				args = new Object[] { startTime, endTime, taskId, unloaderId };
				System.out.println(startTime + "|" + endTime + "|" + taskId + "|" + unloaderId);
				
			} else {
				sql = sqlMap.getSql("FN_010_1");
				sql = StringUtils.replace(sql, "%#", taskId + "");
				args = new Object[] { taskId, unloaderId };
				System.out.println(taskId + "|" + unloaderId);
			}
			List<Map<String, Object>> data = this.jdbcTemplate.queryForList(sql.toString(), args);
			System.out.println(sql.toString());
			result.put("msg", msg);
			result.put("data", data);
			result.put("code", isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", ConstantUtil.FailInt);
			result.put("msg", e.getMessage());
			return result;
		}
		return result;
	}
	
	private void deleteTempData(Integer taskId) {
		try {
			// 【任务子表】删除任务子表：卸船作业信息
			jdbcTemplate.update(sqlMap.getSql("02", "tab_temp_b_" + taskId));
			// 【任务子表】删除任务子表：组信息
			jdbcTemplate.update(sqlMap.getSql("03", "tab_temp_c_" + taskId));
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw e;
		}
	}	
	
}
