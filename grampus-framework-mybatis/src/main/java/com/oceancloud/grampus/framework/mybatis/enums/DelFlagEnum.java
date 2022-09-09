package com.oceancloud.grampus.framework.mybatis.enums;

import lombok.Getter;

/**
 * 删除标识枚举
 * Project: grampus
 *
 * @author Beck
 * @since 2020-12-02
 */
public enum DelFlagEnum {
	/**
	 * 正常
	 */
	NORMAL(0),
	/**
	 * 删除
	 */
	DELETE(1);

	@Getter
	private final int value;

	DelFlagEnum(int value) {
		this.value = value;
	}
}
