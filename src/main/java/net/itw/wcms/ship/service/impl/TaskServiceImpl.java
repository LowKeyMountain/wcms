package net.itw.wcms.ship.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cargo;
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
	public Map<String, Object> getTaskList(Pageable pageable, Integer status, Map<String, String> params) {
		Map<String, Object> map = new HashMap<>();
		Page<Task> page = findAllByStatus(pageable, status);
		if (page == null || page.getTotalPages() == 0) {
			map.put("total", 0);
			map.put("rows", new ArrayList<String>());
			return map;
		}
		int total = page.getTotalPages();
		
//		List<Task> list = new ArrayList<>();
//		for (Task t : page) {
//			list.add(t);
//		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (Task cargo : page) {
			Map<String, Object> data = new HashMap<>();
			data.put("berth", cargo.getBerth() == 1 ? "泊位一":(cargo.getBerth() == 2 ? "泊位二" :""));
			data.put("shipName", cargo.getShip().getShipName());
			data.put("berthingTime", cargo.getBerthingTime() != null ? StringUtil.formatDate(cargo.getBerthingTime(), "yyyy-MM-dd") : "");
			data.put("beginTime", cargo.getBerthingTime() != null ? StringUtil.formatDate(cargo.getBerthingTime(), "yyyy-MM-dd") : "");
			data.put("endTime", cargo.getEndTime() != null ? StringUtil.formatDate(cargo.getEndTime(), "yyyy-MM-dd") : "");
			data.put("departureTime", cargo.getDepartureTime() != null ? StringUtil.formatDate(cargo.getDepartureTime(), "yyyy-MM-dd") : "");
			data.put("id", cargo.getId());
			list.add(data);
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
		
		task.setUpdateUser(operator.getUserName());
		task.setUpdateTime(new Date());
		task.setStatus(0);
		taskRepository.saveAndFlush(task);
		
		return ConstantUtil.SuccessInt;

	}
	
}
