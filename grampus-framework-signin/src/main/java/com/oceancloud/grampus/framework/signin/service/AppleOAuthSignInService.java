package com.oceancloud.grampus.framework.signin.service;

import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.signin.client.AppleOAuthRetrofitClient;
import com.oceancloud.grampus.framework.signin.enums.OAuthSignInPlatform;
import com.oceancloud.grampus.framework.signin.model.dto.AppleOAuthSignInInfo;
import com.oceancloud.grampus.framework.signin.model.response.AppleAuthKeysResponse;
import com.oceancloud.grampus.framework.signin.model.response.AppleAuthTokenResponse;
import com.oceancloud.grampus.framework.signin.properties.AppleOAuthProperties;
import com.oceancloud.grampus.framework.signin.utils.AppleIdTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Apple第三方认证Service
 *
 * @author Beck
 * @since 2022-06-30
 */
@Slf4j
@AllArgsConstructor
public class AppleOAuthSignInService implements OAuthSignInService {

	private final AppleOAuthRetrofitClient appleOAuthRetrofitClient;
	private final AppleOAuthProperties appleOAuthProperties;

	/**
	 * https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
	 */
	@Override
	public AppleOAuthSignInInfo getAuthorizationInfo(String authorizationCode) {
		String clientId = appleOAuthProperties.getClientId();
		String privateKey = appleOAuthProperties.getClientSecret();
		String keyId = appleOAuthProperties.getKeyId();
		String teamId = appleOAuthProperties.getTeamId();
		String redirectUri = appleOAuthProperties.getRedirectUri();

		String clientSecret = AppleIdTokenUtil.genClientSecret(clientId, teamId, keyId, privateKey);
		Call<AppleAuthTokenResponse> authTokenCall = appleOAuthRetrofitClient.authToken(clientId, clientSecret, authorizationCode, "authorization_code", redirectUri);
		String idToken;
		try {
			Response<AppleAuthTokenResponse> authTokenResponse = authTokenCall.execute();

			if (!authTokenResponse.isSuccessful()
					|| authTokenResponse.body() == null) {
				log.error("AppleOAuthSignInService.authToken errorBody={}", authTokenResponse.errorBody().string());
				return null;
			}

			AppleAuthTokenResponse body = authTokenResponse.body();
			idToken = body.getIdToken();
			if (StringUtil.isBlank(idToken)) {
				return null;
			}
		} catch (IOException e) {
			log.error("AppleOAuthSignInService.authToken request error.", e);
			return null;
		}

		return getAuthorizationInfoFromUserToken(idToken);
	}

	/**
	 * https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/authenticating_users_with_sign_in_with_apple
	 * https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/verifying_a_user
	 */
	@Override
	public AppleOAuthSignInInfo getAuthorizationInfoFromUserToken(String idTokenString) {
		// 获取jwt头部kid
		String kid = AppleIdTokenUtil.getHeaderField(idTokenString, "kid");

		// 获取苹果公钥
		AppleAuthKeysResponse.KeysDTO publicKeyDTO;
		Call<AppleAuthKeysResponse> authKeysCall = appleOAuthRetrofitClient.authKeys();
		try {
			Response<AppleAuthKeysResponse> authKeysResponse = authKeysCall.execute();

			if (!authKeysResponse.isSuccessful()
					|| authKeysResponse.body() == null) {
				log.error("AppleOAuthSignInService.authKeys errorBody={}", authKeysResponse.errorBody().string());
				return null;
			}

			AppleAuthKeysResponse body = authKeysResponse.body();
			List<AppleAuthKeysResponse.KeysDTO> keys = body.getKeys();
			Map<String, AppleAuthKeysResponse.KeysDTO> keyMap = keys.stream().collect(Collectors.toMap(
					AppleAuthKeysResponse.KeysDTO::getKid, Function.identity()));

			publicKeyDTO = keyMap.get(kid);
			if (publicKeyDTO == null) {
				return null;
			}
		} catch (IOException e) {
			log.error("AppleOAuthSignInService.authKeys request error.", e);
			return null;
		}

		// 构建公钥
		PublicKey publicKey = AppleIdTokenUtil.buildPublicKey(publicKeyDTO.getN(), publicKeyDTO.getE());

		// 解析identityToken
		Claims claims = AppleIdTokenUtil.parseClaims(idTokenString, publicKey);
		return buildAuthSocialInfo(claims);
	}

	@Override
	public OAuthSignInPlatform platform() {
		return OAuthSignInPlatform.APPLE;
	}

	private AppleOAuthSignInInfo buildAuthSocialInfo(Claims idTokenClaims) {
		AppleOAuthSignInInfo appleOAuthSignInInfo = new AppleOAuthSignInInfo();
		appleOAuthSignInInfo.setUserId(idTokenClaims.getSubject());
		appleOAuthSignInInfo.setEmail(idTokenClaims.get("email", String.class));
		appleOAuthSignInInfo.setName(null);
		appleOAuthSignInInfo.setPictureUrl(null);
		appleOAuthSignInInfo.setEmailVerified(Boolean.parseBoolean(idTokenClaims.get("email_verified", String.class)));
		return appleOAuthSignInInfo;
	}
}
