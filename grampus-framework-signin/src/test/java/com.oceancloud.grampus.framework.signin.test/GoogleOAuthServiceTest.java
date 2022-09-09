package com.oceancloud.grampus.framework.signin.test;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * GoogleOAuthClientTest
 *
 * @author Beck
 * @since 2021-06-24
 */
@Slf4j
public class GoogleOAuthServiceTest {

	private static final String CLIENT_ID = "";
	private static final String CLIENT_SECRET = "";
	private static final String REDIRECT_URI = "";

	@Test
	public void test() throws IOException {
		String authCode = "";
		GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
				new NetHttpTransport(), GsonFactory.getDefaultInstance(),
				"https://oauth2.googleapis.com/token",
				CLIENT_ID, CLIENT_SECRET,
				authCode, REDIRECT_URI)  // Specify the same redirect URI that you use with your web
				// app. If you don't have a web version of your app, you can
				// specify an empty string.
				.execute();
		// Get profile info from ID token
		GoogleIdToken idToken = tokenResponse.parseIdToken();
		GoogleIdToken.Payload payload = idToken.getPayload();
		String userId = payload.getSubject();  // Use this value as a key to identify a user.
		String email = payload.getEmail();
		boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		String name = (String) payload.get("name");
		String pictureUrl = (String) payload.get("picture");
		String locale = (String) payload.get("locale");
		String familyName = (String) payload.get("family_name");
		String givenName = (String) payload.get("given_name");
		log.debug("userId:{} email:{} emailVerified:{} name:{} pictureUrl:{} locale:{} familyName:{} givenName:{}",
			userId, email, emailVerified, name, pictureUrl, locale, familyName, givenName);
	}

	@Test
	public void testIdToken() throws GeneralSecurityException, IOException {
		String idTokenString = "";
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
			new NetHttpTransport(), GsonFactory.getDefaultInstance())
			.setAudience(Collections.singletonList(CLIENT_ID))
			.build();
		GoogleIdToken idToken = verifier.verify(idTokenString);
		GoogleIdToken.Payload payload = idToken.getPayload();
		String userId = payload.getSubject();  // Use this value as a key to identify a user.
		String email = payload.getEmail();
		boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		String name = (String) payload.get("name");
		String pictureUrl = (String) payload.get("picture");
		String locale = (String) payload.get("locale");
		String familyName = (String) payload.get("family_name");
		String givenName = (String) payload.get("given_name");
		log.debug("userId:{} email:{} emailVerified:{} name:{} pictureUrl:{} locale:{} familyName:{} givenName:{}",
			userId, email, emailVerified, name, pictureUrl, locale, familyName, givenName);
	}
}
