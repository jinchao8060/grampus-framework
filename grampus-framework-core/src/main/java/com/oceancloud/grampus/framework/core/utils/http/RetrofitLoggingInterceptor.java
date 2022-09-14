package com.oceancloud.grampus.framework.core.utils.http;

import com.oceancloud.grampus.framework.core.utils.Exceptions;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrofit 请求日志输出拦截器
 *
 * @author Beck
 * @since 2021-09-17
 */
@Slf4j
public class RetrofitLoggingInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		// 构建成一条长 日志，避免并发下日志错乱
		StringBuilder beforeReqLog = new StringBuilder(300);
		// 日志参数
		List<Object> beforeReqArgs = new ArrayList<>();
		beforeReqLog.append("\n\n================ Retrofit2 Request Start  ================\n");
		// 打印路由
		beforeReqLog.append("===> {}: {}\n");
		// 参数
		String requestMethod = request.method();
		String requestUrl = request.url().toString();
		beforeReqArgs.add(requestMethod);
		beforeReqArgs.add(requestUrl);

		// 打印请求头
		Headers headers = request.headers();
		for (String headerName : headers.names()) {
			beforeReqLog.append("===Headers===  {}: {}\n");
			beforeReqArgs.add(headerName);
			beforeReqArgs.add(request.header(headerName));
		}

		// 打印请求体
		beforeReqLog.append("===RequestBody===  {}\n");
		beforeReqArgs.add(parseRequestBody(request.body()));

		beforeReqLog.append("================  Retrofit2 Request End  =================\n");
		log.debug(beforeReqLog.toString(), beforeReqArgs.toArray());

		// 开始时间
		long startNanoTime = System.nanoTime();

		// 执行调用 TODO 性能问题
		Response response = chain.proceed(chain.request());

		MediaType mediaType = response.body().contentType();
		String content = response.body().string();

		// 构建成一条长 日志，避免并发下日志错乱
		StringBuilder responseLog = new StringBuilder(300);
		// 日志参数
		List<Object> responseArgs = new ArrayList<>();
		responseLog.append("\n\n================ Retrofit2 Response Start  ================\n");
		// 打印路由 200 get: /api/xxx/xxx
		responseLog.append("<=== {} {}: {} ({})\n");
		// 参数
		responseArgs.add(response.code());
		responseArgs.add(requestMethod);
		responseArgs.add(requestUrl);
		// 请求耗时计算
		String tookTime = timeFormat(System.nanoTime() - startNanoTime);
		responseArgs.add(tookTime);
		// 打印请求头
		Headers respHeaders = response.headers();
		for (String respHeaderName : respHeaders.names()) {
			responseLog.append("===Headers===  {}: {}\n");
			responseArgs.add(respHeaderName);
			responseArgs.add(response.header(respHeaderName));
		}
		// 打印响应体
		responseLog.append("===ResponseBody===  {}\n");
		responseArgs.add(content);

		responseLog.append("================  Retrofit2 Response End  =================\n");
		log.debug(responseLog.toString(), responseArgs.toArray());

		return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
	}

	private String parseRequestBody(RequestBody body)  {
		if (body == null) {
			return null;
		}
		Buffer buffer = new Buffer();
		try {
			body.writeTo(buffer);
		} catch (IOException e) {
			Exceptions.unchecked(e);
		}
		MediaType contentType = body.contentType();
		Charset charset = contentType != null ?
			contentType.charset(StandardCharsets.UTF_8) :
			StandardCharsets.UTF_8;
		return buffer.readString(charset);
	}

	private String timeFormat(long nanos) {
		if (nanos < 1L) {
			return "0ms";
		} else {
			double millis = (double)nanos / 1000000.0D;
			return millis > 1000.0D ? String.format("%.3fs", millis / 1000.0D) : String.format("%.3fms", millis);
		}
	}
}
