package net.itw.wcms.interfaceApi.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.service.IResourceService;
import net.itw.wcms.x27.service.IUserService;

/**
 * Description: 移动端HTTP接口
 * 
 * @author Michael 19 Nov 2017 08:33:39
 */
@Controller
@RestController
@RequestMapping(value = "/api/http")
public class AppHttpInterface {

	@Autowired
	private IUserService userService;
	@Autowired
	private IResourceService resourceService;

	private InfoQueryHelper infoQueryHelper = new InfoQueryHelper();

	/**
	 * 登录验证
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/user/doLogin")
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
	@RequestMapping(value = "/user/doGetPrivileges")
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

		List<String> list = resourceService.getResourcesByUserName(userName);

		map.put("msg", msg);
		map.put("data", list);
		map.put("code", code + "");
		return map;
	}

	/**
	 * 验证用户是否存在
	 * 
	 * @param jsonObject
	 */
	private void checkUser(JSONObject jsonObject) throws Exception {
		String userName = (String) jsonObject.get("userId");
		// 验证用户是否存在
		User user = userService.getUserByUserName(userName);
		if (user == null) {
			throw new X27Exception("操作失败：该用户不存在！");
		}
	}

	/**
	 * 获取船舶作业列表[FN_001]
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetShipList")
	public Map<String, Object> doGetShipList(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			checkUser(jsonObject); // 验证用户是否存在

			Integer shipStatus = (Integer) jsonObject.get("shipStatus");
			if (shipStatus == 0) {
				jsonObject.put("fuctionType", "FN_001_1");
			} else if (shipStatus == 1) {
				jsonObject.put("fuctionType", "FN_001_2");
			} else if (shipStatus == 2) {
				jsonObject.put("fuctionType", "FN_001_3");
			}
			return infoQueryHelper.doQueryInfo(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}

	/**
	 * 获取所有船舱位置信息[FN_002]
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetShipPosition")
	public Map<String, Object> doGetShipPosition(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_002");

			String taskId = (String) jsonObject.get("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("criteria", JSONObject.parseObject("{'$task_id':'" + taskId + "'}"));
			return infoQueryHelper.doQueryInfo(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}

	/**
	 * 获取船舶详情信息[FN_003]
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetShipDetail")
	public Map<String, Object> doGetShipDetail(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_003");

			String taskId = (String) jsonObject.get("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("criteria", JSONObject.parseObject("{'$task_id':'" + taskId + "'}"));
			return infoQueryHelper.doQueryInfo(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}

	/**
	 * 获取船舱信息[FN_004] <br>
	 * 用于获取船舶所有舱位的相关信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetCabinList")
	public Map<String, Object> doGetCabinList(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_004");

			String taskId = (String) jsonObject.get("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("criteria", JSONObject.parseObject("{'$task_id':'" + taskId + "'}"));
			return infoQueryHelper.doQueryInfo(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}

	/**
	 * 获取货物信息[FN_005] <br>
	 * 获取指定船舱的货物信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetCargoDetail")
	public Map<String, Object> doGetCargoDetail(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_005");

			Object taskId = jsonObject.get("taskId");
			if (taskId == null) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}

			Object cabinNo = jsonObject.get("cabinNo");
			if (cabinNo == null) {
				throw new X27Exception("操作失败：参数[cabinNo]不能为空！");
			}

			jsonObject.put("criteria", JSONObject
					.parseObject("{'$task_id':'" + (String) taskId + "','$cabin_no':" + (Integer) cabinNo + "}"));
			return infoQueryHelper.doQueryInfo(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}

}
