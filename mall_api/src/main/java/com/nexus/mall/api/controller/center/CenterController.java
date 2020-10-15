package com.nexus.mall.api.controller.center;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.service.center.CenterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/10/15 21:29

* @UpdateUser:     Nexus

* @UpdateDate:     2020/10/15 21:29

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "center - 用户中心", tags = {"用户中心展示的相关接口"})
@RestController
@Validated
@RequestMapping("/center")
public class CenterController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("userInfo")
    public ServerResponse userInfo(
            @NotBlank @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {

        Users user = centerUserService.queryUserInfo(userId);
        return ServerResponse.success(user);
    }
}
