package com.nexus.mall.api.controller;

import com.nexus.mall.security.component.DynamicSecurityMetadataSource;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/11/25 23:00

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/25 23:00

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "后台资源管理",tags = {"后台资源管理"})
@RestController
@Validated
@RequestMapping("/resource")
public class BackendResourceController {
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

}
