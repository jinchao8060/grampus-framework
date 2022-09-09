package com.oceancloud.grampus.framework.signin.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.oceancloud.grampus.framework.signin.enums.OAuthSignInPlatform;
import com.oceancloud.grampus.framework.signin.model.dto.GoogleOAuthSignInInfo;
import com.oceancloud.grampus.framework.signin.properties.GoogleOAuthProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Google第三方认证Service
 *
 * @author Beck
 * @since 2021-06-23
 */
@Slf4j
@AllArgsConstructor
public class GoogleOAuthSignInService implements OAuthSignInService {

	private final GoogleOAuthProperties properties;

	/**
	 * https://developers.google.com/identity/sign-in/android/offline-access
	 */
	@Override
	public GoogleOAuthSignInInfo getAuthorizationInfo(String authorizationCode) {
		String clientId = properties.getClientId();
		String clientSecret = properties.getClientSecret();
		if (!StringUtils.hasText(clientId) || !StringUtils.hasText(clientSecret)) {
			throw new IllegalArgumentException("GoogleOAuthProperties clientId or clientSecret is null.");
		}
		GoogleAuthorizationCodeTokenRequest request = new GoogleAuthorizationCodeTokenRequest(
				new NetHttpTransport(), GsonFactory.getDefaultInstance(),
				"https://oauth2.googleapis.com/token",
				clientId, clientSecret, authorizationCode, "");
		try {
			GoogleTokenResponse tokenResponse = request.execute();
			GoogleIdToken idToken = tokenResponse.parseIdToken();
			return buildAuthSocialInfo(idToken.getPayload());
		} catch (IOException e) {
			log.error("GoogleOAuthSignInService.getAuthorizationInfo request error.", e);
		}
		return null;
	}

	/**
	 * https://developers.google.com/identity/gsi/web/guides/verify-google-id-token
	 */
	@Override
	public GoogleOAuthSignInInfo getAuthorizationInfoFromUserToken(String idTokenString) {
		String clientId = properties.getClientId();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
				new NetHttpTransport(), GsonFactory.getDefaultInstance())
				.setAudience(Collections.singletonList(clientId))
				.build();
		try {
			GoogleIdToken idToken = verifier.verify(idTokenString);
			return buildAuthSocialInfo(idToken.getPayload());
		} catch (GeneralSecurityException | IOException e) {
			log.error("GoogleOAuthSignInService.getAuthorizationInfoFromUserToken request error.", e);
		}
		return null;
	}

	@Override
	public OAuthSignInPlatform platform() {
		return OAuthSignInPlatform.GOOGLE;
	}

	private GoogleOAuthSignInInfo buildAuthSocialInfo(GoogleIdToken.Payload payload) {
		GoogleOAuthSignInInfo googleAuthSocialInfo = new GoogleOAuthSignInInfo();
		googleAuthSocialInfo.setUserId(payload.getSubject());
		googleAuthSocialInfo.setEmail(payload.getEmail());
		googleAuthSocialInfo.setEmailVerified(payload.getEmailVerified());
		googleAuthSocialInfo.setName((String) payload.get("name"));
		googleAuthSocialInfo.setPictureUrl((String) payload.get("picture"));
		googleAuthSocialInfo.setLocale((String) payload.get("locale"));
		googleAuthSocialInfo.setFamilyName((String) payload.get("family_name"));
		googleAuthSocialInfo.setGivenName((String) payload.get("given_name"));
		return googleAuthSocialInfo;
	}
}