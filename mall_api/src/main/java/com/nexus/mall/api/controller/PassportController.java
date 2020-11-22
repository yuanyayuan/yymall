package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.user.UserCreatBO;
import com.nexus.mall.pojo.bo.user.UserLoginBO;
import com.nexus.mall.service.UserService;
import com.nexus.mall.util.CookieUtils;
import com.nexus.mall.util.JsonUtils;
import com.nexus.mall.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;


/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/8 21:29

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/8 21:29

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "通行相关接口",tags = {"用于注册登录的相关接口"})
@Validated
@Slf4j
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UserService userService;
    /**
     * UsernameIsExist
     * @Author : Nexus
     * @Description : 用户名唯一校验
     * @Date : 2020/9/6 22:01
     * @Param : username 用户名
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ApiOperation(value = "用户名唯一校验", notes = "用户名唯一校验", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ServerResponse usernameIsExist(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username){
        //查找注册的用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(username);
        if(usernameIsExist){
            return ServerResponse.failed(ResultCode.REGISTER_DUP_FAIL);
        }
        //请求成功，用户名没有重复
        return ServerResponse.success(null,"该用户不存在");
    }
    /**
     * register
     * @Author : Nexus
     * @Description : 前台用户注册
     * @Date : 2020/9/10 23:58
     * @Param : userBO
     * @Param : request
     * @Param : response
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public ServerResponse register(@Validated @RequestBody UserCreatBO userBO,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        //查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return ServerResponse.failed("用户名已经存在");
        }

        //判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return ServerResponse.failed("两次密码输入不一致");
        }

        //实现注册
        Users userResult = userService.createUser(userBO);

        setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据


        return ServerResponse.success(null,"注册成功");
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public ServerResponse login(@Validated @RequestBody UserLoginBO userBO,
                                HttpServletRequest request,
                                HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //实现登录
        Users userResult = null;
        try {
            userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            log.warn("[PassportController.login] ,{}", ExceptionUtils.getStackTrace(e));
        }

        if(userResult == null){
            return ServerResponse.loginFail();
        }

        setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        return ServerResponse.success(userResult);
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public ServerResponse logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return ServerResponse.success();
    }

    private void setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
    }
}
