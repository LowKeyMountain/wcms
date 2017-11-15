package net.itw.wcms.shipping;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.itw.wcms.x27.entity.PrivilegeModelConstant;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.SessionUtil;
import net.itw.wcms.x27.utils.StringUtil;
import net.itw.wcms.x27.utils.WebUtil;

@RestController
@RequestMapping(value = "/shipping")
public class ShippingController {

	@Autowired
	private IUserService userService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

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
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shippinglist")
	public ModelAndView main() {
		return new ModelAndView("./shipping/shippinglist");
	}

	@RequestMapping("/addshipping")
	public ModelAndView addform() {
		// List<Department> departments =
		// departmentService.getDepartmentListForOption();
		modelMap.put("departments", new ArrayList<>());
		return new ModelAndView("./shipping/addshipping");
	}

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 * @return
	 */
/*	@RequestMapping(value = "/add")
	public String add(@ModelAttribute("user") User user) {
		User operator = SessionUtil.getSessionUser(req);
		user.setLastLoginIp(WebUtil.getRemoteHost(req));
		userService.createUser(user, operator);
		return ConstantUtil.Success;
	}*/

	/**
	 * 返回用户信息列表
	 * 
	 * @param user
	 * @param map
	 * @return
	 */
/*	@RequestMapping(value = "/getUserDataTables", produces = "text/json;charset=UTF-8")
	public String getUserDataTables(@ModelAttribute("user") User user, ModelMap map) {
		UserSearchModel searchModel = new UserSearchModel(1, PrivilegeModelConstant.PageSize);
		return userService.getUserDataTables(user, searchModel);
	}

	@RequestMapping("/updateform")
	public ModelAndView updateform(Integer id) {
		// List<Department> departments =
		// departmentService.getDepartmentListForOption();
		modelMap.put("departments", new ArrayList<>());
		User user = userService.getUserById(id);
		modelMap.put("user", user);
		return new ModelAndView("./x27/user/updateform");
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("user") User user) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		userService.updateRealNameAndGenderAndIsAdminById(user, operator);
		return ConstantUtil.Success;
	}

	@RequestMapping(value = "/getUserDataRow", produces = "text/json;charset=UTF-8")
	public String getUserDataRow(@RequestParam("id") Integer id) throws Exception {
		return userService.getUserDataRow(id);
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("id") Integer id) {

		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);

		User user = new User();
		user.setId(id);
		user.setIsDelete(true);

		userService.updateIsDeleteById(user, operator);

		return ConstantUtil.Success;
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

		// map.put("options", roleService.getRoleForOptions(id));
		// map.put("id", id);
		//
		return "user/assignform.ftl";
	}

	@RequestMapping("/assign")
	public String assign(Integer id, String selectedStr) {
		if (id == null || StringUtil.isStrEmpty(id.toString()) || StringUtil.isStrEmpty(selectedStr)) {
			return ConstantUtil.Fail;
		}
		userService.assignRole(id, selectedStr);

		return ConstantUtil.Success;
	}
*/
}
