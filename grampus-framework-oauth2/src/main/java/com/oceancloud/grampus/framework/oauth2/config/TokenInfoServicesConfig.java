package com.oceancloud.grampus.framework.oauth2.config;

import com.oceancloud.grampus.framework.oauth2.support.EnhancedRemoteTokenServices;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * TokenInfoServicesConfig
 * RemoteTokenServices
 *
 * @author Beck
 * @since 2021-07-13
 */
@Configuration
@AllArgsConstructor
public class TokenInfoServicesConfig {
	private final ResourceServerProperties resource;
	private final LoadBalancerClient loadBalancerClient;

	@Bean
	@Primary
	@ConditionalOnProperty(name = "security.oauth2.resource.token-info-uri")
	public EnhancedRemoteTokenServices enhancedRemoteTokenServices() {
		EnhancedRemoteTokenServices services = new EnhancedRemoteTokenServices();
		services.setCheckTokenEndpointUrl(this.resource.getTokenInfoUri());
		services.setClientId(this.resource.getClientId());
		services.setClientSecret(this.resource.getClientSecret());
		services.setLoadBalancerClient(loadBalancerClient);
		return services;
	}

//	private static class TokenInfoCondition extends SpringBootCondition {
//		private TokenInfoCondition() {
//		}
//
//		@Override
//		public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
//			ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth TokenInfo Condition", new Object[0]);
//			Environment environment = context.getEnvironment();
//			Boolean preferTokenInfo = (Boolean)environment.getProperty("security.oauth2.resource.prefer-token-info", Boolean.class);
//			if (preferTokenInfo == null) {
//				preferTokenInfo = environment.resolvePlaceholders("${OAUTH2_RESOURCE_PREFERTOKENINFO:true}").equals("true");
//			}
//
//			String tokenInfoUri = environment.getProperty("security.oauth2.resource.token-info-uri");
//			String userInfoUri = environment.getProperty("security.oauth2.resource.user-info-uri");
//			if (!StringUtils.hasLength(userInfoUri) && !StringUtils.hasLength(tokenInfoUri)) {
//				return ConditionOutcome.match(message.didNotFind("user-info-uri property").atAll());
//			} else {
//				return StringUtils.hasLength(tokenInfoUri) && preferTokenInfo ? ConditionOutcome.match(message.foundExactly("preferred token-info-uri property")) : ConditionOutcome.noMatch(message.didNotFind("token info").atAll());
//			}
//		}
//	}
}