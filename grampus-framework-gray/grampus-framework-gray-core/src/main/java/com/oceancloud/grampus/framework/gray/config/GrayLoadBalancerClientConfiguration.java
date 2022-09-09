package com.oceancloud.grampus.framework.gray.config;

import com.oceancloud.grampus.framework.gray.loadbalancer.GrayServiceInstanceLoadBalancer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * GrayLoadBalancerClientConfiguration
 *
 * @author Beck
 * @since 2021-06-25
 */
public class GrayLoadBalancerClientConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
			LoadBalancerClientFactory loadBalancerClientFactory) {
		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
		return new GrayServiceInstanceLoadBalancer(loadBalancerClientFactory.
				getLazyProvider(name, ServiceInstanceListSupplier.class), name);
	}
}
