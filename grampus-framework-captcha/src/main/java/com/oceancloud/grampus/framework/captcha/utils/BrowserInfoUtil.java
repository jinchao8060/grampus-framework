package com.oceancloud.grampus.framework.captcha.utils;

import com.anji.captcha.util.StringUtils;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

/**
 * BrowserInfoUtil
 *
 * @author Beck
 * @since 2022-12-05
 */
@UtilityClass
public class BrowserInfoUtil {

	public String getRemoteId(HttpServletRequest request) {
		String xfwd = request.getHeader("X-Forwarded-For");
		String ip = getRemoteIpFromXfwd(xfwd);
		String ua = request.getHeader("user-agent");
		if (StringUtils.isNotBlank(ip)) {
			return ip + ua;
		}
		return request.getRemoteAddr() + ua;
	}

	private String getRemoteIpFromXfwd(String xfwd) {
		if (StringUtils.isNotBlank(xfwd)) {
			String[] ipList = xfwd.split(",");
			return StringUtils.trim(ipList[0]);
		}
		return null;
	}
}
