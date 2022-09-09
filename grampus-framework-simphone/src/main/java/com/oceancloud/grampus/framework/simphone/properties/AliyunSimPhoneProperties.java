package com.oceancloud.grampus.framework.simphone.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AliyunSimProperties
 *
 * @author Beck
 * @since 2022-07-25
 */
@Data
@ConfigurationProperties(prefix = "sim.aliyun")
public class AliyunSimPhoneProperties {
	/**
	 * The region ID
	 */
	private String regionId = "cn-shenzhen";
	/**
	 * The AccessKey ID of the RAM account
	 */
	private String accessKeyId;
	/**
	 * The AccessKey Secret of the RAM account
	 */
	private String accessKeySecret;
}
