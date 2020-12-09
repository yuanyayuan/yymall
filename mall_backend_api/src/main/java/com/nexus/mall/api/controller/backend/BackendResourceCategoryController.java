package com.nexus.mall.api.controller.backend;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.BackendResourceCategory;
import com.nexus.mall.service.backend.BackendResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Slf4j
@Validated
@RestController
@RequestMapping("/resourceCategory")
public class BackendResourceCategoryController {
    @Autowired
    private BackendResourceCategoryService resourceCategoryService;

    @ApiOperation(value = "查询所有后台资源分类", notes = "查询所有后台资源分类", httpMethod = "GET")
    @GetMapping(value = "/listAll")
    public ServerResponse<List<BackendResourceCategory>> listAll() {
        List<BackendResourceCategory> resourceList = resourceCategoryService.listAll();
        return ServerResponse.success(resourceList);
    }

    @ApiOperation(value = "添加后台资源分类", notes = "添加后台资源分类", httpMethod = "POST")
    @PostMapping(value = "/create")
    public ServerResponse create(@RequestBody BackendResourceCategory resourceCategory) {
        int count = resourceCategoryService.create(resourceCategory);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation(value = "修改后台资源分类", notes = "修改后台资源分类", httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id,
                                 @RequestBody BackendResourceCategory resourceCategory) {
        int count = resourceCategoryService.update(id, resourceCategory);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
    @ApiOperation(value = "根据ID删除后台资源", notes = "根据ID删除后台资源", httpMethod = "POST")
    @PostMapping(value = "/delete/{id}")
    public ServerResponse delete(@PathVariable Long id) {
        int count = resourceCategoryService.delete(id);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
}
