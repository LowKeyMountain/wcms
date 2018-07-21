package net.itw.wcms.x27.utils;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.itw.wcms.common.log.annotation.OperateLog;
import net.itw.wcms.common.log.entity.SystemLog;
import net.itw.wcms.common.log.service.ISystemLogService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.x27.entity.User;

public class UserSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
	}

	@OperateLog(bussType = ConstantUtil.BusinessType_PZZX, bussTypeDesc = "配置中心", moudleName = "用户管理", operateType = ConstantUtil.LogOperateType_Query, operateTypeDesc = "用户登出")
	public void sessionDestroyed(HttpSessionEvent arg0) {
//		try {
//			SystemLog valueReturn = new SystemLog();
//			String businessType = ConstantUtil.BusinessType_PZZX;
//			String businessDesc = "配置中心";
//
//			String workPlatform = ConstantUtil.BusinessType_GLZX.equalsIgnoreCase(businessType)
//					|| ConstantUtil.BusinessType_PZZX.equalsIgnoreCase(businessType) ? ConstantUtil.WorkPlatform_PC
//							: ConstantUtil.BusinessType_JKZX.equalsIgnoreCase(businessType)
//									? ConstantUtil.WorkPlatform_APP : "";
//			valueReturn.setWorkPlatform(workPlatform);
//
//			valueReturn.setBussType(Integer.parseInt(businessType));
//			valueReturn.setBussTypeDesc(businessDesc);
//			valueReturn.setMoudleName("用户管理");
//			valueReturn.setOperateType(ConstantUtil.LogOperateType_Query);
//			valueReturn.setOperateTypeDesc("用户登出");
//
//			User operator = (User) arg0.getSession().getAttribute(SessionUtil.SessionSystemLoginUserName);
//			valueReturn.setInputUserId(operator != null ? operator.getUserName() : "");
//
//			valueReturn.setLogDetails("用户" + valueReturn.getInputUserId() + "会话超时退出系统 , 退出时间："
//					+ DateTimeUtils.date2StrDateTime(new Date()) + "。");
//			valueReturn.setOperateResult(ConstantUtil.YesOrNo_YES);
//			valueReturn.setOperationTime(new Date());
//
//			ISystemLogService systemLogService = WebApplicationContextUtils
//					.getWebApplicationContext(arg0.getSession().getServletContext()).getBean(ISystemLogService.class);
//			if(systemLogService != null) {
//				systemLogService.save(valueReturn);
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
