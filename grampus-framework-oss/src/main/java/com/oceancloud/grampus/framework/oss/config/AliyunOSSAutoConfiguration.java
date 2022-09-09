package com.oceancloud.grampus.framework.oss.config;

import com.oceancloud.grampus.framework.oss.client.AliyunOSSClient;
import com.oceancloud.grampus.framework.oss.properties.AliyunOssProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * AliyunOSSAutoConfiguration
 *
 * @author Beck
 * @since 2022-07-20
 */
@AllArgsConstructor
@EnableConfigurationProperties({AliyunOssProperties.class})
public class AliyunOSSAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public AliyunOSSClient aliyunOSSClient(AliyunOssProperties aliyunOssProperties) {
		return new AliyunOSSClient(aliyunOssProperties);
	}
}
