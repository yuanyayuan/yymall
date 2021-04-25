package com.nexus.mall.api.interceptor;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.util.RedisOperator;
import com.nexus.mall.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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

    @Autowired
    private RedisOperator redisOperator;

    public static final String REDIS_USER_TOKEN = "redis_user_token";
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

        // 1、验证Token有效性 -> 用户是否登录过
        // 2、解析出JWT中的payload -> userid - randomKey
        // 3、是否需要验签,以及验签的算法
        // 4、判断userid是否有效

        // 拿去user相关信息
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String uniqueToken = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
            if (StringUtils.isBlank(uniqueToken)) {
//                System.out.println("请登录...");
                returnErrorResponse(response, ServerResponse.failed("请登录..."));
                return false;
            } else {
                if (!uniqueToken.equals(userToken)) {
//                    System.out.println("账号在异地登录...");
                    returnErrorResponse(response, ServerResponse.failed("账号在异地登录..."));
                    return false;
                }
            }
        } else {
//            System.out.println("请登录...");
            returnErrorResponse(response, ServerResponse.failed("请登录..."));
            return false;
        }

        /*
          false: 请求被拦截，被驳回，验证出现问题
          true: 请求在经过验证校验以后，是OK的，是可以放行的
         */
        return true;
    }


    public void returnErrorResponse(HttpServletResponse response,
                                    ServerResponse result) {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(Objects.requireNonNull(JsonUtils.objectToJson(result)).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
     **/
    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) {

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
     **/
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {

    }
}
