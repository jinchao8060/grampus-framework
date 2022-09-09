package com.oceancloud.grampus.framework.signin.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 第三方授权信息
 *
 * @author Beck
 * @since 2021-07-13
 */
@Getter
@Setter
@ToString
public abstract class OAuthSignInInfo implements Serializable {
	private static final long serialVersionUID = 5843793522623142026L;
	/**
	 * 用户唯一标识
	 */
	private String userId;
	/**
	 * 邮箱
	 */
	String email;
	/**
	 * 姓名
	 */
	String name;
	/**
	 * 头像URL
	 */
	String pictureUrl;
}
