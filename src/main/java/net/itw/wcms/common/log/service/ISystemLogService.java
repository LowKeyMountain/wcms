package net.itw.wcms.common.log.service;

import net.itw.wcms.common.log.entity.SystemLog;

/**
 * 
 * Description: 系统日志服务
 * 
 * @author Michael 7 Jul 2018 17:34:05
 */
public interface ISystemLogService {

	/**
	 * 保存日志
	 * 
	 * @param log
	 * @return
	 * @throws Exception
	 */
	Integer save(SystemLog log) throws Exception;

}
