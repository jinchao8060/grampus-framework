package com.oceancloud.grampus.framework.rabbitmq.context;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * EnvironmentVersionInitializingBean
 *
 * @author Beck
 * @since 2021-07-21
 */
@AllArgsConstructor
public class ServiceInstanceInitializingBean implements InitializingBean {

	private final Environment environment;
	private final InetUtils inetUtils;
	private final NacosServiceManager nacosServiceManager;
	private final NacosDiscoveryProperties nacosDiscoveryProperties;

	@Override
	public void afterPropertiesSet() {
		RabbitContextHolder.setCurServiceName(environment.getProperty("spring.application.name"));
		// com.alibaba.cloud.nacos.NacosDiscoveryProperties 241
		RabbitContextHolder.setCurIp(inetUtils.findFirstNonLoopbackHostInfo().getIpAddress());
		RabbitContextHolder.setCurPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port"))));
		NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
		RabbitContextHolder.setNamingService(namingService);
		RabbitContextHolder.setCurGroup(nacosDiscoveryProperties.getGroup());
		RabbitContextHolder.setCurNameSpace(nacosDiscoveryProperties.getNamespace());
	}
}