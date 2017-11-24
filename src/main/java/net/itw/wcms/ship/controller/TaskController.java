package net.itw.wcms.ship.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ShipService;
import net.itw.wcms.ship.service.TaskService;
import net.itw.wcms.x27.entity.PrivilegeModelConstant;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;
import net.itw.wcms.x27.utils.StringUtil;
import net.itw.wcms.x27.utils.WebUtil;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

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
	 * 跳转至船舶作业管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tasklist")
	public ModelAndView main() {
		return new ModelAndView("./task/tasklist");
	}

	/**
	 * 跳转到船舶作业管理页面
	 * 
	 * @param user
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getTaskList", produces = "text/json;charset=UTF-8")
//	@ResponseBody
	public String getUserDataTables(@ModelAttribute("task") Task task, ModelMap map) {
		String status = req.getParameter("status").toString();
		Pageable pageable = PageUtils.buildPageRequest(1, PageUtils.PageSize);
		return taskService.getTaskList(task, pageable, status);
	}
		
	@RequestMapping("/addship")
	public ModelAndView addform() {
		// List<Department> departments =
		// departmentService.getDepartmentListForOption();
		modelMap.put("departments", new ArrayList<>());
		return new ModelAndView("./ship/addship");
	}

	/**
	 * 获取船舶列表
	 * 
	 * @param status
	 * @return
	 */
/*	@RequestMapping(value = "/doGetShipList", produces = "text/json;charset=UTF-8")
	public String doGetShipList(String status) {
	    String json = "";
	    if (StringUtils.isNotEmpty(status))
	    {
	      if ("01".equals(status))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"taskId\": \"T00001\",\"shipId\": \"001\",\"cargoType\": \"煤\",\"shipName\": \"1#船舶\"},{\"taskId\": \"T00002\",\"shipId\": \"002\",\"cargoType\": \"镍\",\"shipName\": \"2#船舶\"}]}";
	      else if ("02".equals(status))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"taskId\": \"T00003\",\"shipId\": \"003\",\"cargoType\": \"铁矿\",\"shipName\": \"3#船舶\"},{\"taskId\": \"T00004\",\"shipId\": \"004\",\"cargoType\": \"铜\",\"shipName\": \"4#船舶\"}]}";
	      else if ("03".equals(status))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"taskId\": \"T00005\",\"shipId\": \"005\",\"cargoType\": \"煤\",\"shipName\": \"5#船舶\"},{\"taskId\": \"T00006\",\"shipId\": \"006\",\"cargoType\": \"铝\",\"shipName\": \"6#船舶\"}]}";
	      else
	        json = "{\"code\": 0,\"msg\": \"查询失败【状态编码错误】\",\"data\": []}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"查询失败【船舶状态参数异常】\",\"data\": []}";
	    }

	    return json;
	}*/
	
	/**
	 * 获取船舶基本信息
	 * 
	 * @param shipId
	 * @return
	 */
