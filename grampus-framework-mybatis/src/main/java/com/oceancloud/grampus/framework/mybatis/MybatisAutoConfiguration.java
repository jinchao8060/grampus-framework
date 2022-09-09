package com.oceancloud.grampus.framework.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.oceancloud.grampus.framework.sequence.generator.IdGenerator;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Mybatis配置
 * Project: grampus
 *
 * @author Beck
 * @since 2020-12-02
 */
@Configuration
@ComponentScan(basePackages = {"com.oceancloud.grampus.framework.mybatis"})
@MapperScan(basePackages = "com.oceancloud.**.dao")
public class MybatisAutoConfiguration {

	@Bean
	@ConditionalOnBean(IdGenerator.class)
	public IdentifierGenerator idGenerator(IdGenerator idGenerator) {
		return entity -> idGenerator.genKey();
	}
}
