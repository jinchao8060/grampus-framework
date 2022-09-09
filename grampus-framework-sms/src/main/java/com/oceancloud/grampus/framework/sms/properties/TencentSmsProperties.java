package com.oceancloud.grampus.framework.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 腾讯SMS发送配置
 *
 * @author Beck
 * @since 2022-07-22
 */
@Data
@ConfigurationProperties(prefix = "sms.tencent")
public class TencentSmsProperties implements Serializable {
	private static final long serialVersionUID = 4902661617840937106L;
	/**
	 * 指定接入地域域名，默认就近地域接入域名为 sms.tencentcloudapi.com ，也支持指定地域域名访问，例如广州地域的域名为 sms.ap-guangzhou.tencentcloudapi.com
	 */
	private String endpoint = "sms.tencentcloudapi.com";
	/**
	 * 实例化要请求产品(以sms为例)的client对象
	 * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8
	 */
	private String region = "ap-guangzhou";
	/**
	 * 腾讯云账户密钥对secretId，secretKey。
	 * SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi
	 */
	private String secretId;
	/**
	 * 腾讯云账户密钥对secretId，secretKey。
	 * SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi
	 */
	private String secretKey;
}
