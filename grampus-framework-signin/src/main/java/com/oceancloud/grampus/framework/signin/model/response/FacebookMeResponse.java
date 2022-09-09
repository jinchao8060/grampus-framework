package com.oceancloud.grampus.framework.signin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FacebookMeResponse
 *
 * @author Beck
 * @since 2022-02-14
 */
@NoArgsConstructor
@Data
public class FacebookMeResponse {
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("email")
	private String email;
	@JsonProperty("picture")
	private PictureDTO picture;

	@NoArgsConstructor
	@Data
	public static class PictureDTO {
		@JsonProperty("data")
		private DataDTO data;

		@NoArgsConstructor
		@Data
		public static class DataDTO {
			@JsonProperty("height")
			private Integer height;
			@JsonProperty("is_silhouette")
			private Boolean isSilhouette;
			@JsonProperty("url")
			private String url;
			@JsonProperty("width")
			private Integer width;
		}
	}
}
