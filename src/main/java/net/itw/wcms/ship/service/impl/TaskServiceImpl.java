package net.itw.wcms.ship.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.repository.TaskRepository;
import net.itw.wcms.ship.service.TaskService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.x27.utils.ConstantUtil;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	
    @Autowired
    private TaskRepository taskRepository;
    
	@Override
	public List<Task> getTaskByStatus(String status){
		return taskRepository.getTaskByStatus(status);
	}

	@Override
	public String doGetShipList(Ship ship, Pageable pageable, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getTaskById(Integer id) {
		return taskRepository.getTaskById(id);
	}

	@Override
	public Integer updateShipStatusByid(Integer taskId, String userId, String status) {
		String dateStr=DateTimeUtils.now2StrDateTime();
		Date date = DateTimeUtils.strDateTime2Date(dateStr);
		// TODO Auto-generated method stub
		taskRepository.updateStatusById(taskId, status, date, userId);
		return ConstantUtil.SuccessInt;
	}


}
