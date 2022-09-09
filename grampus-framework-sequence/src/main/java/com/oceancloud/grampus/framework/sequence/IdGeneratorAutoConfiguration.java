package com.oceancloud.grampus.framework.sequence;

import com.oceancloud.grampus.framework.sequence.builder.SnowflakeIdGeneratorBuilder;
import com.oceancloud.grampus.framework.sequence.generator.IdGenerator;
import com.oceancloud.grampus.framework.sequence.properties.SnowflakeIdGeneratorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * IdGeneratorAutoConfiguration
 *
 * @author Beck
 * @since 2020-12-03
 */
@Configuration
@ComponentScan("com.oceancloud.grampus.framework.sequence")
@ConditionalOnMissingBean(IdGenerator.class)
public class IdGeneratorAutoConfiguration {

	/**
	 * snowflak 算法作为ID生成器实现
	 */
	@Bean
	@ConditionalOnBean(SnowflakeIdGeneratorProperties.class)
	public IdGenerator idGenerator(SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties) {
		return SnowflakeIdGeneratorBuilder.builder()
				.dataCenterId(snowflakeIdGeneratorProperties.getDataCenterId())
				.workerId(snowflakeIdGeneratorProperties.getWorkerId())
				.build();
	}
}
