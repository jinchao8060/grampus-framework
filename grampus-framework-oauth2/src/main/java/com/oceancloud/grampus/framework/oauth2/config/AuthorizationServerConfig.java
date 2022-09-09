package com.oceancloud.grampus.framework.oauth2.config;

import com.oceancloud.grampus.framework.oauth2.constant.OAuth2Constant;
import com.oceancloud.grampus.framework.oauth2.exception.EnhancedWebResponseExceptionTranslator;
import com.oceancloud.grampus.framework.oauth2.support.EnhancedTokenEnhanced;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * 认证服务配置
 *
 * @author Beck
 * @since 2021-07-13
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final AuthenticationManager authenticationManager;
	private final DataSource dataSource;
	private final UserDetailsService userDetailsService;
	private final TokenStore tokenStore;

	@Bean
	public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(OAuth2Constant.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(OAuth2Constant.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		// @formatter:off
		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE)
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.tokenStore(tokenStore)
				.tokenEnhancer(new EnhancedTokenEnhanced())
				.exceptionTranslator(new EnhancedWebResponseExceptionTranslator())
				.authorizationCodeServices(jdbcAuthorizationCodeServices());
		// @formatter:on
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		// @formatter:off
		security
				.allowFormAuthenticationForClients()
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
		;
		// @formatter:on
	}
}