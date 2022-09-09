package com.oceancloud.grampus.framework.rabbitmq.listener.adapter;

import com.oceancloud.grampus.framework.core.utils.spring.SpringContextHolder;
import com.oceancloud.grampus.framework.gray.constant.GrayLoadBalancerConstant;
import com.oceancloud.grampus.framework.gray.utils.GrayContextUtil;
import com.oceancloud.grampus.framework.rabbitmq.listener.support.GrayRabbitLoadBalancerSupport;
import com.oceancloud.grampus.framework.rabbitmq.utils.RabbitGrayContextUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareBatchMessageListener;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.RabbitExceptionTranslator;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A listener adapter for batch listeners.
 *
 * @author Gary Russell
 * @since 2.2
 *
 */
public class EnhancedBatchMessagingMessageListenerAdapter extends EnhancedMessagingMessageListenerAdapter
		implements ChannelAwareBatchMessageListener {

	private final MessagingMessageConverterAdapter converterAdapter;

	private final BatchingStrategy batchingStrategy;

	public EnhancedBatchMessagingMessageListenerAdapter(Object bean, Method method, boolean returnExceptions,
														RabbitListenerErrorHandler errorHandler, @Nullable BatchingStrategy batchingStrategy) {

		super(bean, method, returnExceptions, errorHandler, true);
		this.converterAdapter = (MessagingMessageConverterAdapter) getMessagingMessageConverter();
		this.batchingStrategy = batchingStrategy == null ? new SimpleBatchingStrategy(0, 0, 0L) : batchingStrategy;
	}

	@Override
	public void onMessageBatch(List<org.springframework.amqp.core.Message> messages, Channel channel) {
		Message<?> converted;
		if (this.converterAdapter.isAmqpMessageList()) {
			converted = new GenericMessage<>(messages);
		}
		else {
			List<Message<?>> messagingMessages = new ArrayList<>();
			for (org.springframework.amqp.core.Message message : messages) {
				messagingMessages.add(toMessagingMessage(message));
			}
			if (this.converterAdapter.isMessageList()) {
				converted = new GenericMessage<>(messagingMessages);
			}
			else {
				List<Object> payloads = new ArrayList<>();
				for (Message<?> message : messagingMessages) {
					payloads.add(message.getPayload());
				}
				converted = new GenericMessage<>(payloads);
			}
		}
		try {
			// TODO 灰度暂未支持批量模式
			invokeHandlerAndProcessResult(null, channel, converted);
		}
		catch (Exception e) {
			throw RabbitExceptionTranslator.convertRabbitAccessException(e);
		}
	}

	@Override
	protected Message<?> toMessagingMessage(org.springframework.amqp.core.Message amqpMessage) {
		if (this.batchingStrategy.canDebatch(amqpMessage.getMessageProperties())) {

			if (this.converterAdapter.isMessageList()) {
				List<Message<?>> messages = new ArrayList<>();
				this.batchingStrategy.deBatch(amqpMessage, fragment -> {
					messages.add(super.toMessagingMessage(fragment));
				});
				return new GenericMessage<>(messages);
			}
			else {
				List<Object> list = new ArrayList<>();
				this.batchingStrategy.deBatch(amqpMessage, fragment -> {
					list.add(this.converterAdapter.extractPayload(fragment));
				});
				return MessageBuilder.withPayload(list)
						.copyHeaders(this.converterAdapter
								.getHeaderMapper()
								.toHeaders(amqpMessage.getMessageProperties()))
						.build();
			}
		}
		return super.toMessagingMessage(amqpMessage);
	}

}
