package com.oceancloud.grampus.framework.rabbitmq.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * RabbitProperties
 *
 * @author Beck
 * @since 2022-03-04
 */
@Data
public class RabbitProperties implements Serializable {
	private static final long serialVersionUID = -1123387696078992991L;
	/**
	 * direct交换机名称
	 */
	private String directExchangeName = "direct.exchange";
}
