package com.oceancloud.grampus.framework.oauth2.config;

import com.oceancloud.grampus.framework.oauth2.constant.ResourceConstant;
import com.oceancloud.grampus.framework.oauth2.exception.AuthenticationExceptionEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务配置
 *
 * @author Beck
 * @since 2021-07-13
 */
@Configuration
@AllArgsConstructor
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	private final TokenStore tokenStore;
	private final AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.tokenStore(tokenStore)
				.authenticationEntryPoint(authenticationExceptionEntryPoint);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(ResourceConstant.IGNORING_URLS).permitAll()
			.antMatchers("/rmi/**").permitAll()
			.antMatchers("/system/login").permitAll()
			.anyRequest().authenticated()
			.and().headers().frameOptions().disable()
		;
		// @formatter:on
	}

}