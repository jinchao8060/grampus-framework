package com.oceancloud.grampus.framework.sms.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.oceancloud.grampus.framework.sms.properties.AliyunSmsProperties;
import com.oceancloud.grampus.framework.sms.properties.TencentSmsProperties;
import com.oceancloud.grampus.framework.sms.properties.TwilioSmsProperties;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * SmsAutoConfiguration
 *
 * @author Beck
 * @since 2022-07-22
 */
@EnableConfigurationProperties({TwilioSmsProperties.class, AliyunSmsProperties.class, TencentSmsProperties.class})
public class SmsAutoConfiguration {

	/**
	 * https://help.aliyun.com/document_detail/112148.html
	 */
	@Bean("aliyunSmsClient")
	public IAcsClient aliyunSmsAcsClient(AliyunSmsProperties aliyunSmsProperties) {
		String regionId = aliyunSmsProperties.getRegionId();
		String accessKeyId = aliyunSmsProperties.getAccessKeyId();
		String accessKeySecret = aliyunSmsProperties.getAccessKeySecret();
		DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
		return new DefaultAcsClient(profile);
	}

	/**
	 * https://cloud.tencent.com/document/product/382/43194
	 */
	@Bean("tencentSmsClient")
	public SmsClient tencentSmsClient(TencentSmsProperties tencentSmsProperties) {
		String secretId = tencentSmsProperties.getSecretId();
		String secretKey = tencentSmsProperties.getSecretKey();
		String region = tencentSmsProperties.getRegion();
		Credential cred = new Credential(secretId, secretKey);
		return new SmsClient(cred, region);
	}
}
