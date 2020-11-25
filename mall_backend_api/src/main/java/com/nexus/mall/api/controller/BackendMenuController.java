package com.nexus.mall.api.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/11/25 22:57

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/25 22:57

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "后台菜单管理",tags = {"后台菜单管理"})
@RestController
@Validated
@RequestMapping("/menu")
public class BackendMenuController {



}
