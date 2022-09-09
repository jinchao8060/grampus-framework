package com.oceancloud.grampus.framework.signin.service;

import com.oceancloud.grampus.framework.signin.enums.OAuthSignInPlatform;
import com.oceancloud.grampus.framework.signin.model.dto.OAuthSignInInfo;

/**
 * 第三方认证Service
 *
 * @author Beck
 * @since 2021-06-23
 */
public interface OAuthSignInService {
	/**
	 * 获取授权信息(Google)
	 *
	 * @param authorizationCode 授权码
	 * @return 授权信息
	 */
	OAuthSignInInfo getAuthorizationInfo(String authorizationCode);

	/**
	 * 获取授权信息(Google\Facebook\Apple)
	 *
	 * @param userToken 用户Token (Google id_token/Facebook access_token)
	 * @return 授权信息
	 */
	OAuthSignInInfo getAuthorizationInfoFromUserToken(String userToken);

	/**
	 * 所属授权平台
	 *
	 * @return 授权平台
	 */
	OAuthSignInPlatform platform();
}
