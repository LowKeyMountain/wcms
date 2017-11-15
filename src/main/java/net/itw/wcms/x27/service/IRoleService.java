package net.itw.wcms.x27.service;

import java.util.List;

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
	
	/**
	 * 所有角色列表
	 * @return
	 */
	List<Role> getRoleList();
	
	/**
	 * 根据userId获取关联的权限列表
	 * @param userId
	 * @return
	 */
	List<Role> getRoleListByUserId(Integer userId);
	
	/**
	 * 返回jquery-multi-select需要的options数据
	 * @param userId
	 * @return
	 */
	String getRoleForOptions(Integer userId);
}
