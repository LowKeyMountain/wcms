package net.itw.wcms.x27.security.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping(value = "/authorize")
public class AuthorizeController {

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;
	@Autowired
	private AuthorizeUtils authorizeUtils;

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
	 * 验证权限
	 * 
	 * @param buttonUrl
	 * @return
	 */
	@RequestMapping(value = "/authorize", produces = "text/json;charset=UTF-8")
	public String authorize(@RequestParam("buttonUrl") String buttonUrl) {
		boolean result = authorizeUtils.authorize(req, buttonUrl);

		Map<String, Object> map = new HashMap<>();
		map.put("msg", "");
		map.put("code", result ? 1 : 0);
		return JSONObject.toJSON(map).toString();
	}

}
