package com.oceancloud.grampus.framework.log.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用日志事件模型
 *
 * @author Beck
 * @since 2021-05-27
 */
@Getter
@Setter
@ToString
public class LogEvent implements Serializable {
	private static final long serialVersionUID = -7159973353669869079L;
	/**
	 * 所属模块
	 */
	private String module;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * Request Method
	 */
	private String requestMethod;
	/**
	 * Request Uri
	 */
	private String requestUri;
	/**
	 * Request Param
	 */
	private String requestParam;
	/**
	 * Request Body
	 */
	private String requestBody;
	/**
	 * 是否成功(0失败,1成功)
	 */
	private Boolean successful;
	/**
	 * 类-方法
	 */
	private String classMethod;
	/**
	 * 异常信息
	 */
	private String exceptionDetail;
	/**
	 * 请求ip
	 */
	private String requestIp;
	/**
	 * 请求时间
	 */
	private LocalDateTime requestStartTime;
	/**
	 * 请求结束时间
	 */
	private LocalDateTime requestEndTime;
	/**
	 * 请求耗时
	 */
	private Long costTime;
	/**
	 * Header Authorization
	 */
	private String authorization;
	/**
	 * ua 信息
	 */
	private String userAgent;
}
