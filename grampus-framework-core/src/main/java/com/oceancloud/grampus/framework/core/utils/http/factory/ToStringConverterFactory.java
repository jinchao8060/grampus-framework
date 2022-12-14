package com.oceancloud.grampus.framework.core.utils.http.factory;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * ToStringConverterFactory
 *
 * @author Beck
 * @since 2022-08-22
 */
public class ToStringConverterFactory extends Converter.Factory {
	static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

	public static ToStringConverterFactory create() {
		return new ToStringConverterFactory();
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		if (String.class.equals(type)) {
			return (Converter<ResponseBody, String>) value -> value.string();
		}
		return null;
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		if (String.class.equals(type)) {
			return (Converter<String, RequestBody>) value -> RequestBody.create(MEDIA_TYPE, value);
		}
		return null;
	}
}