package net.itw.wcms.common.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.common.log.entity.SystemLog;
import net.itw.wcms.common.log.repository.SystemLogRepository;
import net.itw.wcms.common.log.service.ISystemLogService;
import net.itw.wcms.x27.utils.ConstantUtil;

@Service
@Transactional
public class SystemLogServiceImpl implements ISystemLogService{
	
	@Autowired
	private SystemLogRepository systemLogRepository;
	
	@Override
	public Integer save(SystemLog log) throws Exception {
		try {
			systemLogRepository.save(log);
		} catch (Exception e) {
			return ConstantUtil.FailInt;
		}
		return ConstantUtil.SuccessInt;
	}

}
