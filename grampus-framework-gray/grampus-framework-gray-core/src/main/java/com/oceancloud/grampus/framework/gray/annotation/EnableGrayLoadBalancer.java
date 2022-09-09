package com.oceancloud.grampus.framework.gray.annotation;

import com.oceancloud.grampus.framework.gray.config.GrayFeignInterceptorAutoConfiguration;
import com.oceancloud.grampus.framework.gray.config.GrayLoadBalancerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableGrayLoadBalancer
 *
 * @author Beck
 * @since 2021-06-08
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({GrayLoadBalancerAutoConfiguration.class, GrayFeignInterceptorAutoConfiguration.class})
public @interface EnableGrayLoadBalancer {
}
