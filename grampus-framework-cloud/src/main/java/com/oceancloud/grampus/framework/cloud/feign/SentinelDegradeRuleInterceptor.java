package com.oceancloud.grampus.framework.cloud.feign;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.google.common.collect.Sets;
import com.oceancloud.grampus.framework.cloud.utils.SentinelDegradeRuleUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * Sentinel默认降级Feign请求拦截器
 *
 * @author Beck
 * @since 2022-03-22
 */
@Slf4j
public class SentinelDegradeRuleInterceptor implements RequestInterceptor {

	private static final Set<String> DEFAULT_DEGRADE_RULES = Sets.newConcurrentHashSet();

	private final Object lock = new Object();

	@Override
	public void apply(RequestTemplate template) {
		String method = template.method();
		String url = template.feignTarget().url();
		String path = template.path();
		String resource = String.format("%s:%s%s", method, url, path);

		if (DEFAULT_DEGRADE_RULES.add(resource)) {
			DegradeRule degradeRule = SentinelDegradeRuleUtil.buildResponseTimeDegradeRule(resource);
			synchronized (lock) {
				List<DegradeRule> rules = DegradeRuleManager.getRules();
				Set<DegradeRule> rulesOfResource = DegradeRuleManager.getRulesOfResource(resource);
				if (rulesOfResource == null) {
					rules.add(degradeRule);
					DegradeRuleManager.loadRules(rules);
					log.info("init sentinel default degrade rule. resource:{} rule:{}", resource, degradeRule);
				}
			}
		}
	}
}
