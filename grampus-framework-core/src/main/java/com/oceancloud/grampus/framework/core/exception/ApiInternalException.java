package com.oceancloud.grampus.framework.core.exception;

/**
 * 微服务内部接口调用异常
 *
 * @author Beck
 * @since 2021-06-30
 */
public class ApiInternalException extends RuntimeException {
	private static final long serialVersionUID = -7721969052846074211L;

	protected String code;

	public ApiInternalException() {
		super();
	}

	public ApiInternalException(String code) {
		super();
		this.code = code;
	}

	public ApiInternalException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ApiInternalException(String code, String message, Throwable e) {
		super(message, e);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
