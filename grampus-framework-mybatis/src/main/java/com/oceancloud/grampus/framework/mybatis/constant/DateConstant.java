package com.oceancloud.grampus.framework.mybatis.constant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 数据库日期相关常量
 *
 * @author Beck
 * @since 2022-09-08
 */
public interface DateConstant {
	/**
	 * 最大日期
	 */
	LocalDateTime MAX_LOCAL_DATETIME = LocalDateTime.of(9999, 12, 31, 23, 59, 59, 999_000_000);
	/**
	 * 最小日期
	 */
	LocalDateTime MIN_LOCAL_DATETIME = LocalDateTime.of(1000, 1, 1, 0, 0, 0, 0);
	/**
	 * 最大日期
	 */
	Date MAX_DATE = Date.from(MAX_LOCAL_DATETIME.atZone(ZoneId.systemDefault()).toInstant());
	/**
	 * 最小日期
	 */
	Date MIN_DATE = Date.from(MIN_LOCAL_DATETIME.atZone(ZoneId.systemDefault()).toInstant());
}
