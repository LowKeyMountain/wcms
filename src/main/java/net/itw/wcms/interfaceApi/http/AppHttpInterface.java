package net.itw.wcms.interfaceApi.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IUserService;

/**
 * Description: 移动端HTTP接口
 * 
 * @author Michael 19 Nov 2017 08:33:39
 */
@RestController
@RequestMapping(value = "/user")
public class AppHttpInterface {

	@Autowired
	private IUserService userService;

	/**
	 * 登录验证
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/doLogin")
	public Map<?, ?> doLogin(@RequestParam("json") String json) {

		String msg = "";
		Integer code = 1;
		Map<String, Object> map = new HashMap<String, Object>();

		// 解析参数
		// @userName 用户名
		// @password 密码
		// @token 登录令牌
		String userName;
		String password;
		String token;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			userName = jsonObject.getString("userName");
			password = jsonObject.getString("password");
			token = jsonObject.getString("token");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("msg", "登录失败：解析参数时异常 > " + e1.getMessage());
			map.put("code", "0");
			return map;
		}

		// 用户验证
		try {
			MessageOption option = userService.verifyLogin(userName, password, token);
			code = option.isSuccess() ? 1 : 0;
			msg = option.msg;
		} catch (Exception e) {
			e.printStackTrace();
			code = 0;
			msg = "登录失败：" + e.getMessage();
		}

		map.put("msg", msg);
		map.put("code", code + "");
		return map;
	}

	/**
	 * 获取权限列表
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/doGetPrivileges")
	public Map<?, ?> doGetPrivileges(@RequestParam("json") String json) {

		int code = 1;
		String msg = "操作成功！";

		Map<String, Object> map = new HashMap<>();

		String userName;
		try {
			// 解析参数
			// @userName 用户名
			JSONObject jsonObject = JSONObject.parseObject(json);
			userName = jsonObject.getString("userName");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取权限列表失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}

		// 验证用户是否存在
		User user = userService.getUserByUserName(userName);
		if (user == null) {
			map.put("msg", "获取权限列表失败：该用户不存在！");
			map.put("code", "0");
			return map;
		}

		List<String> list = new ArrayList<>();
		if (StringUtils.equalsIgnoreCase("admin", userName)) {
			list = Arrays.asList("12,56,78,21,7".split(","));
		} else if (StringUtils.equalsIgnoreCase("zhangsan", userName)) {
			list = Arrays.asList("12,56,78,21".split(","));
		}

		map.put("msg", msg);
		map.put("data", list);
		map.put("code", code + "");
		return map;
	}
}
