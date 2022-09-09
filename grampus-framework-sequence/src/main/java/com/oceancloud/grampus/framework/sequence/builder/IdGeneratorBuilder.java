package com.oceancloud.grampus.framework.sequence.builder;

import com.oceancloud.grampus.framework.sequence.generator.IdGenerator;

/**
 * ID生成器Builder
 *
 * @author Beck
 * @since 2020-12-03
 */
public interface IdGeneratorBuilder {

	/**
	 * 构建ID生成器
	 *
	 * @return ID生成器
	 */
	IdGenerator build();
}
