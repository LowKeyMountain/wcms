package net.itw.wcms.ship.controller;

import java.util.HashMap;
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

import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ITaskService;
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
	
	/**
	 * 返回作业信息列表
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getTaskList", produces = "text/json;charset=UTF-8")
	public Map<String, Object> getTaskDataTables(@RequestParam Map<String, String> params, @RequestParam("status") Integer status, ModelMap map) {
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
		int successInt = 1;
		String msg = "数据保存成功！";
		try {
			successInt = taskService.createTask(task, operator);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("taskId", task.getId());
		map.put(ConstantUtil.Success, successInt == 1 ? ConstantUtil.Success : ConstantUtil.Fail);
		return map;
	}
}
