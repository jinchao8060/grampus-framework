package com.oceancloud.grampus.framework.oauth2.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * OAuth2异常序列化
 *
 * @author Beck
 * @since 2021-08-10
 */
public class EnhancedOAuth2ExceptionSerializer extends StdSerializer<EnhancedOAuth2Exception> {
	private static final long serialVersionUID = -1264918675825874490L;
	private static final String DEFAULT_ERROR_PHRASE = "Failure";
	private static final int ERROR_STATUS_CODE = 500;
	private static final int UNAUTHORIZED = 401;

	public EnhancedOAuth2ExceptionSerializer() {
		super(EnhancedOAuth2Exception.class);
	}

	@Override
	public void serialize(EnhancedOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		int status = ERROR_STATUS_CODE;
		if (e instanceof AuthException.TokenExpiredException
				|| e instanceof AuthException.TokenParsedException) {
			status = UNAUTHORIZED;
		}
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("status", status);
		jsonGenerator.writeObjectField("data", e.getMessage());
		jsonGenerator.writeStringField("errorCode", e.getCode());
		jsonGenerator.writeStringField("message", DEFAULT_ERROR_PHRASE);
		jsonGenerator.writeEndObject();
	}
}
