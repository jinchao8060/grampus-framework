package com.oceancloud.grampus.framework.excel.processor;

import java.lang.reflect.Method;

/**
 * @author lengleng
 * @since 2021-5-14
 */
public interface NameProcessor {

	/**
	 * 解析名称
	 *
	 * @param args   拦截器对象
	 * @param method
	 * @param key    表达式
	 * @return
	 */
	String doDetermineName(Object[] args, Method method, String key);
}
