package com.oceancloud.grampus.framework.redis.config;

import com.oceancloud.grampus.framework.redis.serializer.JsonRedisSerializer;
import com.oceancloud.grampus.framework.redis.utils.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * RedisCacheAutoConfiguration
 *
 * @author Beck
 * @since 2021-04-02
 */
@Configuration
public class RedisCacheAutoConfiguration {

	@Resource
	private RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
		redisTemplate.setHashKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new JsonRedisSerializer<>(Object.class));
		redisTemplate.setHashValueSerializer(new JsonRedisSerializer<>(Object.class));
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}

	@Bean
	public RedisCache redisClient(RedisTemplate<String, Object> redisTemplate) {
		return new RedisCache(redisTemplate);
	}
}
