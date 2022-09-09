package com.oceancloud.grampus.framework.sms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 阿里云发送短信DTO
 *
 * @author Beck
 * @since 2022-07-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AliyunSendSmsDTO extends SendSmsDTO {
	private static final long serialVersionUID = 290583239752424207L;
	/**
	 * 短信签名名称（"阿里云"）
	 * <p>
	 * 您可以登录短信服务控制台，选择国内消息或国际/港澳台消息，在签名管理页面获取
	 */
	private String signName;
	/**
	 * 短信模板CODE（"SMS_20933****"）
	 * <p>
	 * 您可以登录短信服务控制台，选择国内消息或国际/港澳台消息，在模板管理页面查看模板CODE。
	 */
	private String templateCode;
	/**
	 * 短信模板变量对应的实际值（"张三"）
	 * 支持传入多个参数，示例：{"name":"张三","number":"15038****76"}。
	 */
	private String templateParam;
}
