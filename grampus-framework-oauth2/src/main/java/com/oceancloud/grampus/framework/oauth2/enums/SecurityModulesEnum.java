package com.oceancloud.grampus.framework.oauth2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SecurityModulesEnum
 *
 * @author Beck
 * @since 2021-08-14
 */
@AllArgsConstructor
public enum SecurityModulesEnum {

	/**
	 * System模块
	 */
	SYSTEM("system");

	@Getter
	private final String module;

	public static SecurityModulesEnum convert(String module) {
		for (SecurityModulesEnum modulesEnum : SecurityModulesEnum.values()) {
			if (modulesEnum.getModule().equals(module)) {
				return modulesEnum;
			}
		}
		return null;
	}
}
