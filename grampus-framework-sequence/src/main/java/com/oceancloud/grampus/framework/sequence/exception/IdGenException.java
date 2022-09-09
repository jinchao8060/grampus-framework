package com.oceancloud.grampus.framework.sequence.exception;

import java.io.Serializable;

/**
 * ID生成器异常
 *
 * @author Beck
 * @since 2020-12-03
 */
public class IdGenException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -6648147134838012009L;

	public IdGenException(String message) {
		super(message);
	}

	public IdGenException(Throwable cause) {
		super(cause);
	}
}
