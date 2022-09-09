package com.oceancloud.grampus.framework.lock.properties;

import com.oceancloud.grampus.framework.lock.strategy.LockStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 分布式锁配置
 *
 * @author Beck
 * @since 2021-01-29
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "distributed.lock")
public class DistributedLockProperties {

	/**
	 * 锁租用时间 单位：毫秒 (默认30秒)
	 */
	private Long leaseTime = 30000L;

	/**
	 * 获取锁等待时间 单位：毫秒 (默认3秒)
	 */
	private Long waitTime = 3000L;

	/**
	 * 获取锁失败时重试时间间隔 单位：毫秒 (默认100毫秒)
	 */
	private Long retryInterval = 100L;

	/**
	 * 默认加锁策略，不设置默认取容器第一个(默认注入顺序，redisson>redisTemplate)
	 */
	private Class<? extends LockStrategy> primaryStrategy;
}
