package com.oceancloud.grampus.framework.core.utils.convert;

import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author Beck
 */
public class EnhancedConversionService extends DefaultFormattingConversionService {
	@Nullable
	private static volatile EnhancedConversionService SHARED_INSTANCE;

	public EnhancedConversionService() {
		super();
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link EnhancedConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 *
	 * @return the shared {@code EnhancedConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		EnhancedConversionService sharedInstance = EnhancedConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (EnhancedConversionService.class) {
				sharedInstance = EnhancedConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new EnhancedConversionService();
					EnhancedConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
