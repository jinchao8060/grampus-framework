package com.oceancloud.grampus.framework.cloud.config;

import com.oceancloud.grampus.framework.cloud.feign.LogRequestInterceptor;
import com.oceancloud.grampus.framework.cloud.feign.SentinelDegradeRuleInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LogRequestAutoConfiguration
 *
 * @author Beck
 * @since 2021-07-09
 */
@Configuration
public class RequestInterceptorAutoConfiguration {

	@Bean
	public LogRequestInterceptor logRequestInterceptor() {
		return new LogRequestInterceptor();
	}

	@Bean
	public SentinelDegradeRuleInterceptor sentinelDefaultDegradeRuleInterceptor() {
		return new SentinelDegradeRuleInterceptor();
	}
}
