package com.oceancloud.grampus.framework.lock.strategy;

/**
 * 抽象分布式锁策略
 *
 * @author Beck
 * @since 2021-01-29
 */
public abstract class AbstractLockStrategy<T> {

	protected T obtainLockInstance(boolean locked, T lockInstance) {
		return locked ? lockInstance : null;
	}

}
