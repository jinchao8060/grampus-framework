package com.oceancloud.grampus.framework.signin.config;

import com.oceancloud.grampus.framework.core.utils.http.RetrofitClientGenerator;
import com.oceancloud.grampus.framework.signin.client.AppleOAuthRetrofitClient;
import com.oceancloud.grampus.framework.signin.client.FacebookOAuthRetrofitClient;
import com.oceancloud.grampus.framework.signin.properties.AppleOAuthProperties;
import com.oceancloud.grampus.framework.signin.properties.FacebookOAuthProperties;
import com.oceancloud.grampus.framework.signin.properties.GoogleOAuthProperties;
import com.oceancloud.grampus.framework.signin.service.AppleOAuthSignInService;
import com.oceancloud.grampus.framework.signin.service.FacebookOAuthSignInService;
import com.oceancloud.grampus.framework.signin.service.GoogleOAuthSignInService;
import com.oceancloud.grampus.framework.signin.utils.FacebookAdminTokenUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * OAuthSignInAutoConfiguration
 *
 * @author Beck
 * @since 2021-07-14
 */
@EnableConfigurationProperties({GoogleOAuthProperties.class, FacebookOAuthProperties.class, AppleOAuthProperties.class})
public class OAuthSignInAutoConfiguration {

	@Bean
	public GoogleOAuthSignInService googleOAuthSocialClient(GoogleOAuthProperties properties) {
		return new GoogleOAuthSignInService(properties);
	}

	@Bean
	public FacebookOAuthSignInService facebookOAuthSocialClient(FacebookAdminTokenUtil facebookAdminTokenUtil,
																FacebookOAuthRetrofitClient facebookOAuthRetrofitClient) {
		return new FacebookOAuthSignInService(facebookAdminTokenUtil, facebookOAuthRetrofitClient);
	}

	@Bean
	public AppleOAuthSignInService appleOAuthSocialService(AppleOAuthRetrofitClient appleOAuthRetrofitClient, AppleOAuthProperties appleOAuthProperties) {
		return new AppleOAuthSignInService(appleOAuthRetrofitClient, appleOAuthProperties);
	}

	@Bean
	public FacebookOAuthRetrofitClient facebookOAuthRetrofitClient() {
		return RetrofitClientGenerator.createRetrofitClient(FacebookOAuthRetrofitClient.class, "https://graph.facebook.com");
	}

	@Bean
	public AppleOAuthRetrofitClient appleOAuthRetrofitClient() {
		return RetrofitClientGenerator.createRetrofitClient(AppleOAuthRetrofitClient.class, "https://appleid.apple.com");
	}
}
