//package com.oceancloud.grampus.framework.datasource.dynamic.aspect;
//
//import com.oceancloud.grampus.framework.datasource.dynamic.annotation.DataSource;
//import com.oceancloud.grampus.framework.datasource.dynamic.enums.DataSourceType;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.hint.HintManager;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * 多数据源切面处理类
// * Project: grampus
// *
// * @author Beck
// * @since 2020-12-02
// */
//@Slf4j
//@Aspect
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class DataSourceAspect {
//
//    @Value("${spring.aop.proxy-target-class:#{true}}")
//    private Boolean proxyTargetClass;
//
//    @Pointcut("@annotation(com.oceancloud.grampus.framework.datasource.dynamic.annotation.DataSource) " +
//            "|| @within(com.oceancloud.grampus.framework.datasource.dynamic.annotation.DataSource)")
//    public void dataSourcePointCut() {
//
//    }
//
//    @Around("dataSourcePointCut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        // 多数据源上下文设为需要切换的数据源
//        DataSourceType dataSourceType = this.getDataSourceType(point);
//        if (DataSourceType.MASTER.equals(dataSourceType)) {
//            HintManager.getInstance().setMasterRouteOnly();
//        }
//        log.debug("=====>thread:{} switch to datasource [{}]", Thread.currentThread().getName(), dataSourceType);
//        try {
//            return point.proceed();
//        } finally {
//            HintManager.clear();
//			log.debug("=====>thread:{} clean datasource", Thread.currentThread().getName());
//        }
//    }
//
//    /**
//     * 获取需要切换的数据源
//     */
//    private DataSourceType getDataSourceType(ProceedingJoinPoint point) throws Exception {
//        // 支持类和方法上使用
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method;
//        if (proxyTargetClass) {
//            method = signature.getMethod();
//        } else {
//            method = this.getDataSourceByClazz(point);
//        }
//        DataSource methodDataSource = method.getAnnotation(DataSource.class);
//        // 方法级别优先级更高
//        if (methodDataSource != null) {
//            return methodDataSource.value();
//        } else {
//            Class<?> targetClass = point.getTarget().getClass();
//            DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
//            return targetDataSource.value();
//        }
//    }
//
//    /**
//     * 支持JDK动态代理
//     */
//    private Method getDataSourceByClazz(JoinPoint joinPoint) throws Exception {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        // JDK动态代理，代理对象的方法不会有注解，要取注解只能从目标对象取
//        Class<?> clazz = joinPoint.getTarget().getClass();
//        return clazz.getDeclaredMethod(methodSignature.getName(), method.getParameterTypes());
//    }
//}
