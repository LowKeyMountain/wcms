package net.itw.wcms.ship.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.service.TaskService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.x27.entity.Resource;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.ConstantUtil;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	public Page<Task> findAllByStatus(Pageable pageable, String status) {
		return taskRepository.findAllByStatus(pageable, status);
	}

	@Override
	public String getTaskList(Task task, Pageable pageable, String status) {

		Page<Task> page = findAllByStatus(pageable, status);
		JSONObject jo=null;
		if (page == null || page.getTotalPages() == 0) {
			return "{\"total\":0,\"rows\":[],}";
		}
		int total = page.getTotalPages();
		JSONArray jsonArray = new JSONArray();
		for (Task t : page) {
			jo = new JSONObject();
			jo.put("id", t.getId());
			jo.put("shipName", t.getShip().getShipName());
			jo.put("berthingTime", t.getBerthingTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getBerthingTime()));
			jo.put("beginTime", t.getBeginTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getBeginTime()));
			jo.put("updateUser", t.getUpdateUser());
			jo.put("updateTime", t.getUpdateTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getUpdateTime()));
			jsonArray.add(jo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", total);
		jsonObject.put("rows", jsonArray);
		return jsonObject.toString();
	}
	
	public Page<Task> findAll(Pageable pageable, Map<String , String > params)
	{
		return taskRepository.findAll(pageable, params);
	}
	
	@Override
	public Map<String, ?> getTaskList(Pageable pageable, Map<String, String> params) {
		Map<String, Object> map = new HashMap<>();
		Page<Task> page = findAll( pageable, params);
		if (page == null || page.getTotalPages() == 0) {
			map.put("total", 0);
			map.put("rows", new ArrayList<String>());
			return map;
		}
		int total = page.getTotalPages();
		List<Task> list = new ArrayList<>();
		for (Task t : page) {
			list.add(t);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
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
		return null;
	}
	
}
