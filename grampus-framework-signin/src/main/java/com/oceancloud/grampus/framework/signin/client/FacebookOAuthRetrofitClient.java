package com.oceancloud.grampus.framework.signin.client;

import com.oceancloud.grampus.framework.signin.model.response.FacebookDebugTokenResponse;
import com.oceancloud.grampus.framework.signin.model.response.FacebookMeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * FacebookOAuthRetrofitClient
 *
 * @author Beck
 * @since 2022-02-14
 */
public interface FacebookOAuthRetrofitClient {

	/**
	 * 检查访问口令
	 *
	 * @param inputToken  您需要检查的口令
	 * @param accessToken 应用访问口令，或者应用开发者的访问口令。值为 {Your AppId}|{Your AppSecret} 后进行urlencode
	 * @return 检查结果
	 */
	@GET("/v13.0/debug_token")
	Call<FacebookDebugTokenResponse> debugToken(@Query("input_token") String inputToken, @Query("access_token") String accessToken);

	/**
	 * 获取用户信息
	 *
	 * @param fields      需要查询用户信息的字段
	 * @param accessToken 用户Token
	 * @return 用户信息
	 */
	@GET("/v13.0/me")
	Call<FacebookMeResponse> me(@Query("fields") String fields, @Query("access_token") String accessToken);
}
