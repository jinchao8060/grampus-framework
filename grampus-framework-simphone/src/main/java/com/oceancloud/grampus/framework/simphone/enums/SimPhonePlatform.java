package com.oceancloud.grampus.framework.simphone.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * SimPhonePlatform
 *
 * @author Beck
 * @since 2022-07-25
 */
@AllArgsConstructor
public enum SimPhonePlatform {

	ALIYUN("aliyun");

	private static final Map<String, SimPhonePlatform> SIM_PHONE_PLATFORM_MAP = Maps.newHashMap();

	static {
		for (SimPhonePlatform value : SimPhonePlatform.values()) {
			SIM_PHONE_PLATFORM_MAP.put(value.getPlatform(), value);
		}
	}

	@Getter
	private final String platform;

	public static SimPhonePlatform convert(String platform) {
		return SIM_PHONE_PLATFORM_MAP.get(platform);
	}

}
