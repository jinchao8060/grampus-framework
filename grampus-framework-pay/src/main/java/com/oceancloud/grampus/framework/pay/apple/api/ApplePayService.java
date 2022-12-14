package com.oceancloud.grampus.framework.pay.apple.api;

import com.egzosn.pay.common.api.BasePayService;
import com.egzosn.pay.common.bean.*;
import com.egzosn.pay.common.bean.result.PayException;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.util.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.pay.apple.bean.ApplePayMessage;
import com.oceancloud.grampus.framework.pay.apple.bean.AppleReceiptData;
import com.oceancloud.grampus.framework.pay.apple.utils.AppleReceiptVerifyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Set;

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
		String receiptDataString = (String) body.get("receiptData");

		AppleReceiptData receiptData = AppleReceiptVerifyUtil.verifyReceipt(receiptDataString);
		Integer status = receiptData.getStatus();
		AppleReceiptData.ReceiptDTO receipt = receiptData.getReceipt();
		String environment = receiptData.getEnvironment();
		String curEnvironment = payConfigStorage.getEnvironment();
		Set<String> curBundleIdSet = payConfigStorage.getBundleId();

		body.put("receiptDataPlain", receiptData);
		body.put("receiptData", receiptDataString);

		return status.equals(0) && curEnvironment.equals(environment) && curBundleIdSet.contains(receipt.getBundleId());
	}

	/**
	 * ??????????????????????????????????????? Map
	 *
	 * @param request ????????????
	 * @return ???????????????????????????
	 */
	@Override
	public NoticeParams getNoticeParams(NoticeRequest request) {
		NoticeParams noticeParams = new NoticeParams();
		try (InputStream is = request.getInputStream()) {
			String body = IOUtils.toString(is);
			JsonNode jsonNode = JSONUtil.readTree(body);
			String receiptData = jsonNode.get("receiptData").asText();
			String payOrderNo = jsonNode.get("payOrderNo").asText();
			Map<String, Object> bodyMap = Maps.newHashMap();
			bodyMap.put("receiptData", receiptData);
			bodyMap.put("payOrderNo", payOrderNo);
			noticeParams.setBody(bodyMap);
		} catch (IOException e) {
			throw new PayErrorException(new PayException("failure", "????????????????????????"), e);
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
	 * ????????????
	 *
	 * @param message ???????????????????????????
	 * @return ??????????????????
	 */
	@Override
	public PayMessage createMessage(Map<String, Object> message) {
		AppleReceiptData receiptData = (AppleReceiptData) message.get("receiptDataPlain");
		String receiptDataString = (String) message.get("receiptData");
//		String receiptDataString = (String) message.get("receiptData");
		String payOrderNo = (String) message.get("payOrderNo");
//		AppleReceiptData receiptData = AppleReceiptVerifyUtil.verifyReceipt(receiptDataString);
		ApplePayMessage applePayMessage = new ApplePayMessage();
		applePayMessage.setPayOrderNo(payOrderNo);
		applePayMessage.setReceiptData(receiptData);
		applePayMessage.setReceiptDataString(receiptDataString);
		return applePayMessage;
	}
}
