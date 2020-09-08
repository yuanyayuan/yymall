package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.UserCreatBO;
import com.nexus.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UserService userService;
    /**
     * UsernameIsExist
     * @Author : Nexus
     * @Description : //TODO
     * @Date : 2020/9/6 22:01
     * @Param : username 用户名
     * @return : int
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
        return ServerResponse.success("该用户不存在");
    }

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

//        CookieUtils.setCookie(request, response, "user",
//                JsonUtils.objectToJson(userResult), true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据


        return ServerResponse.success("注册成功");
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
