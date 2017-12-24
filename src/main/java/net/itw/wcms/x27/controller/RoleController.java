package net.itw.wcms.x27.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IRoleService;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;
import net.itw.wcms.x27.utils.StringUtil;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PagePath = "./x27/role/";
	
	/**
	 * 初始化全局资源
	 * 
	 * @param req
	 * @param res
	 * @param modelMap
	 */
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
		this.req = req;
		this.res = res;
		this.session = req.getSession();
		this.modelMap = modelMap;
		modelMap.put("IncPath", req.getContextPath());
		modelMap.put("BasePath", req.getContextPath());
		modelMap.put("jsVersion", System.currentTimeMillis());
	}

	/**
	 * 跳转到角色管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main() {
		return new ModelAndView(PagePath + "list");
	}

	@RequestMapping("/addform")
	public ModelAndView addform() {
		// List<Department> departments =
		// departmentService.getDepartmentListForOption();
//		modelMap.put("departments", new ArrayList<>());
		return new ModelAndView(PagePath + "addform");
	}

	/**
	 * 新增角色信息
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/add")
	public MessageOption add(@ModelAttribute("role") Role role) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			mo.code = roleService.createRole(role, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}
	
	/**
	 * 返回角色信息列表
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getRoleDataTables", produces = "text/json;charset=UTF-8")
	public String getRoleDataTables(@RequestParam Map<String, String> params, ModelMap map) {
		Pageable pageable = PageUtils.buildPageRequest(params);
		return roleService.getRoleDataTables(pageable, params);
	}
	
	@RequestMapping("/updateform")
	public ModelAndView updateform(Integer id) {
		modelMap.put("id", id);
		return new ModelAndView(PagePath + "/updateform");
	}
	
	/**
	 * 修改角色信息
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/update")
	public Map<String, Object> update(@ModelAttribute("role") Role role) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			mo.code = roleService.updateRoleById(role, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("id", role.getId());
		map.put("code", mo.code);
		return map;
	}

	@RequestMapping(value = "/getUserDataRow", produces = "text/json;charset=UTF-8")
	public String getUserDataRow(@RequestParam("id") Integer id) throws Exception {
		return userService.getUserDataRow(id);
	}

	@RequestMapping("/delete")
	public MessageOption delete(@RequestParam("id") Integer id) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, " 操作成功！");
		try {
			User user = new User();
			user.setId(id);
			user.setIsDelete(true);

			userService.updateIsDeleteById(user, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

	@RequestMapping("/resetpass")
	public String resetpass(@RequestParam("id") Integer id) {

		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);

		User user = new User();
		user.setId(id);
		user.setPassword(ConstantUtil.DefaultMd5Password);

		userService.updatePasswordById(user, operator);

		return ConstantUtil.Success;
	}

	@RequestMapping("/lock")
	public String lock(@RequestParam("id") Integer id) {

		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);

		User user = new User();
		user.setId(id);
		user.setIsLock(true);

		userService.updateIsLockById(user, operator);

		return ConstantUtil.Success;
	}

	@RequestMapping("/unlock")
	public String unlock(@RequestParam("id") Integer id) {

		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);

		User user = new User();
		user.setId(id);
		user.setIsLock(false);

		userService.updateIsLockById(user, operator);

		return ConstantUtil.Success;
	}

	@RequestMapping("/assignform")
	public String assignform(Integer id, ModelMap map) {
		map.put("options", roleService.getRoleForOptions(id));
		map.put("id", id);
		return PagePath + "/assignform.ftl";
	}

	@RequestMapping("/assign")
	public String assign(Integer id, String selectedStr) {
		if (id == null || StringUtil.isStrEmpty(id.toString()) || StringUtil.isStrEmpty(selectedStr)) {
			return ConstantUtil.Fail;
		}
		userService.assignResource(id, selectedStr);
		return ConstantUtil.Success;
	}
	
}
