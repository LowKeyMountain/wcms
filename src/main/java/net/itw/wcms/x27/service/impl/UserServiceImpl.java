package net.itw.wcms.x27.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.security.PasswordUtils;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.entity.UserSearchModel;
import net.itw.wcms.x27.repository.UserRepository;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.StringUtil;


@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
    @Autowired
    private UserRepository userRepository;
    
    public Integer createUser(User user, User operator){
    	setPersonInsert(user, operator);
    	user.setPassword(PasswordUtils.hash(user.getPassword()));
        userRepository.save(user);
        return ConstantUtil.SuccessInt;
    }
    
	private void setPersonInsert(User user,User operator)
	{
		Date d = new Date();
		user.setIsLock(false);
		user.setIsDelete(false);
		user.setCreatePerson(operator.getUserName());
		user.setUpdatePerson(operator.getUserName());
		user.setCreateDate(d);
		user.setUpdateDate(d);
	}
    
	public Page<User> findByIsDeleteFalse(UserSearchModel searchModel)
	{
		return userRepository.findByIsDeleteFalse(false, searchModel);
	}
	
	
	
	@Override
	public String getUserDataTables(User user, UserSearchModel searchModel)
	{
		Page<User> page = findByIsDeleteFalse(searchModel);		
		if(page==null || page.getTotalPages()==0)
		{
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		
		int total = page.getSize();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[", total, total));
		int i= 0;
		for(User u:page)
		{
			if(i != 0) sb.append(",");
			addDataRow(sb,u);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}
	

	private void addDataRow(StringBuilder sb,User u)
	{
		sb.append("[");
		sb.append("\"<input type=\\\"checkbox\\\" name=\\\"id[]\\\" value=\\\"").append(u.getId()).append("\\\">\"");
		sb.append(",").append(u.getId());
		sb.append(",\"").append(u.getUserName()).append("\"");
		sb.append(",\"").append(u.getRealName()).append("\"");
		sb.append(",\"").append(u.getGender()?"男":"女").append("\"");
		sb.append(",\"").append(u.getIsAdmin()?"管理员":"普通").append("\"");
		sb.append(",\"").append(u.getIsLock()?"是":"否").append("\"");
//		sb.append(",\"").append(u.getDepartmentName()==null?"":u.getDepartmentName()).append("\"");
		sb.append(",\"").append(u.getUpdatePerson()).append("\"");
		sb.append(",\"").append(StringUtil.formatDate(u.getUpdateDate(),"yyyy-MM-dd HH:mm:ss")).append("\"");
		sb.append(",\"")
		.append("<a href=\\\"javascript:User.update_click('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-edit\\\"></i> 修改</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:").append(u.getIsLock()?"User.unlock('":"User.lock('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-").append(u.getIsLock()?"un":"").append("lock\\\"></i> ").append(u.getIsLock()?"解锁":"锁定").append("</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:User.remove('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-times\\\"></i> 删除</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:User.assign_click('").append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-key\\\"></i> 分配角色</a>")
		.append("\"");
		sb.append("]");
	}

	@Override
	public User getUserByUsername(String userName) {
		return userRepository.getUserByUserName(userName);
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.getUserById(id);
	}

	@Override
	public Integer getUserTotalBySearch(UserSearchModel searchModel) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void setPersonUpdate(User user,User operator)
	{
		Date d = new Date();
		user.setUpdatePerson(operator.getUserName());
		user.setUpdateDate(d);
	}
	
	@Override
	public Integer updateRealNameAndGenderAndIsAdminById(User user, User operator) {
		setPersonUpdate(user, operator);
		userRepository.updateRealNameAndGenderAndIsAdminById(user.getId(), user.getRealName(), user.getGender(),
				user.getIsAdmin(), user.getUpdateDate(), user.getUpdatePerson());
		return ConstantUtil.SuccessInt;
	}
	
	@Override
	public Integer updatePasswordById(User user, User operator) {
		setPersonUpdate(user, operator);
		userRepository.updatePasswordById(user.getId(), user.getPassword(), user.getUpdateDate(), user.getUpdatePerson());
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Integer updateIsLockById(User user, User operator) {
		setPersonUpdate(user, operator);
		userRepository.updateIsLockById(user.getId(), user.getIsLock(), user.getUpdateDate(), user.getUpdatePerson());
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Integer updateIsDeleteById(User user, User operator) {
		setPersonUpdate(user, operator);
		userRepository.updateIsDeletekById(user.getId(), user.getIsDelete(), user.getUpdateDate(), user.getUpdatePerson());
		return ConstantUtil.SuccessInt;
	}
	
	
	@Override
	public Integer deleteUserById(Integer id, User operator) {
		return userRepository.deleteById(id);
	}

	@Override
	public String assignRole(Integer id, String selectedStr) {
		return null;
	}

	@Override
	public String getUserDataRow(Integer id)
	{
		User u = getUserById(id);
		StringBuilder sb = new StringBuilder();
		addDataRow(sb,u);
		return sb.toString();
	}

}
