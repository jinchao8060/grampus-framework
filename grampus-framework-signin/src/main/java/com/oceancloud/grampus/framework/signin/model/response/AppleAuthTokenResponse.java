package com.oceancloud.grampus.framework.signin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AppleAuthTokenResponse
 *
 * @author Beck
 * @since 2022-07-01
 */
@NoArgsConstructor
@Data
public class AppleAuthTokenResponse {
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("expires_in")
	private Integer expiresIn;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("id_token")
	private String idToken;
}
