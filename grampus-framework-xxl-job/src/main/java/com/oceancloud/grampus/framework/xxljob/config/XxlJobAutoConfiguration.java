package com.oceancloud.grampus.framework.xxljob.config;

import com.oceancloud.grampus.framework.xxljob.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * XxlJobAutoConfiguration
 *
 * @author Beck
 * @since 2021-06-03
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

	@Bean
	public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties properties, Environment environment) {
		log.info(">>>>>>>>>>> xxl-job config init.");
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		xxlJobSpringExecutor.setAdminAddresses(properties.getAdmin().getAddresses());
		xxlJobSpringExecutor.setAppname(properties.getExecutor().getAppname());
		xxlJobSpringExecutor.setIp(properties.getExecutor().getIp());
		xxlJobSpringExecutor.setPort(properties.getExecutor().getPort());
		xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
		xxlJobSpringExecutor.setLogPath(properties.getExecutor().getLogpath());
		xxlJobSpringExecutor.setLogRetentionDays(properties.getExecutor().getLogretentiondays());
		return xxlJobSpringExecutor;
	}
}
