package com.oceancloud.grampus.framework.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 云存储配置文件(目前只支持阿里云)
 *
 * @author Beck
 * @since 2019-10-24
 */
@Data
@ConfigurationProperties(prefix = "oss.aliyun")
public class AliyunOssProperties implements Serializable {
    private static final long serialVersionUID = -3109322525140169249L;
    /**
     * 域名
     */
    private String domain;
	/**
	 * endpoint
	 */
	private String endpoint;
	/**
	 * accesskeyId
	 */
	private String accesskeyId;
	/**
	 * accesskeySecret
	 */
	private String accesskeySecret;
	/**
	 * bucket名称
	 */
	private String bucketName;
}
