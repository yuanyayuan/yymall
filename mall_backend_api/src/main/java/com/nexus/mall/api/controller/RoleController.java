package com.nexus.mall.api.controller;

import com.nexus.mall.service.BackendRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "后台用户角色管理",tags = {"后台用户角色管理"})
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private BackendRoleService roleService;
}
