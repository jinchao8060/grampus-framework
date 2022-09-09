package com.oceancloud.grampus.framework.simphone.service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileRequest;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileResponse;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云手机号码获取
 * <p>
 * https://help.aliyun.com/document_detail/115500.html?spm=a2c4g.11186623.6.569.56147583cvj6HF
 *
 * @author Beck
 * @since 2022-07-25
 */
@Slf4j
@AllArgsConstructor
public class AliyunSimPhoneService implements SimPhoneService {

	private final IAcsClient aliyunSimClient;

	@Override
	public String getMobile(String accessToken) {
		GetMobileRequest request = new GetMobileRequest();
		request.setAccessToken(accessToken);
		try {
			GetMobileResponse response = aliyunSimClient.getAcsResponse(request);
			String phone = response.getGetMobileResultDTO().getMobile();
			log.debug("AliyunSimServiceImpl getMobile. accessToken:{} response:{}", accessToken, JSONUtil.writeValueAsString(response));
			return phone;
		} catch (Exception e) {
			log.error("AliyunSimServiceImpl getMobile error. accessToken:{} ", accessToken, e);
		}
		return null;
	}
}
