package net.itw.wcms.common.log.controller;

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

import net.itw.wcms.common.log.service.ISystemLogService;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;

@RestController
@RequestMapping(value = "/logs")
public class SystemLogController {

	@Autowired
	private ISystemLogService systemLogService;
	
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
	 * 跳转到日志管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main() {
		return new ModelAndView("./x27/logs/list");
	}

	/**
	 * 返回卸船机数据
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getUnloaderList", produces = "text/json;charset=UTF-8")
	public String getUserDataTables(@RequestParam Map<String, String> params, ModelMap map) {
		int pageSize = Integer.parseInt(params.get("limit"));
		int pageNum = Integer.parseInt(params.get("offset"));
		String sortType = params.get("sortName");
		String direction = params.get("sortOrder");
		
		Pageable pageable = PageUtils.buildPageRequest(pageNum, pageSize, sortType, direction);
		return systemLogService.getList(pageable, params);
	}
	
}
