package net.itw.wcms.interfaceApi.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.common.log.annotation.OperateLog;
import net.itw.wcms.ship.service.ITaskShipService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.service.IResourceService;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;

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
	@Autowired
	private ITaskShipService taskShipService;

	private InfoQueryHelper infoQueryHelper = new InfoQueryHelper();

	/**
	 * 登录验证
	 * 
	 * @param json
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_JKZX
            ,bussTypeDesc="接口管理"
            ,moudleName = "用户接口"
            ,operateType = ConstantUtil.LogOperateType_Query
            ,operateTypeDesc = "用户登录"
    )
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
		String userName = jsonObject.getString("userId");
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
			
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("shipStatus")) {
				throw new X27Exception("操作失败：参数[shipStatus]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在
			
			jsonObject.put("fuctionType", "FN_001");
			
			String criteria = "";
			
			Integer shipStatus = (Integer) jsonObject.get("shipStatus");
			if (shipStatus == 3) {
				criteria += "'$t.status|!=':0";
			} else {
				criteria += "'$t.status':" + shipStatus + "";
			}
			
			if (jsonObject.containsKey("startTime") && StringUtils.isNotBlank(jsonObject.getString("startTime"))) {
				criteria += ",'$enter_port_time|>=':'" + jsonObject.getString("startTime") + " 00:00:00'";
			}
			
			if (jsonObject.containsKey("endTime") && StringUtils.isNotBlank(jsonObject.getString("endTime"))) {
				criteria += ",'$enter_port_time|<=':'" + jsonObject.getString("endTime") + " 24:59:59'";
			}
			
			jsonObject.put("order", "desc");
			jsonObject.put("sort", "berthing_time");
			jsonObject.put("criteria", JSONObject.parseObject("{"+criteria+"}"));
			
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
	@RequestMapping(value = "/ship/doGetCabinDetail")
	public Map<String, Object> doGetCabinDetail(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			if (!jsonObject.containsKey("cabinNo")) {
				throw new X27Exception("操作失败：参数[cabinNo]不能为空！");
			}
			
			checkUser(jsonObject); // 验证用户是否存在			
			jsonObject.put("fuctionType", "FN_002");

			String taskId = jsonObject.getString("taskId");
			String cabinNo = jsonObject.getString("cabinNo");

			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "','$cabinNo':'" + cabinNo + "'}"));
			
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			return infoQueryHelper.doQueryInfo(jsonObject, options);
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
			
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			
			checkUser(jsonObject); // 验证用户是否存在
			jsonObject.put("fuctionType", "FN_003");

			String taskId = jsonObject.getString("taskId");

			jsonObject.put("criteria", JSONObject.parseObject("{'$t.id':'" + taskId + "'}"));
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
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_004");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("order", "asc");
			jsonObject.put("sort", "cabinNo");
			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "'}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			return infoQueryHelper.doQueryInfo(jsonObject, options);
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

			return taskShipService.doGetCargoDetail(Integer.parseInt(taskId.toString()), (Integer) cabinNo);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 获取货物信息(通过货物ID)<br>
	 * 获取指定船舱的货物信息, 根据货物ID
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetCargoDetailById")
	public Map<String, Object> doGetCargoDetailById(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_005");

			Object cargoId = jsonObject.get("cargoId");
			if (cargoId == null) {
				throw new X27Exception("操作失败：参数[cargoId]不能为空！");
			}

			return taskShipService.doGetCargoDetailById(Integer.parseInt(cargoId.toString()));

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	

	/**
	 * 获取卸货进度信息[FN_006] <br>
	 * 用于获取船舶所有舱位的相关信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetUnloaderDetail")
	public Map<String, Object> doGetUnloaderDetail(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			
			if (!jsonObject.containsKey("cabinNo")) {
				throw new X27Exception("操作失败：参数[cabinNo]不能为空！");
			}
			
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_006");

			String taskId = jsonObject.getString("taskId");
			String cabinNo = jsonObject.getString("cabinNo");

			jsonObject.put("order", "DESC");
			jsonObject.put("sort", "startTime");
			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "','$cabinNo':'" + cabinNo + "'}"));
			QueryOptions options = new QueryOptions();
			options.searchString = "%#";
			options.replacement = taskId;
			return infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}	
	
	/**
	 * 设置舱位 <br>
	 * 提供设置舱位服务，支持新增、修改舱位功能
	 * 
	 * @param json
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_JKZX
            ,bussTypeDesc="接口管理"
            ,moudleName = "船舶接口"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "设置舱位"
    )
	@RequestMapping(value = "/ship/doSetCabinPosition")
	public Map<String, Object> doSetCabinPosition(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			if (!jsonObject.containsKey("data")) {
				throw new X27Exception("操作失败：参数[data]不能为空！");
			}

			checkUser(jsonObject); // 验证用户是否存在
			String userName = jsonObject.getString("userId");
			String taskId = jsonObject.getString("taskId");

			List<Map<String, Object>> list = new ArrayList<>();
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				JSONObject jo = (JSONObject) iterator.next();
				Map<String, Object> map = new HashMap<>();
				for (String key : jo.keySet()) {
					map.put(key, jo.get(key));
				}
				list.add(map);
			}

			MessageOption mo = taskShipService.setCabinPosition(taskId, userName, list);
			result.put("msg", mo.msg);
			result.put("code", mo.isSuccess() ? "1" : "0");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}

	/**
	 * 设置清舱 <br>
	 * 设置船舱清舱状态
	 * 
	 * @param json
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_JKZX
            ,bussTypeDesc="接口管理"
            ,moudleName = "船舶接口"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "设置清舱状态"
    )
	@RequestMapping(value = "/ship/doSetCabinStatus")
	public Map<String, Object> doSetCabinStatus(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			if (!jsonObject.containsKey("cabinNo")) {
				throw new X27Exception("操作失败：参数[cabinNo]不能为空！");
			}
			if (!jsonObject.containsKey("status")) {
				throw new X27Exception("操作失败：参数[status]不能为空！");
			}
			
			checkUser(jsonObject); // 验证用户是否存在

			String userName = jsonObject.getString("userId");
			String taskId = jsonObject.getString("taskId");
			String cabinNo = jsonObject.getString("cabinNo");
			String status = jsonObject.getString("status");

			MessageOption mo = taskShipService.updateCabinStatus(taskId, userName, cabinNo, status);
			result.put("msg", mo.msg);
			result.put("code", mo.isSuccess() ? "1" : "0");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}

	/**
	 * 设置船舶完成卸货状态
	 * 
	 * @param json
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_JKZX
            ,bussTypeDesc="接口管理"
            ,moudleName = "船舶接口"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "设置船舶状态"
    )
	@RequestMapping(value = "/ship/doSetShipStatus")
	public Map<String, Object> doSetShipStatus(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);

			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			if (!jsonObject.containsKey("status")) {
				throw new    X27Exception("操作失败：参数[status]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在

			String userName = jsonObject.getString("userId");
			String taskId = jsonObject.getString("taskId");
			String status = jsonObject.getString("status");

			MessageOption mo = taskShipService.updateShipStatus(taskId, userName, status);
			result.put("msg", mo.msg);
			result.put("code", mo.isSuccess() ? "1" : "0");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 获取货物卸船情况列表[FN_007] <br>
	 * 查询卸船情况信息（以货物为维度）
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetCargoUnshipInfo")
	public Map<String, Object> doGetCargoUnshipInfo(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_007");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("order", "asc");
			jsonObject.put("sort", "CARGOID");
			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "'}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			return infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 获取船舶卸船情况列表[FN_008] <br>
	 * 查询卸船情况信息（以船舶为维度）
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetShipUnshipInfo")
	public Map<String, Object> doGetShipUnshipInfo(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_008");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("criteria", JSONObject.parseObject("{'$t.task_id':'" + taskId + "'}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			return infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 获取卸船机卸船情况列表[FN_009] <br>
	 * 查询卸船情况信息（以卸船机为维度）
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetUnloaderUnshipInfo")
	public Map<String, Object> doGetUnloaderUnshipInfo(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				// throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			
			// checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_009");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			String startTime = jsonObject.getString("startTime");
			String endTime = jsonObject.getString("endTime");
			return taskShipService.doGetUnloaderUnshipInfo(Integer.parseInt(taskId.toString()), startTime, endTime);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 获取卸船机卸船明细列表[FN_010] <br>
	 * 查询卸船机卸船明细列表信息（以卸船机为维度）
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetUnloaderUnshipDetailList")
	public Map<String, Object> doGetUnloaderUnshipDetailList(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				// throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			if (!jsonObject.containsKey("unloaderId")) {
				throw new X27Exception("操作失败：参数[unloaderId]不能为空！");
			}

			// checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_010");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			String unloaderId = jsonObject.getString("unloaderId");
			if (StringUtils.isBlank(unloaderId)) {
				throw new X27Exception("操作失败：卸船机编号[unloaderId]不能为空！");
			}

			String endTime = jsonObject.getString("endTime");
			String startTime = jsonObject.getString("startTime");
			return taskShipService.doGetUnloaderUnshipDetailList(Integer.parseInt(taskId.toString()), unloaderId,
					startTime, endTime);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 统计货物信息[FN_011] <br>
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doCargoInfoStatistics")
	public Map<String, Object> doCargoInfoStatistics(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_011");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("order", "asc");
			jsonObject.put("sort", "CARGOID");
			
			String criteria = "'$t.task_id':'" + taskId + "'";
			if (jsonObject.containsKey("cargoId") && StringUtils.isNotBlank(jsonObject.getString("cargoId"))) {
				criteria += ",'$t.cargoId':'" + jsonObject.getString("cargoId") + "'";
			}
			
			jsonObject.put("criteria", JSONObject.parseObject("{"+criteria+"}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			return infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 统计船舱信息[FN_012] <br>
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doCabinInfoStatistics")
	public Map<String, Object> doCabinInfoStatistics (@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_012");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}

			jsonObject.put("order", "asc");
			jsonObject.put("sort", "CABINNO");
			
			String criteria = "'$t.task_id':'" + taskId + "'";
			if (jsonObject.containsKey("cargoId") && StringUtils.isNotBlank(jsonObject.getString("cargoId"))) {
				criteria += ",'$t.cargoId':'" + jsonObject.getString("cargoId") + "'";
			}
			if (jsonObject.containsKey("cabinNo") && StringUtils.isNotBlank(jsonObject.getString("cabinNo"))) {
				criteria += ",'$t.cabinNo':'" + jsonObject.getString("cabinNo") + "'";
			}
			
			jsonObject.put("criteria", JSONObject.parseObject("{"+criteria+"}"));
			QueryOptions options = new QueryOptions();
			options.args = new Object[] { taskId, taskId, taskId, taskId };
			return infoQueryHelper.doQueryInfo(jsonObject, options);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 统计卸船机信息[FN_013] <br>
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doUnloaderInfoStatistics")
	public Map<String, Object> doUnloaderInfoStatistics(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				 throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			
			checkUser(jsonObject); // 验证用户是否存在

			jsonObject.put("fuctionType", "FN_013");

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}
			Map<String, Object> args = new HashMap<>();
			args.put("taskId", Integer.parseInt(taskId.toString()));
			args.put("cabinNo", jsonObject.get("cabinNo"));
			args.put("cargoId", jsonObject.get("cargoId"));
			args.put("endTime", jsonObject.get("endTime"));
			args.put("startTime", jsonObject.get("startTime"));
			return taskShipService.doUnloaderInfoStatistics(args);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 统计飘到舱外的作业量统计 <br>
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doOutboardInfoStatistics")
	public Map<String, Object> doOutboardInfoStatistics(@RequestParam("json") String json) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			if (!jsonObject.containsKey("userId")) {
				 throw new X27Exception("操作失败：参数[userId]不能为空！");
			}
			if (!jsonObject.containsKey("taskId")) {
				throw new X27Exception("操作失败：参数[taskId]不能为空！");
			}
			
			checkUser(jsonObject); // 验证用户是否存在

			String taskId = jsonObject.getString("taskId");
			if (StringUtils.isBlank(taskId)) {
				throw new X27Exception("操作失败：作业船舶[taskId]不能为空！");
			}
			Map<String, Object> args = new HashMap<>();
			args.put("taskId", Integer.parseInt(taskId.toString()));
			return taskShipService.doOutboardInfoStatistics(args);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", e.getMessage());
			return result;
		}
	}
}
