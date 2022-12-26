package com.oceancloud.grampus.framework.cloud.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oceancloud.grampus.framework.core.utils.date.DatePattern;
import com.oceancloud.grampus.framework.core.utils.date.DateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * SpringMVC入参/响应数据处理
 *
 * @author Beck
 * @since 2021-04-20
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	// ~ ======================= 入参数据处理（时间支持时间戳以及其他格式） ===============================

	/**
	 * 时间类型入参添加支持
	 */
	@Bean
	public ConfigurableWebBindingInitializer configurableWebBindingInitializer(FormattingConversionService mvcConversionService, Validator mvcValidator) {
		ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
		initializer.setConversionService(mvcConversionService);
		initializer.setValidator(mvcValidator);
		// 装配自定义属性编辑器
		initializer.setPropertyEditorRegistrar(propertyEditorRegistry -> {
			// Date 类型转换
			propertyEditorRegistry.registerCustomEditor(Date.class, new PropertyEditorSupport() {
				@Override
				public void setAsText(String text) {
					setValue(parse(text));
				}
			});
			// LocalDate 类型转换
			propertyEditorRegistry.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
				@Override
				public void setAsText(String text) {
					setValue(parse(text).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
			});
			// LocalDateTime 类型转换
			propertyEditorRegistry.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
				@Override
				public void setAsText(String text) {
					setValue(parse(text).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				}
			});
		});
		return initializer;
	}

	private static final String[] PARSE_PATTERNS = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	private static final DateTimeFormatter[] DATE_TIME_FORMATTERS = {
			DatePattern.JDK_DATETIME_FORMAT,
			DatePattern.NORM_DATETIME_FORMAT,
			DatePattern.UTC_FORMAT,
			DatePattern.NORM_DATETIME_MS_FORMAT,
			DatePattern.NORM_DATE_FORMAT,
			DatePattern.NORM_DATETIME_MINUTE_FORMAT
	};

	/**
	 * 日期字符串解析 TODO DateUtil存在缺陷，无法使用yyyy-MM-dd解析2022-12-9
	 */
	private static Date parse(Object dateTimeString) {
		if (dateTimeString == null) {
			return null;
		}
		try {
			long timestamp = Long.parseLong(String.valueOf(dateTimeString));
			return new Date(timestamp);
		} catch (Exception ignore) {
			// do nothing
		}
		for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
			try {
				return DateUtil.parse(dateTimeString.toString(), formatter);
			} catch (Exception ignore) {
				// do nothing
			}
		}
		for (String parsePattern : PARSE_PATTERNS) {
			try {
				return DateUtil.parse(dateTimeString.toString(), parsePattern);
			} catch (Exception ignore) {
				// do nothing
			}
		}
		return null;
	}

	// ~ ======================= 响应数据处理（Long类型转换为String类型、时间统一为时间戳） ===============================

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
