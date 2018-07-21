package net.itw.wcms.common.log.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.common.log.entity.SystemLog;

/**
 * 
 * Description: 系统日志服务
 * 
 * @author Michael 7 Jul 2018 17:34:05
 */
public interface ISystemLogService {

	/**
	 * 查询日志，根据uids
	 * @param uids
	 * @return
	 */
	SystemLog getSystemLogByUids(String uids);
	
	/**
	 * 保存日志
	 * 
	 * @param log
	 * @return
	 * @throws Exception
	 */
	Integer save(SystemLog log) throws Exception;
	
	/**
	 * 获取日志列表
	 * @param pageable
	 * @param params
	 * @return
	 */
	String getList(Pageable pageable, Map<String, String> params);
	
	/**
	 * 更新日志详情
	 * @param uids
	 * @param logDetails
	 * @return
	 * @throws Exception
	 */
	Integer updateLogDetailsByUids(String uids, String logDetails) throws Exception;
	
}
