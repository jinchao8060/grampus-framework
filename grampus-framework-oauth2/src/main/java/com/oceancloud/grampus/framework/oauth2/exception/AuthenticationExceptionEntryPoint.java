package com.oceancloud.grampus.framework.oauth2.exception;

import com.oceancloud.grampus.framework.oauth2.constant.OAuth2ErrorCode;
import com.oceancloud.grampus.framework.core.result.Result;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * token无效异常
 *
 * @author Beck
 * @since 2020-12-16
 */
public class AuthenticationExceptionEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) {
		handleAuthenticationFailure(response, authException);
	}

	/**
	 * Handle Authentication Failure.
	 */
	@SneakyThrows
	private void handleAuthenticationFailure(HttpServletResponse response, AuthenticationException e) {
		String code;
		String msg = e.getMessage();

		// 用户不存在
		if (e instanceof UsernameNotFoundException) {
			code = OAuth2ErrorCode.Auth.USER_NOT_EXISTED.getCode();
		}
		// 用户被禁用
		else if (e instanceof DisabledException) {
			code = OAuth2ErrorCode.Auth.USER_DISABLE_ERROR.getCode();
		}
		// 密码错误
		else if (e instanceof BadCredentialsException) {
			code = OAuth2ErrorCode.Auth.USER_PASSWORD_ERROR.getCode();
		}
		// 账户锁定
		else if (e instanceof LockedException) {
			code = OAuth2ErrorCode.Auth.USER_DISABLE_ERROR.getCode();
		}
		// 账户过期
		else if (e instanceof AccountExpiredException) {
			code = OAuth2ErrorCode.Auth.TOKEN_EXPIRED_ERROR.getCode();
		}
		// 检索用户信息异常
		else if (e instanceof InternalAuthenticationServiceException) {
			code = OAuth2ErrorCode.Auth.USER_NOT_EXISTED.getCode();
		}
		// 证书过期、不安全
		else if (e instanceof CredentialsExpiredException) {
			code = OAuth2ErrorCode.Auth.TOKEN_EXPIRED_ERROR.getCode();
		}
		else if (e instanceof InsufficientAuthenticationException) {
			code = OAuth2ErrorCode.Auth.TOKEN_PARSED_ERROR.getCode();
		}
		else {
			code = OAuth2ErrorCode.Global.UNKNOWN_ERROR_CODE.getCode();
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(Objects.requireNonNull(JSONUtil.writeValueAsString(Result.error(code, msg))));
	}
}