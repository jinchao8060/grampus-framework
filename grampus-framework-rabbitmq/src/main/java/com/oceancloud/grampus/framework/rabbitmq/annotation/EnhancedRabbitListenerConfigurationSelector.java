package com.oceancloud.grampus.framework.rabbitmq.annotation;

import org.springframework.amqp.rabbit.annotation.MultiRabbitBootstrapConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

/**
 * A {@link DeferredImportSelector} implementation with the lowest order to import a
 * {@link MultiRabbitBootstrapConfiguration} and {@link RabbitBootstrapConfiguration}
 * as late as possible.
 * {@link MultiRabbitBootstrapConfiguration} has precedence to be able to provide the
 * extended BeanPostProcessor, if enabled.
 *
 * @author Artem Bilan
 *
 * @since 2.1.6
 */
@Order
public class EnhancedRabbitListenerConfigurationSelector implements DeferredImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[] { EnhancedMultiRabbitBootstrapConfiguration.class.getName(),
				EnhancedRabbitBootstrapConfiguration.class.getName()};
	}

}
