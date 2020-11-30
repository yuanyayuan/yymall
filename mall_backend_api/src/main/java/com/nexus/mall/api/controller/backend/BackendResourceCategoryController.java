package com.nexus.mall.api.controller.backend;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/11/25 23:01

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/25 23:01

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "后台资源分类管理",tags = {"后台资源分类管理"})
@Validated
@RestController
@RequestMapping("/resourceCategory")
public class BackendResourceCategoryController {
}
