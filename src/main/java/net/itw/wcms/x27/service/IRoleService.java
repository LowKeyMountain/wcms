package net.itw.wcms.x27.service;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.x27.entity.Role;

/**
 * 
 * Description:角色表相关操作
 * 
 * @author Michael 18 Oct 2017 14:45:51
 */
public interface IRoleService {

	/**
	 * 根据查询条件，返回DataTables控件需要的Json数据格式
	 * 
	 * @param pageable
	 * @return
	 */
	String getUserDataTables(Role role, Pageable pageable);
}
