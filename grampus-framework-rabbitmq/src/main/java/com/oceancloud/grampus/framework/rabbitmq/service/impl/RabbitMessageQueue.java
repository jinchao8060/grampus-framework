package com.oceancloud.grampus.framework.rabbitmq.service.impl;

import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.core.utils.WebUtil;
import com.oceancloud.grampus.framework.gray.constant.GrayLoadBalancerConstant;
import com.oceancloud.grampus.framework.gray.support.GrayVersionContextHolder;
import com.oceancloud.grampus.framework.rabbitmq.properties.RabbitProperties;
import com.oceancloud.grampus.framework.rabbitmq.service.MessageQueue;
import com.oceancloud.grampus.framework.rabbitmq.template.EnhancedRabbitTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/***
 * Rabbit消息发送Service（支持灰度）
 *
 * @author Beck
 * @since 2021-07-22
 */
@Slf4j
@AllArgsConstructor
public class RabbitMessageQueue implements MessageQueue {

	private final EnhancedRabbitTemplate enhancedRabbitTemplate;
	private final RabbitProperties rabbitProperties;

	@Override
	public <T> void sendMessage(String exchange, String routingKey, T msg) {
		// 获取对应的数据(队列名，exchange名，传输的数据)
		exchange = rabbitProperties.getDirectExchangeName();
		String valueJson = JSONUtil.writeValueAsString(msg);
		// 附加消息header，messageId
		MessageProperties messageProperties = buildMessageProperties();
		enhancedRabbitTemplate.convertAndSend(exchange, routingKey, msg, messageProperties);
		log.info("===>rabbitmq: send message success, messageId: {} version: {} exchange: {} ,routingKey:{} ,body: {}",
			messageProperties.getMessageId(), messageProperties.getHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION), exchange, routingKey, valueJson);
	}

	@Override
	public <T> void sendMessage(String routingKey, T msg) {
		// 获取对应的数据(队列名，exchange名，传输的数据)
		String exchange = rabbitProperties.getDirectExchangeName();
		String valueJson = JSONUtil.writeValueAsString(msg);
		// 附加消息header，messageId
		MessageProperties messageProperties = buildMessageProperties();
		enhancedRabbitTemplate.convertAndSend(exchange, routingKey, msg, messageProperties);
		log.info("===>rabbitmq: send message success, messageId: {} version: {} exchange: {} ,routingKey:{} ,body: {}",
			messageProperties.getMessageId(), messageProperties.getHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION), exchange, routingKey, valueJson);
	}

	/**
	 * 构建支持灰度发布的消息属性
	 */
	private MessageProperties buildMessageProperties() {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setMessageId(UUID.randomUUID().toString());

		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			messageProperties.setHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION, GrayVersionContextHolder.getVersion());
			return messageProperties;
		}
		String requestVersion = request.getHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION);

		// 附加版本号
		messageProperties.setHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION,
			StringUtil.isNotBlank(requestVersion) ? requestVersion : GrayVersionContextHolder.getVersion());
		return messageProperties;
	}
}
