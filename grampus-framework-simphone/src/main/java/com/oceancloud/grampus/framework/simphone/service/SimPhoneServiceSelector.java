package com.oceancloud.grampus.framework.simphone.service;

import com.google.common.collect.Maps;
import com.oceancloud.grampus.framework.simphone.enums.SimPhonePlatform;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 第三个号码认证策略选择器
 *
 * @author Beck
 * @since 2022-07-25
 */
@UtilityClass
public class SimPhoneServiceSelector {

	private static final Map<SimPhonePlatform, SimPhoneService> CLIENT_MAP = Maps.newConcurrentMap();

	public SimPhoneService getClientInstance(String platform) {
		if (!StringUtils.hasText(platform)) {
			throw new IllegalArgumentException("platform String can not be empty.");
		}
		return CLIENT_MAP.get(SimPhonePlatform.convert(platform));
	}

	public SimPhoneService getClientInstance(SimPhonePlatform platform) {
		return CLIENT_MAP.get(platform);
	}

	@AllArgsConstructor
	@Component
	protected static class AuthSocialClientInitializing implements InitializingBean {

		private final AliyunSimPhoneService aliyunSimPhoneService;

		@Override
		public void afterPropertiesSet() {
			SimPhoneServiceSelector.CLIENT_MAP.put(SimPhonePlatform.ALIYUN, aliyunSimPhoneService);
		}
	}
}
