package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.bo.user.ShopcartBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@Validated
@RequestMapping("/shopcart")
@RestController
public class ShopcatController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public ServerResponse add(@NotBlank @RequestParam String userId,
                              @RequestBody ShopcartBO shopcartBO,
                              HttpServletRequest request,
                              HttpServletResponse response
    ) {

        System.out.println(shopcartBO);

        // TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存

        return ServerResponse.success();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public ServerResponse del(
            @NotBlank @RequestParam String userId,
            @NotBlank @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return ServerResponse.success();
    }
}
