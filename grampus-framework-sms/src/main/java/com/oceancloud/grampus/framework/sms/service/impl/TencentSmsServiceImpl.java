package com.oceancloud.grampus.framework.sms.service.impl;

import com.google.common.collect.Lists;
import com.oceancloud.grampus.framework.core.utils.CollectionUtil;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.sms.dto.SendSmsDTO;
import com.oceancloud.grampus.framework.sms.dto.TencentSendSmsDTO;
import com.oceancloud.grampus.framework.sms.service.SmsService;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * 腾讯短信发送
 * <p>
 * https://cloud.tencent.com/document/product/382/43194
 *
 * @author Beck
 * @since 2022-07-22
 */
@Slf4j
@AllArgsConstructor
@Component
public class TencentSmsServiceImpl implements SmsService {

	private final SmsClient tencentSmsClient;

	@Override
	public Boolean sendSms(SendSmsDTO sendSmsDTO) {

		LinkedList<SendSmsDTO.PhoneNum> phoneNumberList = Lists.newLinkedList();
		if (CollectionUtil.isNotEmpty(sendSmsDTO.getPhoneNums())) {
			phoneNumberList.addAll(sendSmsDTO.getPhoneNums());
		}
		if (sendSmsDTO.getPhoneNum() != null) {
			phoneNumberList.add(sendSmsDTO.getPhoneNum());
		}

		// 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
		// 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号
		// String[] phoneNumberSet = {"+8621212313123", "+8612345678902", "+8612345678903"};
		String[] phoneNumberSet = (String[]) phoneNumberList.stream()
				.map(phoneNum -> "+" + phoneNum.getAreaCode() + phoneNum.getPhone())
				.toArray();

		TencentSendSmsDTO smsDTO = (TencentSendSmsDTO) sendSmsDTO;

		SendSmsRequest req = new SendSmsRequest();
		req.setSmsSdkAppId(smsDTO.getSdkAppId());
		req.setSignName(smsDTO.getSignName());
		req.setTemplateId(smsDTO.getTemplateId());
		req.setTemplateParamSet(smsDTO.getTemplateParam());
		req.setPhoneNumberSet(phoneNumberSet);
		req.setSessionContext(smsDTO.getSessionContext());
		req.setExtendCode(smsDTO.getExtendCode());
		req.setSenderId(smsDTO.getSenderId());

		try {
			SendSmsResponse res = tencentSmsClient.SendSms(req);

			log.info("SmsService.sendSms tencent phoneNum:{}, content:{}, result:{}",
					phoneNumberSet, JSONUtil.writeValueAsBytes(req), SendSmsResponse.toJsonString(res));
		} catch (Exception e) {
			// 当出现以下错误码时，快速解决方案参考
			// [FailedOperation.SignatureIncorrectOrUnapproved](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Afailedoperation.signatureincorrectorunapproved-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
			// [FailedOperation.TemplateIncorrectOrUnapproved](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Afailedoperation.templateincorrectorunapproved-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
			// [UnauthorizedOperation.SmsSdkAppIdVerifyFail](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Aunauthorizedoperation.smssdkappidverifyfail-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
			// [UnsupportedOperation.ContainDomesticAndInternationalPhoneNumber](https://cloud.tencent.com/document/product/382/9558#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E6.8F.90.E7.A4.BA.EF.BC.9Aunsupportedoperation.containdomesticandinternationalphonenumber-.E5.A6.82.E4.BD.95.E5.A4.84.E7.90.86.EF.BC.9F)
			// 更多错误，可咨询[腾讯云助手](https://tccc.qcloud.com/web/im/index.html#/chat?webAppId=8fa15978f85cb41f7e2ea36920cb3ae1&title=Sms)
			log.error("SmsService.sendSms tencent error.", e);
			return false;
		}
		return true;
	}
}
