package com.oceancloud.grampus.framework.sms.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 发送短信DTO
 *
 * @author Beck
 * @since 2022-07-22
 */
@Data
public class SendSmsDTO implements Serializable {
	private static final long serialVersionUID = 2829261699960696815L;
	/**
	 * 接收方手机号
	 */
	private PhoneNum phoneNum;
	/**
	 * 接收方手机号（批量 aliyun/tencent）
	 */
	private List<PhoneNum> phoneNums;

	/**
	 * 手机号码
	 */
	@Data
	public static class PhoneNum implements Serializable {
		private static final long serialVersionUID = 5813466709250492130L;
		/**
		 * 区号
		 */
		private String areaCode;
		/**
		 * 手机号
		 */
		private String phone;
	}
}
