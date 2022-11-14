package com.oceancloud.grampus.framework.pay.apple.api;

import com.egzosn.pay.common.api.BasePayConfigStorage;
import lombok.Data;

/**
 * 苹果支付配置存储
 *
 * @author Beck
 * @since 2022-11-07
 */
@Data
public class ApplePayConfigStorage extends BasePayConfigStorage {

	/**
	 * 包名
	 */
	private String bundleId;
	/**
	 * 票据类型（Production/Sandbox）
	 */
	private String environment;

	@Override
	public String getAppid() {
		return this.bundleId;
	}

	@Override
	public String getAppId() {
		return this.bundleId;
	}

	@Override
	public String getPid() {
		return null;
	}

	@Override
	public String getSeller() {
		return null;
	}
}
