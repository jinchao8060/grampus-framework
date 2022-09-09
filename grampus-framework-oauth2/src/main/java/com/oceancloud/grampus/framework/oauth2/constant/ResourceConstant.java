package com.oceancloud.grampus.framework.oauth2.constant;

/**
 * ResourceConstant
 *
 * @author Beck
 * @since 2021-07-13
 */
public interface ResourceConstant {
    /**
     * 不进行认证的URL
     */
    String [] IGNORING_URLS = {
        "/actuator/**",
        "/v2/api-docs",
        "/webjars/**",
        "/swagger/**",
        "/swagger-resources/**",
        "/doc.html"
    };
}
