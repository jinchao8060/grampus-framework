package com.oceancloud.grampus.framework.simphone.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.oceancloud.grampus.framework.simphone.properties.AliyunSimPhoneProperties;
import com.oceancloud.grampus.framework.simphone.service.AliyunSimPhoneService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * SimAutoConfiguration
 *
 * @author Beck
 * @since 2022-07-25
 */
@AllArgsConstructor
@EnableConfigurationProperties({AliyunSimPhoneProperties.class})
public class SimPhoneAutoConfiguration {

	@Bean("aliyunSimClient")
	public IAcsClient acsClient(AliyunSimPhoneProperties aliyunSimPhoneProperties) {
		DefaultProfile profile = DefaultProfile.getProfile(
				aliyunSimPhoneProperties.getRegionId(),
				aliyunSimPhoneProperties.getAccessKeyId(),
				aliyunSimPhoneProperties.getAccessKeySecret());
		return new DefaultAcsClient(profile);
	}

	@Bean("aliyunSimPhoneService")
	public AliyunSimPhoneService aliyunSimPhoneService(IAcsClient aliyunSimClient) {
		return new AliyunSimPhoneService(aliyunSimClient);
	}
}
