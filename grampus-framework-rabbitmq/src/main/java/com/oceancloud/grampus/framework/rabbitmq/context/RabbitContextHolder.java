package com.oceancloud.grampus.framework.rabbitmq.context;

import com.alibaba.nacos.api.naming.NamingService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

/**
 * RabbitContextHolder
 *
 * @author Beck
 * @since 2021-07-21
 */
@UtilityClass
public class RabbitContextHolder {
	/**
	 * 当前服务名
	 */
	@Getter
	@Setter
	private static String curServiceName;
	/**
	 * 当前IP
	 */
	@Getter
	@Setter
	private static String curIp;
	/**
	 * 当前端口
	 */
	@Getter
	@Setter
	private static Integer curPort;
	/**
	 * 当前Nacos分组
	 */
	@Getter
	@Setter
	private static String curGroup;
	/**
	 * 当前Nacos命名空间
	 */
	@Getter
	@Setter
	private static String curNameSpace;
	/**
	 * Nacos Naming
	 */
	@Getter
	@Setter
	private static NamingService namingService;


}
