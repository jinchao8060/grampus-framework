package com.oceancloud.grampus.framework.excel.aspect;

import com.oceancloud.grampus.framework.excel.annotation.ResponseExcel;
import com.oceancloud.grampus.framework.excel.processor.NameProcessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

/**
 * @author lengleng
 * @since 2021-5-14
 */
@Aspect
@RequiredArgsConstructor
public class DynamicNameAspect {

	public static final String EXCEL_NAME_KEY = "__EXCEL_NAME_KEY__";

	private final NameProcessor processor;

	@Before("@annotation(excel)")
	public void around(JoinPoint point, ResponseExcel excel) {
		MethodSignature ms = (MethodSignature) point.getSignature();
		String name = processor.doDetermineName(point.getArgs(), ms.getMethod(), excel.name());
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Objects.requireNonNull(requestAttributes).setAttribute(EXCEL_NAME_KEY, name, RequestAttributes.SCOPE_REQUEST);
	}
}
