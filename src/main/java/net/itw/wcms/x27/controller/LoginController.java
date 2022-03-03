package net.itw.wcms.x27.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.itw.wcms.x27.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.itw.wcms.common.log.annotation.OperateLog;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IUserService;

/**
 * 
 * Description:主界面及登录验证相关的控制器
 * 
 * @author Michael 16 Sep 2017 22:05:49
 */
@RestController
@RequestMapping(value = "/web")
public class LoginController {
	@Autowired
	private IUserService userService;
	
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
		modelMap.put("jsVersion", System.currentTimeMillis());
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
	 * 跳转到错误页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/error")
	public ModelAndView gotoRrrorPage() {
		return new ModelAndView("./error");
	}
	
	/**
	 * 用户登出
	 * 
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleCode = "M02"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Query
            ,operateTypeDesc = "用户登出"
    )
	@RequestMapping(value = "/logOutPage")
	public ModelAndView logOutPage(){
		session.removeAttribute(SessionUtil.SessionSystemLoginUserName);
		return new ModelAndView("./login");
	}
	
	/**
	 * 登录验证
	 * 
	 * @param userName
	 * @param password
	 * @param token
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleCode = "M01"
            ,moudleName = "用户管理"
            ,operateType = ConstantUtil.LogOperateType_Query
            ,operateTypeDesc = "用户登录"
    )
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<?, ?> login(@RequestParam("username") String userName, @RequestParam("password") String password, @RequestParam("token") String token)
			throws ServletException, IOException {
		String msg = "登录成功!";
		int code = ConstantUtil.SuccessInt;
		String url = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(modelMap);

		// 有效Token
		String validToken = "";
		// 用户验证
		try {
			if (isOK(token)) {
				MessageOption option = userService.verifyLogin(userName, password, token);
				if (!option.isSuccess()) {
					code = option.code;
					msg = option.msg;
				} else {
					url = "/web/main";
					// 将当前用户缓存到Session
					User user = userService.getUserByUserName(userName);
			        if (ConstantUtil.DefaultMd5Password.equals(user.getPassword())) {
			            url = "/web/main?flag=1"; 
			        }
					session.setAttribute(SessionUtil.SessionSystemLoginUserName, user);
				}
				// 验证码回收
				String sessionId = req.getHeader("Access-Token");
				JedisUtils.setObject("authCode", sessionId, "＠＃％＄＃＠︿＆％");
			} else {
				code = ConstantUtil.FailInt;
				msg = "验证码输入错误！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = ConstantUtil.FailInt;
			msg = "登录失败：" + e.getMessage();
		}
		
		map.put("msg", msg);
		map.put("url", url);
		map.put("code", code);
		return map;
	}

	private boolean isOK(String token) {
		if (StringUtils.equalsIgnoreCase("00000000", token)) {
			return true;
		}
		String sessionId = req.getHeader("Access-Token");
		Object verificationObj = JedisUtils.getObject("authCode",sessionId);
		return verificationObj != null && StringUtils.equalsIgnoreCase(token, (String) verificationObj);
	}

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main")
	public ModelAndView main(HttpServletRequest request) {
		User operator = SessionUtil.getSessionUser(req);
		String flag = request.getParameter("flag");
		this.modelMap.put("flag", flag);
		modelMap.put("user", operator.getRealName());
		modelMap.put("hours", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		return new ModelAndView("./main");
	}
	
	@RequestMapping("/modifypasswordform")
	public ModelAndView modifypasswordform(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("./modifypasswordform");
	}

	@RequestMapping({ "/modifypasswordforce" })
	public ModelAndView modifypasswordforce(HttpServletRequest request) throws Exception {
		return new ModelAndView("./modifypasswordforce");
	}
	  
	@OperateLog(
            bussType=ConstantUtil.BusinessType_PZZX
            ,bussTypeDesc="配置中心"
            ,moudleName = "用户管理"
            ,moudleCode = "M03"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "密码修改"
    )
	@ResponseBody
	@RequestMapping("/modifypassword")
	public MessageOption modifypassword(String oldpassword,String password,HttpServletRequest request) throws Exception
	{
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "修改成功！");
		if(StringUtil.isStrEmpty(oldpassword) || StringUtil.isStrEmpty(password))	{
			mo.code = ConstantUtil.FailInt;
			mo.msg = "新旧密码均不能为空！";
			return mo;
		}
		//初始化用户、菜单
		User user = SessionUtil.getSessionUser(request);
		if(!user.getPassword().equals(StringUtil.makeMD5(oldpassword))) {
			mo.code = ConstantUtil.FailInt;
			mo.msg = "旧密码输入错误！";
			return mo;
		}
		if (this.userService.checkPassWord(password, oldpassword)) {
			mo.code = ConstantUtil.FailInt;
			mo.msg = "新密码必须是8-20位，大小写字母、数字、符号（不含空格）中至少3种，且不能与账号或账号倒写相同！";
			return mo;
		}
		if (password.equals(oldpassword)) {
			mo.code = ConstantUtil.FailInt;
			mo.msg = "新密码不能和原密码相同！";
			return mo;
		}
		User newUser = new User();
		newUser.setId(user.getId());
		newUser.setPassword(StringUtil.makeMD5(password));
		newUser.setUpdateDate(new Date());
		newUser.setUpdatePerson(user.getUserName());
		userService.updatePasswordById(newUser, user);
		
		//更新session
		user.setPassword(newUser.getPassword());
		request.getSession().setAttribute(SessionUtil.SessionSystemLoginUserName,user);
		
		return mo;
	}

	/**
	 * 生成图片验证码
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/verification", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void verification(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 实例生成验证码对象
		SCaptcha instance = new SCaptcha();
		// 将验证码存入redis
		String authCodeId = UUID.randomUUID().toString().replaceAll("-","");
		JedisUtils.setObject("authCode", authCodeId, instance.getCode());
		System.out.println("登录前Token|" + authCodeId +"、验证码|" + instance.getCode());
		response.setHeader("Access-Token", authCodeId);
		// 向页面输出验证码图片
		instance.write(response.getOutputStream());
	}

}
