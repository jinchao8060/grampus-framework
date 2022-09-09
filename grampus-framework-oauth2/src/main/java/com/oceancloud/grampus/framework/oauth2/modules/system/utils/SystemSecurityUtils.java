package com.oceancloud.grampus.framework.oauth2.modules.system.utils;

import com.oceancloud.grampus.framework.oauth2.modules.system.users.SystemUserDetails;
import com.oceancloud.grampus.framework.oauth2.utils.SecurityUtils;
import com.oceancloud.grampus.framework.core.utils.WebUtil;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Security工具.
 *
 * @author Beck
 * @since 2020-12-5
 */
@UtilityClass
public class SystemSecurityUtils extends SecurityUtils {

	/**
	 * 获取用户
	 */
	public static SystemUserDetails getUserDetails(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof SystemUserDetails) {
			return ((SystemUserDetails) principal);
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public static SystemUserDetails getUserDetails() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUserDetails(authentication);
	}

	/**
	 * 获取用户名
	 */
	public static String getUserName() {
		SystemUserDetails systemUserDetails = getUserDetails();
		return systemUserDetails == null ? null : systemUserDetails.getUsername();
	}

	/**
	 * 退出
	 */
	public static void logout() {
		HttpServletRequest request = WebUtil.getRequest();
		new SecurityContextLogoutHandler().logout(request, null, null);
	}
}