/*	@RequestMapping(value = "/doGetShipDetail", produces = "text/json;charset=UTF-8")
	public String doGetShipDetail(String shipId) {
		
	    String json = "";
	    if (StringUtils.isNotEmpty(shipId))
	    {
	      if ("001".equals(shipId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": {\"shipId\": \"001\",\"shipName\": \"1#船舶\",\"shipEname\": \"warship1\",\"imoNo\": \"WAR00001\",\"buildDate\": \"2017-01-12\",\"length\": \"400m\",\"width\": \"30m\",\"depth\": \"10m\",\"cabinNum\": \"4\",\"berthingTime\": \"2017-11-12 15:25:32\",\"departureTime\": \"2017-11-12 22:33:45\",\"beginTime\": \"2017-11-12 16:33:45\",\"endTime\": \"2017-11-12 21:33:45\"}}";
	      else if ("002".equals(shipId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": {\"shipId\": \"002\",\"shipName\": \"2#船舶\",\"shipEname\": \"Cargoship1\",\"imoNo\": \"GOODS0001\",\"buildDate\": \"2016-01-12\",\"length\": \"200m\",\"width\": \"60m\",\"depth\": \"30m\",\"cabinNum\": \"5\",\"berthingTime\": \"2017-11-10 15:25:32\",\"departureTime\": \"2017-11-11 22:33:45\",\"beginTime\": \"2017-11-10 16:32:32\",\"endTime\": \"2017-11-11 21:25:47\"}}";
	      else if ("003".equals(shipId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": {\"shipId\": \"003\",\"shipName\": \"3#船舶\",\"shipEname\": \"Cargoship2\",\"imoNo\": \"GOODS0002\",\"buildDate\": \"2017-05-09\",\"length\": \"300m\",\"width\": \"60m\",\"depth\": \"30m\",\"cabinNum\": \"5\",\"berthingTime\": \"2017-11-11 16:25:32\",\"departureTime\": \"2017-11-11 19:03:45\",\"beginTime\": \"2017-11-11 16:32:32\",\"endTime\": \"2017-11-11 21:25:47\"}}";
	      else if ("004".equals(shipId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": {\"shipId\": \"004\",\"shipName\": \"4#船舶\",\"shipEname\": \"warship2\",\"imoNo\": \"WAR00002\",\"buildDate\": \"2015-04-08\",\"length\": \"500m\",\"width\": \"60m\",\"depth\": \"30m\",\"cabinNum\": \"4\",\"berthingTime\": \"2017-11-10 15:25:32\",\"departureTime\": \"2017-11-11 22:33:45\",\"beginTime\": \"2017-11-09 16:32:32\",\"endTime\": \"2017-11-11 19:25:47\"}}";
	      else
	        json = "{\"code\": 0,\"msg\": \"查询失败【船舶id错误】\",\"data\": []}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"查询失败【船舶Id参数异常】\",\"data\": []}";
	    }

	    return json;
	}*/
	

	/**
	 * 获取船舱信息
	 * 
	 * @param shipId
	 * @return
	 */
