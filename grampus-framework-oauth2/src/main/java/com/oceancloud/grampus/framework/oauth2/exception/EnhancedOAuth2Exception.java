package com.oceancloud.grampus.framework.oauth2.exception;

import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * EnhancedOAuth2Exception
 *
 * @author Beck
 * @since 2021-08-10
 */
public class EnhancedOAuth2Exception extends OAuth2Exception {
	private static final long serialVersionUID = -4247313528473011492L;

	@Getter
	protected String code;

	public EnhancedOAuth2Exception(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public EnhancedOAuth2Exception(String code, String msg, Throwable t) {
		super(msg, t);
		this.code = code;
	}

	public EnhancedOAuth2Exception(String msg, Throwable t) {
		super(msg, t);
	}
}
