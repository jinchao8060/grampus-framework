package com.oceancloud.grampus.framework.signin.test;

import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.core.utils.http.RetrofitClientGenerator;
import com.oceancloud.grampus.framework.signin.client.AppleOAuthRetrofitClient;
import com.oceancloud.grampus.framework.signin.model.dto.AppleOAuthSignInInfo;
import com.oceancloud.grampus.framework.signin.properties.AppleOAuthProperties;
import com.oceancloud.grampus.framework.signin.service.AppleOAuthSignInService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * AppleJwtTest
 *
 * @author Beck
 * @since 2022-06-30
 */
@Slf4j
public class AppleOAuthServiceTest {

	@Test
	public void testCodeModel() {
		AppleOAuthSignInService appleOAuthSocialService = buildAppleOAuthSocialService();

		String code = "";

		AppleOAuthSignInInfo appleOAuthSignInInfo1 = appleOAuthSocialService.getAuthorizationInfo(code);

		log.info("{}", JSONUtil.writeValueAsString(appleOAuthSignInInfo1));
	}

	@Test
	public void testIdTokenModel() {
		AppleOAuthSignInService appleOAuthSocialService = buildAppleOAuthSocialService();

		String idToken = "";

		String expireIdToken = "";

		AppleOAuthSignInInfo appleOAuthSignInInfo1 = appleOAuthSocialService.getAuthorizationInfoFromUserToken(idToken);

		AppleOAuthSignInInfo appleOAuthSignInInfo2 = appleOAuthSocialService.getAuthorizationInfoFromUserToken(expireIdToken);

		log.info("{}", JSONUtil.writeValueAsString(appleOAuthSignInInfo1));
		log.info("{}", JSONUtil.writeValueAsString(appleOAuthSignInInfo2));
	}

	public static AppleOAuthSignInService buildAppleOAuthSocialService() {
		AppleOAuthProperties appleOAuthProperties = new AppleOAuthProperties();
		appleOAuthProperties.setClientId("");
		appleOAuthProperties.setClientSecret("");
		appleOAuthProperties.setTeamId("");
		appleOAuthProperties.setKeyId("");
		appleOAuthProperties.setRedirectUri("");

		AppleOAuthSignInService appleOAuthSocialService = new AppleOAuthSignInService(
			RetrofitClientGenerator.createRetrofitClient(AppleOAuthRetrofitClient.class, "https://appleid.apple.com"), appleOAuthProperties);

		return appleOAuthSocialService;
	}
}
