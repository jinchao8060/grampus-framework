package com.oceancloud.grampus.framework.log.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 系统日志路径重写
 *
 * @author Beck
 * @since 2021-05-27
 */
public class ApplicationLoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String appName = environment.getProperty("spring.application.name");
		String logPath = String.format("logs/%s-log/%s.log", appName, appName);
		System.setProperty("logging.file.name", logPath);
	}
}
