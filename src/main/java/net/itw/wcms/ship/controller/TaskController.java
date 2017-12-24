package net.itw.wcms.ship.controller;

import java.util.HashMap;
import java.util.List;
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

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ITaskService;
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

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./core/task/";

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
		return new ModelAndView(PATH + "list");
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
		Pageable pageable = PageUtils.buildPageRequest(params);
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
	
}
