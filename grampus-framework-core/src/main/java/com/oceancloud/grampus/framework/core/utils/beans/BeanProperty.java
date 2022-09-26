package com.oceancloud.grampus.framework.core.utils.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Bean属性
 *
 * @author L.cm
 */
@Getter
@RequiredArgsConstructor
public class BeanProperty {
	private final String name;
	private final Class<?> type;
}
