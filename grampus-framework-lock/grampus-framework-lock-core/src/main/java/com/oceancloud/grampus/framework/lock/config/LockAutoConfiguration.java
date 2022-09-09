package com.oceancloud.grampus.framework.lock.config;

import com.oceancloud.grampus.framework.lock.DefaultLockFailureStrategy;
import com.oceancloud.grampus.framework.lock.DefaultLockKeyBuilder;
import com.oceancloud.grampus.framework.lock.LockFailureStrategy;
import com.oceancloud.grampus.framework.lock.LockKeyBuilder;
import com.oceancloud.grampus.framework.lock.LockTemplate;
import com.oceancloud.grampus.framework.lock.aspect.LockAnnotationAdvisor;
import com.oceancloud.grampus.framework.lock.aspect.LockInterceptor;
import com.oceancloud.grampus.framework.lock.properties.DistributedLockProperties;
import com.oceancloud.grampus.framework.lock.strategy.LockStrategy;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * LockAutoConfiguration
 *
 * @author Beck
 * @since 2021-01-29
 */
@Configuration
@EnableConfigurationProperties(DistributedLockProperties.class)
@AllArgsConstructor
public class LockAutoConfiguration {

	private final DistributedLockProperties properties;

	@SuppressWarnings("rawtypes")
	@Bean
	@ConditionalOnMissingBean
	public LockTemplate lockTemplate(LockFailureStrategy lockFailureStrategy, List<LockStrategy> strategies) {
		LockTemplate lockTemplate = new LockTemplate();
		lockTemplate.setProperties(properties);
		lockTemplate.setLockFailureStrategy(lockFailureStrategy);
		lockTemplate.setStrategies(strategies);
		return lockTemplate;
	}

	@Bean
	@ConditionalOnMissingBean
	public LockKeyBuilder lockKeyBuilder() {
		return new DefaultLockKeyBuilder();
	}

	@Bean
	@ConditionalOnMissingBean
	public LockFailureStrategy lockFailureStrategy() {
		return new DefaultLockFailureStrategy();
	}

	@Bean
	@ConditionalOnMissingBean
	public LockInterceptor lockInterceptor(LockTemplate lockTemplate, LockKeyBuilder lockKeyBuilder) {
		return new LockInterceptor(lockTemplate, lockKeyBuilder);
	}

	@Bean
	@ConditionalOnMissingBean
	public LockAnnotationAdvisor lockAnnotationAdvisor(LockInterceptor lockInterceptor) {
		return new LockAnnotationAdvisor(lockInterceptor, Ordered.HIGHEST_PRECEDENCE);
	}
}
