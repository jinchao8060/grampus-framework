package com.oceancloud.grampus.framework.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger配置
 *
 * @author Beck
 * @since 2020-4-7
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerAutoConfiguration {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//加了ApiOperation注解的类，才生成接口文档
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				//包下的类，才生成接口文档
				//.apis(RequestHandlerSelectors.basePackage("com.oceancloud.grampus.modules.system.controller"))
				.paths(PathSelectors.any())
				.build()
				.directModelSubstitute(java.util.Date.class, String.class);
//				.securitySchemes(Collections.singletonList(new ApiKey("Authorization", "Authorization", "header")));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Grampus")
				.description("接口文档")
				.termsOfServiceUrl("https://oceancloud.cn/")
				.version("1.0.0")
				.contact(new Contact("Beck", "", ""))
				.build();
	}

	/**
	 * Spring Boot 2.6.x版本引入依赖 springfox-boot-starter (Swagger 3.0) 后，启动容器会报错：
	 * <p>
	 * Failed to start bean ‘ documentationPluginsBootstrapper ‘ ; nested exception…
	 * <p>
	 * 原因
	 * Springfox 假设 Spring MVC 的路径匹配策略是 ant-path-matcher，而 Spring Boot 2.6.x版本的默认匹配策略是 path-pattern-matcher，这就造成了上面的报错。
	 */
	@Bean
	public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
		return new BeanPostProcessor() {

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//				if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
				if (bean instanceof WebMvcRequestHandlerProvider) {
					customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
				}
				return bean;
			}

			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
				List<T> copy = mappings.stream()
						.filter(mapping -> mapping.getPatternParser() == null)
						.collect(Collectors.toList());
				mappings.clear();
				mappings.addAll(copy);
			}

			@SuppressWarnings("unchecked")
			private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
				try {
					Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
					field.setAccessible(true);
					return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}
}
