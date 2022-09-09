package com.oceancloud.grampus.framework.signin.test;

import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.core.utils.http.RetrofitClientGenerator;
import com.oceancloud.grampus.framework.signin.client.FacebookOAuthRetrofitClient;
import com.oceancloud.grampus.framework.signin.model.dto.FacebookOAuthSignInInfo;
import com.oceancloud.grampus.framework.signin.properties.FacebookOAuthProperties;
import com.oceancloud.grampus.framework.signin.service.FacebookOAuthSignInService;
import com.oceancloud.grampus.framework.signin.utils.FacebookAdminTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * FacebookOAuthServiceTest
 *
 * @author Beck
 * @since 2022-02-14
 */
@Slf4j
public class FacebookOAuthServiceTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		String clientId = "";
		String clientSecret = "";

		String clientStr = clientId + "|" + clientSecret;
		String clientEncodeStr = URLEncoder.encode(clientStr, StandardCharsets.UTF_8.displayName());

		log.info("{}", clientEncodeStr);
	}

	@Test
	public void test2() {
		FacebookOAuthRetrofitClient facebookOAuthRetrofitClient = RetrofitClientGenerator.createRetrofitClient(FacebookOAuthRetrofitClient.class, "https://graph.facebook.com");

		FacebookOAuthProperties facebookOAuthProperties = new FacebookOAuthProperties();
		facebookOAuthProperties.setClientId("");
		facebookOAuthProperties.setClientSecret("");

		FacebookAdminTokenUtil facebookAdminTokenUtil = new FacebookAdminTokenUtil(facebookOAuthProperties);

		FacebookOAuthSignInService facebookOAuthSocialService = new FacebookOAuthSignInService(facebookAdminTokenUtil, facebookOAuthRetrofitClient);

		String userToken = "";
		FacebookOAuthSignInInfo info = facebookOAuthSocialService.getAuthorizationInfoFromUserToken(userToken);

		log.info("info:{}", JSONUtil.writeValueAsString(info));
	}
}
