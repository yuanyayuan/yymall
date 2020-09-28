package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.service.BackendRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "后台用户角色管理",tags = {"后台用户角色管理"})
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private BackendRoleService roleService;

    @ApiOperation(value = "添加角色", notes = "添加角色", httpMethod = "POST")
    @GetMapping(value = "/create")
    public ServerResponse create(@RequestBody BackendRole role){

        return null;
    }
}
