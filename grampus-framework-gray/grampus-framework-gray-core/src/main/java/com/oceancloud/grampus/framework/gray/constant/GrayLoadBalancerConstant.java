package com.oceancloud.grampus.framework.gray.constant;

/**
 * GrayLoadBalancerConstant
 *
 * @author Beck
 * @since 2021-06-29
 */
public interface GrayLoadBalancerConstant {

	/**
	 * Http header version
	 */
	String HEADER_VERSION = "Version";
	/**
	 * Http header token
	 */
	String HEADER_TOKEN = "Authorization";
	/**
	 * Http header Lon
	 */
	String HEADER_LON = "Lon";
	/**
	 * Http header Lat
	 */
	String HEADER_LAT = "Lat";

	/**
	 * Http header Internal-Version (网关附加，null或没有匹配的实例则全部路由)
	 */
	String HEADER_INTERNAL_VERSION = "Internal-Version";
	/**
	 * Http header Request-Ip (网关附加)
	 */
	String HEADER_INTERNAL_REQUEST_IP = "Internal-Request-Ip";
	/**
	 * Http header Request-Subject (网关附加)
	 */
	String HEADER_INTERNAL_REQUEST_SUBJECT = "Internal-Request-Subject";
	/**
	 * Http header Request-Platform (网关附加)
	 */
	String HEADER_INTERNAL_REQUEST_PLATFORM = "Internal-Request-Platform";

	/**
	 * nacos负载均衡权重字段
	 */
	String METADATA_NACOS_WEIGHT = "nacos.weight";

	/**
	 * 灰度发布版本号字段
	 */
	String METADATA_GRAY_VERSION = "gray.version";
}
