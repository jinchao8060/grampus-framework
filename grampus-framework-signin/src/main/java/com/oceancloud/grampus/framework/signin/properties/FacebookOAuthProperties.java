package com.oceancloud.grampus.framework.signin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * FacebookOAuthProperties
 *
 * @author Beck
 * @since 2022-02-11
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "oauth.facebook")
public class FacebookOAuthProperties implements Serializable {
	private static final long serialVersionUID = -7686650044638619980L;
	/**
	 * clientId
	 */
	private String clientId;
	/**
	 * clientSecret
	 */
	private String clientSecret;
}
