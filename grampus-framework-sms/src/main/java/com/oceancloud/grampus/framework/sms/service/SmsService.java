package com.oceancloud.grampus.framework.sms.service;

import com.oceancloud.grampus.framework.sms.dto.SendSmsDTO;

/**
 * SmsService
 *
 * @author Beck
 * @since 2022-07-22
 */
public interface SmsService {

	/**
	 * 发送短信
	 *
	 * @param sendSmsDTO 发送短信入参
	 * @return 发送成功true 发送失败false
	 */
	Boolean sendSms(SendSmsDTO sendSmsDTO);
}
