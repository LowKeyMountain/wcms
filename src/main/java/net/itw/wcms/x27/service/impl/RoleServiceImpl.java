package net.itw.wcms.x27.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.x27.entity.Resource;
import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.repository.RoleRepository;
import net.itw.wcms.x27.service.IResourceService;
import net.itw.wcms.x27.service.IRoleService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.StringUtil;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private IResourceService resourceService;
	
	public Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@Override
	public List<Role> getRoleListByUserId(Integer userId) {
		return roleRepository.getRoleListByUserId(userId);
	}

	@Override
	public List<Role> getRoleList() {
		return roleRepository.findAll();
	}

	public String getRoleForOptions(Integer userId) {
		List<Role> assignRoles = getRoleListByUserId(userId);
		List<Role> allRoles = getRoleList();

		Map<Integer, Role> hmAssignRoles = new HashMap<Integer, Role>();
		for (Role r : assignRoles) {
			hmAssignRoles.put(r.getId(), r);
		}
		StringBuilder sb = new StringBuilder();
		for (Role r : allRoles) {
			sb.append("<option value=\"").append(r.getId()).append("\"");
			if (hmAssignRoles.containsKey(r.getId())) {
				sb.append(" selected");
			}
			sb.append(">").append(r.getRoleName()).append("</option>");
		}
		return sb.toString();
	}
	
	public String getPrivilegeForOptions(Integer userId) {
		List<Role> assignRoles = getRoleListByUserId(userId);
		List<Role> allRoles = getRoleList();

		Map<Integer, Role> hmAssignRoles = new HashMap<Integer, Role>();
		for (Role r : assignRoles) {
			hmAssignRoles.put(r.getId(), r);
		}
		StringBuilder sb = new StringBuilder();
		for (Role r : allRoles) {
			sb.append("<option value=\"").append(r.getId()).append("\"");
			if (hmAssignRoles.containsKey(r.getId())) {
				sb.append(" selected");
			}
			sb.append(">").append(r.getRoleName()).append("</option>");
		}
		return sb.toString();
	}

	@Override
	public String getRoleDataTables(Pageable pageable, Map<String, String> params) {
		Page<Role> page = findAllSupportQuery(pageable, params);	
		if(page==null || page.getTotalPages()==0)
		{
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		
		int total = page.getTotalPages();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[",page.getContent().size(), page.getTotalElements()));
		int i= 0;
		for(Role r: page)
		{
			if(i != 0) sb.append(",");
			addDataRow(sb,r);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}
	
	public Page<Role> findAllSupportQuery(Pageable pageable, Map<String , String > params)
	{
		return roleRepository.findAllSupportQuery(pageable, params);
	}
	
	private void addDataRow(StringBuilder sb,Role r)
	{
		sb.append("[");
		sb.append("\"<input type=\\\"checkbox\\\"  class=\\\"checkboxes\\\" name=\\\"id[]\\\" value=\\\"").append(r.getId()).append("\\\">\"");
		sb.append(",").append(r.getId());
		sb.append(",\"").append(r.getRoleName()).append("\"");
		sb.append(",\"").append(r.getRemark()).append("\"");
		sb.append(",\"").append(r.getUpdatePersion()).append("\"");
		sb.append(",\"").append(StringUtil.formatDate(r.getUpdateDate(),"yyyy-MM-dd HH:mm:ss")).append("\"");
		sb.append(",\"")
		.append("<a href=\\\"javascript:Role.update_click('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-edit\\\"></i> 修改</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:Role.remove('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-times\\\"></i> 删除</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:Role.assign_click('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-key\\\"></i> 分配权限</a>")
		.append("\"");
		sb.append("]");
	}
	
	private void setPersonInsert(Role role,User user)
	{
		Date d = new Date();
		role.setUpdatePersion(user.getUserName());
		role.setUpdateDate(d);
	}
	
	@Override
	public Integer createRole(Role role,User user) {
		setPersonInsert(role,user);
		roleRepository.save(role);
		return ConstantUtil.SuccessInt;
	}
	
	private void setPersonUpdate(Role role,User operator)
	{
		Date d = new Date();
		role.setUpdatePersion(operator.getUserName());
		role.setUpdateDate(d);
	}
	
	@Override
	public Integer updateRoleById(Role role,User operator) {
		setPersonUpdate(role,operator);
		roleRepository.updateRoleById(role.getId(), role.getRoleName(), role.getRemark(), role.getUpdateDate(), role.getUpdatePersion());
		return ConstantUtil.SuccessInt;
	}
	
	@Override
	public Integer deleteRoleById(Integer id, User operator) {
		return roleRepository.deleteById(id);
	}
	
	@Override
	public Role getRoleById(Integer id) {
		return roleRepository.getOne(id);
	}
	
	@Override
	public String getRoleDataRow(Integer id) {
		Role u = getRoleById(id);
		StringBuilder sb = new StringBuilder();
		addDataRow(sb, u);
		return sb.toString();
	}
	
	@Override
	public String assignResource(Integer id, String selectedStr) {

		Role role = this.getRoleById(id);

		String[] selectedArr = selectedStr.split(",");

		role.getResources().clear();

		for (String s : selectedArr) {
			if (org.springframework.util.StringUtils.hasText(s)) {
				Resource resource = resourceService.getResourceById(Integer.parseInt(s));
				if (resource != null) {
					role.getResources().add(resource);
				}
			}
		}
		roleRepository.saveAndFlush(role);
		return ConstantUtil.Success;
	}
	
}
