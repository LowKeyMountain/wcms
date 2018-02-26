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
	 * 跳转至船舶实时统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/statistics")
	public ModelAndView shipStatistics() {
		return new ModelAndView(PATH_REPORT + "statistics");
	}

	/**
	 * 根据报表类型跳转至对应报表明细页面
	 * 
	 * @return
	 */
/*	@RequestMapping(value = "/reportview")
	public ModelAndView report(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
		String reportType = req.getParameter("reportType");
		String fileName = "";
		switch (reportType) {
		case "1":
			fileName ="progressview";
			break;
		case "2":
			fileName ="unloadview";
			break;
		case "3":
			fileName ="overview";
			break;
		case "4":
			fileName ="statistics";
			break;
		case "5":
			fileName ="cabinquantity";
			break;
		case "6":
			fileName ="cargoquantity";
			break;			
		default:
			break;
		}		
		return new ModelAndView(PATH_REPORT + fileName);
	}*/

	/**
	 * 跳转至报表管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/reportview")
	public ModelAndView report() {
		return new ModelAndView(PATH_REPORT + "report");
	}
	
	/**
	 * 跳转至船舶作业列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shiplist")
	public ModelAndView shipList() {
		String reportType = req.getParameter("reportType");
		modelMap.put("reportType", StringUtils.isBlank(reportType) ? "1" : reportType);		
		return new ModelAndView(PATH_REPORT + "shiplist");
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
	
	/**
	 * 跳转至船舶舱口卸货统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/unloadview")
	public ModelAndView unloadview() {
		return new ModelAndView(PATH_REPORT + "unloadview");
	}
	
	/**
	 * 跳转至船舶舱口卸货统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cabinquantity")
	public ModelAndView cabinQuantity() {
		return new ModelAndView(PATH_REPORT + "cabinquantity");
	}	
	
	/**
	 * 跳转至船舶舱口卸货统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cargoquantity")
	public ModelAndView cargoquantity() {
		return new ModelAndView(PATH_REPORT + "cargoquantity");
	}	
}
