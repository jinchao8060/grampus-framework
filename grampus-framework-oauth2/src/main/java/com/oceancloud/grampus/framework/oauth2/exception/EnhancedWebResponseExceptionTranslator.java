package com.oceancloud.grampus.framework.oauth2.exception;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * Enhanced translator that converts exceptions into {@link OAuth2Exception}s. The output matches the OAuth 2.0
 * specification in terms of error response format and HTTP status code.
 *
 * @author Beck
 */
public class EnhancedWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

		// Try to extract a SpringSecurityException from the stacktrace
		Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

		// OAuth2Exception异常
		Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class,
				causeChain);
		if (ase != null) {
			return handleOAuth2Exception(convertOAuth2Exception((OAuth2Exception) ase));
		}

		// 认证失败异常(UsernameNotFoundException、BadCredentialsException等)
		ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
				causeChain);
		if (ase != null) {
			return handleOAuth2Exception(convertAuthenticationException((AuthenticationException) ase));
		}

		// 接口权限异常
		ase = (AccessDeniedException) throwableAnalyzer
				.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
		if (ase instanceof AccessDeniedException) {
			return handleOAuth2Exception(new AuthException.ForbiddenException(ase.getMessage(), ase));
		}

		// 请求方法不支持异常
		ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(
				HttpRequestMethodNotSupportedException.class, causeChain);
		if (ase instanceof HttpRequestMethodNotSupportedException) {
			return handleOAuth2Exception(new AuthException.MethodNotAllowed(ase.getMessage(), ase));
		}

		return handleOAuth2Exception(new AuthException.ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));

	}

	private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

		int status = e.getHttpErrorCode();
		HttpHeaders headers = new HttpHeaders();
		// 认证相关异常默认返回200，使用响应数据的status字段区分是否异常
		HttpStatus responseStatus = HttpStatus.OK;
		headers.set("Cache-Control", "no-store");
		headers.set("Pragma", "no-cache");
		if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
			headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
			responseStatus = HttpStatus.valueOf(status);
		}

		ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(e, headers, responseStatus);

		return response;

	}

	/**
	 * OAuth2异常转换
	 */
	private OAuth2Exception convertOAuth2Exception(OAuth2Exception e) {
		String errorCode = e.getOAuth2ErrorCode();
		if (OAuth2Exception.INVALID_SCOPE.equals(errorCode)
				|| OAuth2Exception.UNSUPPORTED_GRANT_TYPE.equals(errorCode)
				|| OAuth2Exception.UNSUPPORTED_RESPONSE_TYPE.equals(errorCode)
				|| OAuth2Exception.REDIRECT_URI_MISMATCH.equals(errorCode)
				|| OAuth2Exception.INVALID_CLIENT.equals(errorCode)
				|| OAuth2Exception.UNAUTHORIZED_CLIENT.equals(errorCode)
				|| OAuth2Exception.INVALID_REQUEST.equals(errorCode)) {
			return new AuthException.AuthParamsErrorException();
		} else if (OAuth2Exception.INVALID_TOKEN.equals(errorCode)) {
			return new AuthException.TokenParsedException();
		} else if (OAuth2Exception.ACCESS_DENIED.equals(errorCode)) {
			return new AuthException.UserAccessDeniedException();
		} else if (OAuth2Exception.INVALID_GRANT.equals(errorCode)) {
			return new AuthException.UserPasswordErrorException();
		}
		return new AuthException.ServerErrorException();
	}

	/**
	 * AuthenticationException异常转换
	 */
	private OAuth2Exception convertAuthenticationException(AuthenticationException e) {
		Throwable cause = e.getCause();
		if (cause instanceof UsernameNotFoundException) {
			return new AuthException.UserNotFoundException();
		} else if (cause instanceof DisabledException) {
			return new AuthException.UserDisabledException();
		}
		return new AuthException.UnauthorizedException(e.getMessage(), e);
	}
}
