package com.oceancloud.grampus.framework.log.factory;

import com.oceancloud.grampus.framework.core.constant.Constant;
import com.oceancloud.grampus.framework.core.utils.BeanUtil;
import com.oceancloud.grampus.framework.core.utils.ClassUtil;
import com.oceancloud.grampus.framework.core.utils.CollectionUtil;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.core.utils.ObjectUtil;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.core.utils.WebUtil;
import com.oceancloud.grampus.framework.core.utils.StringPool;
import com.oceancloud.grampus.framework.log.event.LogEvent;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 系统日志构建工厂类
 *
 * @author Beck
 * @since 2021-05-28
 */
public class LogEventFactory {

	/**
	 * 构造 LogEvent
	 *
	 * @return LogEvent
	 */
	public static LogEvent buildLogEvent(MethodInvocation invocation) {
		LogEvent event = new LogEvent();
		HttpServletRequest request = WebUtil.getRequest();
		String requestMethod = request.getMethod();
		String requestUri = request.getRequestURI();
		event.setRequestMethod(requestMethod);
		event.setRequestUri(requestUri);
		// paramMap
		event.setRequestParam(buildGetParams(request));
		// 获取请求 ip 和 ua
		event.setRequestIp(WebUtil.getIP());
		event.setAuthorization(request.getHeader(Constant.AUTHORIZATION_HEADER));
		event.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		// 设置post json数据
		event.setRequestBody(buildPostJson(invocation));
		return event;
	}

	private static String buildGetParams(HttpServletRequest request) {
		Map<String, String[]> paraMap = request.getParameterMap();
		if (ObjectUtil.isEmpty(paraMap)) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		paraMap.forEach((key, values) -> {
			builder.append(key).append(StringPool.EQUALS);
			if ("password".equalsIgnoreCase(key)) {
				builder.append("******");
			} else {
				builder.append(StringUtil.join(values));
			}
			builder.append(StringPool.AMPERSAND);
		});
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	private static String buildPostJson(MethodInvocation invocation) {
		Object[] args = invocation.getArguments();
		Method method = invocation.getMethod();
		// 一次请求只能有一个 request body
		Object requestBodyValue = null;
		for (int i = 0; i < args.length; i++) {
			// 读取方法参数
			MethodParameter methodParam = ClassUtil.getMethodParameter(method, i);
			RequestBody requestBody = methodParam.getParameterAnnotation(RequestBody.class);
			// 如果是body的json则是对象
			if (requestBody != null) {
				requestBodyValue = args[i];
				break;
			}
		}
		// 若使用BeanUtil.toMap对requestBodyValue转Map会导致原入参的值被修改
		Map<String, Object> requestBodyMap = BeanUtil.toNewMap(requestBodyValue);
		if (requestBodyMap.containsKey("password")) {
			requestBodyMap.put("password", "******");
		}
		return CollectionUtil.isEmpty(requestBodyMap) ? null : JSONUtil.writeValueAsString(requestBodyMap);
	}
}