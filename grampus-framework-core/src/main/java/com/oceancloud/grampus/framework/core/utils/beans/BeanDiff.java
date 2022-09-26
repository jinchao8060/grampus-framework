package com.oceancloud.grampus.framework.core.utils.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 跟踪类变动比较
 *
 * @author L.cm
 */
@Getter
public class BeanDiff {
	/**
	 * 变更字段
 	 */
	@JsonIgnore
	private transient Set<String> fields = new HashSet<>();
	/**
	 * 旧值
	 */
	@JsonIgnore
	private transient Map<String, Object> oldValues = new HashMap<>();
	/**
	 * 新值
	 */
	@JsonIgnore
	private transient Map<String, Object> newValues = new HashMap<>();
}
