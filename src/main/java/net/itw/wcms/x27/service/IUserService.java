package net.itw.wcms.x27.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;

/**
 * 
 * Description: 用户表相关操作
 * 
 * @author Michael 16 Sep 2017 22:37:15
 */
public interface IUserService {
	
	/**
	 * 验证用户登录
	 * @param userName
	 * @param password
	 * @param token
	 * @return
	 * @throws Exception
	 */
	MessageOption verifyLogin(String userName, String password, String token) throws Exception;
	
	/**
	 * 根据用户名查询用户
	 * 
	 * @param userName
	 * @return
	 */
	User getUserByUserName(String userName);

	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 */
	User getUserById(Integer id);

	/**
	 * 根据条件查询用户总数
	 * 
	 * @param pageable
	 * @return
	 */
	Integer getUserTotalBySearch(Pageable pageable);

	/**
	 * 根据条件查询用户List
	 * 
	 * @param pageable
	 * @return
	 */
	Page<User> findByIsDeleteFalse(Pageable pageable);

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @param operator
	 * @return
	 */
	Integer createUser(User user, User operator);

	/**
	 * 根据id更新用户信息
	 * 
	 * @param user
	 * @param operator
	 * @return
	 */
	Integer updateIsDeleteById(User user, User operator);

	/**
	 * 根据id更新用户信息
	 * 
	 * @param user
	 * @param operator
	 * @return
	 */
	Integer updatePasswordById(User user, User operator);

	/**
	 * 根据id更新用户信息
	 * 
	 * @param user
	 * @param operator
	 * @return
	 */
	Integer updateIsLockById(User user, User operator);

	/**
	 * 根据id更新用户信息
	 * 
	 * @param user
	 * @param operator
	 * @return
	 */
	Integer updateRealNameAndGenderAndIsAdminById(User user, User operator);

	/**
	 * 根据id删除用户
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteUserById(Integer id, User operator);

	/**
	 * 用户分配资源
	 * 
	 * @param id
	 * @param selectedStr
	 * @return
	 */
	String assignResource(Integer id, String selectedStr);

	/**
	 * 根据查询条件，返回DataTables控件需要的Json数据格式
	 * 
	 * @param pageable
	 * @return
	 */
	String getUserDataTables(User user, Pageable pageable);
	
	/**
	 * 根据查询条件，返回DataTables控件需要的Json数据格式
	 * 
	 * @param pageable
	 * @param params
	 * @return
	 */
	String getUserDataTables(Pageable pageable, Map<String, String> params);
	
    /**
     * 查询用户信息列表(支持分页和多条件查询)
     * @param serArgs
     * @param sortType
     * @return
     * @throws Exception
     */
    Map<String, Object> getUserBySearch(final Map<String, String> serArgs, final String sortType) throws Exception;
	

	/**
	 * 返回DataTables控件需要的一行Json数据格式
	 * 
	 * @param id
	 * @return
	 */
	String getUserDataRow(Integer id);

}
