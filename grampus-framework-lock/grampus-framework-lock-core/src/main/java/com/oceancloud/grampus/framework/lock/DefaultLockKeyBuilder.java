package com.oceancloud.grampus.framework.lock;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 分布式锁Key生成器
 *
 * @author Beck
 * @since 2021-01-29
 */
public class DefaultLockKeyBuilder implements LockKeyBuilder {

	private static final String DEFAULT_KEY_PREFIX = "distributed_lock";

	private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

	private static final ExpressionParser PARSER = new SpelExpressionParser();

	@Override
	public String buildKey(MethodInvocation invocation, String definitionKey, String[] definitionKeyParams) {
		StringBuilder sb = new StringBuilder(getKeyPrefix());
		Method method = invocation.getMethod();
		sb.append(":");
		sb.append(StringUtils.isEmpty(definitionKey) ? method.getDeclaringClass().getName() + "." + method.getName() : definitionKey);
		if (definitionKeyParams.length > 1 || !"".equals(definitionKeyParams[0])) {
			sb.append(":").append(getSpelDefinitionKeyParam(definitionKeyParams, method, invocation.getArguments()));
		}
		return sb.toString();
	}

	protected String getSpelDefinitionKeyParam(String[] definitionKeyParams, Method method, Object[] parameterValues) {
		EvaluationContext context = new MethodBasedEvaluationContext(null, method, parameterValues, NAME_DISCOVERER);
		List<String> definitionKeyParamList = new ArrayList<>(definitionKeyParams.length);
		for (String definitionKeyParam : definitionKeyParams) {
			if (definitionKeyParam != null && !definitionKeyParam.isEmpty()) {
				String keyParam = PARSER.parseExpression(definitionKeyParam).getValue(context).toString();
				definitionKeyParamList.add(keyParam);
			}
		}
		return StringUtils.collectionToDelimitedString(definitionKeyParamList, ".", "", "");
	}

	protected String getKeyPrefix() {
		return DEFAULT_KEY_PREFIX;
	}

}
