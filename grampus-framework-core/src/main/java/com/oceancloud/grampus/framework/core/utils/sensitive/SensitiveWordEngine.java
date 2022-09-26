package com.oceancloud.grampus.framework.core.utils.sensitive;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词过滤引擎
 *
 * @author Beck
 */
@UtilityClass
public class SensitiveWordEngine {
	/**
	 * 敏感词库
	 */
	@SuppressWarnings("rawtypes")
	public static Map sensitiveWordMap = null;

	/**
	 * 只过滤最小敏感词(识别到敏感词则跳出)
	 */
	public static int minMatchTYpe = 1;

	/**
	 * 过滤所有敏感词(识别到敏感词继续向后执行)
	 */
	public static int maxMatchType = 2;

	/**
	 * 初始化敏感词
	 *
	 * @param keyWordSet 敏感词集合
	 */
	public static void initSensitiveWord(Set<String> keyWordSet) {
		// 初始化HashMap对象并控制容器的大小
		sensitiveWordMap = new HashMap(keyWordSet.size());
		// 敏感词
		String key = null;
		// 用来按照相应的格式保存敏感词库数据
		Map nowMap = null;
		// 用来辅助构建敏感词库
		Map<String, String> newWorMap = null;
		// 使用一个迭代器来循环敏感词集合
		Iterator<String> iterator = keyWordSet.iterator();
		while (iterator.hasNext()) {
			key = iterator.next();
			// 等于敏感词库，HashMap对象在内存中占用的是同一个地址，所以此nowMap对象的变化，sensitiveWordMap对象也会跟着改变
			nowMap = sensitiveWordMap;
			for (int i = 0; i < key.length(); i++) {
				// 截取敏感词当中的字，在敏感词库中字为HashMap对象的Key键值
				char keyChar = key.charAt(i);

				// 判断这个字是否存在于敏感词库中
				Object wordMap = nowMap.get(keyChar);
				if (wordMap != null) {
					nowMap = (Map) wordMap;
				} else {
					newWorMap = new HashMap<String, String>();
					newWorMap.put("isEnd", "0");
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}

				// 如果该字是当前敏感词的最后一个字，则标识为结尾字
				if (i == key.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 敏感词库敏感词数量
	 *
	 * @return 敏感词库敏感词数量
	 */
	public static int getWordSize() {
		if (SensitiveWordEngine.sensitiveWordMap == null) {
			return 0;
		}
		return SensitiveWordEngine.sensitiveWordMap.size();
	}

	/**
	 * 是否包含敏感词
	 *
	 * @param txt       文本信息
	 * @param matchType 匹配类型(1只过滤最小敏感词(识别到敏感词则跳出) 2过滤所有敏感词(识别到敏感词继续向后执行))
	 * @return 是否包含敏感词
	 */
	public static boolean isContainSensitiveWord(String txt, int matchType) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {
			int matchFlag = checkSensitiveWord(txt, i, matchType);
			if (matchFlag > 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取敏感词内容
	 *
	 * @param txt       文本信息
	 * @param matchType 匹配类型（1只过滤最小敏感词(识别到敏感词则跳出) 2过滤所有敏感词(识别到敏感词继续向后执行)）
	 * @return 敏感词内容
	 */
	public static Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<>();
		for (int i = 0; i < txt.length(); i++) {
			int length = checkSensitiveWord(txt, i, matchType);
			if (length > 0) {
				// 将检测出的敏感词保存到集合中
				sensitiveWordList.add(txt.substring(i, i + length));
				i = i + length - 1;
			}
		}
		return sensitiveWordList;
	}

	/**
	 * 替换敏感词
	 *
	 * @param txt         文本信息
	 * @param matchType   匹配类型（1只过滤最小敏感词(识别到敏感词则跳出) 2过滤所有敏感词(识别到敏感词继续向后执行)）
	 * @param replaceChar 敏感词替换字符
	 * @return 脱敏后的文本
	 */
	public static String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 *
	 * @param replaceChar 替换字符
	 * @param length      替换字符的长度
	 * @return 替换字符串
	 */
	private static String getReplaceChars(String replaceChar, int length) {
		// JDK11
		// return replaceChar + replaceChar.repeat(Math.max(0, length - 1));
		StringBuilder resultReplace = new StringBuilder(replaceChar);
		for (int i = 1; i < length; i++) {
			resultReplace.append(replaceChar);
		}
		return resultReplace.toString();
	}

	/**
	 * 检查敏感词数量
	 *
	 * @param txt        文本信息
	 * @param beginIndex 开始检查的字符位置
	 * @param matchType  匹配类型（1只过滤最小敏感词(识别到敏感词则跳出) 2过滤所有敏感词(识别到敏感词继续向后执行)）
	 * @return 敏感词数量
	 */
	@SuppressWarnings("rawtypes")
	public static int checkSensitiveWord(String txt, int beginIndex, int matchType) {
		boolean flag = false;
		// 记录敏感词数量
		int matchFlag = 0;
		char word = 0;
		Map nowMap = SensitiveWordEngine.sensitiveWordMap;
		for (int i = beginIndex; i < txt.length(); i++) {
			word = txt.charAt(i);
			// 判断该字是否存在于敏感词库中
			nowMap = (Map) nowMap.get(word);
			if (nowMap != null) {
				matchFlag++;
				// 判断是否是敏感词的结尾字，如果是结尾字则判断是否继续检测
				if ("1".equals(nowMap.get("isEnd"))) {
					flag = true;
					// 判断过滤类型，如果是小过滤则跳出循环，否则继续循环
					if (SensitiveWordEngine.minMatchTYpe == matchType) {
						break;
					}
				}
			} else {
				break;
			}
		}
		if (!flag) {
			matchFlag = 0;
		}
		return matchFlag;
	}

}