package com.oceancloud.grampus.framework.signin.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Facebook第三方授权信息
 *
 * @author Beck
 * @since 2022-02-11
 */
@Getter
@Setter
@ToString
public class FacebookOAuthSignInInfo extends OAuthSignInInfo {
	private static final long serialVersionUID = -1721585070771852341L;
}
