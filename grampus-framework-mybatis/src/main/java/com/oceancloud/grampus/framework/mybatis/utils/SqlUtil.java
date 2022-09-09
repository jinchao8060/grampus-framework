package com.oceancloud.grampus.framework.mybatis.utils;

import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.core.utils.StringPool;

/**
 * SQL 工具类
 *
 * @author Beck
 * @since 2020-12-15
 */
public class SqlUtil {

	/**
	 * 下划线字符
	 */
	public static final char UNDERLINE = '_';

	private final static String[] KEYWORDS = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop", "sleep" };

	/**
	 * 验证 order by 语法是否符合规范
	 */
	public static boolean isValidOrderBySql(String value) {
		for (String keyword : KEYWORDS) {
			if (StringUtil.containsIgnoreCase(value, keyword)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符串驼峰转下划线格式
	 *
	 * @param param 需要转换的字符串
	 * @return 转换好的字符串
	 */
	public static String camelToUnderline(String param) {
		if (StringUtil.isBlank(param)) {
			return StringPool.EMPTY;
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c) && i > 0) {
				sb.append(StringPool.UNDERSCORE);
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 字符串下划线转驼峰格式
	 *
	 * @param param 需要转换的字符串
	 * @return 转换好的字符串
	 */
	public static String underlineToCamel(String param) {
		if (StringUtil.isBlank(param)) {
			return StringPool.EMPTY;
		}
		String temp = param.toLowerCase();
		int len = temp.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = temp.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(temp.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 首字母转换小写
	 *
	 * @param param 需要转换的字符串
	 * @return 转换好的字符串
	 */
	public static String firstToLowerCase(String param) {
		if (StringUtil.isBlank(param)) {
			return StringPool.EMPTY;
		}
		return param.substring(0, 1).toLowerCase() + param.substring(1);
	}
}
