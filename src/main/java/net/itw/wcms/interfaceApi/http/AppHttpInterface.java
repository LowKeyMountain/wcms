package net.itw.wcms.interfaceApi.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.entity.TaskDetail;
import net.itw.wcms.ship.service.TaskDetailService;
import net.itw.wcms.ship.service.TaskService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;

/**
 * Description: 移动端HTTP接口
 * 
 * @author Michael 19 Nov 2017 08:33:39
 */
@RestController
@RequestMapping(value = "/api/http")
public class AppHttpInterface {

	@Autowired
	private IUserService userService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskDetailService taskDetailService;

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

	/**
	 * 返回船舶作业信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetShipList")
	public Map<?, ?> doGetShipList(@RequestParam("json") String json) {

		Map<String, Object> map = new HashMap<>();
		String status;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			status = jsonObject.getString("status");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舶列表失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(status)) {
			map.put("msg", "查询失败:船舶状态参数不能为空!");
			map.put("code", "0");
		} else {
			List<Task> taskList = taskService.getTaskByStatus(status);
			if (taskList.size() > 0) {
				JSONArray jsonArray = new JSONArray();
				for (Task task : taskList) {
					JSONObject jo = new JSONObject();
					jo.put("taskId", task.getId());
					jo.put("cargoType", task.getCargoType() == null ? "" : task.getCargoType());
					jo.put("shipName", task.getShip().getShipName());
					jsonArray.add(jo);
				}
				map.put("msg", "查询成功!");
				map.put("code", "1");
				map.put("data", JSONObject.toJSON(jsonArray));
			} else {
				map.put("msg", "查询失败:未找到相应数据!");
				map.put("code", "0");
				map.put("data", "{}");
			}
		}
		return map;
	}

	/**
	 * 返回船舶详情信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetShipDetail")
	public Map<?, ?> doGetShipDetail(@RequestParam("json") String json) {

		JSONObject data = new JSONObject();
		Map<String, Object> map = new HashMap<>();
		String taskId;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			taskId = jsonObject.getString("taskId");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舶详情失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(taskId)) {
			map.put("msg", "查询失败:船舶作业id不能为空!");
			map.put("code", "0");
		} else {
			Task task = taskService.getTaskById(Integer.parseInt(taskId));
			if (task != null) {
				data.put("shipId", task.getShip().getId());
				data.put("shipName", task.getShip().getShipName());
				data.put("shipEname", task.getShip().getShipEnName() == null ? "" : task.getShip().getShipEnName());
				data.put("imoNo", task.getShip().getImoNo() == null ? "" : task.getShip().getImoNo());
				data.put("buildDate", task.getShip().getBuildDate() == null ? ""
						: DateTimeUtils.date2StrDate(task.getShip().getBuildDate()));
				data.put("length", task.getShip().getLength() == null ? "" : task.getShip().getLength());
				data.put("width", task.getShip().getBreadth() == null ? "" : task.getShip().getBreadth());
				data.put("depth", task.getShip().getMouldedDepth() == null ? "" : task.getShip().getMouldedDepth());
				data.put("cabinNum", task.getShip().getCabinNum() == null ? "" : task.getShip().getCabinNum());
				data.put("berthingTime",
						task.getBerthingTime() == null ? "" : DateTimeUtils.date2StrDateTime(task.getBerthingTime()));
				data.put("departureTime",
						task.getDepartureTime() == null ? "" : DateTimeUtils.date2StrDateTime(task.getDepartureTime()));
				data.put("beginTime",
						task.getBeginTime() == null ? "" : DateTimeUtils.date2StrDateTime(task.getBeginTime()));
				data.put("endTime", task.getEndTime() == null ? "" : DateTimeUtils.date2StrDateTime(task.getEndTime()));
				map.put("msg", "查询成功!");
				map.put("code", "1");
				map.put("data", data);
			} else {
				map.put("msg", "查询失败:未找到相应数据!");
				map.put("code", "0");
				map.put("data", "{}");
			}
		}
		return map;
	}

	/**
	 * 返回船舶船舱列表
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetCabinList")
	public Map<?, ?> doGetCabinList(@RequestParam("json") String json) {

		new JSONObject();
		Map<String, Object> map = new HashMap<>();
		String taskId;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			taskId = jsonObject.getString("taskId");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舱列表失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(taskId)) {
			map.put("msg", "查询失败:船舶作业id不能为空!");
			map.put("code", "0");
		} else {
			List<TaskDetail> cabinList = taskDetailService.getTaskDetailByTaskId(Integer.parseInt(taskId));
			if (cabinList != null) {
				JSONArray jsonArray = new JSONArray();
				for (TaskDetail cabin : cabinList) {
					JSONObject jo = new JSONObject();
					jo.put("detailId", cabin.getId());
					jo.put("cabinNo", cabin.getCabinNo());
					jo.put("cargoType", cabin.getCargoType() == null ? "" : cabin.getCargoType());
					jo.put("total", cabin.getPreunloading() == null ? "" : cabin.getPreunloading());
					jo.put("finish", cabin.getActualUnloading() == null ? "" : cabin.getActualUnloading());
					jo.put("remainder", cabin.getRemainder() == null ? "" : cabin.getRemainder());
					jo.put("clearance", cabin.getClearance() == null ? "" : cabin.getClearance());
					jo.put("status", cabin.getStatus() == null ? "" : cabin.getStatus());
					jsonArray.add(jo);
				}
				map.put("msg", "查询成功!");
				map.put("code", "1");
				map.put("data", JSONObject.toJSON(jsonArray));
			} else {
				map.put("msg", "查询失败:未找到相应数据!");
				map.put("code", "0");
				map.put("data", "{}");
			}
		}
		return map;
	}

	/**
	 * 返回船舶各舱位置列表
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetShipPosition")
	public Map<?, ?> doGetShipPosition(@RequestParam("json") String json) {

		Map<String, Object> map = new HashMap<>();
		String taskId;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			taskId = jsonObject.getString("taskId");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舱位置失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(taskId)) {
			map.put("msg", "查询失败:船舶作业id不能为空!");
			map.put("code", "0");
		} else {
			List<TaskDetail> cabinList = taskDetailService.getTaskDetailByTaskId(Integer.parseInt(taskId));
			if (cabinList != null) {
				JSONArray jsonArray = new JSONArray();
				for (TaskDetail cabin : cabinList) {
					JSONObject jo = new JSONObject();
					jo.put("detailId", cabin.getId());
					jo.put("cabinNo", cabin.getCabinNo());
					jo.put("startPosition", cabin.getStartPosition() == null ? "" : cabin.getStartPosition());
					jo.put("endPosition", cabin.getEndPosition() == null ? "" : cabin.getEndPosition());
					jsonArray.add(jo);
				}
				map.put("msg", "查询成功!");
				map.put("code", "1");
				map.put("data", JSONObject.toJSON(jsonArray));
			} else {
				map.put("msg", "查询失败:未找到相应数据!");
				map.put("code", "0");
				map.put("data", "{}");
			}
		}
		return map;
	}

	/**
	 * 返回船仓详细信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetCabinDetail")
	public Map<?, ?> doGetCabinDetail(@RequestParam("json") String json) {

		JSONObject data = new JSONObject();
		Map<String, Object> map = new HashMap<>();
		String detailId;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			detailId = jsonObject.getString("detailId");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舱详情失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(detailId)) {
			map.put("msg", "查询失败:船舶作业详情id不能为空!");
			map.put("code", "0");
		} else {
			TaskDetail taskDetail = taskDetailService.getTaskDetailById(Integer.parseInt(detailId));
			if (taskDetail != null) {
				data.put("detailId", taskDetail.getId());
				data.put("cabinNo", taskDetail.getCabinNo());
				data.put("cargoType", taskDetail.getCargoType() == null ? "" : taskDetail.getCargoType());
				data.put("total", taskDetail.getPreunloading() == null ? "" : taskDetail.getPreunloading());
				data.put("finish", taskDetail.getActualUnloading() == null ? "" : taskDetail.getActualUnloading());
				data.put("remainder", taskDetail.getRemainder() == null ? "" : taskDetail.getRemainder());
				data.put("clearance", taskDetail.getClearance() == null ? "" : taskDetail.getClearance());
				data.put("status", taskDetail.getStatus() == null ? "" : taskDetail.getStatus());
				data.put("startTime", taskDetail.getStartTime() == null ? ""
						: DateTimeUtils.date2StrDateTime(taskDetail.getStartTime()));
				data.put("endTime",
						taskDetail.getEndTime() == null ? "" : DateTimeUtils.date2StrDateTime(taskDetail.getEndTime()));
				data.put("usedTime", taskDetail.getUsedTime() == null ? "" : taskDetail.getUsedTime());
				data.put("unloadingTonnage",
						taskDetail.getActualUnloading() == null ? "" : taskDetail.getActualUnloading());
				data.put("efficiency", taskDetail.getEfficiency() == null ? "" : taskDetail.getEfficiency());
				map.put("msg", "查询成功!");
				map.put("code", "1");
				map.put("data", data);
			} else {
				map.put("msg", "查询失败:未找到相应数据!");
				map.put("code", "0");
				map.put("data", "{}");
			}
		}
		return map;
	}

	/**
	 * 返回货物详细信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doGetCargoDetail")
	public Map<?, ?> doGetCargoDetail(@RequestParam("json") String json) {

		JSONObject data = new JSONObject();
		Map<String, Object> map = new HashMap<>();
		String detailId;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			detailId = jsonObject.getString("detailId");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取货物详情失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(detailId)) {
			map.put("msg", "查询失败:船舶作业详情id不能为空!");
			map.put("code", "0");
		} else {
			TaskDetail taskDetail = taskDetailService.getTaskDetailById(Integer.parseInt(detailId));
			if (taskDetail != null) {
				data.put("detailId", taskDetail.getId());
				data.put("cargoType", taskDetail.getCargoType() == null ? "" : taskDetail.getCargoType());
				data.put("cargoCategory", taskDetail.getCargoCategory() == null ? "" : taskDetail.getCargoCategory());
				data.put("loadingPort", taskDetail.getLoadingPort() == null ? "" : taskDetail.getLoadingPort());
				data.put("quality", taskDetail.getQuality() == null ? "" : taskDetail.getQuality());
				data.put("moisture", taskDetail.getMoisture() == null ? "" : taskDetail.getMoisture());
				data.put("cargoOwner", taskDetail.getCargoOwner() == null ? "" : taskDetail.getCargoOwner());
				data.put("stowageTonnage", taskDetail.getStowage() == null ? "" : taskDetail.getStowage());
				map.put("msg", "查询成功!");
				map.put("code", "1");
				map.put("data", data);
			} else {
				map.put("msg", "查询失败:未找到相应数据!");
				map.put("code", "0");
				map.put("data", "{}");
			}
		}
		return map;
	}

	/**
	 * 修改船舱状态
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doSetCabinStatus")
	public Map<?, ?> doSetCabinStatus(@RequestParam("json") String json) {

		Map<String, Object> map = new HashMap<>();
		String detailId;
		String status;
		String userId;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			detailId = jsonObject.getString("detailId");
			userId = jsonObject.getString("userId");
			status = jsonObject.getString("status");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舱状态信息失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(detailId) || StringUtils.isEmpty(status) || StringUtils.isEmpty(userId)) {
			map.put("msg", "船舱状态修改失败:船舶作业详情id、用户id、状态值参数均不能为空!");
			map.put("code", "0");
		} else {
			int code = taskDetailService.updateCabinStatusByid(Integer.parseInt(detailId), userId, status);
			if (code == 1) {
				map.put("msg", "修改成功!");
				map.put("code", "1");
			} else {
				map.put("msg", "修改失败!");
				map.put("code", "0");
			}
		}
		return map;
	}

	/**
	 * 修改船舶状态
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doSetShipStatus")
	public Map<?, ?> doSetShipStatus(@RequestParam("json") String json) {

		Map<String, Object> map = new HashMap<>();
		String taskId;
		String status;
		String userId;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			taskId = jsonObject.getString("taskId");
			userId = jsonObject.getString("userId");
			status = jsonObject.getString("status");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舶状态信息失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(status) || StringUtils.isEmpty(userId)) {
			map.put("msg", "船舶状态修改失败:船舶作业id、用户id、状态值参数均不能为空!");
			map.put("code", "0");
		} else {
			int code = taskService.updateShipStatusByid(Integer.parseInt(taskId), userId, status);
			if (code == 1) {
				map.put("msg", "修改成功!");
				map.put("code", "1");
			} else {
				map.put("msg", "修改失败!");
				map.put("code", "0");
			}
		}
		return map;
	}

	/**
	 * 修改船舱位置
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/ship/doAlterShipPosition")
	public Map<?, ?> doAlterShipPosition(@RequestParam("json") String json) {

		Map<String, Object> map = new HashMap<>();
		String userId;
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;

		try {
			jsonObject = JSONObject.parseObject(json);
			jsonArray = jsonObject.getJSONArray("data");
			userId = jsonObject.getString("userId");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "获取船舶位置信息失败：解析参数时异常 > " + e.getMessage());
			map.put("code", "0");
			return map;
		}
		if (StringUtils.isEmpty(userId)) {
			map.put("msg", "船舶位置修改失败:用户id不能为空!");
			map.put("code", "0");
		} else {
			if (jsonArray.size() > 1) {
				List<TaskDetail> taskDetailList = new ArrayList<TaskDetail>();
				for (Iterator<?> it = jsonArray.iterator(); it.hasNext();) {
					Object object = it.next();
					TaskDetail taskDetail = new TaskDetail();
					if (object instanceof JSONObject) {
						JSONObject jsonObj = (JSONObject) object;
						Object detailId = jsonObj.getString("detailId");
						Object startPosition = jsonObj.getString("startPosition") == null ? 0
								: jsonObj.getString("startPosition");
						Object endPosition = jsonObj.getString("endPosition") == null ? 0
								: jsonObj.getString("endPosition");
						taskDetail.setId(Integer.parseInt((String) detailId));
						taskDetail.setStartPosition(Double.valueOf((String) startPosition));
						taskDetail.setEndPosition(Double.valueOf((String) endPosition));
					}
					taskDetailList.add(taskDetail);
				}
				int code = taskDetailService.updateCabinPositionByid(taskDetailList, userId);
				if (code == ConstantUtil.SuccessInt) {
					map.put("msg", "修改成功!");
					map.put("code", "1");
				}
			} else {
				map.put("msg", "修改失败:船舶位置信息为空!");
				map.put("code", "0");
			}
		}
		return map;
	}
}
