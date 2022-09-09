package com.oceancloud.grampus.framework.lock.annotation;

import com.oceancloud.grampus.framework.lock.strategy.LockStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁注解
 *
 * @author Beck
 * @since 2021-01-29
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DistributedLock {

	/**
	 * @return lock 执行器
	 */
	Class<? extends LockStrategy> strategy() default LockStrategy.class;

	/**
	 * @return KEY 分布式锁KEY（默认包名.类名.方法名）自定义key主要用于支持不同方法使用同一把锁的情况，例如不同方法需要更新数据库同一条数据。
	 */
	String key() default "";

	/**
	 * @return KEY 分布式锁KEY参数（分布式锁完整的KEY包括 key + keyParams）注意：值不能为null
	 */
	String[] keyParams() default "";

	/**
	 * @return 锁过期时间 单位：毫秒
	 * <pre>
	 *     锁过期时间一定是要长于业务的执行时间. 未设置则为默认时间3秒
	 * </pre>
	 */
	long leaseTime() default 0;

	/**
	 * @return 尝试获取锁等待时间（在该时间获取锁失败，则自旋重试，重试间隔默认100ms） 单位：毫秒
	 * <pre>
	 *     结合业务,建议该时间不宜设置过长,特别在并发高的情况下. 未设置则为默认时间3秒
	 * </pre>
	 */
	long waitTime() default 0;

	/**
	 * @return 尝试获取锁次数（在waitTime内获取锁失败，则自旋重试。重试中若尝试获取锁次数超过该次数则不再重试）
	 * <pre>
	 *    未设置则为默认不限制次数
	 *    建议不限制次数，通过waitTime控制重试. 若业务上需要获取锁失败后立即抛出异常，则该值设置为1.
	 * </pre>
	 */
	long lockCount() default 0;
}
