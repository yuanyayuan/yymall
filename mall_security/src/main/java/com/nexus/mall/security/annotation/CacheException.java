package com.nexus.mall.security.annotation;

import java.lang.annotation.*;
/**

* @Description:    自定义注解，有该注解的缓存方法会抛出异常

* @Author:         Nexus

* @CreateDate:     2021/4/4 21:29

* @UpdateUser:     Nexus 

* @UpdateDate:     2021/4/4 21:29

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
