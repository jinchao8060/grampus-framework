package com.oceancloud.grampus.framework.cloud.log;

import com.oceancloud.grampus.framework.cloud.constant.LogRequestConstant;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LogHandlerInterceptor
 *
 * @author Beck
 * @since 2021-07-09
 */
public class LogHandlerInterceptor implements HandlerInterceptor {
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestId = request.getHeader(LogRequestConstant.HEADER_INTERNAL_REQUEST_ID);
        MDC.put(LogRequestConstant.LOG_REQUEST_ID_MDC_TRACE, requestId);
        return true;
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        MDC.remove(LogRequestConstant.LOG_REQUEST_ID_MDC_TRACE);
    }
 
}