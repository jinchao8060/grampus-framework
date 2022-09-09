package com.oceancloud.grampus.framework.signin.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Google第三方授权信息
 *
 * @author Beck
 * @since 2021-07-13
 */
@Getter
@Setter
@ToString
public class GoogleOAuthSignInInfo extends OAuthSignInInfo {
	private static final long serialVersionUID = -6321354616592339845L;
	/**
	 * 是否通过邮箱认证
	 */
	boolean emailVerified;
	/**
	 * 所属语言 zh-CN
	 */
	String locale;
	/**
	 * 姓
	 */
	String familyName;
	/**
	 * 名
	 */
	String givenName;
}
