package com.oceancloud.grampus.framework.gray.config;

import com.oceancloud.grampus.framework.gray.feign.GrayRequestInterceptor;
import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GrayRequestInterceptorAutoConfiguration
 *
 * @author Beck
 * @since 2021-07-01
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class GrayFeignInterceptorAutoConfiguration {

	@Bean
	public RequestInterceptor grayRequestInterceptor() {
		return new GrayRequestInterceptor();
	}
}
