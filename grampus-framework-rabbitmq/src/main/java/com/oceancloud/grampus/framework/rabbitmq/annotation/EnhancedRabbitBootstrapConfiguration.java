package com.oceancloud.grampus.framework.rabbitmq.annotation;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor;
import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

/**
 * An {@link ImportBeanDefinitionRegistrar} class that registers
 * a {@link RabbitListenerAnnotationBeanPostProcessor} bean capable of processing
 * Spring's @{@link RabbitListener} annotation.
 * Also register a default {@link RabbitListenerEndpointRegistry}.
 *
 * <p>This configuration class is automatically imported when using the @{@link EnableRabbit}
 * annotation.
 *
 * @author Stephane Nicoll
 * @author Artem Bilan
 *
 * @since 1.4
 *
 * @see RabbitListenerAnnotationBeanPostProcessor
 * @see RabbitListenerEndpointRegistry
 * @see EnableRabbit
 */
public class EnhancedRabbitBootstrapConfiguration implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(@Nullable AnnotationMetadata importingClassMetadata,
			BeanDefinitionRegistry registry) {

		if (!registry.containsBeanDefinition(
				RabbitListenerConfigUtils.RABBIT_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)) {

			registry.registerBeanDefinition(RabbitListenerConfigUtils.RABBIT_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME,
					new RootBeanDefinition(EnhancedRabbitListenerAnnotationBeanPostProcessor.class));
		}

		if (!registry.containsBeanDefinition(RabbitListenerConfigUtils.RABBIT_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME)) {
			registry.registerBeanDefinition(RabbitListenerConfigUtils.RABBIT_LISTENER_ENDPOINT_REGISTRY_BEAN_NAME,
					new RootBeanDefinition(RabbitListenerEndpointRegistry.class));
		}
	}

}
