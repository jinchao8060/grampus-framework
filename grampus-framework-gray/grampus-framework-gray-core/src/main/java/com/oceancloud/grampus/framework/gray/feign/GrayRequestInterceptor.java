package com.oceancloud.grampus.framework.gray.feign;

import com.oceancloud.grampus.framework.core.utils.StringUtil;
import com.oceancloud.grampus.framework.core.utils.WebUtil;
import com.oceancloud.grampus.framework.gray.constant.GrayLoadBalancerConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * GrayRequestInterceptor
 *
 * @author Beck
 * @since 2021-06-29
 */
@Slf4j
public class GrayRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		String reqVersion = WebUtil.getRequest() != null ?
				WebUtil.getRequest().getHeader(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION) : null;

		if (StringUtil.isNotBlank(reqVersion)) {
			log.debug("feign gray add header version :{}", reqVersion);
			template.header(GrayLoadBalancerConstant.HEADER_INTERNAL_VERSION, reqVersion);
		}
	}
}
