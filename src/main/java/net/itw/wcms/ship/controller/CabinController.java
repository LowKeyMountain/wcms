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

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.service.ICabinService;
import net.itw.wcms.ship.service.ICargoService;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;

@RestController
@RequestMapping(value = "/cabin")
public class CabinController {

	@Autowired
	private ITaskService taskService;
	@Autowired
	private ICabinService cabinService;
	
	@Autowired
	private ICargoService cargoService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./core/cabin/";

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
	public ModelAndView addform(@RequestParam("taskId") Integer taskId) {
		 Task task = taskService.getTaskById(taskId);
		 List<Cargo> cargos = cargoService.getCargosByTaskId(taskId);
		 modelMap.put("cargos", cargos);
		 int cabinNo = task.getCabins() != null ? task.getCabins().size() : 0;
		 modelMap.put("cabinNo", cabinNo + 1);
		return new ModelAndView(PATH + "addform");
	}

	/**
	 * 返回信息列表
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getCabinList") //, produces = "text/json;charset=UTF-8"
	public Map<String, Object> getDataTables(@RequestParam Map<String, String> params,
			@RequestParam("taskId") Integer taskId, ModelMap map) {
		// 从session取出User对象
		int successInt = 1;
		String msg = "数据修改成功！";
		Map<String, Object> result = null;
		try {
			Pageable pageable = PageUtils.buildPageRequest(params);
			result = cabinService.getCabinList(pageable, taskId, params);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
			successInt = ConstantUtil.FailInt;
		}
		result.put("msg", msg);
		result.put(ConstantUtil.Success, successInt == 1 ? ConstantUtil.Success : ConstantUtil.Fail);
		return result;
	}
	

	/**
	 * 新增信息
	 * 
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Map<String, Object> add(@ModelAttribute("cabin") Cabin cabin, @RequestParam("taskId") Integer taskId) {
		User operator = SessionUtil.getSessionUser(req);
		int successInt = 1;
		String msg = "数据保存成功！";
		try {
			Task task = taskService.getTaskById(taskId);
			if (task == null) {
				throw new X27Exception("操作失败：船舶信息未找到！");
			}
			cabin.setTask(task);
			successInt = cabinService.createCabin(cabin, operator);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put(ConstantUtil.Success, successInt == 1 ? ConstantUtil.Success : ConstantUtil.Fail);
		return map;
	}

	/**
	 * 修改信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateform")
	public ModelAndView updateform(Integer id) {
		Cabin cabin = cabinService.findOne(id);
		modelMap.put("cabin", cabin);
		if (cabin != null) {
			List<Cargo> cargos = cargoService.getCargosByTaskId(cabin.getTask().getId());
			modelMap.put("cargos", cargos);
		}
		return new ModelAndView(PATH + "updateform");
	}

	/**
	 * 修改货物信息
	 * 
	 * @param cargo
	 * @return
	 */
	@RequestMapping(value = "/update")
	public Map<String, Object> update(@ModelAttribute("cabin") Cabin cabin, @RequestParam("taskId") Integer taskId) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		int successInt = 1;
		String msg = "数据修改成功！";
		try {
			Task task = taskService.getTaskById(taskId);
			if (task == null) {
				throw new X27Exception("操作失败：船舶信息未找到！");
			}
			cabin.setTask(task);
			successInt = cabinService.updateCabin(cabin, operator);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("id", cabin.getId());
		map.put(ConstantUtil.Success, successInt == 1 ? ConstantUtil.Success : ConstantUtil.Fail);
		return map;
	}
	
	@RequestMapping("/delete")
	public MessageOption delete(@RequestParam("id") Integer id) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		Cabin cabin = cabinService.findOne(id);
		MessageOption mo = cabinService.delete(cabin, operator);
		return mo;
	}
	
}
