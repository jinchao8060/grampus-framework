package com.oceancloud.grampus.framework.sms.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.google.common.collect.Lists;
import com.oceancloud.grampus.framework.core.utils.CollectionUtil;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.sms.dto.AliyunSendSmsDTO;
import com.oceancloud.grampus.framework.sms.dto.SendSmsDTO;
import com.oceancloud.grampus.framework.sms.dto.TencentSendSmsDTO;
import com.oceancloud.grampus.framework.sms.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * 阿里短信发送
 * <p>
 * https://help.aliyun.com/document_detail/112148.html
 *
 * @author Beck
 * @since 2022-07-22
 */
@Slf4j
@AllArgsConstructor
@Component
public class AliyunSmsServiceImpl implements SmsService {

	private final IAcsClient aliyunSmsClient;

	@Override
	public Boolean sendSms(SendSmsDTO sendSmsDTO) {

		LinkedList<SendSmsDTO.PhoneNum> phoneNumberList = Lists.newLinkedList();
		if (CollectionUtil.isNotEmpty(sendSmsDTO.getPhoneNums())) {
			phoneNumberList.addAll(sendSmsDTO.getPhoneNums());
		}
		if (sendSmsDTO.getPhoneNum() != null) {
			phoneNumberList.add(sendSmsDTO.getPhoneNum());
		}

		// 接收短信的手机号码（"1368846****"）
		// 手机号码格式：
		// 国内短信：+/+86/0086/86或无任何前缀的11位手机号码，例如1590000****。
		// 国际/港澳台消息：国际区号+号码，例如852000012****。
		// 支持对多个手机号码发送短信，手机号码之间以半角逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
		String phoneNumbers = phoneNumberList.stream()
				.map(phoneNum -> phoneNum.getAreaCode() + phoneNum.getPhone())
				.collect(Collectors.joining(","));

		AliyunSendSmsDTO smsDTO = (AliyunSendSmsDTO) sendSmsDTO;

		SendSmsRequest request = new SendSmsRequest();
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(smsDTO.getSignName());
		request.setTemplateCode(smsDTO.getTemplateCode());
		request.setTemplateParam(smsDTO.getTemplateParam());

		try {
			SendSmsResponse result = aliyunSmsClient.getAcsResponse(request);
			log.info("SmsService.sendSms aliyun phoneNum:{}, content:{}, result:{}",
					phoneNumbers, JSONUtil.writeValueAsBytes(request), result);
		} catch (Exception e) {
			log.error("SmsService.sendSms aliyun error.", e);
			return false;
		}
		return true;
	}
}
