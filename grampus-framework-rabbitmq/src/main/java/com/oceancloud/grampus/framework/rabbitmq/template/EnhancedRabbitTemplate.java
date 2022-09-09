package com.oceancloud.grampus.framework.rabbitmq.template;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * EnhancedRabbitTemplate
 * 增强 RabbitTemplate，可进行Header设置
 *
 * @author Beck
 * @since 2021-07-22
 */
public class EnhancedRabbitTemplate extends RabbitTemplate {

	public void convertAndSend(String exchange, String routingKey, final Object object, MessageProperties messageProperties) throws AmqpException {
		Message message;
		if (object instanceof Message) {
			message = (Message) object;
		} else {
			message = this.getRequiredMessageConverter().toMessage(object, messageProperties);
		}
		send(exchange, routingKey, message);
	}

	private MessageConverter getRequiredMessageConverter() throws IllegalStateException {
		MessageConverter converter = getMessageConverter();
		if (converter == null) {
			throw new AmqpIllegalStateException(
				"No 'messageConverter' specified. Check configuration of RabbitTemplate.");
		}
		return converter;
	}
}
