package com.oceancloud.grampus.framework.lock;

import org.aopalliance.intercept.MethodInvocation;

/**
 * LockKeyBuilder
 *
 * @author Beck
 * @since 2021-01-29
 */
public interface LockKeyBuilder {

	/**
	 * 构建key
	 *
	 * @param invocation          invocation
	 * @param definitionKey       KEY标识
	 * @param definitionKeyParams KEY参数
	 * @return
	 */
	String buildKey(MethodInvocation invocation, String definitionKey, String[] definitionKeyParams);
}
