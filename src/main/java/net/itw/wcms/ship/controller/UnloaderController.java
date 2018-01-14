package net.itw.wcms.ship.controller;

import java.io.IOException;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Unloader;
import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.ship.service.IUnloaderService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;

@RestController
@RequestMapping(value = "/unloader")
public class UnloaderController {

	@Autowired
	private IUnloaderService unloaderService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./core/unloader/";

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
	 * 跳转至卸船机数据页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main() {
		return new ModelAndView(PATH + "list");
	}
	
	/**
	 * 返回卸船机数据
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getUnloaderList", produces = "text/json;charset=UTF-8")
	public String getUserDataTables(@RequestParam Map<String, String> params, ModelMap map) {
		int pageSize = Integer.parseInt(params.get("limit"));
		int pageNum = Integer.parseInt(params.get("offset"));
		String sortType = params.get("sortName");
		String direction = params.get("sortOrder");
		
		Pageable pageable = PageUtils.buildPageRequest(pageNum, pageSize, sortType, direction);
		return unloaderService.getUnloaderList(pageable, params);
	}

	@RequestMapping(value = "/addUnloader", produces = "text/json;charset=UTF-8")
	public void addUnloader(@RequestParam Map<String, String> params) throws IOException {
		UnloaderAll unloader = new UnloaderAll();		
		JSONObject jsonObject = new JSONObject();
		String tableName="";
		String cmsId= params.get("fcmsid");
		if("1".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_1");
			tableName="tab_unloader_1";
		} else if ("2".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_2");
			tableName="tab_unloader_2";
		} else if ("3".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_3");
			tableName="tab_unloader_3";
		} else if ("4".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_4");
			tableName="tab_unloader_4";
		} else if ("5".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_5");
			tableName="tab_unloader_5";
		} else if ("6".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_6");
			tableName="tab_unloader_6";
		}
		unloader.setTime(new Date());
		unloader.setPushTime(new Date());
		unloader.setOneTask(Float.parseFloat(params.get("onetask")));
		unloader.setUnloaderMove(Float.parseFloat(params.get("move")));
		unloader.setOperationType(params.get("operationtype"));
		unloader.setDirection(params.get("direction"));
		
		try {
			int val = unloaderService.addUnloader(unloader , tableName);
			if (val > 0) {
				jsonObject.put("success", true);
				jsonObject.put("msg", "保存成功！");
			} else {
				jsonObject.put("failure", true);
				jsonObject.put("msg", "保存失败！");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("failure", true);
			jsonObject.put("msg", "保存失败！");
		}
		res.getWriter().write(jsonObject.toString());

	}
	
	/**
	 * 添加卸船机作业信息
	 * 
	 * @param unloader
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Map<String, Object> add(@ModelAttribute("unloader") Unloader unloader) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			unloaderService.createUnloader(unloader, operator);
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

	
}
