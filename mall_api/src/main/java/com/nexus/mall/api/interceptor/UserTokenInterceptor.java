package com.nexus.mall.api.interceptor;

import com.nexus.mall.properties.JwtProperties;
import com.nexus.mall.util.JwtTokenUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/12/9 0:24

* @UpdateUser:     Nexus

* @UpdateDate:     2020/12/9 0:24

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public class UserTokenInterceptor implements HandlerInterceptor {
    /**
     * 拦截请求，在访问controller调用之前
     * @Author : Nexus
     * @Description : 拦截请求，在访问controller调用之前
     * @Date : 2020/12/9 0:26
     * @Param : request
     * @Param : response
     * @Param : handler
     * @return : boolean
     **/
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        // JWT工具类
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        JwtProperties jwtProperties = JwtProperties.getJwtProperties();

        // 1、验证Token有效性 -> 用户是否登录过

        // 2、解析出JWT中的payload -> userid - randomKey

        // 3、是否需要验签,以及验签的算法

        // 4、判断userid是否有效

        /*
          false: 请求被拦截，被驳回，验证出现问题
          true: 请求在经过验证校验以后，是OK的，是可以放行的
         */
        return true;
    }
    /**
     * 请求访问controller之后，渲染视图之前
     * @Author : Nexus
     * @Description : 请求访问controller之后，渲染视图之前
     * @Date : 2020/12/9 0:26
     * @Param : request
     * @Param : response
     * @Param : handler
     * @Param : modelAndView
     * @return : void
     **/
    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {

    }
    /**
     * 请求访问controller之后，渲染视图之后
     * @Author : Nexus
     * @Description : 请求访问controller之后，渲染视图之后
     * @Date : 2020/12/9 0:26
     * @Param : request
     * @Param : response
     * @Param : handler
     * @Param : ex
     * @return : void
     **/
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {

    }
}
