package com.oceancloud.grampus.framework.signin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * FacebookDebugTokenResponse
 *
 * @author Beck
 * @since 2022-02-14
 */
@NoArgsConstructor
@Data
public class FacebookDebugTokenResponse {
	@JsonProperty("data")
	private DataDTO data;

	@NoArgsConstructor
	@Data
	public static class DataDTO {
		@JsonProperty("app_id")
		private Long appId;
		@JsonProperty("type")
		private String type;
		@JsonProperty("application")
		private String application;
		@JsonProperty("expires_at")
		private Integer expiresAt;
		@JsonProperty("is_valid")
		private Boolean isValid;
		@JsonProperty("issued_at")
		private Integer issuedAt;
		@JsonProperty("metadata")
		private MetadataDTO metadata;
		@JsonProperty("scopes")
		private List<String> scopes;
		@JsonProperty("user_id")
		private String userId;

		@NoArgsConstructor
		@Data
		public static class MetadataDTO {
			@JsonProperty("sso")
			private String sso;
		}
	}
}
