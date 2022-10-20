package com.oceancloud.grampus.framework.pay.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.egzosn.pay.common.bean.NoticeRequest;

/**
 * web 相关请求支持（修复微信支付使用全小写获取请求头字段BUG）
 * <p>
 * WxPayService.verify 202行 noticeParams.getHeader("wechatpay-serial"); 使用小写获取请求头信息，实际上回调为大写开头。
 *
 * @author Egan
 * <pre>
 * email egzosn@gmail.com
 * date 2021/8/16
 * </pre>
 */
public class WxHttpRequestNoticeParamsFix implements NoticeRequest {

	private final HttpServletRequest httpServletRequest;

	public WxHttpRequestNoticeParamsFix(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	/**
	 * 根据请求头名称获取请求头信息
	 *
	 * @param name 名称
	 * @return 请求头值
	 */
	@Override
	public String getHeader(String name) {
		String header = httpServletRequest.getHeader(name);
		if (header != null) {
			return header;
		}
		return httpServletRequest.getHeader(formatHeaderName(name));
	}

	/**
	 * 根据请求头名称获取请求头信息
	 *
	 * @param name 名称
	 * @return 请求头值
	 */
	@Override
	public Enumeration<String> getHeaders(String name) {
		Enumeration<String> headers = httpServletRequest.getHeaders(name);
		if (headers != null && headers.hasMoreElements()) {
			return headers;
		}
		return httpServletRequest.getHeaders(formatHeaderName(name));
	}

	/**
	 * 获取所有的请求头名称
	 *
	 * @return 请求头名称
	 */
	@Override
	public Enumeration<String> getHeaderNames() {
		final Set<String> headers = new HashSet<>();
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.add(headerName);
			// 修复微信支付使用全小写获取请求头字段BUG
			if ("Wechatpay-Timestamp".equals(headerName)) {
				headers.add("wechatpay-timestamp");
			} else if ("Wechatpay-Signature-Type".equals(headerName)) {
				headers.add("wechatpay-signature-type");
			}else if ("Wechatpay-Serial".equals(headerName)) {
				headers.add("wechatpay-serial");
			}else if ("Wechatpay-Nonce".equals(headerName)) {
				headers.add("wechatpay-nonce");
			}else if ("Wechatpay-Signature".equals(headerName)) {
				headers.add("wechatpay-signature");
			}
		}

		Iterator<String> iterator = headers.iterator();

		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}
			@Override
			public String nextElement() {
				return iterator.next();
			}
		};
	}

	/**
	 * 输入流
	 *
	 * @return 输入流
	 * @throws IOException IOException
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return httpServletRequest.getInputStream();
	}

	/**
	 * 获取所有的请求参数
	 *
	 * @return 请求参数
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		return httpServletRequest.getParameterMap();
	}

	/**
	 * 请求头格式化（首字母大写）
	 *
	 * @param headerName 请求头名称
	 * @return 请求头
	 */
	public String formatHeaderName(String headerName) {
		String[] nameSplit = headerName.split("-");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nameSplit.length; i++) {
			String strFirst = nameSplit[i].substring(0, 1).toUpperCase();
			String strOther = nameSplit[i].substring(1).toLowerCase();
			sb.append(strFirst).append(strOther);
			if (i < nameSplit.length - 1) {
				sb.append("-");
			}
		}
		return sb.toString();
	}
}
