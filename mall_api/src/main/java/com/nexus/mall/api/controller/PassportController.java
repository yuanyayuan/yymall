package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(value = "通行相关接口",tags = {"passport"})
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
    @GetMapping("/UsernameIsExist")
    public ServerResponse usernameIsExist(@RequestParam String username){
        //1. 判断用户名不能为空
        if(StringUtils.isBlank(username)){
            return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR);
        }

        //2. 查找注册的用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(username);
        if(usernameIsExist){
            return ServerResponse.failed(ResultCode.REGISTER_DUP_FAIL);
        }
        //3. 请求成功，用户名没有重复
        return ServerResponse.success("该用户不存在");
    }
}
