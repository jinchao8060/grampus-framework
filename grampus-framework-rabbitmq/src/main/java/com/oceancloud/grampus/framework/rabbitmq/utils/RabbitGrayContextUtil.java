package com.oceancloud.grampus.framework.rabbitmq.utils;

import com.oceancloud.grampus.framework.gray.constant.GrayLoadBalancerConstant;
import com.oceancloud.grampus.framework.gray.utils.GrayContextUtil;
import lombok.experimental.UtilityClass;
import org.springframework.amqp.core.MessageProperties;

/**
 * GrayContextUtils
 *
 * @author Beck
 * @since 2021-07-23
 */
@UtilityClass
public class RabbitGrayContextUtil {

	public void setGrayContext(MessageProperties properties) {
		String grayVersion = properties.getHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION);
		GrayContextUtil.setGrayContext(grayVersion);
	}

	public void clearGrayContext() {
		GrayContextUtil.clearGrayContext();
	}
}
