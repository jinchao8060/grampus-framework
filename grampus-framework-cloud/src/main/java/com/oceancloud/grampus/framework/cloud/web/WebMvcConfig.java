package com.oceancloud.grampus.framework.cloud.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.time.*;
import java.util.List;

/**
 * SpringMVC响应数据处理（Long类型转换为String类型、时间统一为时间戳）
 *
 * @author Beck
 * @since 2021-04-20
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new AllEncompassingFormHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());
		converters.add(jackson2HttpMessageConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
				new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		// 统一时间戳,忽略未知属性
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(buildSimpleModule());
		jackson2HttpMessageConverter.setObjectMapper(objectMapper);
		return jackson2HttpMessageConverter;
	}

	public static SimpleModule buildSimpleModule() {
		SimpleModule simpleModule = new SimpleModule();

		// ~ ======================= Long类型序列化规则 ===============================
		// Long类型转换为String类型(兼容JS长整数精度丢失问题)
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

		// ~ ======================= 时间序列化规则 ===============================
		simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeToTimestampSerializer());
		simpleModule.addSerializer(LocalDate.class, new LocalDateToTimestampSerializer());

		// ~ ======================= 时间反序列化规则 ==============================
		simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeToTimestampDeserializer());
		simpleModule.addDeserializer(LocalDate.class, new LocalDateToTimestampDeserializer());

		return simpleModule;
	}

	public static class LocalDateTimeToTimestampSerializer extends JsonSerializer<LocalDateTime> {
		@Override
		public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			if (value != null) {
				long timestamp = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
				gen.writeNumber(timestamp);
			}
		}
	}

	public static class LocalDateToTimestampSerializer extends JsonSerializer<LocalDate> {
		@Override
		public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			if (value != null) {
				long timestamp = value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
				gen.writeNumber(timestamp);
			}
		}
	}

	public static class LocalDateTimeToTimestampDeserializer extends JsonDeserializer<LocalDateTime> {
		@Override
		public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			long timestamp = p.getValueAsLong();
			if (timestamp > 0) {
				return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
			} else {
				return null;
			}
		}
	}

	public static class LocalDateToTimestampDeserializer extends JsonDeserializer<LocalDate> {
		@Override
		public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			long timestamp = p.getValueAsLong();
			if (timestamp > 0) {
				return LocalDate.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
			} else {
				return null;
			}
		}
	}
}
