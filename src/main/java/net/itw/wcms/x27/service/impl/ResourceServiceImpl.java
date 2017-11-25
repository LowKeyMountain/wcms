package net.itw.wcms.x27.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.x27.entity.Resource;
import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.repository.ResourceRepository;
import net.itw.wcms.x27.service.IResourceService;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.StringUtil;

@Service
@Transactional
public class ResourceServiceImpl implements IResourceService {

	@Autowired
	private IUserService userService;
	@Autowired
	private ResourceRepository resourceRepository;

	public List<Resource> getResourceListByUserId(Integer userId) {
		return resourceRepository.getResourceListByUserId(userId);
	}
	
	public List<String> getResourcesByUserName(String userName) {
		User user = userService.getUserByUserName(userName);
		List<String> ids = new ArrayList<>();
		if (user != null) {
			List<Resource> list = this.getResourceListByUserId(user.getId());
			if (list != null) {
				for (Resource resource : list) {
					ids.add(resource.getId()+"");
				}
			}
		}
		return ids;
	}
	
	public List<Resource> getResourceList() {
		return resourceRepository.findAll();
	}

	public String getResourceForOptions(Integer userId) {
		List<Resource> assignResources = getResourceListByUserId(userId);
		List<Resource> allResources = getResourceList();

		Map<Integer, Resource> hmAssignRoles = new HashMap<Integer, Resource>();
		for (Resource r : assignResources) {
			hmAssignRoles.put(r.getId(), r);
		}
		StringBuilder sb = new StringBuilder();
		for (Resource r : allResources) {
			sb.append("<option value=\"").append(r.getId()).append("\"");
			if (hmAssignRoles.containsKey(r.getId())) {
				sb.append(" selected");
			}
			sb.append(">").append(r.getName()).append("</option>");
		}
		return sb.toString();
	}

	@Override
	public Resource getResourceById(Integer id) {
		return resourceRepository.getResourceById(id);
	}
	
	public Page<Resource> findAll(Pageable pageable) {
		return resourceRepository.findAll(pageable);
	}

	public String getPrivilegeForOptions(Integer userId) {
		List<Resource> assignResources = getResourceListByUserId(userId);
		List<Resource> allResources = getResourceList();

		Map<Integer, Resource> hmAssignResources = new HashMap<Integer, Resource>();
		for (Resource r : assignResources) {
			hmAssignResources.put(r.getId(), r);
		}
		StringBuilder sb = new StringBuilder();
		for (Resource r : allResources) {
			sb.append("<option value=\"").append(r.getId()).append("\"");
			if (hmAssignResources.containsKey(r.getId())) {
				sb.append(" selected");
			}
			sb.append(">").append(r.getName()).append("</option>");
		}
		return sb.toString();
	}

	@Override
	public String getResourceDataTables(Pageable pageable, Map<String, String> params) {
		Page<Resource> page = findAllSupportQuery(pageable, params);	
		if(page==null || page.getTotalPages()==0)
		{
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		
		long total = page.getTotalElements();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[",total, pageable.getPageSize()));
		int i= 0;
		for(Resource r: page)
		{
			if(i != 0) sb.append(",");
			addDataRow(sb,r);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}
	
	public Page<Resource> findAllSupportQuery(Pageable pageable, Map<String , String > params)
	{
		return resourceRepository.findAllSupportQuery(pageable, params);
	}
	
	private void addDataRow(StringBuilder sb,Resource r)
	{
		sb.append("[");
		sb.append("\"<input type=\\\"checkbox\\\"  class=\\\"checkboxes\\\" name=\\\"id[]\\\" value=\\\"").append(r.getId()).append("\\\">\"");
		sb.append(",").append(r.getId());
		sb.append(",\"").append(r.getName()).append("\"");
		sb.append(",\"").append(r.getRemark()).append("\"");
		sb.append(",\"").append(r.getUpdatePerson()).append("\"");
		if (r.getUpdateDate() != null) {
			sb.append(",\"").append(StringUtil.formatDate(r.getUpdateDate(),"yyyy-MM-dd HH:mm:ss")).append("\"");
		} else {
			sb.append(",\"").append("").append("\"");
		}
		sb.append(",\"")
		.append("<a href=\\\"javascript:Resource.update_click('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-edit\\\"></i> 修改</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:Resource.remove('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-times\\\"></i> 删除</a>")
		.append("\"");
		sb.append("]");
	}
	
	private void setPersonInsert(Resource resource,User user)
	{
		Date d = new Date();
		resource.setUpdatePerson(user.getUserName());
		resource.setUpdateDate(d);
	}
	
	@Override
	public Integer createResource(Resource resource,User user) {
		setPersonInsert(resource,user);
		resourceRepository.save(resource);
		return ConstantUtil.SuccessInt;
	}
	
	private void setPersonUpdate(Resource resource,User user)
	{
		Date d = new Date();
		resource.setUpdatePerson(user.getUserName());
		resource.setUpdateDate(d);
	}
	
	@Override
	public Integer updateResourceById(Resource resource,User user) {
		setPersonUpdate(resource,user);
		resourceRepository.updateResourceById(resource.getId(), resource.getName(), resource.getRemark(), resource.getUpdateDate(), resource.getUpdatePerson());
		return ConstantUtil.SuccessInt;
	}

	@Override
	public String getResourceDataRow(Integer id) {
		Resource resource = getResourceById(id);
		StringBuilder sb = new StringBuilder();
		addDataRow(sb, resource);
		return sb.toString();
	}
	
	@Override
	public void deleteById(Integer id) {
		resourceRepository.delete(id);
	}
}
