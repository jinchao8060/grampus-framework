package com.oceancloud.grampus.framework.cloud.constant;

/**
 * LogRequestConstant
 *
 * @author Beck
 * @since 2021-07-09
 */
public interface LogRequestConstant {

	/**
	 * Http header Internal-Request-Id (网关附加，日志全链路追踪)
	 */
	String HEADER_INTERNAL_REQUEST_ID = "Internal-Request-Id";

	String LOG_REQUEST_ID_MDC_TRACE = "requestId";
}
