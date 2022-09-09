package com.oceancloud.grampus.framework.sequence.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Snowflake ID生成器配置
 *
 * @author Beck
 * @since 2020-12-03
 */
@Data
@Component
@ConfigurationProperties(prefix = "mapper.idgenerator.snowflake")
public class SnowflakeIdGeneratorProperties implements Serializable {
	private static final long serialVersionUID = 194187987481430560L;
	/**
	 * 机器ID(0-31)
	 */
	private long workerId;
	/**
	 * 数据中心ID(0-31)
	 */
	private long dataCenterId;
}
