package com.oceancloud.grampus.framework.sms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Twilio发送短信DTO
 *
 * @author Beck
 * @since 2022-07-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TwilioSendSmsDTO extends SendSmsDTO {
	private static final long serialVersionUID = 290583239752424207L;
	/**
	 * 短信内容
	 */
	private String msg;
}
