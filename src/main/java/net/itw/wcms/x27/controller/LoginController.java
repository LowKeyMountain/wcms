package net.itw.wcms.x27.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.SessionUtil;
import net.itw.wcms.x27.utils.StringUtil;

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
	public Map<?, ?> login(@RequestParam("username") String userName, @RequestParam("password") String password)
			throws ServletException, IOException {
		String msg = "登录成功!";
		int code = ConstantUtil.SuccessInt;
		String url = "";

		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(modelMap);
		
		// 用户验证
		try {
			MessageOption option = userService.verifyLogin(userName, password, ConstantUtil.DefaultToken);
			if (!option.isSuccess()) { 
				code = option.code;
				msg = option.msg;
			} else {
				url = "/web/main";
				// 将当前用户缓存到Session
				User user = userService.getUserByUserName(userName);
				session.setAttribute(SessionUtil.SessionSystemLoginUserName, user);
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

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main")
	public ModelAndView main() {
		User operator = SessionUtil.getSessionUser(req);
		modelMap.put("user", operator.getRealName());
		modelMap.put("hours", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		return new ModelAndView("./main");
	}
	
	@RequestMapping("/modifypasswordform")
	public ModelAndView modifypasswordform(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("./modifypasswordform");
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
			mo.msg = "新老密码不能为空！";
			return mo;
		}
		//初始化用户、菜单
		User user = SessionUtil.getSessionUser(request);
		if(!user.getPassword().equals(StringUtil.makeMD5(oldpassword))) {
			mo.code = ConstantUtil.FailInt;
			mo.msg = "旧密码输入错误！";
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
	
}
