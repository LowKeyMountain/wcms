package net.itw.wcms.common.log.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import net.itw.wcms.common.log.annotation.OperateLog;
import net.itw.wcms.common.log.entity.SystemLog;
import net.itw.wcms.common.log.service.ISystemLogService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.toolkit.hibernate.Entityable;
import net.itw.wcms.toolkit.lang.Int32;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.SessionUtil;

@Aspect
@Component
public class OperateLogAop {

	private final Logger logger = Logger.getLogger("operateLog");

	@Autowired
	ISystemLogService systemLogService;
	User operateUser = null;
	HttpServletRequest request = null;
	ThreadLocal<Long> time = new ThreadLocal<Long>();
	// 用于生成操作日志的唯一标识，用于业务流程审计日志调用
	public static ThreadLocal<String> tag = new ThreadLocal<String>();

	// 声明AOP切入点，凡是使用了OperateLog的方法均被拦截
	@Pointcut("@annotation(net.itw.wcms.common.log.annotation.OperateLog)")
	public void log() {
		System.out.println("我是一个切入点");
	}

	/**
	 * 在所有标注@Log的地方切入
	 * 
	 * @param joinPoint
	 */
	@Before("log() && args(..)")
	public void beforeExec(JoinPoint joinPoint) {
		time.set(System.currentTimeMillis());
		// 设置日志记录的唯一标识号
		tag.set(UUID.randomUUID().toString());
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		operateUser = SessionUtil.getSessionUser(request);
	}

	// 后置通知（获取返回值）
	@AfterReturning(pointcut = "log() && args(..)", returning = "result")
	public void doAfterReturning(JoinPoint joinPoint, Object result) throws Exception {
		info(joinPoint, result);
		saveLogDetails(joinPoint, result);
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		logger.info("标记为" + tag.get() + "的方法" + method.getName() + "运行消耗" + (System.currentTimeMillis() - time.get())
				+ "ms");
	}

	@After("log()")
	public void afterExec(JoinPoint joinPoint) {
	}

