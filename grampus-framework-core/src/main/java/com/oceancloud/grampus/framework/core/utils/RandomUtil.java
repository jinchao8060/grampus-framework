package com.oceancloud.grampus.framework.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;

import java.security.SecureRandom;

/**
 * 随机数工具
 *
 * @author Beck
 * @since 2021-09-30
 */
@UtilityClass
public class RandomUtil {

	/**
	 * SECURE_RANDOM
	 */
	public static final SecureRandom SECURE_RANDOM = new SecureRandom();

	/**
	 * 随机字符串因子
	 */
	private static final String NUMBER_STR = "0123456789";
	private static final String UPPER_CHAR_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER_CHAR_STR = "abcdefghijklmnopqrstuvwxyz";
	private static final String ALL_STR = NUMBER_STR + UPPER_CHAR_STR + LOWER_CHAR_STR;

	/**
	 * 随机字符串生成（a-zA-Z0-9）
	 *
	 * @param count 字符长度
	 * @return 随机数
	 */
	public static String randomString(int count) {
		return randomString(count, ALL_STR);
	}

	/**
	 * 随机字符串生成（A-Z）
	 *
	 * @param count 字符长度
	 * @return 随机数
	 */
	public static String randomStringUpper(int count) {
		return randomString(count, UPPER_CHAR_STR);
	}

	/**
	 * 随机字符串生成（a-z）
	 *
	 * @param count 字符长度
	 * @return 随机数
	 */
	public static String randomStringLower(int count) {
		return randomString(count, LOWER_CHAR_STR);
	}

	/**
	 * 随机字符串生成（0-9）
	 *
	 * @param count 字符长度
	 * @return 随机数
	 */
	public static String randomStringNumber(int count) {
		return randomString(count, NUMBER_STR);
	}

	/**
	 * 随机字符串生成
	 *
	 * @param length     字符串的长度
	 * @param baseString 随机字符串因子
	 * @return 随机数
	 */
	public static String randomString(int length, String baseString) {
		if (length == 0 || StringUtil.isBlank(baseString)) {
			return StringPool.EMPTY;
		}
		Assert.isTrue(length > 0, "Requested random string length " + length + " is less than 0.");
		char[] buffer = new char[length];
		int baseLength = baseString.length();
		for (int i = 0; i < length; i++) {
			int index = SECURE_RANDOM.nextInt(baseLength);
			buffer[i] = baseString.charAt(index);
		}
		return new String(buffer);
	}
}
