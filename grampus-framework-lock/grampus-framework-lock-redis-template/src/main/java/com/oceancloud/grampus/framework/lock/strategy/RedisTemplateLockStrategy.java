package com.oceancloud.grampus.framework.lock.strategy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * 分布式锁原生RedisTemplate处理器
 *
 * @author Beck
 * @since 2021-01-29
 */
@Slf4j
@AllArgsConstructor
public class RedisTemplateLockStrategy extends AbstractLockStrategy<String> implements LockStrategy<String> {

	private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript<>("return redis.call('set',KEYS[1]," +
			"ARGV[1],'NX','PX',ARGV[2])", String.class);
	private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript<>("if redis.call('get',KEYS[1]) " +
			"== ARGV[1] then return tostring(redis.call('del', KEYS[1])==1) else return 'false' end", String.class);
	private static final String LOCK_SUCCESS = "OK";

	private final StringRedisTemplate redisTemplate;

	@Override
	public String lock(String lockKey, String lockValue, long expire, long waitTime) {
		String lock = redisTemplate.execute(SCRIPT_LOCK,
				redisTemplate.getStringSerializer(),
				redisTemplate.getStringSerializer(),
				Collections.singletonList(lockKey),
				lockValue, String.valueOf(expire));
		final boolean locked = LOCK_SUCCESS.equals(lock);
		return obtainLockInstance(locked, lock);
	}

	@Override
	public boolean releaseLock(String lockKey, String lockValue, String lockInstance) {
		String releaseResult = redisTemplate.execute(SCRIPT_UNLOCK,
				redisTemplate.getStringSerializer(),
				redisTemplate.getStringSerializer(),
				Collections.singletonList(lockKey), lockValue);
		return Boolean.parseBoolean(releaseResult);
	}

}
