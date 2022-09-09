package com.oceancloud.grampus.framework.excel;

import com.alibaba.excel.converters.Converter;
import com.oceancloud.grampus.framework.excel.aspect.ResponseExcelReturnValueHandler;
import com.oceancloud.grampus.framework.excel.properties.ExcelConfigProperties;
import com.oceancloud.grampus.framework.excel.write.ManySheetWriteHandler;
import com.oceancloud.grampus.framework.excel.write.SheetWriteHandler;
import com.oceancloud.grampus.framework.excel.write.SingleSheetWriteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author lengleng
 * @since 2021-5-14
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class ExcelHandlerConfiguration {

	private final ExcelConfigProperties configProperties;

	private final ObjectProvider<List<Converter<?>>> converterProvider;

	/**
	 * 单sheet 写入处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public SingleSheetWriteHandler singleSheetWriteHandler() {
		return new SingleSheetWriteHandler(configProperties, converterProvider);
	}

	/**
	 * 多sheet 写入处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public ManySheetWriteHandler manySheetWriteHandler() {
		return new ManySheetWriteHandler(configProperties, converterProvider);
	}

	/**
	 * 返回Excel文件的 response 处理器
	 *
	 * @param sheetWriteHandlerList 页签写入处理器集合
	 * @return ResponseExcelReturnValueHandler
	 */
	@Bean
	@ConditionalOnMissingBean
	public ResponseExcelReturnValueHandler responseExcelReturnValueHandler(
			List<SheetWriteHandler> sheetWriteHandlerList) {
		return new ResponseExcelReturnValueHandler(sheetWriteHandlerList);
	}
}
