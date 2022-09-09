package com.oceancloud.grampus.framework.signin.service;

import com.google.common.collect.Maps;
import com.oceancloud.grampus.framework.core.utils.spring.SpringContextHolder;
import com.oceancloud.grampus.framework.signin.enums.OAuthSignInPlatform;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 第三方认证策略选择器
 *
 * @author Beck
 * @since 2021-07-14
 */
@UtilityClass
public class OAuthSignInServiceSelector {

	private static final Map<OAuthSignInPlatform, OAuthSignInService> CLIENT_MAP = Maps.newConcurrentMap();

	public OAuthSignInService getClientInstance(String platform) {
		if (!StringUtils.hasText(platform)) {
			throw new IllegalArgumentException("platform String can not be empty.");
		}
		return CLIENT_MAP.get(OAuthSignInPlatform.convert(platform));
	}

	public OAuthSignInService getClientInstance(OAuthSignInPlatform platform) {
		return CLIENT_MAP.get(platform);
	}

	@AllArgsConstructor
	@Component
	protected static class AuthSocialClientInitializing implements InitializingBean {

		private final List<OAuthSignInService> oAuthSignInServiceList;

		@Override
		public void afterPropertiesSet() {
			for (OAuthSignInService oAuthSignInService : oAuthSignInServiceList) {
				OAuthSignInServiceSelector.CLIENT_MAP.put(oAuthSignInService.platform(), oAuthSignInService);
			}
		}
	}
}
