package com.oceancloud.grampus.framework.signin.utils;

import com.oceancloud.grampus.framework.signin.properties.FacebookOAuthProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

/**
 * FacebookAdminTokenUtil
 *
 * @author Beck
 * @since 2022-02-14
 */
@AllArgsConstructor
@Component
public class FacebookAdminTokenUtil {

	private final FacebookOAuthProperties facebookOAuthProperties;

	public String genAccessToken() {
		String clientId = facebookOAuthProperties.getClientId();
		String clientSecret = facebookOAuthProperties.getClientSecret();
		return UriUtils.encode(clientId + "|" + clientSecret, StandardCharsets.UTF_8);
	}
}
