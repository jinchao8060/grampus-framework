package com.oceancloud.grampus.framework.excel.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Excel配置
 *
 * @author lengleng
 * @since 2021-5-14
 */
@Data
@ConfigurationProperties(prefix = ExcelConfigProperties.PREFIX)
public class ExcelConfigProperties {

	static final String PREFIX = "excel";

	/**
	 * 模板路径
	 */
	private String templatePath = "excel";
}
