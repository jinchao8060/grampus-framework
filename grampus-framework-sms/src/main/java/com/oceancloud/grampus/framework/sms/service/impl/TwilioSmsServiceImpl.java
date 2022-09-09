package com.oceancloud.grampus.framework.sms.service.impl;

import com.oceancloud.grampus.framework.sms.dto.SendSmsDTO;
import com.oceancloud.grampus.framework.sms.dto.TwilioSendSmsDTO;
import com.oceancloud.grampus.framework.sms.properties.TwilioSmsProperties;
import com.oceancloud.grampus.framework.sms.service.SmsService;
import com.twilio.sdk.Twilio;
import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.resource.api.v2010.account.Message;
import com.twilio.sdk.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Twilio短信发送
 *
 * @author Beck
 * @since 2022-07-22
 */
@Slf4j
@AllArgsConstructor
@Component
public class TwilioSmsServiceImpl implements SmsService {

	private final TwilioSmsProperties twilioSmsProperties;

	@Override
	public Boolean sendSms(SendSmsDTO sendSmsDTO) {

		TwilioSendSmsDTO smsDTO = (TwilioSendSmsDTO) sendSmsDTO;

		String accountSid = twilioSmsProperties.getAccountSid();
		String authToken = twilioSmsProperties.getAuthToken();
		String fromPhoneNum = twilioSmsProperties.getFromPhoneNum();

		SendSmsDTO.PhoneNum phoneNum = smsDTO.getPhoneNum();
		String toPhoneNum = phoneNum.getAreaCode() + phoneNum.getPhone();

		Twilio.init(accountSid, authToken);

		try {
			// Send a text message
			Message result = new MessageCreator(
					accountSid,
					new PhoneNumber(toPhoneNum),
					new PhoneNumber(fromPhoneNum),
					smsDTO.getMsg()
			).execute();
			log.info("SmsService.sendSms twilio phoneNum:{}, content:{}, result:{}",
					toPhoneNum, smsDTO.getMsg(), result);
		} catch (Exception e) {
			log.error("SmsService.sendSms twilio error.", e);
		}

		return true;
	}
}
