package com.oceancloud.grampus.framework.core.utils.exception;

import com.oceancloud.grampus.framework.core.result.Result;
import org.springframework.lang.Nullable;

/**
 * 业务异常
 *
 * @author Beck
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Nullable
	private final Result<?> result;

	public ServiceException(String message) {
		super(message);
		this.result = null;
	}

	public ServiceException(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		doFillInStackTrace();
		this.result = null;
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public <T> Result<T> getResult() {
		return (Result<T>) result;
	}

	/**
	 * 提高性能
	 *
	 * @return Throwable
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}

}
