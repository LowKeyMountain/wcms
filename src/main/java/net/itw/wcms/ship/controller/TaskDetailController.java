package net.itw.wcms.ship.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.TaskDetail;
import net.itw.wcms.ship.service.ShipService;
import net.itw.wcms.ship.service.TaskDetailService;
//import net.itw.wcms.ship.service.TaskDetailService;
import net.itw.wcms.x27.entity.PrivilegeModelConstant;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;
import net.itw.wcms.x27.utils.StringUtil;
import net.itw.wcms.x27.utils.WebUtil;

@RestController
@RequestMapping(value = "/detail")
public class TaskDetailController {

	@Autowired
	private TaskDetailService taskDetailService;

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

	

}
