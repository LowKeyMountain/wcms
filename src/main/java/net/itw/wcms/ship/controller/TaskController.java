package net.itw.wcms.ship.controller;

import java.util.HashMap;
import java.util.List;
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

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.interfaceApi.http.InfoQueryHelper;
import net.itw.wcms.interfaceApi.http.QueryOptions;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.ship.service.ITaskShipService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

	@Autowired
	private ITaskService taskService;
	@Autowired
	private ITaskShipService taskShipService;
	private InfoQueryHelper infoQueryHelper = new InfoQueryHelper();

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./core/task/";
	private static final String PATH_UNSHIPINFO = "./core/unshipInfo/";

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
	 * 跳转至船舶作业管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tasklist")
	public ModelAndView main() {
		String type = req.getParameter("type");
		modelMap.put("type", StringUtils.isBlank(type) ? "1" : type);
		return new ModelAndView(PATH + "list");
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
	
	@RequestMapping("/addform")
	public ModelAndView newTask() {
		return new ModelAndView(PATH + "addTask");
	}
	
	@RequestMapping("/newCalibration")
	public Map<String, Object> newCalibration() {
		Map<String, Object> result = new HashMap<>();
		List<Task> tasks = taskService.getTaskByStatus(0);
		result.put("msg", tasks.size() < 2  ? "" : "系统提示：泊位已满，不能进行新增船舶！");
		result.put(ConstantUtil.Success, tasks.size() < 2 ? ConstantUtil.Success : ConstantUtil.Fail);
		return result;
	}
	
	@RequestMapping(value = "/berthCheckout", produces = "text/json;charset=UTF-8")
	public String berthCheckout(@RequestParam("id") Integer taskId, @RequestParam("berth") Integer berth) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "校验成功！");
		List<Task> tasks = taskService.getTaskByStatus(0);
		for (Task task : tasks) {
			if (task.getId() == taskId) {
				continue;
			}
			if (task.getBerth() == berth) {
				mo.msg = "矿" + (berth == 1 ? "一" : (berth == 2 ? "二" : "其他")) + "已被占用！";
				mo.code = ConstantUtil.FailInt;
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("code", mo.code);
 		return JSONObject.toJSON(map).toString();
	}
	
	@RequestMapping("/updateform")
	public ModelAndView updateform(Integer id) {
		Task user = taskService.getTaskById(id);
		modelMap.put("task", user);
		return new ModelAndView(PATH + "updateTask");
	}
	
	/**
	 * 返回作业信息列表
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getTaskList", produces = "text/json;charset=UTF-8")
	public String getUserDataTables(@RequestParam Map<String, String> params, @RequestParam("status") Integer status, ModelMap map) {
		int pageSize = Integer.parseInt(params.get("limit"));
		int pageNum = Integer.parseInt(params.get("offset"));
		String sortType = params.get("sortName");
		String direction = params.get("sortOrder");
		Pageable pageable = PageUtils.buildPageRequest(pageNum, pageSize, sortType, direction);
		return taskService.getTaskList(pageable, status, params);
	}
	
	/**
	 * 新增船舶信息
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Map<String, Object> add(@ModelAttribute("task") Task task) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			mo.code = taskService.createTask(task, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("taskId", task.getId());
		map.put("code", mo.code);
		return map;
	}
	
	/**
	 * 修改船舶信息
	 * 
	 * @param cargo
	 * @return
	 */
	@RequestMapping(value = "/update")
	public Map<String, Object> update(@ModelAttribute("task") Task task) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据修改成功！");
		try {
			mo.code = taskService.updateTask(task, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("taskId", task.getId());
		map.put("code", mo.code);
		return map;
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public MessageOption delete(@RequestParam("id") Integer id) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			Task task = taskService.getTaskById(id);
			mo = taskService.delete(task);
		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
			e.printStackTrace();
		}
		return mo;
	}
	
	/**
	 * 卸船情况
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/unshipInfo")
	public ModelAndView unshipInfo(@RequestParam("id") Integer id) {
		Task task = taskService.getTaskById(id);
		modelMap.put("taskId", id);
		modelMap.put("task", task);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");
		return new ModelAndView(PATH_UNSHIPINFO + "list");
	}
	
	/**
	 * 返回卸船情况列表
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getUnshipInfoList")
	public Map<String, Object> getUnshipInfoList(@RequestParam("taskId") Integer taskId) {
		// 从session取出User对象
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		Map<String, Object> result = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fuctionType", "FN_004");
			jsonObject.put("order", "asc");
			jsonObject.put("sort", "cabinNo");
			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "'}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			result = infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		result.put("msg", mo.msg);
		result.put("code", mo.code);
		return result;
	}
	
	/**
	 * 查看船舶信息
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView shipInfoview(Integer taskId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fuctionType", "FN_003");
		jsonObject.put("criteria", JSONObject.parseObject("{'$t.id':'" + taskId + "'}"));
		Map<String, Object> map = infoQueryHelper.doQueryInfo(jsonObject);
		Map<String, Object> data = (Map<String, Object>) map.get("data");
		modelMap.put("task", data);
		return new ModelAndView(PATH + "view");
	}
	
	/**
	 * 设置船舶状态
	 * 
	 * @param taskId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/doSetShipStatus")
	public Map<String, Object> doSetShipStatus(@RequestParam("taskId") String taskId,
			@RequestParam("status") String status) {
		User operator = SessionUtil.getSessionUser(req);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			MessageOption mo = this.taskShipService.updateShipStatus(taskId, operator.getUserName(), status);
			result.put("msg", mo.msg);
			result.put("code", mo.isSuccess() ? "1" : "0");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 卸船进度
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/unloadProgress")
	public ModelAndView unloadProgress(@RequestParam("id") Integer id) {
		Task task = taskService.getTaskById(id);
		modelMap.put("taskId", id);
		modelMap.put("task", task);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");
		return new ModelAndView(PATH_UNSHIPINFO + "progresslist");
	}
	
	/**
	 * 返回卸船进度列表
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getUnloadProgressList", produces = "text/json;charset=UTF-8")
	public String getUnloadProgressList(@RequestParam("taskId") Integer taskId) {
		Map<String, Object> result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fuctionType", "FN_007");
			jsonObject.put("order", "asc");
			jsonObject.put("sort", "cargoId");
			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "'}"));
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
	 * 卸船机总览
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/unloaderOverview")
	public ModelAndView unloaderOverview(@RequestParam("id") Integer id) {
		Task task = taskService.getTaskById(id);
		modelMap.put("taskId", id);
		modelMap.put("task", task);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");
		return new ModelAndView(PATH_UNSHIPINFO + "overviewList");
	}
	
	/**
	 * 返回卸船机总览列表
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getUnloaderOverviewList", produces = "text/json;charset=UTF-8")
	public String getUnloaderOverviewList(@RequestParam("taskId") Integer taskId) {
		Map<String, Object> result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fuctionType", "FN_006");
			jsonObject.put("order", "asc");
			jsonObject.put("sort", "startTime");
			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "'}"));
			QueryOptions options = new QueryOptions();
			options.searchString = "%#";
			options.replacement = taskId + "";
			result = infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("rows",result.get("data"));
		json.put("total",result.get("total"));

		return json.toString();
	}
	
	/**
	 * 返回卸船机总览列表
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getUnloaderDetailList", produces = "text/json;charset=UTF-8")
	public String getUnloaderDetailList(@RequestParam("taskId") Integer taskId,@RequestParam("unloaderId") String unloaderId) {
		Map<String, Object> result = null;
		JSONObject json = new JSONObject();
		try {
			result = taskShipService.doGetUnloaderUnshipDetailList(taskId,unloaderId, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("rows",result.get("data"));
//		json.put("total",result.get("total"));

		return json.toString();
	}
	
	/**
	 * 返回所有船舶作业数据
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getTaskWhList", produces = "text/json;charset=UTF-8")
	public String getTaskWhList(@RequestParam Map<String, String> params, ModelMap map) {
		int pageSize = Integer.parseInt(params.get("limit"));
		int pageNum = Integer.parseInt(params.get("offset"));
		String sortType = params.get("sortName");
		String direction = params.get("sortOrder");
		
		Pageable pageable = PageUtils.buildPageRequest(pageNum, pageSize, sortType, direction);
		return taskService.getTaskList(pageable, params);
	}

	/**
	 * 船舱列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/cabinlist")
	public ModelAndView cabinList(@RequestParam("id") Integer id) {
		Task task = taskService.getTaskById(id);
		modelMap.put("taskId", id);
		modelMap.put("task", task);
		modelMap.put("shipName", task != null && task.getShip() != null ? task.getShip().getShipName() : "");
		return new ModelAndView(PATH + "cabinlist");
	}
	
	/**
	 * 返回船舱列表
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getCabinList", produces = "text/json;charset=UTF-8")
	public String getCabinList(@RequestParam("taskId") Integer taskId) {
		Map<String, Object> result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fuctionType", "FN_004");
			jsonObject.put("order", "asc");
			jsonObject.put("sort", "cabinNo");
			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "'}"));
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
}
