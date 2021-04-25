package com.nexus.mall.security.aspect;


import com.nexus.mall.security.annotation.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**

* @Description:    Redis缓存切面，防止Redis宕机影响正常业务逻辑

* @Author:         Nexus

* @CreateDate:     2021/4/4 21:24

* @UpdateUser:     Nexus

* @UpdateDate:     2021/4/4 21:24

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Component
@Order(2)
@Slf4j
public class RedisCacheAspect {
    @Pointcut("execution(public * com.nexus.mall.service.backend.*CacheService.*(..)) || execution(public * com.nexus.mall.service.*CacheService.*(..))")
    public void cacheAspect() {
    }
    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            //有CacheException注解的方法需要抛出异常
            if (method.isAnnotationPresent(CacheException.class)) {
                throw throwable;
            } else {
                log.error(throwable.getMessage());
            }
        }
        return result;
    }
}
