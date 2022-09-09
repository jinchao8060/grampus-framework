package com.oceancloud.grampus.framework.cloud.web;

import com.oceancloud.grampus.framework.cloud.log.LogHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * LogHandlerInterceptor
 *
 * @author Beck
 * @since 2021-07-09
 */
@Configuration
public class WebInterceptorAdapter implements WebMvcConfigurer {
    @Bean
    public LogHandlerInterceptor logHandlerInterceptor() {
        return new LogHandlerInterceptor();
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logHandlerInterceptor());
    }
}