package com.oceancloud.grampus.framework.rabbitmq.listener;

import com.oceancloud.grampus.framework.rabbitmq.listener.adapter.EnhancedMessagingMessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.DelegatingInvocableHandler;
import org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.validation.Validator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary Russell
 * @since 1.5
 *
 */
public class EnhancedMultiMethodRabbitListenerEndpoint extends EnhancedMethodRabbitListenerEndpoint {

	private final List<Method> methods;

	private final Method defaultMethod;

	private Validator validator;

	/**
	 * Construct an instance for the provided methods and bean.
	 * @param methods the methods.
	 * @param bean the bean.
	 */
	public EnhancedMultiMethodRabbitListenerEndpoint(List<Method> methods, Object bean) {
		this(methods, null, bean);
	}

	/**
	 * Construct an instance for the provided methods, default method and bean.
	 * @param methods the methods.
	 * @param defaultMethod the default method.
	 * @param bean the bean.
	 * @since 2.0.3
	 */
	public EnhancedMultiMethodRabbitListenerEndpoint(List<Method> methods, @Nullable Method defaultMethod, Object bean) {
		this.methods = methods;
		this.defaultMethod = defaultMethod;
		setBean(bean);
	}

	/**
	 * Set a payload validator.
	 * @param validator the validator.
	 * @since 2.3.7
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	protected HandlerAdapter configureListenerAdapter(EnhancedMessagingMessageListenerAdapter messageListener) {
		List<InvocableHandlerMethod> invocableHandlerMethods = new ArrayList<InvocableHandlerMethod>();
		InvocableHandlerMethod defaultHandler = null;
		for (Method method : this.methods) {
			InvocableHandlerMethod handler = getMessageHandlerMethodFactory()
					.createInvocableHandlerMethod(getBean(), method);
			invocableHandlerMethods.add(handler);
			if (method.equals(this.defaultMethod)) {
				defaultHandler = handler;
			}
		}
		DelegatingInvocableHandler delegatingHandler = new DelegatingInvocableHandler(invocableHandlerMethods,
				defaultHandler, getBean(), getResolver(), getBeanExpressionContext(), this.validator);
		return new HandlerAdapter(delegatingHandler);
	}

}
