package com.oceancloud.grampus.framework.log.aspect;

import com.oceancloud.grampus.framework.log.annotation.RequestLog;
import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 请求日志AOP通知
 *
 * @author Beck
 * @since 2021-05-27
 */
public class LogAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
	private static final long serialVersionUID = -2311838059335810152L;

	private final Advice advice;

	private final Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(RequestLog.class);

	public LogAnnotationAdvisor(@NonNull LogInterceptor logInterceptor, int order) {
		this.advice = logInterceptor;
		setOrder(order);
	}

	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (this.advice instanceof BeanFactoryAware) {
			((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
		}
	}
}
