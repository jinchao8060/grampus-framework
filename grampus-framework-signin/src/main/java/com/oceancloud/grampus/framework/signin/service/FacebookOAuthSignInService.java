package com.oceancloud.grampus.framework.signin.service;

import com.oceancloud.grampus.framework.signin.client.FacebookOAuthRetrofitClient;
import com.oceancloud.grampus.framework.signin.enums.OAuthSignInPlatform;
import com.oceancloud.grampus.framework.signin.model.dto.FacebookOAuthSignInInfo;
import com.oceancloud.grampus.framework.signin.model.response.FacebookDebugTokenResponse;
import com.oceancloud.grampus.framework.signin.model.response.FacebookMeResponse;
import com.oceancloud.grampus.framework.signin.utils.FacebookAdminTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Facebook第三方认证Service
 *
 * @author Beck
 * @since 2022-02-11
 */
@Slf4j
@AllArgsConstructor
public class FacebookOAuthSignInService implements OAuthSignInService {

	private final FacebookAdminTokenUtil facebookAdminTokenUtil;
	private final FacebookOAuthRetrofitClient facebookOAuthRetrofitClient;

	@Override
	public FacebookOAuthSignInInfo getAuthorizationInfo(String authorizationCode) {
		return null;
	}

	/**
	 * https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow
	 */
	@Override
	public FacebookOAuthSignInInfo getAuthorizationInfoFromUserToken(String userToken) {
		String adminToken = facebookAdminTokenUtil.genAccessToken();
		Call<FacebookDebugTokenResponse> debugTokenCall = facebookOAuthRetrofitClient.debugToken(userToken, adminToken);
		Call<FacebookMeResponse> meCall = facebookOAuthRetrofitClient.me("id,name,email,picture.width(400)", userToken);
		try {
			Response<FacebookDebugTokenResponse> debugTokenResponse = debugTokenCall.execute();

			if (!debugTokenResponse.isSuccessful()
					|| debugTokenResponse.body() == null
					|| !debugTokenResponse.body().getData().getIsValid()) {
				log.error("FacebookOAuthSignInService.debugToken errorBody={}", debugTokenResponse.errorBody().string());
				return null;
			}

			Response<FacebookMeResponse> meResponse = meCall.execute();
			if (!meResponse.isSuccessful() || meResponse.body() == null) {
				log.error("FacebookOAuthSignInService.me errorBody={}", meResponse.errorBody().string());
				return null;
			}
			return buildAuthSocialInfo(meResponse.body());
		} catch (IOException e) {
			log.error("FacebookOAuthSignInService.getAuthorizationInfoFromUserToken request error.", e);
		}
		return null;
	}

	@Override
	public OAuthSignInPlatform platform() {
		return OAuthSignInPlatform.FACEBOOK;
	}

	private FacebookOAuthSignInInfo buildAuthSocialInfo(FacebookMeResponse facebookMeResponse) {
		FacebookOAuthSignInInfo facebookOauthSignInInfo = new FacebookOAuthSignInInfo();
		facebookOauthSignInInfo.setUserId(facebookMeResponse.getId());
		facebookOauthSignInInfo.setEmail(facebookMeResponse.getEmail());
		facebookOauthSignInInfo.setName(facebookMeResponse.getName());
		facebookOauthSignInInfo.setPictureUrl(facebookMeResponse.getPicture().getData().getUrl());
		return facebookOauthSignInInfo;
	}
}
