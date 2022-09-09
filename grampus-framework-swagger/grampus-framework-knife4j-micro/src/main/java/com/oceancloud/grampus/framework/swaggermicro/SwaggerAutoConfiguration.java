package com.oceancloud.grampus.framework.swaggermicro;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
				.title("Grampus-Cloud")
				.description("接口文档")
				.termsOfServiceUrl("https://oceancloud.cn/")
				.version("1.0.0")
				.contact(new Contact("Beck", "", ""))
				.build();
	}
}
