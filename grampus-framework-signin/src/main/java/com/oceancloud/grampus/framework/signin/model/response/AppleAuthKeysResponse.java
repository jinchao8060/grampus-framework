package com.oceancloud.grampus.framework.signin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AppleAuthKeysResponse
 *
 * @author Beck
 * @since 2022-07-01
 */
@NoArgsConstructor
@Data
public class AppleAuthKeysResponse {
	@JsonProperty("keys")
	private List<KeysDTO> keys;

	@NoArgsConstructor
	@Data
	public static class KeysDTO {
		@JsonProperty("kty")
		private String kty;
		@JsonProperty("kid")
		private String kid;
		@JsonProperty("use")
		private String use;
		@JsonProperty("alg")
		private String alg;
		@JsonProperty("n")
		private String n;
		@JsonProperty("e")
		private String e;
	}
}
