package com.oceancloud.grampus.framework.cloud.utils;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;
import com.oceancloud.grampus.framework.cloud.client.NacosServiceInstanceRetrofitClient;
import com.oceancloud.grampus.framework.core.utils.Exceptions;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import lombok.AllArgsConstructor;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * ServiceInstanceUtil
 *
 * @author Beck
 * @since 2021-07-21
 */
@AllArgsConstructor
@Component
public class NacosServiceInstanceUtil {

	private final Environment environment;
	private final InetUtils inetUtils;
	private final NamingService namingService;
	private final ConfigService configService;
	private final NacosDiscoveryProperties nacosDiscoveryProperties;
	private final NacosServiceInstanceRetrofitClient nacosServiceInstanceRetrofitClient;

	/**
	 * 当前服务名
	 */
	public String getCurServiceName() {
		return environment.getProperty("spring.application.name");
	}

	/**
	 * 当前服务IP
	 */
	public String getCurIp() {
		// com.alibaba.cloud.nacos.NacosDiscoveryProperties 241
		return inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
	}

	/**
	 * 当前服务端口
	 */
	public Integer getCurPort() {
		return Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
	}

	/**
	 * 当前服务Nacos分组
	 */
	public String getCurGroup() {
		return nacosDiscoveryProperties.getGroup();
	}

	/**
	 * 当前服务Nacos命名空间
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
	 * 当前服务元数据
	 */
	public Map<String, String> getMetadata() {
		return nacosDiscoveryProperties.getMetadata();
	}

	/**
	 * 当前服务权重
	 */
	public Float getWeight() {
		return nacosDiscoveryProperties.getWeight();
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

	/**
	 * 更新当前实例
	 *
	 * @param weight 权重
	 * @param metadata 元数据
	 * @param enabled 服务启用状态
	 * @return ok成功
	 */
	public String updateCurrentInstance(Float weight, Map<String, String> metadata, Boolean enabled) {
		String result;
		Call<String> call = nacosServiceInstanceRetrofitClient.updateInstance(
			getCurServiceName(), getCurGroup(), getCurIp(), getCurPort(), "DEFAULT", getCurNameSpace(),
			weight, JSONUtil.writeValueAsString(metadata), enabled, true);
		try {
			Response<String> response = call.execute();
			if (!response.isSuccessful()) {
				throw new RuntimeException(response.errorBody() != null ? response.errorBody().string() : null);
			}
			result = response.body();
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
		return result;
	}
}
