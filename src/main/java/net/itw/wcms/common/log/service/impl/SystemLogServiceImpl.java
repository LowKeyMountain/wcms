package net.itw.wcms.common.log.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.common.log.entity.SystemLog;
import net.itw.wcms.common.log.repository.SystemLogRepository;
import net.itw.wcms.common.log.service.ISystemLogService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.x27.utils.ConstantUtil;

@Service
@Transactional
public class SystemLogServiceImpl implements ISystemLogService {

	@Autowired
	private SystemLogRepository systemLogRepository;
	
	public SystemLog getSystemLogByUids(String uids) {
		return systemLogRepository.getSystemLogByUids(uids);
	}
	
	@Override
	public Integer save(SystemLog log) throws Exception {
		try {
			systemLogRepository.save(log);
		} catch (Exception e) {
			return ConstantUtil.FailInt;
		}
		return ConstantUtil.SuccessInt;
	}

	public Integer updateLogDetailsByUids(String uids, String logDetails) throws Exception {
		try {
			SystemLog sl = systemLogRepository.getSystemLogByUids(uids);
			if (sl == null) {
				return ConstantUtil.FailInt;
			}
			sl.setLogDetails(logDetails);
			systemLogRepository.save(sl);
		} catch (Exception e) {
			return ConstantUtil.FailInt;
		}
		return ConstantUtil.SuccessInt;
	}

	/**
	 * 获取日志列表
	 * 
	 * @param pageable
	 * @param params
	 * @return
	 */
	public String getList(Pageable pageable, Map<String, String> params) {

		Page<SystemLog> page = findAll(pageable, params);
		JSONObject jo = null;
		if (page == null || page.getTotalPages() == 0) {
			return "{\"total\":0,\"rows\":[],}";
		}
		long total = page.getTotalElements();
		JSONArray jsonArray = new JSONArray();
		for (SystemLog t : page) {
			jo = new JSONObject();
			jo.put("id", t.getId());
			jo.put("bussTypeDesc", t.getBussTypeDesc());
			jo.put("operateTypeDesc", t.getOperateTypeDesc());
			jo.put("moudleName", t.getMoudleName());
			jo.put("inputUserId", t.getInputUserId());
			jo.put("requestIp", t.getRequestIp());
			jo.put("requestUrl", t.getRequestUrl());
			jo.put("operateResult", t.getOperateResult());
			jo.put("errorMessage", t.getErrorMessage());
			jo.put("operationTime",
					t.getOperationTime() != null ? DateTimeUtils.date2StrDateTime(t.getOperationTime()) : "");
			jo.put("logDetails", t.getLogDetails());
			String workPlatform = t.getWorkPlatform();
			workPlatform = ConstantUtil.WorkPlatform_PC.equalsIgnoreCase(workPlatform) ? "电脑端"
					: ConstantUtil.WorkPlatform_APP.equalsIgnoreCase(workPlatform) ? "移动端" :"";
			jo.put("workPlatform", workPlatform);
			jsonArray.add(jo);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", total);
		jsonObject.put("rows", jsonArray);
		return jsonObject.toString();

	}

	public Page<SystemLog> findAll(Pageable pageable, Map<String, String> params) {
		return systemLogRepository.findAllByParams(pageable, params);
	}

}
