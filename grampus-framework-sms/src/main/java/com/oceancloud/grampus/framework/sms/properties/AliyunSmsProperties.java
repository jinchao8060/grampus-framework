package com.oceancloud.grampus.framework.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 阿里云SMS发送配置
 *
 * @author Beck
 * @since 2022-07-22
 */
@Data
@ConfigurationProperties(prefix = "sms.aliyun")
public class AliyunSmsProperties implements Serializable {
	private static final long serialVersionUID = 3520962493616158111L;
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
