package com.oceancloud.grampus.framework.oauth2.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oceancloud.grampus.framework.oauth2.constant.OAuth2ErrorCode;

/**
 * AuthException
 *
 * @author Beck
 * @since 2021-08-10
 */
public class AuthException {

	/**
	 * 认证传参错误异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class AuthParamsErrorException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = 765159580385523781L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.AUTH_PARAMS_ERROR;

		public AuthParamsErrorException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public AuthParamsErrorException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public AuthParamsErrorException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

	}

	/**
	 * Token过期异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class TokenExpiredException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = -7284332526504876630L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.TOKEN_EXPIRED_ERROR;

		public TokenExpiredException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public TokenExpiredException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public TokenExpiredException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

	}

	/**
	 * Token解析失败异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class TokenParsedException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = 985062659360376840L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.TOKEN_PARSED_ERROR;

		public TokenParsedException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public TokenParsedException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public TokenParsedException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

	}

	/**
	 * 用户被禁用异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class UserDisabledException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = 1791940330757308184L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.USER_DISABLE_ERROR;

		public UserDisabledException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public UserDisabledException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public UserDisabledException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

	}

	/**
	 * 用户不存在异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class UserNotFoundException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = 5186572331502903237L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.USER_NOT_EXISTED;

		public UserNotFoundException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public UserNotFoundException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public UserNotFoundException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

	}

	/**
	 * 用户密码错误异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class UserPasswordErrorException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = 8127084390337697670L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.USER_PASSWORD_ERROR;

		public UserPasswordErrorException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public UserPasswordErrorException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public UserPasswordErrorException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

	}

	/**
	 * 用户权限异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class UserAccessDeniedException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = -6024358137463783577L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.USER_ACCESS_DENIED_ERROR;

		public UserAccessDeniedException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public UserAccessDeniedException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public UserAccessDeniedException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "access_denied";
		}

		@Override
		public int getHttpErrorCode() {
			return 500;
		}

	}

	/**
	 * ForbiddenException
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class ForbiddenException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = 1527150905400787330L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.USER_PASSWORD_ERROR;

		public ForbiddenException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public ForbiddenException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public ForbiddenException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "access_denied";
		}

		@Override
		public int getHttpErrorCode() {
			return 403;
		}

	}

	/**
	 * 未知异常
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class ServerErrorException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = -4742994140067255007L;

		private static final OAuth2ErrorCode.Global ERROR_CODE = OAuth2ErrorCode.Global.UNKNOWN_ERROR_CODE;

		public ServerErrorException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public ServerErrorException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public ServerErrorException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "server_error";
		}

		@Override
		public int getHttpErrorCode() {
			return 500;
		}

	}

	/**
	 * UnauthorizedException
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class UnauthorizedException extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = -5584454228445454971L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.UNAUTHORIZED_EXCEPTION;

		public UnauthorizedException() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public UnauthorizedException(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public UnauthorizedException(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "unauthorized";
		}

		@Override
		public int getHttpErrorCode() {
			return 401;
		}

	}

	/**
	 * MethodNotAllowed
	 */
	@JsonSerialize(using = EnhancedOAuth2ExceptionSerializer.class)
	public static class MethodNotAllowed extends EnhancedOAuth2Exception {

		private static final long serialVersionUID = 4913744050628475501L;

		private static final OAuth2ErrorCode.Auth ERROR_CODE = OAuth2ErrorCode.Auth.METHOD_NOT_ALLOWED;

		public MethodNotAllowed() {
			super(ERROR_CODE.getCode(), ERROR_CODE.getMsg());
		}

		public MethodNotAllowed(String msg) {
			super(ERROR_CODE.getCode(), msg);
		}

		public MethodNotAllowed(String msg, Throwable t) {
			super(ERROR_CODE.getCode(), msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "method_not_allowed";
		}

		@Override
		public int getHttpErrorCode() {
			return 405;
		}

	}
}
