package net.itw.wcms.ship.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.ship.service.ITaskShipService;

@RestController
@RequestMapping(value = "/report")
public class ReportController {

	@Autowired
	private ITaskService taskService;
	@Autowired
	private ITaskShipService taskShipService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./core/task/";
	private static final String PATH_UNSHIPINFO = "./core/unshipInfo/";
	private static final String PATH_REPORT = "./core/report/";


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
	 * 跳转至船舶作业维护页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/maintenance")
	public ModelAndView maintenance() {
		return new ModelAndView(PATH + "maintenancelist");
	}

	/**
	 * 跳转至船舶实时统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/statistics")
	public ModelAndView shipStatistics() {
		return new ModelAndView(PATH_UNSHIPINFO + "statistics");
	}

	/**
	 * 跳转至报表管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/report")
	public ModelAndView report() {
		return new ModelAndView(PATH_UNSHIPINFO + "report");
	}
	
	/**
	 * 跳转至船舶实时统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/overview")
	public ModelAndView overview() {
		return new ModelAndView(PATH_REPORT + "overview");
	}

	/**
	 * 跳转至货物进度统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/progressview")
	public ModelAndView processview() {
		return new ModelAndView(PATH_REPORT + "progressview");
	}
}
