package com.oceancloud.grampus.framework.lock.aspect;

import com.oceancloud.grampus.framework.lock.annotation.DistributedLock;
import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 分布式锁AOP通知
 *
 * @author Beck
 * @since 2021-01-29
 */
public class LockAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
	private static final long serialVersionUID = 5197456013415866635L;

	private final Advice advice;

	private final Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(DistributedLock.class);

	public LockAnnotationAdvisor(@NonNull LockInterceptor lockInterceptor, int order) {
		this.advice = lockInterceptor;
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