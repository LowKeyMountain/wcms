package net.itw.wcms.x27.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.x27.entity.Resource;
import net.itw.wcms.x27.entity.User;

/**
 * 
 * Description:资源表相关操作
 * 
 * @author Michael 15 Nov 2017 23:09:48
 */
public interface IResourceService {
	
	/**
	 * 返回jquery-multi-select需要的options数据
	 * 
	 * @param userId
	 * @return
	 */
	String getResourceForOptions(Integer userId);
	
	/**
	 * 根据id查询资源
	 * 
	 * @param id
	 * @return
	 */
	Resource getResourceById(Integer id);
	
	/**
	 * 所有资源列表
	 * 
	 * @return
	 */
	List<Resource> getResourceList();

	/**
	 * 根据userId获取关联的权限列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Resource> getResourceListByUserId(Integer userId);
	
	/**
	 * 根据userName获取权限ID列表
	 * @param userName
	 * @return
	 */
	List<String> getResourcesByUserName(String userName);

	/**
	 * 获取资源列表
	 * @param pageable
	 * @param params
	 * @return
	 */
	String getResourceDataTables(Pageable pageable, Map<String, String> params);
	
	/**
	 * 创建资源
	 * @param resource
	 * @param user
	 * @return
	 */
	Integer createResource(Resource resource, User user);
	
	/**
	 * 
	 * @param resource
	 * @param user
	 * @return
	 */
	Integer updateResourceById(Resource resource, User user);

	String getResourceDataRow(Integer id);

	void deleteById(Integer id);
	
}
