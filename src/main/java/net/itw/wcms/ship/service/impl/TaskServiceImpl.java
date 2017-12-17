package net.itw.wcms.ship.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.StringUtil;


@Service
@Transactional
public class TaskServiceImpl implements ITaskService {
	
	@Autowired
	private TaskRepository taskRepository;

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
			jo.put("berth", t.getBerth() == 1 ? "泊位一":(t.getBerth() == 2 ? "泊位二" :""));
			jo.put("endTime", t.getEndTime() != null ? StringUtil.formatDate(t.getEndTime(), "yyyy-MM-dd") : "");
			jo.put("departureTime", t.getDepartureTime() != null ? StringUtil.formatDate(t.getDepartureTime(), "yyyy-MM-dd") : "");
			
			jo.put("id", t.getId());
			jo.put("shipName", t.getShip().getShipName());
			jo.put("berthingTime", t.getBerthingTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getBerthingTime()));
			jo.put("beginTime", t.getBeginTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getBeginTime()));
			jo.put("updateUser", t.getUpdateUser());
			jo.put("updateTime", t.getUpdateTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getUpdateTime()));
			jo.put("operation", "<a href='javascript:Task.update_click(" + t.getId() + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i> 修改</a>");
			jsonArray.add(jo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", total);
		jsonObject.put("rows", jsonArray);
		return jsonObject.toString();
	}
	
	@Override
	public List<Task> getTaskByStatus(String status) {
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
		taskRepository.saveAndFlush(task);
		
		return ConstantUtil.SuccessInt;

	}
	
	@Override
	public Integer updateTask(Task task, User operator) {
		
//		task.setUpdateUser(operator.getUserName());
		task.setUpdateTime(new Date());
		task.setStatus(0);
		taskRepository.saveAndFlush(task);
		
		return ConstantUtil.SuccessInt;

	}
	
}
