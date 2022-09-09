package com.oceancloud.grampus.framework.mybatis.handler;

import com.oceancloud.grampus.framework.mybatis.interceptor.TableFieldObject;

/**
 * 字段填充处理器
 *
 * @author Beck
 * @since 2021-04-26
 */
@FunctionalInterface
public interface FieldFillHandler {

	/**
	 * 填充字段
	 *
	 * @param tableFieldObject TableField注解标注的实体对象信息
	 */
	void fill(TableFieldObject tableFieldObject);
}
