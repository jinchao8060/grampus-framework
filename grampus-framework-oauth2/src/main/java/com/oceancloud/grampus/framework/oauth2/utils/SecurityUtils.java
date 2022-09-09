package com.oceancloud.grampus.framework.oauth2.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security工具.
 *
 * @author Beck
 * @since 2020-12-5
 */
public abstract class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取Principal
	 */
	public static Object getPrincipal() {
		return getAuthentication().getPrincipal();
	}
}
