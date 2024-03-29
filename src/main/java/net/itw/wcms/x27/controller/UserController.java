package net.itw.wcms.x27.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.itw.wcms.common.log.annotation.OperateLog;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IRoleService;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;
import net.itw.wcms.x27.utils.StringUtil;
import net.itw.wcms.x27.utils.WebUtil;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
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
		modelMap.put("jsVersion", System.currentTimeMillis());
		User operator = SessionUtil.getSessionUser(req);
		modelMap.put("user", operator!=null?operator.getRealName():"");
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main() {
		return new ModelAndView("./x27/user/list");
	}

	@RequestMapping("/addform")
	public ModelAndView addform() {
		// List<Department> departments =
		// departmentService.getDepartmentListForOption();
		modelMap.put("departments", new ArrayList<>());
		return new ModelAndView("./x27/user/addform");
	}

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "新增用户信息"
    )
	@RequestMapping(value = "/add")
	public MessageOption add(@ModelAttribute("user2") User user) {
		User operator = SessionUtil.getSessionUser(req);
		user.setLastLoginIp(WebUtil.getRemoteHost(req));
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			// 校验用户名唯一性
			User tempUser = userService.getUserByUserName(user.getUserName());
			if (tempUser != null) {
				mo.code = ConstantUtil.FailInt;
				mo.msg = "操作失败：新增用户名已存在!";
				return mo;
			}
			mo.code = userService.createUser(user, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}
	
	/**
	 * 返回用户信息列表
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getUserDataTables", produces = "text/json;charset=UTF-8")
	public String getUserDataTables(@RequestParam Map<String, String> params, ModelMap map) {
		Pageable pageable = PageUtils.buildPageRequest(params);
		return userService.getUserDataTables(pageable, params);
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
	
	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "修改用户信息"
    )
	@RequestMapping(value = "/update")
	public Map<String, Object> update(@ModelAttribute("user2") User user) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		user.setLastLoginIp(WebUtil.getRemoteHost(req));
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据修改成功！");
		try {
			mo.code = userService.updateRealNameAndGenderAndIsAdminById(user, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("id", user.getId());
		map.put("code", mo.code);
		return map;
	}

	@RequestMapping(value = "/getUserDataRow", produces = "text/json;charset=UTF-8")
	public String getUserDataRow(@RequestParam("id") Integer id) throws Exception {
		return userService.getUserDataRow(id);
	}
	
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "删除用户信息"
    )
	@RequestMapping("/delete")
	public MessageOption delete(@RequestParam("id") Integer id) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据删除成功！");
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
	
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "重置用户密码"
    )
	@RequestMapping("/resetpass")
	public MessageOption resetpass(@RequestParam("id") Integer id) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			// 从session取出User对象
			User operator = SessionUtil.getSessionUser(req);

			User user = new User();
			user.setId(id);
			user.setPassword(ConstantUtil.DefaultMd5Password);

			userService.updatePasswordById(user, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}
	
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "用户账户锁定"
    )
	@RequestMapping("/lock")
	public MessageOption lock(@RequestParam("id") Integer id) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			// 从session取出User对象
			User operator = SessionUtil.getSessionUser(req);

			User user = new User();
			user.setId(id);
			user.setIsLock(true);

			userService.updateIsLockById(user, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}

		return mo;
	}
	
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "用户账户解锁"
    )
	@RequestMapping("/unlock")
	public MessageOption unlock(@RequestParam("id") Integer id) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			// 从session取出User对象
			User operator = SessionUtil.getSessionUser(req);

			User user = new User();
			user.setId(id);
			user.setIsLock(false);

			userService.updateIsLockById(user, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}

		return mo;
	}

	@RequestMapping("/assignform")
	public ModelAndView assignform(Integer id, ModelMap map) {
		map.put("options", roleService.getRoleForOptions(id));
		map.put("id", id);
		return new ModelAndView("./x27/user/assignform");
	}
	
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "用户角色分配"
    )
	@RequestMapping("/assign")
	public MessageOption assign(Integer id, String selectedStr) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		if (id == null || StringUtil.isStrEmpty(id.toString())) { // || StringUtil.isStrEmpty(selectedStr)
			mo.code = ConstantUtil.FailInt;
			mo.msg = "分配权限失败：用户信息未找到！";
			return mo;
		}
		// 用户关联角色个数校验，一个用户只能关联一个角色
		if (StringUtils.isNotEmpty(selectedStr) && selectedStr.split(",").length >= 2) {
			mo.code = ConstantUtil.FailInt;
			mo.msg = "分配权限失败：用户只能关联一个角色!";
			return mo;
		}
		userService.assignResource(id, selectedStr);
		return mo;
	}
	
}
