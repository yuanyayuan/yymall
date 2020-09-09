package com.nexus.mall.api.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

* @Description:    用户操作相关接口

* @Author:         Nexus

* @CreateDate:     2020/9/9 22:01

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 22:01

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "后台用户相关接口",tags = {"用于后台用户的相关接口"})
@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


}
