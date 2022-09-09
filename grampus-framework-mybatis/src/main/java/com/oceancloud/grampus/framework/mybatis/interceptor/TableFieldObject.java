package com.oceancloud.grampus.framework.mybatis.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.mapping.SqlCommandType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * TableField注解标注的实体对象
 *
 * @author Beck
 * @since 2021-04-27
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class TableFieldObject implements Serializable {
	private static final long serialVersionUID = -5900043669148661637L;
	/**
	 * SQL 命令类型(INSERT UPDATE DELETE...)
	 */
	private SqlCommandType sqlCommandType;
	/**
	 * 标注TableField注解的所有变量
	 */
	private List<Field> fields;
	/**
	 * mybatis查询的参数的实体对象
	 */
	private Object paramObj;
}
