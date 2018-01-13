package net.itw.wcms.ship.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.ship.entity.Unloader;
import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.x27.entity.User;

/**
 * Description: 卸船机数据
 *
 * @author sparking 2017-12-19 下午07:20:35
 *
 */
public interface IUnloaderService {
	
	/**
	 * 获取卸船机数据
	 * @param pageable
	 * @param Cmsid
	 * @param time
	 * @param move
	 * @param params
	 * @return
	 */
	String getUnloaderList(Pageable pageable, Map<String, String> params);
	
	/**
	 * 添加卸船机作业信息
	 * 
	 * @param unloader
	 * @param operator
	 * @return
	 */
	void createUnloader(Unloader unloader, User operator) throws Exception;
	
	/**
	 * 添加卸船机作业信息
	 * 
	 * @param unloader
	 * @return
	 */
	int addUnloader(UnloaderAll unloader, String tablename) throws Exception;
}
