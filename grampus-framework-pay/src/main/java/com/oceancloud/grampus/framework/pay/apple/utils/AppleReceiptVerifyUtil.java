package com.oceancloud.grampus.framework.pay.apple.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.pay.apple.bean.AppleReceiptData;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * 苹果IAP内购验证工具类
 *
 * @author Beck
 * @since 2022-10-19
 */
@UtilityClass
public class AppleReceiptVerifyUtil {

	private final RestTemplate restTemplate = new RestTemplate();

	/**
	 * 验证收据（<a href="https://developer.apple.com/documentation/appstorereceipts/verifyreceipt">verifyReceipt</a>）
	 * <p>
	 * 状态码（<a href="https://developer.apple.com/documentation/appstorereceipts/status">status</a>）
	 * 0 支付成功
	 * 21000 App Store不能读取你提供的JSON对象
	 * 21002 receipt-data域的数据有问题
	 * 21003 receipt无法通过验证
	 * 21004 提供的shared secret不匹配你账号中的shared secret
	 * 21005 receipt服务器当前不可用
	 * 21006 receipt合法，但是订阅已过期。服务器接收到这个状态码时，receipt数据仍然会解码并一起发送
	 * 21007 receipt是Sandbox receipt，但却发送至生产系统的验证服务
	 * 21008 receipt是生产receipt，但却发送至Sandbox环境的验证服务
	 * 21009 内部数据访问错误，稍后再试
	 * 21010 系统找不到该用户帐号或该用户帐号已被删除
	 *
	 * @param receipt 账单
	 * @return null或返回结果
	 */
	public AppleReceiptData verifyReceipt(String receipt) {
		String requestBody = "{\"receipt-data\":\"" + receipt + "\"}";
		// 生产环境
		String json = post("https://buy.itunes.apple.com/verifyReceipt", requestBody);
		JsonNode jsonNode = JSONUtil.readTree(json);
		String status = jsonNode.get("status").asText();
		if ("21007".equals(status)) {
			// 沙盒环境
			json = post("https://sandbox.itunes.apple.com/verifyReceipt", requestBody);
		}
		return JSONUtil.readValue(json, AppleReceiptData.class);
	}

	public String post(String requestUrl, String requestBody) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json");
		httpHeaders.add("Proxy-Connection", "Keep-Alive");
		HttpEntity<String> request = new HttpEntity<>(requestBody, httpHeaders);
		HttpEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, request, String.class);
		return response.getBody();
	}

//	public String post(String requestUrl, String requestBody) {
//		try {
//			SSLContext sc = SSLContext.getInstance("SSL");
//			sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
//			URL console = new URL(requestUrl);
//			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
//			conn.setSSLSocketFactory(sc.getSocketFactory());
//			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("content-type", "application/json");
//			conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			BufferedOutputStream hurlBufOus = new BufferedOutputStream(conn.getOutputStream());
//
//			hurlBufOus.write(requestBody.getBytes());
//			hurlBufOus.flush();
//
//			InputStream is = conn.getInputStream();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//			String line;
//			StringBuilder sb = new StringBuilder();
//			while ((line = reader.readLine()) != null) {
//				sb.append(line);
//			}
//			return sb.toString();
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//	private static class TrustAnyTrustManager implements X509TrustManager {
//
//		@Override
//		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//		}
//
//		@Override
//		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//		}
//
//		@Override
//		public X509Certificate[] getAcceptedIssuers() {
//			return new X509Certificate[]{};
//		}
//	}
//
//	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
//		@Override
//		public boolean verify(String hostname, SSLSession session) {
//			return true;
//		}
//	}
}