package com.oceancloud.grampus.framework.excel.write;

import com.oceancloud.grampus.framework.excel.annotation.ResponseExcel;

import javax.servlet.http.HttpServletResponse;

/**
 * sheet 写出处理器
 * <p>
 * @author Beck
 * @since 2021-5-14
 */
public interface SheetWriteHandler {

	/**
	 * 是否支持
	 *
	 * @param obj
	 * @return
	 */
	boolean support(Object obj);

	/**
	 * 校验
	 *
	 * @param responseExcel 注解
	 */
	void check(ResponseExcel responseExcel);

	/**
	 * 返回的对象
	 *
	 * @param o             obj
	 * @param response      输出对象
	 * @param responseExcel 注解
	 */
	void export(Object o, HttpServletResponse response, ResponseExcel responseExcel);

	/**
	 * 写成对象
	 *
	 * @param o             obj
	 * @param response      输出对象
	 * @param responseExcel 注解
	 */
	void write(Object o, HttpServletResponse response, ResponseExcel responseExcel);
}
