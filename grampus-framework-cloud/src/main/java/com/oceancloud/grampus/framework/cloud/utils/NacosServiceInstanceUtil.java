package com.oceancloud.grampus.framework.cloud.utils;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * ServiceInstanceUtil TODO 注入有问题，暂时先停掉
 *
 * @author Beck
 * @since 2021-07-21
 */
@AllArgsConstructor
//@Component
public class NacosServiceInstanceUtil {

	private final Environment environment;
	private final InetUtils inetUtils;
	private final NamingService namingService;
	private final ConfigService configService;
	private final NacosDiscoveryProperties nacosDiscoveryProperties;

	/**
	 * 当前服务名
	 */
	public String getCurServiceName() {
		return environment.getProperty("spring.application.name");
	}

	/**
	 * 当前IP
	 */
	public String getCurIp() {
		// com.alibaba.cloud.nacos.NacosDiscoveryProperties 241
		return inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
	}

	/**
	 * 当前端口
	 */
	public Integer getCurPort() {
		return Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
	}

	/**
	 * 当前Nacos分组
	 */
	public String getCurGroup() {
		return nacosDiscoveryProperties.getGroup();
	}

	/**
	 * 当前Nacos命名空间
	 */
	public String getCurNameSpace() {
		return nacosDiscoveryProperties.getNamespace();
	}

	/**
	 * Nacos服务器访问地址
	 */
	public String getServerAddress() {
		return nacosDiscoveryProperties.getServerAddr();
	}

	/**
	 * Nacos Naming
	 */
	public NamingService getNamingService() {
		return namingService;
	}

	/**
	 * Nacos Config
	 */
	public ConfigService getConfigService() {
		return configService;
	}
}
