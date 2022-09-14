package com.oceancloud.grampus.framework.cloud.utils;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.oceancloud.grampus.framework.core.utils.Exceptions;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * NacosConfigUtil
 *
 * @author Beck
 * @since 2021-12-8
 */
@AllArgsConstructor
@Component
public class NacosConfigUtil {

	private final NacosServiceInstanceUtil nacosServiceInstanceUtil;
	private final ConfigService configService;

	/**
	 * 发布配置
	 * <p>
	 * 默认类型为json
	 *
	 * @param dataId  dataId
	 * @param content 内容
	 * @return 是否发布成功 true成功 false失败
	 */
	public Boolean publishConfig(String dataId, Object content) {
		return publishConfig(dataId, JSONUtil.writeValueAsString(content, true), "json");
	}

	/**
	 * 发布配置
	 *
	 * @param dataId  dataId
	 * @param content 配置内容
	 * @param type    properties xml json text html yaml unset
	 * @return 是否发布成功 true成功 false失败
	 */
	public Boolean publishConfig(String dataId, String content, String type) {
		try {
			return configService.publishConfig(dataId, nacosServiceInstanceUtil.getCurGroup(), content, type);
		} catch (NacosException nacosException) {
			throw Exceptions.unchecked(nacosException);
		}
	}

	/**
	 * 移除配置
	 *
	 * @param dataId dataId
	 * @return 是否移除成功 true成功 false失败
	 */
	public Boolean removeConfig(String dataId) {
		try {
			return configService.removeConfig(dataId, nacosServiceInstanceUtil.getCurGroup());
		} catch (NacosException nacosException) {
			throw Exceptions.unchecked(nacosException);
		}
	}

	/**
	 * 获取配置
	 *
	 * @param dataId dataId
	 * @param clazz  反序列化类
	 * @param <T>    泛型
	 * @return 配置
	 */
	public <T> T getConfig(String dataId, Class<T> clazz) {
		return JSONUtil.readValue(getConfig(dataId), clazz);
	}

	/**
	 * 获取配置
	 *
	 * @param dataId dataId
	 * @return 配置
	 */
	public String getConfig(String dataId) {
		try {
			return configService.getConfig(dataId, nacosServiceInstanceUtil.getCurGroup(), 300000L);
		} catch (NacosException nacosException) {
			throw Exceptions.unchecked(nacosException);
		}
	}

	/**
	 * 添加配置变更监听器
	 *
	 * @param dataId   dataId
	 * @param consumer 配置变更处理逻辑
	 */
	public void addListener(String dataId, Consumer<String> consumer) {
		try {
			configService.addListener(dataId, nacosServiceInstanceUtil.getCurGroup(), new Listener() {
				@Override
				public Executor getExecutor() {
					return null;
				}

				@Override
				public void receiveConfigInfo(String configInfo) {
					consumer.accept(configInfo);
				}
			});
		} catch (NacosException nacosException) {
			throw Exceptions.unchecked(nacosException);
		}
	}
}
