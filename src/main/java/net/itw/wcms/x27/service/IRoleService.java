package net.itw.wcms.x27.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.entity.User;

/**
 * 
 * Description:角色表相关操作
 * 
 * @author Michael 18 Oct 2017 14:45:51
 */
public interface IRoleService {

	/**
	 * 所有角色列表
	 * 
	 * @return
	 */
	List<Role> getRoleList();

	/**
	 * 根据userId获取关联的权限列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> getRoleListByUserId(Integer userId);

	/**
	 * 返回jquery-multi-select需要的options数据
	 * 
	 * @param userId
	 * @return
	 */
	String getRoleForOptions(Integer userId);
	
	/**
	 * 获取角色列表
	 * @param pageable
	 * @param params
	 * @return
	 */
	String getRoleDataTables(Pageable pageable, Map<String, String> params);
	
	/**
	 * 创建角色
	 * @param role
	 * @param user
	 * @return
	 */
	Integer createRole(Role role, User user);
	
	/**
	 * 
	 * @param role
	 * @param user
	 * @return
	 */
	Integer updateRoleById(Role role, User user);
}
