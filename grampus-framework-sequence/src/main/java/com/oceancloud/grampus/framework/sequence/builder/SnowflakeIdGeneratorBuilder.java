package com.oceancloud.grampus.framework.sequence.builder;

import com.oceancloud.grampus.framework.sequence.generator.IdGenerator;
import com.oceancloud.grampus.framework.sequence.generator.SnowflakeIdGenerator;
import com.oceancloud.grampus.framework.sequence.utils.SnowflakeIdWorker;
import lombok.Getter;
import lombok.Setter;

/**
 * Snowflake ID生成器Builder
 *
 * @author Beck
 * @since 2020-12-03
 */
@Getter
@Setter
public class SnowflakeIdGeneratorBuilder implements IdGeneratorBuilder {
	/**
	 * 机器ID(0-31)
	 */
	private long workerId;
	/**
	 * 数据中心ID(0-31)
	 */
	private long dataCenterId;

	public static SnowflakeIdGeneratorBuilder builder() {
		return new SnowflakeIdGeneratorBuilder();
	}

	@Override
	public IdGenerator build() {
		SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(workerId, dataCenterId);
		return new SnowflakeIdGenerator(snowflakeIdWorker);
	}

	public SnowflakeIdGeneratorBuilder workerId(long workerId) {
		this.workerId = workerId;
		return this;
	}

	public SnowflakeIdGeneratorBuilder dataCenterId(long dataCenterId) {
		this.dataCenterId = dataCenterId;
		return this;
	}
}
