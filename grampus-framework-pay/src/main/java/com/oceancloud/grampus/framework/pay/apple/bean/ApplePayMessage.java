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
	 * 支付订单号（业务支付订单ID）
	 * <p>
	 * 由客户端保证支付成功订单的具体业务订单ID
	 */
	private String payOrderNo;
	/**
	 * 交易的唯一标识符（第三方支付订单ID）
	 * <p>
	 * 由客户端保证支付成功订单的具体transactionId，inApp数组内只获取transactionId对应的数据进行处理，其余的数据记录到apple交易记录，方便分析人工补单。
	 */
	private String transactionId;
	/**
	 * 苹果内购收据
	 */
	private AppleReceiptData receiptData;
}
