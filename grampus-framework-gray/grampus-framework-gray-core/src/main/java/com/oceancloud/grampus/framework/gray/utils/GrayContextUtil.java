package com.oceancloud.grampus.framework.gray.utils;

import com.oceancloud.grampus.framework.gray.support.GrayVersionContextHolder;
import lombok.experimental.UtilityClass;

/**
 * GrayContextUtils
 *
 * @author Beck
 * @since 2021-07-23
 */
@UtilityClass
public class GrayContextUtil {

	public void setGrayContext(String grayVersion) {
		GrayVersionContextHolder.setVersion(grayVersion);
	}

	public void clearGrayContext() {
		GrayVersionContextHolder.clear();
	}
}
