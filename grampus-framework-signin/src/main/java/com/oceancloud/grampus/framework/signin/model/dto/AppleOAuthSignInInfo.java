package com.oceancloud.grampus.framework.signin.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Apple第三方授权信息
 *
 * @author Beck
 * @since 2022-07-01
 */
@Getter
@Setter
@ToString
public class AppleOAuthSignInInfo extends OAuthSignInInfo {
	private static final long serialVersionUID = -3598530875407088571L;
	/**
	 * 是否通过邮箱认证
	 */
	boolean emailVerified;
}
