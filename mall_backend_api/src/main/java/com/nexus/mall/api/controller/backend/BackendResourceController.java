package com.nexus.mall.api.controller.backend;

import com.nexus.mall.common.api.CommonPage;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.security.component.DynamicSecurityMetadataSource;
import com.nexus.mall.service.backend.BackendResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private BackendResourceService resourceService;

    @ApiOperation(value = "添加后台资源", notes = "添加后台资源", httpMethod = "GET")
    @GetMapping(value = "/create")
    public ServerResponse create(@RequestBody BackendResource resource) {
        int count = resourceService.create(resource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0){
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation(value = "修改后台资源", notes = "修改后台资源", httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id,
                                 @RequestBody BackendResource resource){
        int count = resourceService.update(id, resource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0){
            return ServerResponse.success(count);
        }else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation(value = "根据ID获取资源详情", notes = "根据ID获取资源详情", httpMethod = "POST")
    @PostMapping(value = "/{id}")
    public ServerResponse<BackendResource> getItem(@PathVariable Long id){
        BackendResource backendResource = resourceService.getItem(id);
        return ServerResponse.success(backendResource);
    }

    @ApiOperation(value = "根据ID删除后台资源",notes = "根据ID删除后台资源",httpMethod = "POST")
    @PostMapping(value = "/delete/{id}")
    public ServerResponse delete(@PathVariable Long id) {
        int count = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
    @ApiOperation(value = "分页模糊查询后台资源",notes = "分页模糊查询后台资源",httpMethod = "GET")
    @GetMapping(value = "/list")
    public ServerResponse<CommonPage<BackendResource>> list(
            @RequestParam(required = false)
                    Long categoryId,
            @RequestParam(required = false)
                    String nameKeyword,
            @RequestParam(required = false)
                    String urlKeyword,
            @RequestParam(value = "page", defaultValue = "1")
                    Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5")
                    Integer pageSize) {
        List<BackendResource> resourceList = resourceService.list(categoryId, nameKeyword, urlKeyword, page, pageSize);
        return ServerResponse.success(CommonPage.restPage(resourceList));
    }
    @ApiOperation(value = "查询所有后台资源",notes = "查询所有后台资源",httpMethod = "GET")
    @GetMapping(value = "/listAll")
    public ServerResponse<List<BackendResource>> listAll() {
        List<BackendResource> resourceList = resourceService.listAll();
        return ServerResponse.success(resourceList);
    }

}
