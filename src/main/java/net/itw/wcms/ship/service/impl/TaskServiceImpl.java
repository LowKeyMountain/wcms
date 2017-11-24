package net.itw.wcms.ship.service.impl;

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
import net.itw.wcms.ship.entity.TaskDetail;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.service.TaskService;
import net.itw.wcms.toolkit.DateTimeUtils;
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

/*	@Override
	public String getTaskList(Task task, Pageable pageable, String status) {

		Page<Task> page = findAllByStatus(pageable, status);
		if (page == null || page.getTotalPages() == 0) {
			return "{\"total\":0,\"rows\":0,\"aaData\":[]}";
		}

		int total = page.getTotalPages();
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("{\"total\":%d,\"rows\":[", total, total));
		int i = 0;
		for (Task sp : page) {
			if (i != 0)
				sb.append(",");
			addDataRow(sb, sp);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}*/
	@Override
	public String getTaskList(Task task, Pageable pageable, String status) {

		Page<Task> page = findAllByStatus(pageable, status);
		Map<String, Object> map = new HashMap<>();
		if (page == null || page.getTotalPages() == 0) {
			return "{\"total\":0,\"rows\":[],}";
		}

		int total = page.getTotalPages();
		JSONArray jsonArray = new JSONArray();
		for (Task t : page) {
			JSONObject jo = new JSONObject();
			jo.put("id", task.getId());
			jo.put("shipName", "");
			jo.put("updateUser", task.getUpdateUser());
			jo.put("updateTime", task.getUpdateTime());
			jsonArray.add(jo);
		}
		map.put("total", total);
		map.put("rows", JSONObject.toJSON(jsonArray));
		return map.toString();
	}

	private void addDataRow(StringBuilder sb, Task task) {
		sb.append("{\"id\":");
		sb.append(task.getId()).append(",\"shipName\":\"").append(task.getShip().getShipName()).append("\"")
				.append(",\"updateUser\":\"").append(task.getUpdateUser()).append("\"").append(",\"updateTime\":\"")
				.append(task.getUpdateTime()).append("\"");
		// .append(",operation:\"").append("&nbsp;&nbsp;")
		// .append("\"");
		sb.append("}");
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
		// TODO Auto-generated method stub
		taskRepository.updateStatusById(taskId, status, date, userId);
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Integer createTask(Task task, User operator) {
		// TODO Auto-generated method stub
		return null;
	}

}
