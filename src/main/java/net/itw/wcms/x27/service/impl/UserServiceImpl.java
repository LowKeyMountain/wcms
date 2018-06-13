package net.itw.wcms.x27.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.toolkit.security.PasswordUtils;
import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.repository.UserRepository;
import net.itw.wcms.x27.service.IRoleService;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.StringUtil;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IRoleService roleService;
	
//	@Autowired
//	private IResourceService resourceService;
	

	public Integer createUser(User user, User operator) {
		user.setIsAdmin(false);
		setPersonInsert(user, operator);
		user.setPassword(PasswordUtils.hash(user.getPassword()));
		userRepository.save(user);
		return ConstantUtil.SuccessInt;
	}

	private void setPersonInsert(User user, User operator) {
		Date d = new Date();
		user.setIsLock(false);
		user.setIsDelete(false);
		user.setCreatePerson(operator.getUserName());
		user.setUpdatePerson(operator.getUserName());
		user.setCreateDate(d);
		user.setUpdateDate(d);
	}

	public Page<User> findByIsDeleteFalse(Pageable pageable) {
		return userRepository.findByIsDeleteFalse(pageable);
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public Page<User> findAllSupportQuery(Pageable pageable, Map<String, String> params) {
		return userRepository.findAllSupportQuery(pageable, params);
	}

	@Override
	public String getUserDataTables(User user, Pageable pageable) {
		Page<User> page = findAllSupportQuery(pageable, null);
		if (page == null || page.getTotalPages() == 0) {
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}

		int total = page.getTotalPages();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[", total, total));
		int i = 0;
		for (User u : page) {
			if (i != 0)
				sb.append(",");
			addDataRow(sb, u);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}

	private void addDataRow(StringBuilder sb, User u) {
		sb.append("[");
//		sb.append("\"<input type=\\\"checkbox\\\" class=\\\"checkboxes\\\" name=\\\"id[]\\\" value=\\\"")
//				.append(u.getId()).append("\\\">\"").append(",");
		sb.append(u.getId());
		sb.append(",\"").append(u.getUserName()).append("\"");
		sb.append(",\"").append(u.getRealName()).append("\"");
		//sb.append(",\"").append(u.getGender() ? "男" : "女").append("\"");
//		sb.append(",\"").append(u.getIsAdmin() ? "管理员" : "普通").append("\"");
		sb.append(",\"").append(u.getIsLock() ? "是" : "否").append("\"");
		// sb.append(",\"").append(u.getDepartmentName()==null?"":u.getDepartmentName()).append("\"");
		sb.append(",\"").append(u.getUpdatePerson()).append("\"");
		sb.append(",\"").append(StringUtil.formatDate(u.getUpdateDate(), "yyyy-MM-dd HH:mm:ss")).append("\"");
		sb.append(",\"").append("<a href=\\\"javascript:User.update_click('").append(u.getId())
				.append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-edit\\\"></i> 修改</a>")
				.append("&nbsp;&nbsp;<a href=\\\"javascript:").append(u.getIsLock() ? "User.unlock('" : "User.lock('")
				.append(u.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-")
				.append(u.getIsLock() ? "un" : "").append("lock\\\"></i> ").append(u.getIsLock() ? "解锁" : "锁定")
				.append("</a>").append("&nbsp;&nbsp;<a href=\\\"javascript:User.remove('").append(u.getId())
				.append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-times\\\"></i> 删除</a>")
				.append("&nbsp;&nbsp;<a href=\\\"javascript:User.assign_click('").append(u.getId())
				.append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-key\\\"></i> 关联角色</a>")
				.append("\"");
		sb.append("]");
	}

	@Override
	public User getUserByUserName(String userName) {
		return userRepository.getUserByUserName(userName);
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.getUserById(id);
	}

	@Override
	public Integer getUserTotalBySearch(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setPersonUpdate(User user, User operator) {
		Date d = new Date();
		user.setUpdatePerson(operator.getUserName());
		user.setUpdateDate(d);
	}

	@Override
	public Integer updateRealNameAndGenderAndIsAdminById(User user, User operator) {
		setPersonUpdate(user, operator);
		userRepository.updateRealNameAndGenderAndIsAdminById(user.getId(), user.getRealName(), user.getGender(),
				false, user.getUpdateDate(), user.getUpdatePerson());
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Integer updatePasswordById(User user, User operator) {
		setPersonUpdate(user, operator);
		userRepository.updatePasswordById(user.getId(), user.getPassword(), user.getUpdateDate(),
				user.getUpdatePerson());
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
		userRepository.updateIsDeletekById(user.getId(), user.getIsDelete(), user.getUpdateDate(),
				user.getUpdatePerson());
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Integer deleteUserById(Integer id, User operator) {
		return userRepository.deleteById(id);
	}

	@Override
	public String assignResource(Integer id, String selectedStr) {

		User user = this.getUserById(id);

		String[] selectedArr = selectedStr.split(",");

		user.getRoles().clear();

		for (String s : selectedArr) {
			if (org.springframework.util.StringUtils.hasText(s)) {
				Role role = roleService.getRoleById(Integer.parseInt(s));
				if (role != null) {
					user.getRoles().add(role);
				}
			}
		}
		
		userRepository.saveAndFlush(user);
		return ConstantUtil.Success;
	}

	@Override
	public String getUserDataRow(Integer id) {
		User u = getUserById(id);
		StringBuilder sb = new StringBuilder();
		addDataRow(sb, u);
		return sb.toString();
	}

	@Override
	public Map<String, Object> getUserBySearch(Map<String, String> serArgs, String sortType) throws Exception {
		// 获得分页对象pageable，并且在pageable中页码是从0开始，设定按照sortType升序排列
		Pageable pageable = PageUtils.buildPageRequest(Integer.valueOf(serArgs.get("pageNum")),
				Integer.valueOf(serArgs.get("pageSize")), sortType);

		Page<User> objPage = userRepository.findAll(new Specification<User>() {

			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(serArgs.get("serSbmc"))) {
					lstPredicates.add(cb.like(root.get("sbmc").as(String.class), "%" + serArgs.get("serSbmc") + "%"));
				}
				if (StringUtils.isNotBlank(serArgs.get("serSblx"))) {
					lstPredicates.add(cb.like(root.get("sblx").as(String.class), "%" + serArgs.get("serSblx") + "%"));
				}
				if (StringUtils.isNotBlank(serArgs.get("serSssx"))) {
					lstPredicates.add(cb.like(root.get("sssx").as(String.class), "%" + serArgs.get("serSssx") + "%"));
				}
				if (StringUtils.isNotBlank(serArgs.get("serJdmc"))) {
					lstPredicates.add(cb.like(root.get("jdmc").as(String.class), "%" + serArgs.get("serJdmc") + "%"));
				}

				if (StringUtils.isNotBlank(serArgs.get("serSbzt"))) {
					lstPredicates.add(cb.equal(root.get("sbzt"), serArgs.get("serSbzt")));
				}

				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		}, pageable);

		return PageUtils.getPageMap(objPage);
	}

	@Override
	public String getUserDataTables(Pageable pageable, Map<String, String> params) {
		Page<User> page = findAllSupportQuery(pageable, params);
		if (page == null || page.getTotalPages() == 0) {
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[", page.getContent().size(), page.getTotalElements()));
		int i = 0;
		for (User u : page) {
			if (i != 0)
				sb.append(",");
			addDataRow(sb, u);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}

	public MessageOption verifyLogin(String userName, String password, String token) throws Exception {

		MessageOption option = new MessageOption(ConstantUtil.SuccessInt, "登录成功！");

		if (!StringUtils.equals(ConstantUtil.DefaultToken, token)) {
			option.code = ConstantUtil.FailInt;
			option.msg = "登录失败：用户令牌失效！";
			return option;
		}

		User user = userRepository.getUserByUserName(userName);

		if (user == null || user.getIsDelete()) {
			option.code = ConstantUtil.FailInt;
			option.msg = "登录失败：用户名不存在！";
			return option;
		}
		
		if (!StringUtils.equals(password, user.getPassword())) {
			option.code = ConstantUtil.FailInt;
			option.msg = "登录失败：密码输入有误，请重新输入！";
			return option;
		}
		
		if (user.getIsLock()) {
			option.code = ConstantUtil.FailInt;
			option.msg = "登录失败：该用户被锁定，请联系系统管理员！";
			return option;
		}

		return option;
	}

}
