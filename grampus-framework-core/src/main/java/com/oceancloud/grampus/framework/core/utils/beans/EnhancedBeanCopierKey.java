package com.oceancloud.grampus.framework.core.utils.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * EnhancedBeanCopierKey
 *
 * @author L.cm
 * @since 2021-01-25
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class EnhancedBeanCopierKey {
	private final Class<?> source;
	private final Class<?> target;
	private final boolean useConverter;
	private final boolean nonNull;
}
