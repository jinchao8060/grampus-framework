package com.oceancloud.grampus.framework.lock.config;

import com.oceancloud.grampus.framework.lock.strategy.RedisTemplateLockStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisTemplateLockAutoConfiguration
 *
 * @author Beck
 * @since 2021-09-01
 */
@Configuration
public class RedisTemplateLockAutoConfiguration {

	@Configuration
	@ConditionalOnClass(RedisOperations.class)
	static class RedisStrategyAutoConfiguration {
		@Bean
		@Order(200)
		public RedisTemplateLockStrategy redisTemplateLockStrategy(StringRedisTemplate stringRedisTemplate) {
			return new RedisTemplateLockStrategy(stringRedisTemplate);
		}
	}
}
