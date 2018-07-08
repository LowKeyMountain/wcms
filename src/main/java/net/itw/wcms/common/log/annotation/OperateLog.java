package net.itw.wcms.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {
	/**
	 * 操作类型描述
	 * 
	 * @return
	 */
	String operateTypeDesc() default "";

	/**
	 * 操作类型
	 * 
	 * @return
	 */
	long operateType() default -1;

	/**
	 * 模块编码
	 * 
	 * @return
	 */
	String moudleCode() default "M30";

	/**
	 * 模块名称
	 * 
	 * @return
	 */
	String moudleName() default "XX模块";

	/**
	 * 业务类型
	 * 
	 * @return
	 */
	String bussType() default "";

	/**
	 * 业务类型描述
	 * 
	 * @return
	 */
	String bussTypeDesc() default "";
}
