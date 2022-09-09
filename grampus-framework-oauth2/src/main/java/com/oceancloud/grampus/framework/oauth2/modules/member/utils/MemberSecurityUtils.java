package com.oceancloud.grampus.framework.oauth2.modules.member.utils;

import com.oceancloud.grampus.framework.core.utils.WebUtil;
import com.oceancloud.grampus.framework.oauth2.modules.member.users.MemberUserDetails;
import com.oceancloud.grampus.framework.oauth2.utils.SecurityUtils;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员Security工具.
 *
 * @author Beck
 * @since 2021-08-25
 */
@UtilityClass
public class MemberSecurityUtils extends SecurityUtils {

	/**
	 * 获取用户
	 */
	public static MemberUserDetails getUserDetails(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof MemberUserDetails) {
			return ((MemberUserDetails) principal);
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public static MemberUserDetails getUserDetails() {
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
		MemberUserDetails memberUserDetails = getUserDetails();
		return memberUserDetails == null ? null : memberUserDetails.getUsername();
	}

	/**
	 * 退出
	 */
	public static void logout() {
		HttpServletRequest request = WebUtil.getRequest();
		new SecurityContextLogoutHandler().logout(request, null, null);
	}
}
