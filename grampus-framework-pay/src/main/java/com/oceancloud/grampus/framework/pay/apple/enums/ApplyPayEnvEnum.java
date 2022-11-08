package com.oceancloud.grampus.framework.pay.apple.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ApplyPayEnum
 *
 * @author Beck
 * @since 2022-11-07
 */
@AllArgsConstructor
public enum ApplyPayEnvEnum {
	/**
	 * 生产环境
	 */
	PRODUCTION("Production"),
	/**
	 * 沙盒环境
	 */
	SANDBOX("Sandbox");

	@Getter
	private final String environment;
}
