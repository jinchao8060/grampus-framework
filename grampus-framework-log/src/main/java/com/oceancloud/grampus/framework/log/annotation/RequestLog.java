package com.oceancloud.grampus.framework.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求日志注解
 *
 * @author Beck
 * @since 2021-05-27
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RequestLog {

	/**
	 * @return 描述
	 */
	String value();
}
