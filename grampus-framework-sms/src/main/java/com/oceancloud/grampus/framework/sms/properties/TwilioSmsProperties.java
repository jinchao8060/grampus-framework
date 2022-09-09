package com.oceancloud.grampus.framework.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * TwilioSMS发送配置
 *
 * @author Beck
 * @since 2022-07-22
 */
@Data
@ConfigurationProperties(prefix = "sms.twilio")
public class TwilioSmsProperties implements Serializable {
	private static final long serialVersionUID = 8098594584922145181L;
	/**
	 * account to use
	 */
	private String accountSid;
	/**
	 * auth token for the account
	 */
	private String authToken;
	/**
	 * The phone number that initiated the message
	 */
	private String fromPhoneNum;
}
