package com.oceancloud.grampus.framework.oauth2.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 *
 * @author Beck
 * @since 2021-05-06
 */
public interface OAuth2ErrorCode {

	/**
	 * 0：成功
	 * -1：未知异常
	 *
	 * 	错误码组成：应用标识+功能域+错误类型+错误编码
	 * 	错误码位数：10位
	 * 	错误码示例：111101P001
	 *	使用规范：只增不改，避免混乱
	 *
	 * 	应用标识(2位字母数字)
	 * 		COMMON: 10
	 * 		APP：11
	 * 		ADMIN：12
	 *  服务标识(2位数字)
	 *  	认证授权服务：11
	 *  	系统服务：12
	 *  	会员服务：13
	 *  	消息中心：14
	 *  	聊天服务：15
	 * 	功能域(2位数字)
	 * 		未分类：00
	 * 		X1功能相关：01
	 * 		X2功能相关：02
	 * 		X3功能相关：03
	 * 		……
	 * 	错误类型(1位字母)
	 * 		参数错误：P
	 * 		业务错误：B
	 * 		系统错误：S
	 * 		网络错误：N
	 * 		数据库错误：D
	 * 		缓存错误：C
	 * 		RPC错误：R
	 * 		文件IO错误：F
	 * 		其他错误：O
	 * 	错误编码(3位数字)
	 * 		自增即可
	 */

	/**
	 * 全局通用错误码
	 */
	@AllArgsConstructor
	enum Global {
		/**
		 * 未知异常错误码
		 */
		UNKNOWN_ERROR_CODE("-1", "未知异常错误码");

		@Getter String code;
		@Getter String msg;
	}

	/**
	 * 认证服务相关 1011
	 */
	@AllArgsConstructor
	enum Auth {
		// ~ ============================ 登录认证相关 01 ============================
		/**
		 * 认证传参错误异常
		 */
		AUTH_PARAMS_ERROR("101101P001", "认证传参错误异常"),
		/**
		 * 用户密码错误异常
		 */
		USER_PASSWORD_ERROR("101101B001", "用户密码错误异常"),
		/**
		 * 会员不存在异常
		 */
		USER_NOT_EXISTED("101101B002", "会员不存在异常"),
		/**
		 * 用户账号禁用异常
		 */
		USER_DISABLE_ERROR("101101B003", "用户账号禁用异常"),
		/**
		 * 用户访问权限异常
		 */
		USER_ACCESS_DENIED_ERROR("101101B004", "用户访问权限异常"),
		/**
		 * Token失效异常
		 */
		TOKEN_EXPIRED_ERROR("101101B005", "Token失效异常"),
		/**
		 * Token解析失败异常
		 */
		TOKEN_PARSED_ERROR("101101B006", "Token解析失败异常"),

		/**
		 * FORBIDDEN_EXCEPTION
		 */
		FORBIDDEN_EXCEPTION("101101B007", "forbidden exception"),
		/**
		 * UNAUTHORIZED_EXCEPTION
		 */
		UNAUTHORIZED_EXCEPTION("101101B008", "unauthorized exception"),
		/**
		 * METHOD_NOT_ALLOWED
		 */
		METHOD_NOT_ALLOWED("101101B009", "method not allow.");

		@Getter String code;
		@Getter String msg;
	}
}
