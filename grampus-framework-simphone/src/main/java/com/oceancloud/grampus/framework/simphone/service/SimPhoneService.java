package com.oceancloud.grampus.framework.simphone.service;

/**
 * SimService
 *
 * @author Beck
 * @since 2022-07-25
 */
public interface SimPhoneService {

	/**
	 * 获取手机号
	 *
	 * @param accessToken 获取手机号的AccessToken
	 * @return 手机号
	 */
	String getMobile(String accessToken);
}
