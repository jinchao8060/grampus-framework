package com.oceancloud.grampus.framework.cloud.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.oceancloud.grampus.framework.cloud.client.NacosServiceInstanceRetrofitClient;
import com.oceancloud.grampus.framework.core.utils.http.factory.ToStringConverterFactory;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

import java.util.Properties;

/**
 * NacosOperateAutoConfiguration
 *
 * @author Beck
 * @since 2021-07-26
 */
@AllArgsConstructor
@Configuration
public class NacosServiceAutoConfiguration {

	private final NacosDiscoveryProperties nacosDiscoveryProperties;

	@Bean
	public NamingService namingService() throws NacosException {
		Properties properties = new Properties();
		properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosDiscoveryProperties.getServerAddr());
		properties.setProperty(PropertyKeyConst.NAMESPACE, nacosDiscoveryProperties.getNamespace());
		return NamingFactory.createNamingService(properties);
	}

	@Bean
	public ConfigService configService() throws NacosException {
		Properties properties = new Properties();
		properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosDiscoveryProperties.getServerAddr());
		properties.setProperty(PropertyKeyConst.NAMESPACE, nacosDiscoveryProperties.getNamespace());
		return NacosFactory.createConfigService(properties);
	}

	@Bean
	public NacosServiceInstanceRetrofitClient nacosServiceInstanceRetrofitClient() {
		String baseUrl = "http://" + nacosDiscoveryProperties.getServerAddr();
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(baseUrl)
			.client(new OkHttpClient.Builder().build())
			.addConverterFactory(ToStringConverterFactory.create())
			.build();
		return retrofit.create(NacosServiceInstanceRetrofitClient.class);
	}
}
