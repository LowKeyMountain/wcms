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

import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ICargoService;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;

@RestController
@RequestMapping(value = "/cargo")
public class CargoController {

	@Autowired
	private ITaskService taskService;
	@Autowired
	private ICargoService cargoService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./core/cargo/";

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
	 * 列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main() {
		return new ModelAndView(PATH + "list");
	}

	@RequestMapping("/addform")
	public ModelAndView newTask() {
		return new ModelAndView(PATH + "addform");
	}

	/**
	 * 返回作业信息列表
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getCargoList")
	public Map<String, Object> getDataTables(@RequestParam Map<String, String> params,
			@RequestParam("taskId") Integer taskId, ModelMap map) {
		// 从session取出User对象
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		Map<String, Object> result = null;
		try {
			Pageable pageable = PageUtils.buildPageRequest(params);
			result = cargoService.getCargoList(pageable, taskId, params);
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
	 * 新增信息
	 * 
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Map<String, Object> add(@ModelAttribute("cargo") Cargo cargo, @RequestParam("taskId") Integer taskId) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			Task task = taskService.getTaskById(taskId);
			if (task == null) {
				throw new X27Exception("操作失败：船舶信息未找到！");
			}
			cargo.setTask(task);
			mo.code = cargoService.createCargo(cargo, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("code", mo.code);
		return map;
	}

	/**
	 * 修改信息
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/updateform")
	public ModelAndView updateform(Integer taskId) {
		Cargo cargo = cargoService.findOne(taskId);
		modelMap.put("cargo", cargo);
		return new ModelAndView(PATH + "updateform");
	}

	/**
	 * 修改货物信息
	 * 
	 * @param cargo
	 * @return
	 */
	@RequestMapping(value = "/update")
	public Map<String, Object> update(@ModelAttribute("cargo") Cargo cargo, @RequestParam("taskId") Integer taskId) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据修改成功！");
		try {
			Task task = taskService.getTaskById(taskId);
			if (task == null) {
				throw new X27Exception("操作失败：船舶信息未找到！");
			}
			cargo.setTask(task);
			mo.code = cargoService.updateCargo(cargo, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("id", cargo.getId());
		map.put("code", mo.code);
		return map;
	}
	
	@RequestMapping("/delete")
	public MessageOption delete(@RequestParam("id") Integer id) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			// 从session取出User对象
			User operator = SessionUtil.getSessionUser(req);
			Cargo cargo = cargoService.findOne(id);
			mo = cargoService.delete(cargo, operator);
		} catch (Exception e) {
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
			e.printStackTrace();
		}
		return mo;
	}
	
}
