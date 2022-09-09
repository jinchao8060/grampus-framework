package com.oceancloud.grampus.framework.rabbitmq.service;

/**
 * 消息队列的对外接口，目的为封装其他实现具体逻辑，外部调用只需要该接口实现。(目前接口的功能只有发布消息。)
 *
 * @author jerry
 * @date 2018年8月14日
 */
public interface MessageQueue {


	/**
	 * 发布消息
	 *
	 * @param exchange 交换机
	 * @param routingKey 路由键
	 * @param msg 消息内容
	 * @param <T> 消息体类型
	 */
	<T> void sendMessage(String exchange, String routingKey, T msg);

	/**
	 * 发布消息
	 *
	 * @param routingKey 路由键
	 * @param msg 消息内容
	 * @param <T> 消息体类型
	 */
	<T> void sendMessage(String routingKey, T msg);
}
