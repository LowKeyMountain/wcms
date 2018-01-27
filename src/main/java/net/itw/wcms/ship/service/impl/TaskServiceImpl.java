package net.itw.wcms.ship.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.repository.CabinRepository;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.ConstantUtil;


@Service
@Transactional
public class TaskServiceImpl implements ITaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CabinRepository cabinRepository;

	public Page<Task> findAllByStatus(Pageable pageable, Integer status) {
		return taskRepository.findAllByStatus(pageable, status);
	}

	
	public Page<Task> findAll(Pageable pageable, Map<String , String > params)
	{
		return taskRepository.findAll(pageable, params);
	}
	
	@Override
	public String getTaskList(Pageable pageable, Integer status, Map<String, String> params) {
		Page<Task> page = findAllByStatus(pageable, status);
		JSONObject jo=null;
		if (page == null || page.getTotalPages() == 0) {
			return "{\"total\":0,\"rows\":[],}";
		}
		int total = page.getTotalPages();
		JSONArray jsonArray = new JSONArray();
		for (Task t : page) {
			jo = new JSONObject();
			jo.put("berth", t.getBerth() == 1 ? "矿一":(t.getBerth() == 2 ? "矿二" :""));
			jo.put("endTime", t.getEndTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getEndTime()));
			jo.put("departureTime", t.getDepartureTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getDepartureTime()));
			
			jo.put("shipName", t.getShip().getShipName());
			jo.put("berthingTime", t.getBerthingTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getBerthingTime()));
			jo.put("beginTime", t.getBeginTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getBeginTime()));
			jo.put("updateUser", t.getUpdateUser());
			jo.put("updateTime", t.getUpdateTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getUpdateTime()));
			String operation = "<a href='javascript:Task.update_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i> 修改</a>";
			switch (status) {
			case 0:
				operation += "<a href='javascript:Task.unshipInfo_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船情况</a>";
				operation += "<a href='javascript:Task.unloadProgress_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船进度</a>";
				operation += "<a href='javascript:Task.unloaderOverview_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船机总览</a>";
				operation += "<a href='javascript:Task.remove(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-times'></i> 删除</a>";
				break;
			case 1:
				operation += "<a href='javascript:Task.unshipInfo_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船情况</a>";
				operation += "<a href='javascript:Task.unloadProgress_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船进度</a>";
				operation += "<a href='javascript:Task.unloaderOverview_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船机总览</a>";
				break;	
			case 2:
				operation += "<a href='javascript:Task.unshipInfo_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船情况</a>";
				operation += "<a href='javascript:Task.unloadProgress_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船进度</a>";
				operation += "<a href='javascript:Task.unloaderOverview_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i>卸船机总览</a>";
				break;	
			default:
				break;
			}
			jo.put("operation", operation);
			jsonArray.add(jo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", total);
		jsonObject.put("rows", jsonArray);
		return jsonObject.toString();
	}
	
	@Override
	public List<Task> getTaskByStatus(Integer status) {
		return taskRepository.getTaskByStatus(status);
	}

	@Override
	public Task getTaskById(Integer id) {
		return taskRepository.getTaskById(id);
	}

	@Override
	public Integer updateShipStatusByid(Integer taskId, String userId, String status) {
		String dateStr = DateTimeUtils.now2StrDateTime();
		Date date = DateTimeUtils.strDateTime2Date(dateStr);
		taskRepository.updateStatusById(taskId, status, date, userId);
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Integer createTask(Task task, User operator) {
		task.setUpdateUser(operator.getUserName());
		task.setUpdateTime(new Date());
		task.setStatus(0);
		
		autoCreateCargoCabinInfo(task, operator);
		taskRepository.saveAndFlush(task);
		return ConstantUtil.SuccessInt;

	}
	
	/**
	 * 自动创建货物、船舱信息
	 * 
	 * @param task
	 * @param operator
	 */
	private void autoCreateCargoCabinInfo(Task task, User operator) {
		Integer cabinNum = task.getShip().getCabinNum();
		Task po_task = taskRepository.getTaskById(task.getId());
		if (po_task == null) {
			Cargo cargo = new Cargo();
			cargo.setTask(task);
			for (int i = 1; i <= cabinNum; i++) {
				Cabin cabin = new Cabin();
				cabin.setCabinNo(i);
				cabin.setStartPosition(Float.valueOf(0));
				cabin.setEndPosition(Float.valueOf(0));
				cabin.setPreunloading(Float.valueOf(0));
				cabin.setCargo(cargo);
				cabin.setUpdateTime(new Date());
				cabin.setUpdateUser(operator.getUserName());
				cargo.getCabins().add(cabin);
			}
			task.getCargos().add(cargo);
		} else {
			Cargo cargo = null;
			Map<Integer, Cabin> map = new HashMap<>();
			for (Cargo c : po_task.getCargos()) {
				if (StringUtils.isEmpty(c.getCargoCategory())) {
					cargo = c;
				}
				for (Cabin cabin : c.getCabins()) {
					map.put(cabin.getCabinNo(), cabin);
				}
			}
			if (cargo == null) {
				cargo = new Cargo();
				cargo.setTask(task);
				task.getCargos().add(cargo);
			}
			Integer num = cabinNum - map.size();
			if (num > 0) { // 需新增船舱
				for (int i = 1; i <= num; i++) {
					Cabin cabin = new Cabin();
					cabin.setCargo(cargo);
					cabin.setStartPosition(Float.valueOf(0));
					cabin.setEndPosition(Float.valueOf(0));
					cabin.setPreunloading(Float.valueOf(0));
					cabin.setCabinNo(map.size() + i);
					cabin.setUpdateTime(new Date());
					cabin.setUpdateUser(operator.getUserName());
					cargo.getCabins().add(cabin);
				}
			} else if (num < 0) { // 需删除船舱
				List<Cabin> entities = new ArrayList<>();
				for (num = Math.abs(num) -1 ; num >= 0; num--) {
					Cabin cabin = map.get(map.size() - num);
					if (cabin == null) {
						continue;
					}
					cabin.setCargo(null);
					entities.add(cabin);
				}
				cabinRepository.deleteInBatch(entities);
			}
		}
	}
	
	@Override
	public Integer updateTask(Task task, User operator) {
		
		Task persist = taskRepository.getTaskById(task.getId());
		
		task.setUpdateUser(operator.getUserName());
		task.setUpdateTime(new Date());
		task.setStatus(persist.getStatus());
		task.setBeginTime(persist.getBeginTime());
		task.setEndTime(persist.getEndTime());
		
		autoCreateCargoCabinInfo(task, operator);
		taskRepository.saveAndFlush(task);
		
		return ConstantUtil.SuccessInt;

	}


	@Override
	public MessageOption delete(Task task) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			if (task == null) {
				mo.code = ConstantUtil.FailInt;
				mo.msg = "操作失败：船舶信息未找到！";
				return mo;
			}
			if (task.getStatus() != 0) {
				mo.code = ConstantUtil.FailInt;
				mo.msg = "操作失败：只有预靠船舶可以删除！";
				return mo;
			}
			task.setShip(null);
			taskRepository.delete(task);
		} catch (Exception e) {
			e.printStackTrace();
			mo.code = ConstantUtil.FailInt;
			mo.msg = e.getMessage();
		}
		return mo;
	}
	
}
