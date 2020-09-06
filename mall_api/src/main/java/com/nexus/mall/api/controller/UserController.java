package com.nexus.mall.api.controller;

import com.nexus.mall.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "用户相关接口",tags = {"user"})
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


}
