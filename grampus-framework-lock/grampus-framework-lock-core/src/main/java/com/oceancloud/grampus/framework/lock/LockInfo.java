package com.oceancloud.grampus.framework.lock;

import com.oceancloud.grampus.framework.lock.strategy.LockStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 锁信息
 *
 * @author Beck
 * @since 2021-01-29
 */
@Builder
@Data
@AllArgsConstructor
public class LockInfo {

	/**
	 * 锁名称
	 */
	private String lockKey;

	/**
	 * 锁值
	 */
	private String lockValue;

	/**
	 * 过期时间
	 */
	private Long expire;

	/**
	 * 获取锁超时时间
	 */
	private Long waitTime;

	/**
	 * 获取锁次数
	 */
	private int lockCount;

	/**
	 * 锁实例
	 */
	private Object lockInstance;

	/**
	 * 锁执行器
	 */
	private LockStrategy lockStrategy;
}
