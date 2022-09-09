package com.oceancloud.grampus.framework.rabbitmq.listener;

import com.oceancloud.grampus.framework.rabbitmq.listener.adapter.EnhancedBatchMessagingMessageListenerAdapter;
import com.oceancloud.grampus.framework.rabbitmq.listener.adapter.EnhancedMessagingMessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.AbstractRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A {@link EnhancedMethodRabbitListenerEndpoint} providing the method to invoke to process
 * an incoming message for this endpoint.
 *
 * @author Stephane Nicoll
 * @author Artem Bilan
 * @author Gary Russell
 *
 * @since 1.4
 */
public class EnhancedMethodRabbitListenerEndpoint extends AbstractRabbitListenerEndpoint {

	private Object bean;

	private Method method;

	private MessageHandlerMethodFactory messageHandlerMethodFactory;

	private boolean returnExceptions;

	private RabbitListenerErrorHandler errorHandler;

	/**
	 * Set the object instance that should manage this endpoint.
	 * @param bean the target bean instance.
	 */
	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return this.bean;
	}

	/**
	 * Set the method to invoke to process a message managed by this endpoint.
	 * @param method the target method for the {@link #bean}.
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	public Method getMethod() {
		return this.method;
	}

	/**
	 * Set the {@link MessageHandlerMethodFactory} to use to build the
	 * {@link InvocableHandlerMethod} responsible to manage the invocation
	 * of this endpoint.
	 * @param messageHandlerMethodFactory the {@link MessageHandlerMethodFactory} instance.
	 */
	public void setMessageHandlerMethodFactory(MessageHandlerMethodFactory messageHandlerMethodFactory) {
		this.messageHandlerMethodFactory = messageHandlerMethodFactory;
	}

	/**
	 * Set whether exceptions thrown by the listener should be returned to the sender
	 * using the normal {@code replyTo/@SendTo} semantics.
	 * @param returnExceptions true to return exceptions.
	 * @since 2.0
	 */
	public void setReturnExceptions(boolean returnExceptions) {
		this.returnExceptions = returnExceptions;
	}

	/**
	 * Set the {@link RabbitListenerErrorHandler} to invoke if the listener method
	 * throws an exception.
	 * @param errorHandler the error handler.
	 * @since 2.0
	 */
	public void setErrorHandler(RabbitListenerErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/**
	 * @return the messageHandlerMethodFactory
	 */
	protected MessageHandlerMethodFactory getMessageHandlerMethodFactory() {
		return this.messageHandlerMethodFactory;
	}

	@Override
	protected EnhancedMessagingMessageListenerAdapter createMessageListener(MessageListenerContainer container) {
		Assert.state(this.messageHandlerMethodFactory != null,
				"Could not create message listener - MessageHandlerMethodFactory not set");
		EnhancedMessagingMessageListenerAdapter messageListener = createMessageListenerInstance();
		messageListener.setHandlerAdapter(configureListenerAdapter(messageListener));
		String replyToAddress = getDefaultReplyToAddress();
		if (replyToAddress != null) {
			messageListener.setResponseAddress(replyToAddress);
		}
		MessageConverter messageConverter = getMessageConverter();
		if (messageConverter != null) {
			messageListener.setMessageConverter(messageConverter);
		}
		if (getBeanResolver() != null) {
			messageListener.setBeanResolver(getBeanResolver());
		}
		return messageListener;
	}

	/**
	 * Create a {@link HandlerAdapter} for this listener adapter.
	 * @param messageListener the listener adapter.
	 * @return the handler adapter.
	 */
	protected HandlerAdapter configureListenerAdapter(EnhancedMessagingMessageListenerAdapter messageListener) {
		InvocableHandlerMethod invocableHandlerMethod =
				this.messageHandlerMethodFactory.createInvocableHandlerMethod(getBean(), getMethod());
		return new HandlerAdapter(invocableHandlerMethod);
	}

	/**
	 * Create an empty {@link MessagingMessageListenerAdapter} instance.
	 * @return the {@link MessagingMessageListenerAdapter} instance.
	 */
	protected EnhancedMessagingMessageListenerAdapter createMessageListenerInstance() {
		if (isBatchListener()) {
			return new EnhancedBatchMessagingMessageListenerAdapter(this.bean, this.method, this.returnExceptions,
					this.errorHandler, getBatchingStrategy());
		}
		else {
			return new EnhancedMessagingMessageListenerAdapter(this.bean, this.method, this.returnExceptions,
					this.errorHandler);
		}
	}

	@Nullable
	private String getDefaultReplyToAddress() {
		Method listenerMethod = getMethod();
		if (listenerMethod != null) {
			SendTo ann = AnnotationUtils.getAnnotation(listenerMethod, SendTo.class);
			if (ann != null) {
				String[] destinations = ann.value();
				if (destinations.length > 1) {
					throw new IllegalStateException("Invalid @" + SendTo.class.getSimpleName() + " annotation on '"
							+ listenerMethod + "' one destination must be set (got " + Arrays.toString(destinations) + ")");
				}
				return destinations.length == 1 ? resolveSendTo(destinations[0]) : "";
			}
		}
		return null;
	}

	private String resolveSendTo(String value) {
		if (getBeanFactory() != null) {
			String resolvedValue = getBeanExpressionContext().getBeanFactory().resolveEmbeddedValue(value);
			Object newValue = getResolver().evaluate(resolvedValue, getBeanExpressionContext());
			Assert.isInstanceOf(String.class, newValue, "Invalid @SendTo expression");
			return (String) newValue;
		}
		else {
			return value;
		}
	}

	@Override
	protected StringBuilder getEndpointDescription() {
		return super.getEndpointDescription()
				.append(" | bean='").append(this.bean).append("'")
				.append(" | method='").append(this.method).append("'");
	}

}
