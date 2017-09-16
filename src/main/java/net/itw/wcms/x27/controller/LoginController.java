package net.itw.wcms.x27.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/web")
public class LoginController {

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

	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gotoLoginPage")
	public ModelAndView gotoLoginPage() {
		return new ModelAndView("./login");
	}

	/**
	 * 登录验证
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<?, ?> login(@RequestParam("username") String userName, @RequestParam("password") String password)
			throws ServletException, IOException {
		String msg = "登录成功!";
		int success = 0; // 0|是、1|否
		String url = "";

		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(modelMap);
		// 用户验证
		if (!"admin".equalsIgnoreCase(userName) || !"123456".equalsIgnoreCase(password)) { // TODO:
																							// 待完善。
			success = 1;
			msg = "用户名或密码错误, 请重新输入!";
		} else {
			url = "/web/main";
		}
		map.put("msg", msg);
		map.put("url", url);
		map.put("success", success);
		return map;
	}

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main")
	public ModelAndView gotoMainPage() {
		modelMap.put("user", "管理员");
		modelMap.put("hours", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		return new ModelAndView("./main");
	}

}
