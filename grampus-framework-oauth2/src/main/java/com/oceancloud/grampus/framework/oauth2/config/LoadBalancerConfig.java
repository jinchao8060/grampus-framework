package com.oceancloud.grampus.framework.oauth2.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * LoadBalancerConfig
 * security.oauth2.resource.token-info-uri/client.access-token-uri RestTemplate使用lb
 *
 * @author Beck
 * @since 2021-08-11
 */
@AllArgsConstructor
@Configuration
public class LoadBalancerConfig implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private final LoadBalancerClient loadBalancer;

//	@Bean
//	@Primary
//	@LoadBalanced
//	public RestTemplate lbRestTemplate() {
//		// 获取上下文配置的ClientHttpRequestInterceptor 实现
//		Map<String, ClientHttpRequestInterceptor> beansOfType = applicationContext
//				.getBeansOfType(ClientHttpRequestInterceptor.class);
//
//		LoadBalancerInterceptor loadBalancerInterceptor = new LoadBalancerInterceptor(loadBalancer);
//		List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<>(beansOfType.values());
//		interceptorList.add(loadBalancerInterceptor);
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.setInterceptors(interceptorList);
//		return restTemplate;
//	}

	@Bean
	@Primary
	@LoadBalanced
	public RestTemplate lbRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Collections.singletonList(new LoadBalancerInterceptor(loadBalancer)));
		return restTemplate;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

//	@Component
//	@AllArgsConstructor
//	public static class LBRestTemplateInitializing implements InitializingBean {
//
//		private final RemoteTokenServices remoteTokenServices;
//		private final RestTemplate restTemplate;
//
//		@Override
//		public void afterPropertiesSet() throws Exception {
//			remoteTokenServices.setRestTemplate(restTemplate);
//		}
//	}
}
