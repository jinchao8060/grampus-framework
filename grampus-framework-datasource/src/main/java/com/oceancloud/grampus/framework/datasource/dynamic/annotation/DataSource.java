package com.oceancloud.grampus.framework.datasource.dynamic.annotation;

import com.oceancloud.grampus.framework.datasource.dynamic.enums.DataSourceType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多数据源注解
 * Project: grampus
 *
 * @author Beck
 * @since 2020-12-02
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
	DataSourceType value() default DataSourceType.MASTER;
}