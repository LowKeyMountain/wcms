package net.itw.wcms.x27.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.repository.RoleRepository;
import net.itw.wcms.x27.service.IRoleService;
import net.itw.wcms.x27.utils.StringUtil;


@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	
    @Autowired
    private RoleRepository roleRepository;
    
	public Page<Role> findAll(Pageable pageable)
	{
		return roleRepository.findAll(pageable);
	}
	
	
	@Override
	public String getUserDataTables(Role role, Pageable pageable)
	{
		Page<Role> page = findAll(pageable);		
		if(page==null || page.getTotalPages()==0)
		{
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		
		int total = page.getSize();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[", total, total));
		int i= 0;
		for(Role u:page)
		{
			if(i != 0) sb.append(",");
			addDataRow(sb,u);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}
	

	private void addDataRow(StringBuilder sb, Role r)
	{
		sb.append("[");
		sb.append("\"<input type=\\\"checkbox\\\" name=\\\"id[]\\\" value=\\\"").append(r.getId()).append("\\\">\"");
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
	
	@Override
	public List<Role> getRoleListByUserId(Integer userId)
	{
		return roleRepository.getRoleListByUserId(userId);
	}
	
	@Override
	public List<Role> getRoleList()
	{
		return roleRepository.findAll();
	}
	
	@Override
	public String getRoleForOptions(Integer userId)
	{
		List<Role> assignRoles = getRoleListByUserId(userId);
		List<Role> allRoles = getRoleList();
		
		Map<Integer,Role> hmAssignRoles = new HashMap<Integer,Role>();
		for(Role r:assignRoles)
		{
			hmAssignRoles.put(r.getId(),r);
		}
		StringBuilder sb = new StringBuilder();
		for(Role r:allRoles)
		{
			sb.append("<option value=\"").append(r.getId()).append("\"");
			if(hmAssignRoles.containsKey(r.getId()))
			{
				sb.append(" selected");
			}
			sb.append(">").append(r.getRoleName()).append("</option>");
		}
		return sb.toString();
	}

}
