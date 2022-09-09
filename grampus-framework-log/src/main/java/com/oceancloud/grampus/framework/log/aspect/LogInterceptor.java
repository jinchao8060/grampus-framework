package com.oceancloud.grampus.framework.log.aspect;

import com.oceancloud.grampus.framework.core.utils.Exceptions;
import com.oceancloud.grampus.framework.core.utils.StringPool;
import com.oceancloud.grampus.framework.log.annotation.RequestLog;
import com.oceancloud.grampus.framework.log.event.LogEvent;
import com.oceancloud.grampus.framework.log.factory.LogEventFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 请求日志AOP处理器 TODO 考虑发布订阅是否使用Redis实现
 *
 * @author Beck
 * @since 2021-05-27
 */
@Slf4j
@AllArgsConstructor
public class LogInterceptor implements MethodInterceptor {

	private final ApplicationContext applicationContext;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Environment environment = applicationContext.getEnvironment();
		// 应用名、注解、类、方法信息
		String applicationName = environment.getProperty("spring.application.name");
		RequestLog requestLog = invocation.getMethod().getAnnotation(RequestLog.class);
		String strClassName = invocation.getThis().getClass().getName();
		String strMethodName = invocation.getMethod().getName();
		LogEvent event = LogEventFactory.buildLogEvent(invocation);
		event.setModule(applicationName);
		event.setDescription(requestLog.value());
		event.setClassMethod(strClassName + StringPool.HASH + strMethodName);
		event.setRequestStartTime(LocalDateTime.now());
		// 执行时间
		long startNs = System.nanoTime();
		try {
			Object result = invocation.proceed();
			// 耗时
			event.setRequestEndTime(LocalDateTime.now());
			event.setCostTime(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs));
			event.setSuccessful(Boolean.TRUE);
			// 发送异步日志事件
			applicationContext.publishEvent(event);
			return result;
		} catch (Throwable e) {
			// 耗时
			event.setRequestEndTime(LocalDateTime.now());
			event.setCostTime(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs));
			// 异常详情
			event.setExceptionDetail(Exceptions.getStackTraceAsString(e));
			event.setSuccessful(Boolean.FALSE);
			// 发送异步日志事件
			applicationContext.publishEvent(event);
			throw e;
		}
	}
}
