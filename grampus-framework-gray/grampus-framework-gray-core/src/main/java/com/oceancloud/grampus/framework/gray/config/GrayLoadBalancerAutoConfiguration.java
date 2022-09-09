package com.oceancloud.grampus.framework.gray.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * GrayLoadBalancerAutoConfiguration
 *
 * @author Beck
 * @since 2021-06-28
 */
@Configuration
@LoadBalancerClients(defaultConfiguration = GrayLoadBalancerClientConfiguration.class)
public class GrayLoadBalancerAutoConfiguration {

}
