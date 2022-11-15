package com.oceancloud.grampus.framework.pay.apple.bean;

import com.egzosn.pay.common.bean.PayMessage;
import lombok.Data;

/**
 * 支付回调消息，兼容退款回调
 *
 * @author Beck
 * @since 2022-11-15
 */
@Data
public class ApplePayMessage extends PayMessage {
	/**
	 * 支付订单号
	 */
	private String payOrderNo;
	/**
	 * 苹果内购收据
	 */
	private AppleReceiptData receiptData;
}
