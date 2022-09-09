package com.oceancloud.grampus.framework.signin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * AppleOAuthProperties
 * <p>
 * 该配置类，仅在需要使用AppleOAuthSocialInfo getAuthorizationInfo(String authorizationCode)时，才需要配置
 *
 * @author Beck
 * @since 2022-06-30
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "oauth.apple")
public class AppleOAuthProperties implements Serializable {
	private static final long serialVersionUID = 5891968797464684964L;
	/**
	 * APP ID
	 */
	private String clientId;
	/**
	 * Keys 服务端访问秘钥
	 */
	private String clientSecret;
	/**
	 * App Team ID
	 */
	private String teamId;
	/**
	 * 服务端访问秘钥 Key ID
	 */
	private String keyId;
	/**
	 * 重定向URI
	 */
	private String redirectUri;
}
