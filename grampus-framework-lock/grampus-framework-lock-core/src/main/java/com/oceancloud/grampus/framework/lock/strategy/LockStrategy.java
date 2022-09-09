package com.oceancloud.grampus.framework.lock.strategy;

/**
 * 分布式锁策略接口
 *
 * @author Beck
 * @since 2021-01-29
 */
public interface LockStrategy<T> {

	/**
	 * 加锁
	 *
	 * @param lockKey   锁key
	 * @param lockValue 锁value
	 * @param leaseTime 锁租用时间
	 * @param waitTime  尝试获取锁等待时间(ms)
	 * @return 锁信息
	 */
	T lock(String lockKey, String lockValue, long leaseTime, long waitTime);

	/**
	 * 解锁
	 *
	 * <pre>
	 * 为何解锁需要校验lockValue
	 * 客户端A加锁，一段时间之后客户端A解锁，在执行releaseLock之前，锁突然过期了。
	 * 此时客户端B尝试加锁成功，然后客户端A再执行releaseLock方法，则将客户端B的锁给解除了。
	 * </pre>
	 *
	 * @param lockKey      加锁key
	 * @param lockValue    加锁value
	 * @param lockInstance 锁实例
	 * @return 是否释放成功
	 */
	boolean releaseLock(String lockKey, String lockValue, T lockInstance);

}
