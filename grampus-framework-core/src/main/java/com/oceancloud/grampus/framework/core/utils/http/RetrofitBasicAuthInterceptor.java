package com.oceancloud.grampus.framework.core.utils.http;

import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Retrofit Basic认证拦截器
 *
 * @author Beck
 * @since 2021-09-17
 */
@AllArgsConstructor
public class RetrofitBasicAuthInterceptor implements Interceptor {
	private final String key;
	private final String secret;

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request original = chain.request();

		String credentials = key + ":" + secret;
		final String basic = "Basic " + Base64Utils.encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

		Request.Builder requestBuilder = original.newBuilder()
			.header("Authorization", basic)
			.header("Accept", "application/json")
			.method(original.method(), original.body());

		Request request = requestBuilder.build();
		return chain.proceed(request);
	}
}
