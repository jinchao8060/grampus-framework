package com.oceancloud.grampus.framework.lock.exception;

/**
 * 加锁失败异常
 *
 * @author Beck
 * @since 2021-01-29
 */
public class LockFailureException extends LockException {

	public LockFailureException() {

	}

	public LockFailureException(String message) {
		super(message);
	}
}
