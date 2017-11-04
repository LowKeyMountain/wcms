package net.itw.wcms.x27.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.service.IRoleService;
import net.itw.wcms.x27.utils.PageUtils;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./x27/role";

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
		return new ModelAndView(PATH + "/list");
	}

	/**
	 * 返回角色信息列表
	 * 
	 * @param user
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getUserDataTables", produces = "text/json;charset=UTF-8")
	public String getUserDataTables(@ModelAttribute("role") Role role, ModelMap map) {
		Pageable pageable = PageUtils.buildPageRequest(1, PageUtils.PageSize);
		return roleService.getUserDataTables(role, pageable);
	}

}
