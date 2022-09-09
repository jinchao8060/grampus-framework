package com.oceancloud.grampus.framework.lock.exception;

/**
 * 加锁异常
 *
 * @author Beck
 * @since 2021-01-29
 */
public class LockException extends RuntimeException {

	public LockException() {
		super();
	}

	public LockException(String message) {

		super(message);
	}

}
