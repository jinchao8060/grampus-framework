package com.oceancloud.grampus.framework.core.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Miscellaneous utilities for web applications.
 *
 * @author L.cm
 */
@Slf4j
@UtilityClass
public class WebUtil extends org.springframework.web.util.WebUtils {

	/**
	 * 判断是否ajax请求
	 * spring ajax 返回含有 ResponseBody 或者 RestController注解
	 *
	 * @param handlerMethod HandlerMethod
	 * @return 是否ajax请求
	 */
	public static boolean isBody(HandlerMethod handlerMethod) {
		ResponseBody responseBody = ClassUtil.getAnnotation(handlerMethod, ResponseBody.class);
		return responseBody != null;
	}

	/**
	 * 读取cookie
	 *
	 * @param name cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(String name) {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		return getCookieVal(request, name);
	}

	/**
	 * 读取cookie
	 *
	 * @param request HttpServletRequest
	 * @param name    cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * 清除 某个指定的cookie
	 *
	 * @param response HttpServletResponse
	 * @param key      cookie key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * 设置cookie
	 *
	 * @param response        HttpServletResponse
	 * @param name            cookie name
	 * @param value           cookie value
	 * @param maxAgeInSeconds maxage
	 */
	public static void setCookie(HttpServletResponse response, String name, @Nullable String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(StringPool.SLASH);
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 获取 HttpServletRequest
	 *
	 * @return {HttpServletRequest}
	 */
	@Nullable
	public static HttpServletRequest getRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
				.map(x -> (ServletRequestAttributes) x)
				.map(ServletRequestAttributes::getRequest)
				.orElse(null);
	}

	/**
	 * 获取 HttpServletResponse
	 *
	 * @return {HttpServletResponse}
	 */
	@Nullable
	public static HttpServletResponse getResponse() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
				.map(x -> (ServletRequestAttributes) x)
				.map(ServletRequestAttributes::getResponse)
				.orElse(null);
	}

	/**
	 * 获取ip
	 *
	 * @return {String}
	 */
	@Nullable
	public static String getIP() {
		return Optional.ofNullable(WebUtil.getRequest())
				.map(WebUtil::getIP)
				.orElse(null);
	}

	private static final String[] IP_HEADER_NAMES = new String[]{
			"x-forwarded-for",
			"Proxy-Client-IP",
			"WL-Proxy-Client-IP",
			"HTTP_CLIENT_IP",
			"HTTP_X_FORWARDED_FOR"
	};

	private static final Predicate<String> IS_BLANK_IP = (ip) -> StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip);

	/**
	 * 获取ip
	 *
	 * @param request HttpServletRequest
	 * @return {String}
	 */
	@Nullable
	public static String getIP(@Nullable HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String ip = null;
		for (String ipHeader : IP_HEADER_NAMES) {
			ip = request.getHeader(ipHeader);
			if (!IS_BLANK_IP.test(ip)) {
				break;
			}
		}
		if (IS_BLANK_IP.test(ip)) {
			ip = request.getRemoteAddr();
		}
		return StringUtil.isBlank(ip) ? null : StringUtil.splitTrim(ip, StringPool.COMMA)[0];
	}

	/**
	 * 获取Accept-Language
	 *
	 * @return Accept-Language
	 */
	public static String getAcceptLanguage() {
		HttpServletRequest request = WebUtil.getRequest();
		if(request == null){
			return null;
		}
		return request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
	}

	/**
	 * 返回json
	 *
	 * @param response HttpServletResponse
	 * @param result   结果对象
	 */
	public static void renderJson(HttpServletResponse response, @Nullable Object result) {
		String jsonText = JSONUtil.writeValueAsString(result);
		if (jsonText != null) {
			renderText(response, jsonText, MediaType.APPLICATION_JSON_VALUE);
		}
	}

	/**
	 * 返回json
	 *
	 * @param response HttpServletResponse
	 * @param jsonText json 文本
	 */
	public static void renderJson(HttpServletResponse response, @Nullable String jsonText) {
		if (jsonText != null) {
			renderText(response, jsonText, MediaType.APPLICATION_JSON_VALUE);
		}
	}

	/**
	 * 返回json
	 *
	 * @param response    HttpServletResponse
	 * @param text        文本
	 * @param contentType contentType
	 */
	public static void renderText(HttpServletResponse response, String text, String contentType) {
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(contentType);
		try (PrintWriter out = response.getWriter()) {
			out.append(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取请求参数相关日志
	 *
	 * @return 请求日志
	 */
	public static String getRequestLog() {
		HttpServletRequest request = getRequest();
		// 构建成一条长 日志，避免并发下日志错乱
		StringBuilder beforeReqLog = new StringBuilder(300);
		// 日志参数
		List<Object> beforeReqArgs = new ArrayList<>();
		beforeReqLog.append("\n\n================ Servlet Request Start  ================\n");
		// 打印路由
		beforeReqLog.append("===> %s: %s\n");
		// 参数
		String requestMethod = request.getMethod();
		String requestUrl = request.getRequestURL().toString();
		beforeReqArgs.add(requestMethod);
		beforeReqArgs.add(requestUrl);

		// 打印请求头
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			beforeReqLog.append("===Headers===  %s: %s\n");
			beforeReqArgs.add(headerName);
			beforeReqArgs.add(request.getHeader(headerName));
		}

		// 打印请求体
		beforeReqLog.append("===RequestBody===  %s\n");
		// 直接从HttpServletRequest的Reader流中获取RequestBody
		StringBuilder requestBody = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			String line = reader.readLine();
			while(line != null){
				requestBody.append(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			Exceptions.unchecked(e);
		}
		beforeReqArgs.add(requestBody.toString());

		beforeReqLog.append("================  Servlet Request End  =================\n");
		return String.format(beforeReqLog.toString(), beforeReqArgs.toArray());
	}
}

