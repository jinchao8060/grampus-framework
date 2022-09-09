package com.oceancloud.grampus.framework.gray.support;

import lombok.experimental.UtilityClass;

/**
 * GrayVersionContextHolder
 *
 * @author Beck
 * @since 2021-07-21
 */
@UtilityClass
public class GrayVersionContextHolder {
	private final ThreadLocal<String> THREAD_LOCAL_VERSION = new InheritableThreadLocal<>();

	public void setVersion(String version) {
		THREAD_LOCAL_VERSION.set(version);
	}

	public String getVersion() {
		return THREAD_LOCAL_VERSION.get();
	}

	public void clear() {
		THREAD_LOCAL_VERSION.remove();
	}
}
