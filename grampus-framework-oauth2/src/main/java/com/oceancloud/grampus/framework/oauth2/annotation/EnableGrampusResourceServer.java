package com.oceancloud.grampus.framework.oauth2.annotation;

import com.oceancloud.grampus.framework.oauth2.config.ResourceServerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableGrampusResourceServer
 *
 * @author Beck
 * @since 2021-08-05
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ResourceServerConfig.class})
public @interface EnableGrampusResourceServer {
}
