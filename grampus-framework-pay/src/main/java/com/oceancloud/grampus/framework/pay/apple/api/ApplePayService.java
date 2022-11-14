package com.oceancloud.grampus.framework.pay.apple.api;

import com.egzosn.pay.common.api.BasePayService;
import com.egzosn.pay.common.bean.*;
import com.egzosn.pay.common.bean.result.PayException;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.util.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.pay.apple.bean.AppleReceiptData;
import com.oceancloud.grampus.framework.pay.apple.utils.AppleReceiptVerifyUtil;
import org.assertj.core.util.Maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * ApplePayService
 *
 * @author Beck
 * @since 2022-11-07
 */
public class ApplePayService extends BasePayService<ApplePayConfigStorage> {

	public ApplePayService(ApplePayConfigStorage payConfigStorage) {
		super(payConfigStorage);
	}

	public ApplePayService(ApplePayConfigStorage payConfigStorage, HttpConfigStorage configStorage) {
		super(payConfigStorage, configStorage);
	}

	@Override
	public boolean verify(Map<String, Object> map) {
		return false;
	}

	@Override
	public boolean verify(NoticeParams noticeParams) {
		Map<String, Object> body = noticeParams.getBody();
		String receiptDataString = (String) body.get("receipt-data");

		AppleReceiptData receiptData = AppleReceiptVerifyUtil.verifyReceipt(receiptDataString);
		Integer status = receiptData.getStatus();
		String environment = receiptData.getEnvironment();
		String curEnvironment = payConfigStorage.getEnvironment();
		return status.equals(0) && curEnvironment.equals(environment);
	}

	/**
	 * 将请求参数或者请求流转化为 Map
	 *
	 * @param request 通知请求
	 * @return 获得回调的请求参数
	 */
	@Override
	public NoticeParams getNoticeParams(NoticeRequest request) {
		NoticeParams noticeParams = new NoticeParams();
		try (InputStream is = request.getInputStream()) {
			String body = IOUtils.toString(is);
			JsonNode jsonNode = JSONUtil.readTree(body);
			String receiptData = jsonNode.get("receipt-data").asText();
			noticeParams.setBody(Maps.newHashMap("receipt-data", receiptData));
		}
		catch (IOException e) {
			throw new PayErrorException(new PayException("failure", "获取回调参数异常"), e);
		}
		return noticeParams;
	}

	@Override
	public <O extends PayOrder> Map<String, Object> orderInfo(O o) {
		return null;
	}

	@Override
	public PayOutMessage getPayOutMessage(String s, String s1) {
		return null;
	}

	@Override
	public PayOutMessage successPayOutMessage(PayMessage payMessage) {
		return null;
	}

	@Override
	public String buildRequest(Map<String, Object> map, MethodType methodType) {
		return null;
	}

	@Override
	public <O extends PayOrder> String getQrPay(O o) {
		return null;
	}

	@Override
	public <O extends PayOrder> Map<String, Object> microPay(O o) {
		return null;
	}

	@Override
	public Map<String, Object> query(String s, String s1) {
		return null;
	}

	@Override
	public Map<String, Object> query(AssistOrder assistOrder) {
		return null;
	}

	@Override
	public Map<String, Object> close(String s, String s1) {
		return null;
	}

	@Override
	public Map<String, Object> close(AssistOrder assistOrder) {
		return null;
	}

	@Override
	public RefundResult refund(RefundOrder refundOrder) {
		return null;
	}

	@Override
	public Map<String, Object> refundquery(RefundOrder refundOrder) {
		return null;
	}

	@Override
	public Map<String, Object> downloadBill(Date date, BillType billType) {
		return null;
	}

	@Override
	public String getReqUrl(TransactionType transactionType) {
		return null;
	}

	/**
	 * 创建消息
	 *
	 * @param message 支付平台返回的消息
	 * @return 支付消息对象
	 */
	@Override
	public PayMessage createMessage(Map<String, Object> message) {
		String receipt = (String) message.get("receipt-data");
		return AppleReceiptVerifyUtil.verifyReceipt(receipt);
	}
}
