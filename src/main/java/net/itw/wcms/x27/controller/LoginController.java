package net.itw.wcms.x27.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class LoginController {

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;

	@ModelAttribute
	public void setSetReqAndRes(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
		this.session = req.getSession();
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "web/gotoLoginPage")
	public ModelAndView gotoLoginPage() {
		Map<String, Object> map = new HashMap<>();
		map.put("IncPath", req.getContextPath());
		return new ModelAndView("./x27/login", map);
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
	@RequestMapping(value = "web/login", method = RequestMethod.POST)
	public void login(@RequestParam("username") String userName, @RequestParam("password") String password)
			throws ServletException, IOException {
		String msg = "登录成功!";
		int success = 0; // 0|是、1|否
		String url = "";
		Map<String, Object> map = new HashMap<>();
		map.put("IncPath", req.getContextPath());
		// 用户验证
		if (!"admin".equalsIgnoreCase(userName) || !"123456".equalsIgnoreCase(password)) { // TODO: 待完善。
			success = 1;
			msg = "用户名或密码错误, 请重新输入!";
		} else {
			url = "/web/gotoMainPage";
		}
		map.put("msg", msg);
		map.put("url", url);
		map.put("success", success);
		res.setContentType("text/html; charset=UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(map);
		res.getWriter().write(jsonStr);
	}
	
	/**
	 * 跳转到主页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "web/gotoMainPage")
	public ModelAndView gotoMainPage() {
		Map<String, Object> map = new HashMap<>();
		map.put("IncPath", req.getContextPath());
		return new ModelAndView("./x27/main", map);
	}
	
}
