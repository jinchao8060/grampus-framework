package com.oceancloud.grampus.framework.cloud.feign;

import com.oceancloud.grampus.framework.cloud.constant.LogRequestConstant;
import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.core.utils.WebUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * LogRequestInterceptor
 *
 * @author Beck
 * @since 2021-07-09
 */
@Slf4j
public class LogRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		String requestId = WebUtil.getRequest() != null ?
				WebUtil.getRequest().getHeader(LogRequestConstant.HEADER_INTERNAL_REQUEST_ID) : null;

		if (StringUtil.isNotBlank(requestId)) {
			template.header(LogRequestConstant.HEADER_INTERNAL_REQUEST_ID, requestId);
		}
	}
}