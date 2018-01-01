package net.itw.wcms.ship.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.itw.wcms.ship.service.ICabinService;
import net.itw.wcms.ship.service.ICargoService;
import net.itw.wcms.ship.service.ITaskService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
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
		 List<Cargo> cargos = cargoService.getCargosByTaskId(taskId);
		 modelMap.put("cargos", cargos);
		 List<Cabin> cabins = new ArrayList<>();
		 for (Cargo cargo : cargos) {
			 for (Cabin cabin : cargo.getCabins()) {
				 cabins.add(cabin);
			 }
		 }
		 modelMap.put("cabinNo", cabins.size() + 1);
		return new ModelAndView(PATH + "addform");
	}

	/**
	 * 返回信息列表
	 * @param params
	 * @param taskId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getCabinList")
	public Map<String, Object> getDataTables(@RequestParam Map<String, String> params,
			@RequestParam("taskId") Integer taskId, ModelMap map) {
		// 从session取出User对象
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		Map<String, Object> result = null;
		try {
			Pageable pageable = PageUtils.buildPageRequest(params);
			result = cabinService.getCabinList(pageable, taskId, params);
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
	 * @param cabin
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Map<String, Object> add(@ModelAttribute("cabin") Cabin cabin) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			mo.code = cabinService.createCabin(cabin, operator);
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
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateform")
	public ModelAndView updateform(Integer id) {
		Cabin cabin = cabinService.findOne(id);
		modelMap.put("cabin", cabin);
		Set<Cargo> cargos = cabin.getCargo().getTask().getCargos();
		modelMap.put("cargos", cargos);
		return new ModelAndView(PATH + "updateform");
	}

	/**
	 * 修改信息
	 * @param cabin
	 * @return
	 */
	@RequestMapping(value = "/update")
	public Map<String, Object> update(@ModelAttribute("cabin") Cabin cabin) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据修改成功！");
		try {
			mo.code = cabinService.updatePreunloadingAndCargo(cabin, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.code = ConstantUtil.FailInt;
			mo.msg = e.getMessage();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("id", cabin.getId());
		map.put("code", mo.code);
		return map;
	}
	
	@RequestMapping("/delete")
	public MessageOption delete(@RequestParam("id") Integer id) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			// 从session取出User对象
			User operator = SessionUtil.getSessionUser(req);
			Cabin cabin = cabinService.findOne(id);
			mo = cabinService.delete(cabin, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.code = ConstantUtil.FailInt;
			mo.msg = e.getMessage();
		}
		return mo;
	}
	
}
