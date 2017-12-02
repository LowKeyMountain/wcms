package net.itw.wcms.x27.controller;

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

import net.itw.wcms.x27.entity.Resource;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IResourceService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;

@RestController
@RequestMapping(value = "/resource")
public class ResourceController {

	@Autowired
	private IResourceService resourceService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./x27/resource/";

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
	 * 跳转到资源管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main() {
		return new ModelAndView(PATH + "list");
	}

	@RequestMapping("/addform")
	public ModelAndView addform() {
		return new ModelAndView(PATH + "addform");
	}

	/**
	 * 新增资源信息
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Map<String, Object> add(@ModelAttribute("resource") Resource resource) {
		User operator = SessionUtil.getSessionUser(req);
		int successInt = 1;
		String msg = "数据保存成功！";
		try {
			successInt = resourceService.createResource(resource, operator);
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
	 * 返回资源信息列表
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getResourceDataTables", produces = "text/json;charset=UTF-8")
	public String getResourceDataTables(@RequestParam Map<String, String> params, ModelMap map) {
		Pageable pageable = PageUtils.buildPageRequest(params);
		return resourceService.getResourceDataTables(pageable, params);
	}
	
	@RequestMapping("/updateform")
	public ModelAndView updateform(Integer id) {
		modelMap.put("id", id);
		Resource resource = resourceService.getResourceById(id);
		modelMap.put("resource", resource);
		return new ModelAndView(PATH + "/updateform");
	}
	
	/**
	 * 修改资源信息
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "/update")
	public Map<String, Object> update(@ModelAttribute("resource") Resource resource) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		int successInt = 1;
		String msg = "数据修改成功！";
		try {
			successInt = resourceService.updateResourceById(resource, operator);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("id", resource.getId());
		map.put(ConstantUtil.Success, successInt == 1 ? ConstantUtil.Success : ConstantUtil.Fail);
		return map;
	}

	@RequestMapping(value = "/getUserDataRow", produces = "text/json;charset=UTF-8")
	public String getUserDataRow(@RequestParam("id") Integer id) throws Exception {
		return resourceService.getResourceDataRow(id);
	}

	@RequestMapping("/delete")
	public Map<String, ?> delete(@RequestParam("id") Integer id) {
		String msg = "删除成功";
		Integer code = ConstantUtil.SuccessInt;
		try {
			resourceService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
			code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}

}
