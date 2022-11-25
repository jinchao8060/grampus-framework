package com.oceancloud.grampus.framework.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.oceancloud.grampus.framework.sequence.generator.IdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置
 * Project: grampus
 *
 * @author Beck
 * @since 2020-12-02
 */
@Configuration
//@MapperScan(basePackages = "com.oceancloud.**.dao")
public class MybatisAutoConfiguration {

	@Bean
	@ConditionalOnBean(IdGenerator.class)
	public IdentifierGenerator idGenerator(IdGenerator idGenerator) {
		return entity -> idGenerator.genKey();
	}
}
