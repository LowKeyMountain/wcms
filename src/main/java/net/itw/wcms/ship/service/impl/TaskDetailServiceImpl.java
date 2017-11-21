package net.itw.wcms.ship.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.TaskDetail;
import net.itw.wcms.ship.repository.TaskDetailRepository;
import net.itw.wcms.ship.service.TaskDetailService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.x27.utils.ConstantUtil;


@Service
@Transactional
public class TaskDetailServiceImpl implements TaskDetailService {
	
    @Autowired
    private TaskDetailRepository taskDetailRepository;

	@Override
	public TaskDetail getTaskDetailById(Integer detailId) {
		// TODO Auto-generated method stub
		return taskDetailRepository.getTaskDetailById(detailId);
	}

	@Override
	public List<TaskDetail> getTaskDetailByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		return taskDetailRepository.getTaskDetailByTaskId(taskId);
	}
	
	@Override
	public Integer updateCabinStatusByid(Integer detailId, String userId, String status) {
		String dateStr=DateTimeUtils.now2StrDateTime();
		Date date = DateTimeUtils.strDateTime2Date(dateStr);
		// TODO Auto-generated method stub
		taskDetailRepository.updateStatusById(detailId, status, date, userId);
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Integer updateCabinPositionByid(List<TaskDetail> taskDetailList, String userId) {
		
		String dateStr=DateTimeUtils.now2StrDateTime();
		Date date = DateTimeUtils.strDateTime2Date(dateStr);		
		for(TaskDetail taskDetail:taskDetailList) {
			taskDetailRepository.updatePositionById(taskDetail.getId(), taskDetail.getStartPosition(), taskDetail.getEndPosition(), date, userId);
		}
		return ConstantUtil.SuccessInt;
	}



}
