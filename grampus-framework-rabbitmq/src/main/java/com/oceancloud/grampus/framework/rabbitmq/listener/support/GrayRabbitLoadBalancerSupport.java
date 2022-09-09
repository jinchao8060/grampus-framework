package com.oceancloud.grampus.framework.rabbitmq.listener.support;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.gray.constant.GrayLoadBalancerConstant;
import com.oceancloud.grampus.framework.rabbitmq.context.RabbitContextHolder;
import com.oceancloud.grampus.framework.rabbitmq.loadbalancer.GrayRabbitServiceInstanceLoadBalancer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

/**
 * GrayRabbitLoadBalancerSupport
 *
 * @author Beck
 * @since 2022-03-08
 */
@Slf4j
@UtilityClass
public class GrayRabbitLoadBalancerSupport {

    /**
	 * 匹配消息版本
	 *
	 * @param message 消息
	 * @return true消息灰度版本号匹配成功 false消息灰度版本号匹配失败
	 */
	public boolean matchMessageVersion(Message message) {
		// 获取本次监听的exchange，routingKey等数据
		MessageProperties messageProperties = message.getMessageProperties();
		String receivedExchange = messageProperties.getReceivedExchange();
		String receivedRoutingKey = messageProperties.getReceivedRoutingKey();
		String messageId = messageProperties.getMessageId();
		String grayVersion = messageProperties.getHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION);

		if (StringUtil.isNotBlank(grayVersion)) {
			try {
				Instance instance = GrayRabbitServiceInstanceLoadBalancer.getInstanceByVersion(grayVersion);
				if (instance != null && !isCurrentService(instance)) {
					log.info("==>rabbitmq: message requeue. instance is not current. instanceIp:{} instancePort:{} curIp:{} curPort:{}",
						instance.getIp(), instance.getPort(), RabbitContextHolder.getCurIp(), RabbitContextHolder.getCurPort());
					return false;
				}
			} catch (NacosException e) {
				// TODO 后续优化添加重试次数和时间
				log.error("==>rabbitmq: receive message failure. nacos get instance fail!", e);
				return false;
			}
		}

		// 读取对应的消息主体
		String messageBody = new String(message.getBody());

		log.info("==>rabbitmq: receive message success: messageId: {} version: {} exchangeKey: {} , routingKey: {}, messageBody: {} " +
				"curIp: {} curPort: {} curNameSpace:{} curGroup: {} curServiceName:{}",
			messageId, grayVersion, receivedExchange, receivedRoutingKey, messageBody,
				RabbitContextHolder.getCurIp(), RabbitContextHolder.getCurPort(), RabbitContextHolder.getCurNameSpace(),
				RabbitContextHolder.getCurGroup(), RabbitContextHolder.getCurServiceName());

		return true;
	}

	private boolean isCurrentService(Instance instance) {
		return instance.getIp().equals(RabbitContextHolder.getCurIp())
			&& instance.getPort() == RabbitContextHolder.getCurPort();
	}
}
