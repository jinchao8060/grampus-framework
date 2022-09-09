package com.oceancloud.grampus.framework.lock;

import com.oceancloud.grampus.framework.lock.properties.DistributedLockProperties;
import com.oceancloud.grampus.framework.lock.exception.LockException;
import com.oceancloud.grampus.framework.lock.strategy.LockStrategy;
import com.oceancloud.grampus.framework.lock.util.LockUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * LockTemplate
 *
 * @author Beck
 * @since 2021-01-29
 */
@SuppressWarnings("rawtypes")
@Slf4j
public class LockTemplate implements InitializingBean {

	private final Map<Class<? extends LockStrategy>, LockStrategy> strategyMap = new LinkedHashMap<>();
	@Setter
	private DistributedLockProperties properties;
	@Setter
	private LockFailureStrategy lockFailureStrategy;
	@Setter
	private List<LockStrategy> strategies;

	private LockStrategy primaryStrategy;

	public LockTemplate() {
	}

	public LockInfo lock(String key) {
		return lock(key, 0, 0);
	}

	public LockInfo lock(String key, long expire, long waitTime) {
		return lock(key, expire, waitTime, 0, null);
	}

	public LockInfo lock(String key, long expire, long waitTime, long lockCount) {
		return lock(key, expire, waitTime, lockCount, null);
	}

	/**
	 * 加锁方法
	 *
	 * @param key       锁key 同一个key只能被一个客户端持有
	 * @param expire    过期时间(ms) 防止死锁
	 * @param waitTime  尝试获取锁等待时间(ms)
	 * @param lockCount 尝试获取锁次数
	 * @param strategy  加锁策略
	 * @return 加锁成功返回锁信息 失败返回null
	 */
	public LockInfo lock(String key, long expire, long waitTime, long lockCount, Class<? extends LockStrategy> strategy) {
		expire = expire == 0 ? properties.getLeaseTime() : expire;
		waitTime = waitTime == 0 ? properties.getWaitTime() : waitTime;
		long retryInterval = properties.getRetryInterval();
		// 防止重试时间大于超时时间
		if (retryInterval >= waitTime) {
			log.warn("retryInterval more than waitTime,please check your configuration");
		}
		LockStrategy lockStrategy = obtainStrategy(strategy);
		int currentLockCount = 0;
		String value = LockUtil.simpleUUID();
		long start = System.currentTimeMillis();
		try {
			while (System.currentTimeMillis() - start < waitTime) {
				currentLockCount++;
				Object lockInstance = lockStrategy.lock(key, value, expire, waitTime);
				if (lockInstance != null) {
					return LockInfo.builder()
							.lockKey(key)
							.lockValue(value)
							.expire(expire)
							.waitTime(waitTime)
							.lockCount(currentLockCount)
							.lockInstance(lockInstance)
							.lockStrategy(lockStrategy)
							.build();
				}
				if (lockCount != 0 && currentLockCount == lockCount) {
					break;
				}
				TimeUnit.MILLISECONDS.sleep(retryInterval);
			}
		} catch (InterruptedException e) {
			log.error("lock error", e);
			throw new LockException();
		}
		// lock failure
		lockFailureStrategy.onLockFailure(key, waitTime, currentLockCount);
		return null;
	}

	/**
	 * 释放锁
	 *
	 * @param lockInfo 锁信息
	 * @return 释放成功返回true 锁为空或者释放失败返回false
	 */
	@SuppressWarnings("unchecked")
	public boolean releaseLock(LockInfo lockInfo) {
		if (null == lockInfo) {
			return false;
		}
		return lockInfo.getLockStrategy().releaseLock(lockInfo.getLockKey(), lockInfo.getLockValue(),
				lockInfo.getLockInstance());
	}

	protected LockStrategy obtainStrategy(Class<? extends LockStrategy> clazz) {
		if (null == clazz || clazz == LockStrategy.class) {
			return primaryStrategy;
		}
		final LockStrategy lockStrategy = strategyMap.get(clazz);
		Assert.notNull(lockStrategy, String.format("can not get bean type of %s", clazz));
		return lockStrategy;
	}

	@Override
	public void afterPropertiesSet() {

		Assert.isTrue(properties.getWaitTime() > 0, "tryWaitTime must more than 0");
		Assert.isTrue(properties.getLeaseTime() > 0, "leaseTime must more than 0");
		Assert.isTrue(properties.getRetryInterval() >= 0, "retryInterval must more than 0");
		Assert.notEmpty(strategies, "strategies must have at least one");

		for (LockStrategy strategy : strategies) {
			strategyMap.put(strategy.getClass(), strategy);
		}

		final Class<? extends LockStrategy> primaryStrategy = properties.getPrimaryStrategy();
		if (null == primaryStrategy) {
			this.primaryStrategy = strategies.get(0);
		} else {
			this.primaryStrategy = strategyMap.get(primaryStrategy);
			Assert.notNull(this.primaryStrategy, "primaryStrategy must not be null");
		}
	}
}