	// 在执行目标方法的过程中，会执行这个方法，可以在这里实现日志的记录
	@Around("log()")
	public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable {
		Object ret = pjp.proceed();
		try {
			Object[] orgs = pjp.getArgs();
			SystemLog valueReturn = null;
			for (int i = 0; i < orgs.length; i++) {
				if (orgs[i] instanceof SystemLog) {
					valueReturn = (SystemLog) orgs[i];
				}
			}
			if (valueReturn == null) {
				valueReturn = new SystemLog();
			}
			if (valueReturn != null && request != null) {

				MethodSignature ms = (MethodSignature) pjp.getSignature();
				Method method = ms.getMethod();
				// 获取注解的操作日志信息
				OperateLog log = method.getAnnotation(OperateLog.class);
				String businessType = log.bussType();
				String businessDesc = log.bussTypeDesc();

				String workPlatform = ConstantUtil.BusinessType_GLZX.equalsIgnoreCase(businessType)
						|| ConstantUtil.BusinessType_PZZX.equalsIgnoreCase(businessType) ? ConstantUtil.WorkPlatform_PC
								: ConstantUtil.BusinessType_JKZX.equalsIgnoreCase(businessType)
										? ConstantUtil.WorkPlatform_APP : "";
				valueReturn.setWorkPlatform(workPlatform);

				valueReturn.setBussType(Integer.parseInt(businessType));
				valueReturn.setBussTypeDesc(businessDesc);

				valueReturn.setMoudleCode(log.moudleCode());
				valueReturn.setMoudleName(log.moudleName());
				valueReturn.setOperateResult(ConstantUtil.YesOrNo_YES);
				valueReturn.setOperateType(log.operateType());
				
				String userName = "";
				User operator = SessionUtil.getSessionUser(request);
				operator = operator != null ? operator : operateUser;
				if (operator != null) {
					userName = operator.getUserName();
				}
				if (StringUtils.isBlank(userName)
						&& StringUtils.equalsIgnoreCase(ConstantUtil.BusinessType_JKZX, log.bussType())) {
					JSONObject jsonObject = JSONObject.parseObject(pjp.getArgs()[0].toString());
					userName = jsonObject.getString("userId");
					if (StringUtils.isBlank(userName)) {
						userName = jsonObject.getString("userName");	
					}
				}
				valueReturn.setInputUserId(userName);
				
				valueReturn.setOperateTypeDesc(getOperateTypeDesc(pjp, log));
				valueReturn.setRequestIp(getRemoteHost(request));
				valueReturn.setRequestUrl(request.getRequestURI());
				valueReturn.setServerIp(request.getLocalAddr());
				valueReturn.setOperationTime(new Date());
				valueReturn.setUids(tag.get());

				// 日志特殊字段处理
				///////////////////////////////////////////
				switch (log.moudleCode()) {
				case "M01":

					break;
				default:
					break;
				}
				///////////////////////////////////////////

				// 保存操作日志
				systemLogService.save(valueReturn);
			} else {
				logger.info("不记录日志信息");
			}
			// 保存操作结果
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// 记录异常日志
	@AfterThrowing(pointcut = "log()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		try {
			info(joinPoint, null);
			saveLogDetails(joinPoint, null);
			Object[] orgs = joinPoint.getArgs();
			SystemLog valueReturn = null;
			for (int i = 0; i < orgs.length; i++) {
				if (orgs[i] instanceof SystemLog) {
					valueReturn = (SystemLog) orgs[i];
				}
			}
			if (valueReturn == null) {
				valueReturn = new SystemLog();
			}
			if (valueReturn != null && request != null) {
				MethodSignature ms = (MethodSignature) joinPoint.getSignature();
				Method method = ms.getMethod();
				OperateLog log = method.getAnnotation(OperateLog.class);
				String businessType = log.bussType();
				String businessDesc = log.bussTypeDesc();

				String workPlatform = ConstantUtil.BusinessType_GLZX.equalsIgnoreCase(businessType)
						|| ConstantUtil.BusinessType_PZZX.equalsIgnoreCase(businessType) ? ConstantUtil.WorkPlatform_PC
								: ConstantUtil.BusinessType_JKZX.equalsIgnoreCase(businessType)
										? ConstantUtil.WorkPlatform_APP : "";
				valueReturn.setWorkPlatform(workPlatform);

				valueReturn.setBussType(Integer.parseInt(businessType));
				valueReturn.setBussTypeDesc(businessDesc);
				valueReturn.setMoudleCode(log.moudleCode());
				valueReturn.setMoudleName(log.moudleName());
				valueReturn.setOperateType(log.operateType());
				valueReturn.setOperateTypeDesc(getOperateTypeDesc(joinPoint, log));

				String userName = "";
				User operator = SessionUtil.getSessionUser(request);
				operator = operator != null ? operator : operateUser;
				if (operator != null) {
					userName = operator.getUserName();
				}
				if (StringUtils.isBlank(userName)
						&& StringUtils.equalsIgnoreCase(ConstantUtil.BusinessType_JKZX, log.bussType())) {
					JSONObject jsonObject = JSONObject.parseObject(joinPoint.getArgs()[0].toString());
					userName = jsonObject.getString("userId");
					if (StringUtils.isBlank(userName)) {
						userName = jsonObject.getString("userName");	
					}
				}
				valueReturn.setInputUserId(userName);
				
				valueReturn.setOperateResult(ConstantUtil.YesOrNo_NO);
				String errMes = e.getMessage();
				if (errMes != null && errMes.length() > 800) {
					errMes = errMes.substring(0, 800);
				}
				valueReturn.setErrorMessage(errMes);
				valueReturn.setRequestIp(getRemoteHost(request));
				valueReturn.setRequestUrl(request.getRequestURI());
				valueReturn.setServerIp(request.getLocalAddr());
				valueReturn.setUids(tag.get());
				valueReturn.setOperationTime(new Date());
				systemLogService.save(valueReturn);
			} else {
				logger.info("不记录日志信息");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 处理操作类型描述
	 * 
	 * @param jp
	 * @param log
	 * @return
	 */
	private String getOperateTypeDesc(JoinPoint jp, OperateLog log) {
		String desc = log.operateTypeDesc();
		try {
			if (!(StringUtils.isNotBlank(desc) && StringUtils.startsWith(desc, "^")
					&& StringUtils.endsWith(desc, "$"))) {
				return desc;
			}
			String temp = desc.substring(1, desc.length() - 1);
			String[] arrays = temp.split("\\|");
			if (arrays.length != 3) {
				return desc;
			}
			switch (arrays[0]) {
			case "ParamIndex":
				MutableInt mi = new MutableInt();
				if (!Int32.tryParse(arrays[1], mi)) {
					return desc;
				}
				if (jp.getArgs().length < mi.intValue()) {
					return desc;
				}
				Object param = jp.getArgs()[mi.intValue()];
				if (param == null) {
					return desc;
				}
				String obj = param.toString();
				for (String e : arrays[2].split(",")) {
					if (StringUtils.startsWith(e, obj)) {
						desc = e.substring(e.indexOf("#") + 1);
					}
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			desc = log.operateTypeDesc();
		}
		return desc;
	}

	private void info(JoinPoint joinPoint, Object returnResult) throws Exception {

		logger.info("--------------------------------------------------");
		// logger.info("King:\t" + joinPoint.getKind());
		logger.info("Target:\t" + joinPoint.getTarget().toString());

		logger.info("Args:");
		Object[] args = joinPoint.getArgs();
		String classType = joinPoint.getTarget().getClass().getName();
		Class<?> clazz = Class.forName(classType);
		String clazzName = clazz.getName();
		String methodName = joinPoint.getSignature().getName(); // 获取方法名称
		// 获取参数名称和值
		Map<String, Object> nameAndArgs = this.getFieldsName(this.getClass(), clazzName, methodName, args);
		for (Map.Entry<String, Object> entry : nameAndArgs.entrySet()) {
			String objStr = "";
			Object obj = entry.getValue();
			if (obj != null && (obj instanceof Entityable || obj instanceof Serializable)) {
				objStr = JSONObject.toJSONString(obj);
			} else {
				objStr = String.valueOf(entry.getValue());
			}
			logger.info("\t==>参数[" + entry.getKey() + "]:\t" + objStr);
		}

		logger.info("return:");
		if (returnResult != null) {
			logger.info(JSONObject.toJSONString(returnResult));
		}

		logger.info("Signature:\t" + joinPoint.getSignature());
		// logger.info("SourceLocation:\t" + joinPoint.getSourceLocation());
		// logger.info("StaticPart:\t" + joinPoint.getStaticPart());
		logger.info("--------------------------------------------------");
	}

	/**
	 * 保存日志详情信息
	 * 
	 * @param joinPoint
	 * @param returnResult
	 * @throws Exception
	 */
	private void saveLogDetails(JoinPoint joinPoint, Object returnResult) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("--------------------------------------------------" + "</br>");
		sb.append("King:\t" + joinPoint.getKind() + "</br>");
		sb.append("Target:\t" + joinPoint.getTarget().toString() + "</br>");

		sb.append("Args:" + "</br>");
		Object[] args = joinPoint.getArgs();
		String classType = joinPoint.getTarget().getClass().getName();
		Class<?> clazz = Class.forName(classType);
		String clazzName = clazz.getName();
		String methodName = joinPoint.getSignature().getName(); // 获取方法名称
		// 获取参数名称和值
		Map<String, Object> nameAndArgs = this.getFieldsName(this.getClass(), clazzName, methodName, args);
		for (Map.Entry<String, Object> entry : nameAndArgs.entrySet()) {
			String objStr = "";
			Object obj = entry.getValue();
			if (obj != null && (obj instanceof Entityable || obj instanceof Serializable)) {
				objStr = JSONObject.toJSONString(obj);
			} else {
				objStr = String.valueOf(entry.getValue());
			}
			sb.append("\t==>参数[" + entry.getKey() + "]:\t" + objStr + "</br>");
		}

		sb.append("return:" + "</br>");
		if (returnResult != null) {
			sb.append(JSONObject.toJSONString(returnResult) + "</br>");
		}

		sb.append("Signature:\t" + joinPoint.getSignature() + "</br>");
		sb.append("--------------------------------------------------" + "</br>");

		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		sb.append("标记为" + tag.get() + "的方法" + method.getName() + "运行消耗" + (System.currentTimeMillis() - time.get())
				+ "ms");

		// 优化系统日志描述
		///////////////////////////////////////////
		StringBuffer osb = new StringBuffer();
		operateUser = SessionUtil.getSessionUser(request);
		String userName = operateUser != null && StringUtils.isNotBlank(operateUser.getUserName())
				? operateUser.getUserName() : "";
		OperateLog log = method.getAnnotation(OperateLog.class);
		SystemLog systemLog = systemLogService.getSystemLogByUids(tag.get());
		switch (log.moudleCode()) {
		case "M01": // 用户登录
			try {
				if (returnResult instanceof Map<?, ?>) {
					Map<?, ?> map = (Map<?, ?>) returnResult;
					Integer code = (Integer) map.get("code");
					boolean isSucceed = ConstantUtil.SuccessInt == code ? true : false;
					if (StringUtils.isBlank(userName)) {
						userName = args.length > 0 ? args[0].toString() : "";
					}
					osb.append("用户" + userName + "系统登录"
							+ (isSucceed ? "成功" : "失败") + ", 登录时间：" + DateTimeUtils.date2StrDateTime(new Date()) + "。");
					if (!isSucceed) {
						osb.append("</br>失败原因 >> ").append(map.get("msg") != null ? map.get("msg").toString() : "");
					}
					sb.delete(0, sb.length());
					sb.append(osb.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "M02": // 用户登出
			try {
				osb.append("用户" + userName + "正常登出系统 , 登出时间：" + DateTimeUtils.date2StrDateTime(new Date()) + "。");
				sb.delete(0, sb.length());
				sb.append(osb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "M03": // 密码修改
			try {
				if (returnResult instanceof MessageOption) {
					MessageOption mo = (MessageOption) returnResult;
					if (mo.isSuccess()) {
						osb.append("用户" + userName + "密码修改成功。");
					} else {
						osb.append("用户" + userName + "密码修改失败。");
						osb.append("</br>失败原因 >> ").append(mo.msg);
					}
					sb.delete(0, sb.length());
					sb.append(osb.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		systemLog.setLogDetails(sb.toString());
		
		// 处理操作是否成功
		if (returnResult instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) returnResult;
			Integer code = null;
			Object obj = map.get("code");
			if (obj instanceof Integer) {
				code = (Integer) obj;
			} else if (obj instanceof String) {
				code = Integer.parseInt((String) obj);
			}
			boolean isSucceed = ConstantUtil.SuccessInt == code ? true : false;
			systemLog.setOperateResult(isSucceed ? ConstantUtil.YesOrNo_YES : ConstantUtil.YesOrNo_NO);
		} else if (returnResult instanceof MessageOption) {
			MessageOption mo = (MessageOption) returnResult;
			systemLog.setOperateResult(mo.isSuccess() ? ConstantUtil.YesOrNo_YES : ConstantUtil.YesOrNo_NO);
		}
		
		///////////////////////////////////////////


		systemLogService.save(systemLog);
	}

	/**
	 * 获取远程客户端Ip
	 * 
	 * @param request
	 * @return
	 */
	private String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * @Description 获取字段名和字段值
	 * @Author 刘俊重
	 * @date 2017年7月6日
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		ClassPool pool = ClassPool.getDefault();
		ClassClassPath classPath = new ClassClassPath(cls);
		pool.insertClassPath(classPath);

		CtClass cc = pool.get(clazzName);
		CtMethod cm = cc.getDeclaredMethod(methodName);
		MethodInfo methodInfo = cm.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		if (attr == null) {
			// exception
		}
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		for (int i = 0; i < cm.getParameterTypes().length; i++) {
			map.put(attr.variableName(i + pos), args[i]);// paramNames即参数名
		}
		return map;
	}

}
