package com.oceancloud.grampus.framework.sms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 腾讯云发送短信DTO
 *
 * @author Beck
 * @since 2022-07-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TencentSendSmsDTO extends SendSmsDTO {
	private static final long serialVersionUID = 290583239752424207L;
	/**
	 * 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666
	 * 应用 ID 可前往 [短信控制台](https://console.cloud.tencent.com/smsv2/app-manage) 查看
	 * String sdkAppId = "1400009099";
	 */
	private String sdkAppId;
	/**
	 * 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名
	 * 签名信息可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-sign) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-sign) 的签名管理查看
	 * String signName = "腾讯云";
	 */
	private String signName;
	/**
	 * 模板 ID: 必须填写已审核通过的模板 ID
	 * 模板 ID 可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-template) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-template) 的正文模板管理查看
	 * String templateId = "449739";
	 */
	private String templateId;
	/**
	 * 模板参数: 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致，若无模板参数，则设置为空
	 * String[] templateParamSet = {"1234"};
	 */
	private String[] templateParam;
	/**
	 * 用户的 session 内容（无需要可忽略）: 可以携带用户侧 ID 等上下文信息，server 会原样返回
	 * String sessionContext = "";
	 */
	private String sessionContext;
	/**
	 * 短信码号扩展号（无需要可忽略）: 默认未开通，如需开通请联系 [腾讯云短信小助手]
	 * String extendCode = "";
	 */
	private String extendCode;
	/**
	 * 国际/港澳台短信 SenderId（无需要可忽略）: 国内短信填空，默认未开通，如需开通请联系 [腾讯云短信小助手]
	 * String senderId = "";
	 */
	private String senderId;
}
