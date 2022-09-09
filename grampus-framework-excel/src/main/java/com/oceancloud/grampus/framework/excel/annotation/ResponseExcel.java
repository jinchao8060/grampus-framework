package com.oceancloud.grampus.framework.excel.annotation;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.oceancloud.grampus.framework.excel.head.HeadGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 导出
 *
 * @author lengleng
 * @since 2021-5-14
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {

	/**
	 * 文件名称
	 */
	String name() default "";

	/**
	 * 文件类型 （xlsx xls）
	 */
	ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;

	/**
	 * 文件密码
	 */
	String password() default "";

	/**
	 * sheet 名称，支持多个
	 */
	String[] sheet() default {"Sheet1"};

	/**
	 * 内存操作
	 */
	boolean inMemory() default false;

	/**
	 * excel 模板
	 */
	String template() default "";

	/**
	 * + 包含字段
	 */
	String[] include() default {};

	/**
	 * 排除字段
	 */
	String[] exclude() default {};

	/**
	 * 拦截器，自定义样式等处理
	 */
	Class<? extends WriteHandler>[] writeHandler() default {};

	/**
	 * 转换器
	 */
	Class<? extends Converter>[] converter() default {};

	/**
	 * 自定义Excel头生成器
	 */
	Class<? extends HeadGenerator> headGenerator() default HeadGenerator.class;

}