/*	@RequestMapping(value = "/doGetCabinList", produces = "text/json;charset=UTF-8")
	public String doGetCabinList(String taskId) {
		
	    String json = "";
	    if (StringUtils.isNotEmpty(taskId))
	    {
	      if ("T00001".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\":\"T00001001\",\"cabinName\":\"#1\",\"cargoType\":\"铜\",\"total\":\"100t\",\"finish\":\"80t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"00\"},{\"detailId\":\"T00001002\",\"cabinName\":\"#2\",\"cargoType\":\"锌\",\"total\":\"500t\",\"finish\":\"10t\",\"remainder\":\"400t\",\"clearance\":\"20t\",\"status\":\"01\"},{\"detailId\":\"T00001003\",\"cabinName\":\"#3\",\"cargoType\":\"镍\",\"total\":\"200t\",\"finish\":\"180t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"02\"}]}";
	      else if ("T00002".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\":\"T00002001\",\"cabinName\":\"#1\",\"cargoType\":\"铝\",\"total\":\"100t\",\"finish\":\"80t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"01\"},{\"detailId\":\"T00002002\",\"cabinName\":\"#2\",\"cargoType\":\"镁\",\"total\":\"500t\",\"finish\":\"10t\",\"remainder\":\"400t\",\"clearance\":\"20t\",\"status\":\"00\"},{\"detailId\":\"T00002003\",\"cabinName\":\"#3\",\"cargoType\":\"铁\",\"total\":\"200t\",\"finish\":\"180t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"01\"}]}";
	      else if ("T00003".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\":\"T00003001\",\"cabinName\":\"#1\",\"cargoType\":\"铬\",\"total\":\"100t\",\"finish\":\"80t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"01\"},{\"detailId\":\"T00003002\",\"cabinName\":\"#2\",\"cargoType\":\"钾\",\"total\":\"500t\",\"finish\":\"10t\",\"remainder\":\"400t\",\"clearance\":\"20t\",\"status\":\"02\"},{\"detailId\":\"T00003003\",\"cabinName\":\"#3\",\"cargoType\":\"钢\",\"total\":\"200t\",\"finish\":\"180t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"00\"}]}";
	      else if ("T00004".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\":\"T00004001\",\"cabinName\":\"#1\",\"cargoType\":\"银\",\"total\":\"100t\",\"finish\":\"80t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"02\"},{\"detailId\":\"T00004002\",\"cabinName\":\"#2\",\"cargoType\":\"铬\",\"total\":\"500t\",\"finish\":\"10t\",\"remainder\":\"400t\",\"clearance\":\"20t\",\"status\":\"01\"},{\"detailId\":\"T00004003\",\"cabinName\":\"#3\",\"cargoType\":\"煤\",\"total\":\"200t\",\"finish\":\"180t\",\"remainder\":\"20t\",\"clearance\":\"20t\",\"status\":\"01\"}]}";
	      else
	        json = "{\"code\": 0,\"msg\": \"查询失败【船舶作业id不存在】\",\"data\": []}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"查询失败【船舶作业id参数异常】\",\"data\": []}";
	    }

	    return json;
	}*/
	
	/**
	 * 获取船舶舱位位置信息
	 * 
	 * @param taskId
	 * @return
	 */	
	@RequestMapping(value = "/doGetCabinPosition", produces = "text/json;charset=UTF-8")
	public String doGetCabinPosition(String taskId) {
		
	    String json = "";
	    if (StringUtils.isNotEmpty(taskId))
	    {
	      if ("T00001".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\": \"T00001001\",\"cabinNo\": \"1#舱\",\"startPosition\": \"10\",\"endPosition\": \"20\"},{\"taskId\": \"T00001002\",\"cabinNo\": \"2#舱\",\"startPosition\": \"30\",\"endPosition\": \"40\"},{\"taskId\": \"T00001003\",\"cabinNo\": \"3#舱\",\"startPosition\": \"60\",\"endPosition\": \"90\"},{\"taskId\": \"T00001004\",\"cabinNo\": \"4#舱\",\"startPosition\": \"120\",\"endPosition\": \"160\"}]}";
	      else if ("T00002".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\": \"T00002001\",\"cabinNo\": \"1#舱\",\"startPosition\": \"20\",\"endPosition\": \"30\"},{\"taskId\": \"T00002002\",\"cabinNo\": \"2#舱\",\"startPosition\": \"40\",\"endPosition\": \"50\"},{\"taskId\": \"T00002003\",\"cabinNo\": \"3#舱\",\"startPosition\": \"70\",\"endPosition\": \"100\"},{\"taskId\": \"T00002004\",\"cabinNo\": \"4#舱\",\"startPosition\": \"130\",\"endPosition\": \"170\"}]}";
	      else if ("T00003".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\": \"T00003001\",\"cabinNo\": \"1#舱\",\"startPosition\": \"30\",\"endPosition\": \"40\"},{\"taskId\": \"T00003002\",\"cabinNo\": \"2#舱\",\"startPosition\": \"50\",\"endPosition\": \"60\"},{\"taskId\": \"T00003003\",\"cabinNo\": \"3#舱\",\"startPosition\": \"80\",\"endPosition\": \"110\"},{\"taskId\": \"T00003004\",\"cabinNo\": \"4#舱\",\"startPosition\": \"140\",\"endPosition\": \"180\"}]}";
	      else if ("T00004".equals(taskId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\": \"T00004001\",\"cabinNo\": \"1#舱\",\"startPosition\": \"40\",\"endPosition\": \"50\"},{\"taskId\": \"T00004002\",\"cabinNo\": \"2#舱\",\"startPosition\": \"60\",\"endPosition\": \"70\"},{\"taskId\": \"T00004003\",\"cabinNo\": \"3#舱\",\"startPosition\": \"90\",\"endPosition\": \"120\"},{\"taskId\": \"T00004004\",\"cabinNo\": \"4#舱\",\"startPosition\": \"150\",\"endPosition\": \"190\"}]}";
	      else
	        json = "{\"code\": 0,\"msg\": \"查询失败【船舶作业id不存在】\",\"data\": []}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"查询失败【船舶作业id参数异常】\",\"data\": []}";
	    }

	    return json;		
	}

	/**
	 * 获取舱位详细信息
	 * 
	 * @param taskId
	 * @return
	 */	
/*	@RequestMapping(value = "/doGetCabinDetail", produces = "text/json;charset=UTF-8")
	public String doGetCabinDetail(String detailId) {
		
	    String json = "";
	    if (StringUtils.isNotEmpty(detailId))
	    {
	      json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": [{\"detailId\": \"T0000X00X\",\"cabinNo\": \"#1仓\",\"cargoType\": \"钛合金\",\"total\": \"100t\",\"finish\": \"80t\",\"remainder\": \"20t\",\"clearance\": \"20t\",\"status\": \"00\",\"beginTime\":\"2017-10-28 09:50:16\",\"endTime\":\"2017-10-28 10:50:42\",\"uesdTime\":50,\"unloadingTonnage\":30,\"efficiency\":69.5}]}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"查询失败【船舶作业详情id参数异常】\",\"data\": []}";
	    }

	    return json;		
	}	*/	

	/**
	 * 修改船舶位置信息
	 * 
	 * @param taskId
	 * @return
	 */	
	@RequestMapping(value = "/doAlterShipPosition", produces = "text/json;charset=UTF-8")
	public String doAlterShipPosition(String json) {
		
	    if (StringUtils.isNotEmpty(json))
	    {
	      json = "{\"code\": 1,\"msg\": \"修改成功!\"}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"修改失败!\"}";
	    }

	    return json;	
	}

	/**
	 * 查询船舱货物详情信息
	 * 
	 * @param taskId
	 * @return
	 */	
/*	@RequestMapping(value = "/doGetCargoDetail", produces = "text/json;charset=UTF-8")
	public String doGetCargoDetail(String detailId) {
		
	    String json = "";
	    if (StringUtils.isNotEmpty(detailId))
	    {
	      if ("T00001001".equals(detailId))
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": {\"detailId\": \"T00001001\",\"cargoType\": \"铜\",\"cargoCategory\": \"青铜\",\"loadingPort\": \"天津大港\",\"quality\": \"优级\",\"moisture\": \"60%\",\"cargoOwner\": \"阮大成\",\"stowageTonnage\": 500}}";
	      else
	        json = "{\"code\": 1,\"msg\": \"查询成功\",\"data\": {\"detailId\": \"T0000X00X\",\"cargoType\": \"铁\",\"cargoCategory\": \"青铜\",\"loadingPort\": \"天津大港\",\"quality\": \"优级\",\"moisture\": \"60%\",\"cargoOwner\": \"阮大成\",\"stowageTonnage\": 500}}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"查询失败【船舶作业详情id参数异常】\",\"data\": []}";
	    }

	    return json;	
	}
*/
	/**
	 * 设置船舱清仓、完成状态
	 * 
	 * @param taskId
	 * @return
	 */	
	@RequestMapping(value = "/doSetCabinStatus", produces = "text/json;charset=UTF-8")
	public String doSetCabinStatus(String detailId,  String status) {
		
	    String json = "";
	    if ((StringUtils.isNotEmpty(detailId)) && (StringUtils.isNotEmpty(status)))
	    {
	      json = "{\"code\": 1,\"msg\": \"修改成功!\"}";
	    }
	    else {
	      json = "{\"code\": 0,\"msg\": \"修改失败【船舶作业详情id或状态参数异常】\"}";
	    }

	    return json;	
	}
}
