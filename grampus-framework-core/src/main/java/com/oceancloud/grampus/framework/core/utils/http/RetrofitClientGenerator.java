package com.oceancloud.grampus.framework.core.utils.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oceancloud.grampus.framework.core.utils.CollectionUtil;
import lombok.experimental.UtilityClass;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;

/**
 * RetrofitClientGenerator
 *
 * @author Beck
 * @since 2021-09-17
 */
@UtilityClass
public class RetrofitClientGenerator {

	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public <S> S createRetrofitClient(Class<S> serviceClass, String baseUrl) {
		return createRetrofitClient(serviceClass, null, baseUrl);
	}

    public <S> S createRetrofitClient(Class<S> serviceClass, List<Interceptor> interceptors, String baseUrl) {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

		if (CollectionUtil.isNotEmpty(interceptors)) {
			for (Interceptor interceptor : interceptors) {
				clientBuilder.addInterceptor(interceptor);
			}
		}

        OkHttpClient client = clientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(baseUrl)
			.client(client)
			.addConverterFactory(JacksonConverterFactory.create(objectMapper))
			.build();
        return retrofit.create(serviceClass);
    }
}