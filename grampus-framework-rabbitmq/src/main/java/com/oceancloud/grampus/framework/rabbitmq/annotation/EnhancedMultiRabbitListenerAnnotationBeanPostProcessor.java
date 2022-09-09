package com.oceancloud.grampus.framework.rabbitmq.annotation;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor;
import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;

/**
 * An extension of {@link RabbitListenerAnnotationBeanPostProcessor} that indicates the proper
 * RabbitAdmin bean to be used when processing to the listeners, and also associates it to the
 * declarables (Exchanges, Queues, and Bindings) returned.
 * <p>
 * This processing restricts the {@link org.springframework.amqp.rabbit.core.RabbitAdmin} according to the related
 * configuration, preventing the server from automatic binding non-related structures.
 *
 * @author Wander Costa
 *
 * @since 2.3
 */
public class EnhancedMultiRabbitListenerAnnotationBeanPostProcessor extends EnhancedRabbitListenerAnnotationBeanPostProcessor {

	@Override
	protected Collection<Declarable> processAmqpListener(RabbitListener rabbitListener, Method method,
														 Object bean, String beanName) {
		final String rabbitAdmin = resolveMultiRabbitAdminName(rabbitListener);
		final RabbitListener rabbitListenerRef = proxyIfAdminNotPresent(rabbitListener, rabbitAdmin);
		final Collection<Declarable> declarables = super.processAmqpListener(rabbitListenerRef, method, bean, beanName);
		for (final Declarable declarable : declarables) {
			if (declarable.getDeclaringAdmins().isEmpty()) {
				declarable.setAdminsThatShouldDeclare(rabbitAdmin);
			}
		}
		return declarables;
	}

	private RabbitListener proxyIfAdminNotPresent(final RabbitListener rabbitListener, final String rabbitAdmin) {
		if (StringUtils.hasText(rabbitListener.admin())) {
			return rabbitListener;
		}
		return (RabbitListener) Proxy.newProxyInstance(
				RabbitListener.class.getClassLoader(), new Class<?>[]{RabbitListener.class},
				new RabbitListenerAdminReplacementInvocationHandler(rabbitListener, rabbitAdmin));
	}

	/**
	 * Resolves the name of the RabbitAdmin bean based on the RabbitListener, or falls back to
	 * the default RabbitAdmin name provided by MultiRabbit.
	 * @param rabbitListener The RabbitListener to process the name from.
	 * @return The name of the RabbitAdmin bean.
	 */
	protected String resolveMultiRabbitAdminName(RabbitListener rabbitListener) {
		String admin = super.resolveExpressionAsString(rabbitListener.admin(), "admin");
		if (!StringUtils.hasText(admin) && StringUtils.hasText(rabbitListener.containerFactory())) {
			admin = rabbitListener.containerFactory() + RabbitListenerConfigUtils.MULTI_RABBIT_ADMIN_SUFFIX;
		}
		if (!StringUtils.hasText(admin)) {
			admin = RabbitListenerConfigUtils.RABBIT_ADMIN_BEAN_NAME;
		}
		return admin;
	}

	/**
	 * An {@link InvocationHandler} to provide a replacing admin() parameter of the listener.
	 */
	private static final class RabbitListenerAdminReplacementInvocationHandler implements InvocationHandler {

		private final RabbitListener target;
		private final String admin;

		private RabbitListenerAdminReplacementInvocationHandler(final RabbitListener target, final String admin) {
			this.target = target;
			this.admin = admin;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args)
				throws InvocationTargetException, IllegalAccessException {
			if (method.getName().equals("admin")) {
				return this.admin;
			}
			return method.invoke(this.target, args);
		}
	}

}
