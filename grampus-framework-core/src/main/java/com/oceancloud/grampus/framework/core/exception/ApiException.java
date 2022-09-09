package com.oceancloud.grampus.framework.core.exception;

/**
 * 接口调用异常
 *
 * @author Beck
 * @since 2020-12-9
 */
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1525116257471128717L;

	protected String code;

	public ApiException() {
		super();
	}

	public ApiException(String code) {
		super();
		this.code = code;
	}

	public ApiException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ApiException(String code, String message, Throwable e) {
		super(message, e);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
