package com.oceancloud.grampus.framework.rabbitmq;

import com.oceancloud.grampus.framework.rabbitmq.properties.RabbitProperties;
import com.oceancloud.grampus.framework.rabbitmq.service.MessageQueue;
import com.oceancloud.grampus.framework.rabbitmq.service.impl.RabbitMessageQueue;
import com.oceancloud.grampus.framework.rabbitmq.template.EnhancedRabbitTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * @author Beck
 * @since 2021-10-25
 */
@Slf4j
@Configuration
public class RabbitAutoConfiguration {

	@Bean
	public EnhancedRabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
		EnhancedRabbitTemplate template = new EnhancedRabbitTemplate();
//		template.setMessageConverter(new Jackson2JsonMessageConverter());
		configurer.configure(template, connectionFactory);
		return template;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public MessageQueue messageQueue(EnhancedRabbitTemplate enhancedRabbitTemplate) {
		return new RabbitMessageQueue(enhancedRabbitTemplate, new RabbitProperties());
	}
}
