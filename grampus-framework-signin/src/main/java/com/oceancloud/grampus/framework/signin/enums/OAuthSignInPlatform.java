package com.oceancloud.grampus.framework.signin.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * 第三方授权平台
 *
 * @author Beck
 * @since 2021-07-13
 */
public enum OAuthSignInPlatform {
	/**
	 * 谷歌
	 */
	GOOGLE("google"),
	/**
	 * 脸书
	 */
	FACEBOOK("facebook"),
	/**
	 * 苹果
	 */
	APPLE("apple");

	private static final Map<String, OAuthSignInPlatform> OAUTH_SIGN_IN_PLATFORM_MAP = Maps.newHashMap();

	static {
		for (OAuthSignInPlatform value : OAuthSignInPlatform.values()) {
			OAUTH_SIGN_IN_PLATFORM_MAP.put(value.getPlatform(), value);
		}
	}

	OAuthSignInPlatform(String platform) {
		this.platform = platform;
	}

	@Getter
	private final String platform;

	public static OAuthSignInPlatform convert(String platform) {
		return OAUTH_SIGN_IN_PLATFORM_MAP.get(platform);
	}
}
