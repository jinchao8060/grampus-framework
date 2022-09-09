package com.oceancloud.grampus.framework.core.utils.beans;

import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;

/**
 * EnhancedBeanMapKey
 *
 * @author L.cm
 * @since 2021-01-25
 */
@EqualsAndHashCode
@AllArgsConstructor
public class EnhancedBeanMapKey {
	private final Class type;
	private final int require;
}
