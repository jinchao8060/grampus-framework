package com.oceancloud.grampus.framework.signin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * GoogleOAuthProperties
 *
 * @author Beck
 * @since 2021-07-13
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "oauth.google")
public class GoogleOAuthProperties implements Serializable {
	private static final long serialVersionUID = 933155913441852998L;
	/**
	 * clientId
	 */
	private String clientId;
	/**
	 * clientSecret
	 */
	private String clientSecret;
}
