package net.itw.wcms.ship.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.interfaceApi.http.InfoQueryHelper;
import net.itw.wcms.interfaceApi.http.QueryOptions;
import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.entity.Unloader;
import net.itw.wcms.ship.service.ICargoService;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.ship.service.ITaskShipService;
import net.itw.wcms.ship.service.IUnloaderService;

@RestController
@RequestMapping(value = "/report")
public class ReportController {

	@Autowired
	private ITaskService taskService;
	@Autowired
	private ICargoService cargoService;
	@Autowired
	private ITaskShipService taskShipService;
	private InfoQueryHelper infoQueryHelper = new InfoQueryHelper();

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
	@RequestMapping(value = "/workShiftStats")
	public ModelAndView workShiftStats(@RequestParam("taskId") Integer taskId) {
		Task task = taskService.getTaskById(taskId);
		modelMap.put("taskId", taskId);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");		
		return new ModelAndView(PATH_REPORT + "workShiftStats");
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
	 * 跳转至卸船机作业量统计页面(报表三)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/unloaderStats")
	public ModelAndView unloaderStats(@RequestParam("taskId") Integer taskId) {
		Task task = taskService.getTaskById(taskId);
		modelMap.put("taskId", taskId);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");		
		return new ModelAndView(PATH_REPORT + "unloaderStats");
	}
	
	/**
	 * 跳转至卸船机作业量明细统计页面(报表三)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/unloaderStatsDetail")
	public ModelAndView unloaderStatsDetail(@RequestParam("taskId") Integer taskId, @RequestParam("unloaderId") String unloaderId) {
		modelMap.put("taskId", taskId);
		modelMap.put("unloaderId", unloaderId);
		String unloaderNo = "#" + unloaderId.substring(8);
		modelMap.put("unloaderNo", unloaderNo);
		return new ModelAndView(PATH_REPORT + "unloaderStatsDetail");
	}	

	/**
	 * 跳转至舱口卸货详情统计页面（报表一）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cabinInfoStats")
	public ModelAndView cabinInfoStats(@RequestParam("taskId") Integer taskId, @RequestParam("cargoId") Integer cargoId) {
		Task task = taskService.getTaskById(taskId);
		modelMap.put("taskId", taskId);
		if (cargoId != null) {
			modelMap.put("cargoId", cargoId);
			Cargo cargo = cargoService.findOne(cargoId);
			modelMap.put("cargoName", cargo != null && cargo.getCargoType() != null ? cargo.getCargoType() : "");
		}
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");
		return new ModelAndView(PATH_REPORT + "cabinInfoStats");
	}

	
	/**
	 * 跳转至货物进度统计页面（报表一）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cargoInfoStats")
	public ModelAndView cargoInfoStats(@RequestParam("taskId") Integer taskId) {
		Task task = taskService.getTaskById(taskId);
		modelMap.put("taskId", taskId);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");
		return new ModelAndView(PATH_REPORT + "cargoInfoStats");
	}	

	/**
	 * 跳转至舱口卸货进度统计页面（报表二）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cabinUnloadStats")
	public ModelAndView cabinUnloadStats(@RequestParam("taskId") Integer taskId) {
		Task task = taskService.getTaskById(taskId);
		modelMap.put("taskId", taskId);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");
		return new ModelAndView(PATH_REPORT + "cabinUnloadStats");
	}	
	
	/**
	 * 返回船舶货物进度列表（报表一）
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getCargoInfoStats", produces = "text/json;charset=UTF-8")
	public String getCargoInfoStats(@RequestParam Map<String, String> params) {
		Map<String, Object> result = null;
		JSONObject json = new JSONObject();
		String taskId = params.get("taskId");
		String cargoId = params.get("cargoId");
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fuctionType", "FN_011");
			jsonObject.put("order", "asc");
			jsonObject.put("sort", "cargoId");
			String criteria = "'$t.task_id':'" + taskId + "'";
			if (StringUtils.isNotBlank(cargoId)) {
				criteria += ",'$t.cargoId':'" + cargoId + "'";
			}
			jsonObject.put("criteria", JSONObject.parseObject("{"+criteria+"}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			result = infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("rows",result.get("data"));
		json.put("total",result.get("total"));

		return json.toString();
	}

	/**
	 * 返回船舱卸货进度列表（报表一）
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getCabinInfoStats", produces = "text/json;charset=UTF-8")
	public String doCabinInfoStats(@RequestParam Map<String, String> params) {
		Map<String, Object> result = null;
		JSONObject json = new JSONObject();
		String taskId = params.get("taskId");
		String cargoId = params.get("cargoId");
		String cabinNo = params.get("cabinNo");	
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fuctionType", "FN_012");
			jsonObject.put("order", "asc");
			jsonObject.put("sort", "CABINNO");
			String criteria = "'$t.task_id':'" + taskId + "'";
			if (StringUtils.isNotBlank(cargoId)) {
				criteria += ",'$t.cargoId':'" + cargoId + "'";
			}
			if (StringUtils.isNotBlank(cabinNo)) {
				criteria += ",'$t.cargoId':'" + cabinNo + "'";
			}
			jsonObject.put("criteria", JSONObject.parseObject("{"+criteria+"}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			result = infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("rows",result.get("data"));
		json.put("total",result.get("total"));

		return json.toString();
	}

	/**
	 * 返回船舱卸货进度列表（报表一）
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getCabinUnloadStats", produces = "text/json;charset=UTF-8")
	public String getCabinUnloadStats(@RequestParam("taskId") Integer taskId) {
		Map<String, Object> result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fuctionType", "FN_004");
			jsonObject.put("order", "asc");
			jsonObject.put("sort", "cabinNo");
			String criteria = "'$t.task_id':'" + taskId + "'";
			jsonObject.put("criteria", JSONObject.parseObject("{"+criteria+"}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			result = infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("rows",result.get("data"));
		json.put("total",result.get("total"));

		return json.toString();
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
