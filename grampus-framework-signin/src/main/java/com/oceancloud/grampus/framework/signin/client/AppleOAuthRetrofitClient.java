package com.oceancloud.grampus.framework.signin.client;

import com.oceancloud.grampus.framework.signin.model.response.AppleAuthKeysResponse;
import com.oceancloud.grampus.framework.signin.model.response.AppleAuthTokenResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * AppleOAuthRetrofitClient
 *
 * @author Beck
 * @since 2022-07-01
 */
public interface AppleOAuthRetrofitClient {

	/**
	 * 获取公钥
	 *
	 * @return 用户信息
	 */
	@GET("/auth/keys")
	Call<AppleAuthKeysResponse> authKeys();

	/**
	 * 获取令牌 idToken
	 *
	 * @param client_id     APP ID
	 * @param client_secret JWT秘钥
	 * @param code          授权码
	 * @param grant_type    authorization_code
	 * @param redirect_uri  重定向URI，需要与客户端/WEB生成授权码时使用的一致，WEB的重定向需要配置identifiers的services Id
	 * @return 令牌信息
	 */
	@POST("/auth/token")
	Call<AppleAuthTokenResponse> authToken(@Query("client_id") String client_id,
										   @Query("client_secret") String client_secret,
										   @Query("code") String code,
										   @Query("grant_type") String grant_type,
										   @Query("redirect_uri") String redirect_uri);
}
