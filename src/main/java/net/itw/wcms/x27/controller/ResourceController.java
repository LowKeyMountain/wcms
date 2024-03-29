package net.itw.wcms.x27.controller;

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

import net.itw.wcms.common.log.annotation.OperateLog;
import net.itw.wcms.toolkit.MessageOption;
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
		User operator = SessionUtil.getSessionUser(req);
		modelMap.put("user", operator!=null?operator.getRealName():"");
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
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "权限管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "新增权限"
    )
	@RequestMapping(value = "/add")
	public MessageOption add(@ModelAttribute("resource") Resource resource) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			mo.code = resourceService.createResource(resource, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
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
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "权限管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "修改权限"
    )
	@RequestMapping(value = "/update")
	public MessageOption update(@ModelAttribute("resource") Resource resource) {
		// 从session取出User对象
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据修改成功！");
		try {
			mo.code = resourceService.updateResourceById(resource, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

	@RequestMapping(value = "/getUserDataRow", produces = "text/json;charset=UTF-8")
	public String getUserDataRow(@RequestParam("id") Integer id) throws Exception {
		return resourceService.getResourceDataRow(id);
	}
	
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "权限管理"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "删除权限"
    )
	@RequestMapping("/delete")
	public MessageOption delete(@RequestParam("id") Integer id) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "删除成功");
		try {
			resourceService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		return mo;
	}

}
