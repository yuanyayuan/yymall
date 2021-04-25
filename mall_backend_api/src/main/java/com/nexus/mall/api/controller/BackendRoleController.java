package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.CommonPage;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.service.backend.BackendRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "后台用户角色管理",tags = {"后台用户角色管理"})
@RestController
@RequestMapping("/role")
public class BackendRoleController {
    @Autowired
    private BackendRoleService roleService;

    @ApiOperation(value = "添加角色", notes = "添加角色", httpMethod = "POST")
    @PostMapping(value = "/create")
    public ServerResponse create(@RequestBody BackendRole role){
        int count = roleService.create(role);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "修改角色", notes = "修改角色", httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id, @RequestBody BackendRole role){
        int count = roleService.update(id, role);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "批量删除角色", notes = "批量删除角色", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public ServerResponse delete(@RequestParam("ids") List<Long> ids){
        int count = roleService.delete(ids);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "获取所有角色", notes = "获取所有角色", httpMethod = "GET")
    @GetMapping(value = "/listAll")
    public ServerResponse<List<BackendRole>> listAll() {
        List<BackendRole> roleList = roleService.list();
        return ServerResponse.success(roleList.stream().filter(role -> role.getStatus() == 1).collect(Collectors.toList()));
    }

    @ApiOperation(value = "根据角色名称分页获取角色列表", notes = "根据角色名称分页获取角色列表", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ServerResponse<CommonPage<BackendRole>> listAll(
            @ApiParam(name = "keyword", value = "关键字", required = true)
            @RequestParam(value = "keyword", required = false)
                    String keyword,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page",defaultValue = "1",required = false)
                    Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)
                    Integer pageSize) {
        List<BackendRole> roleList = roleService.list(keyword, page, pageSize);
        return ServerResponse.success(CommonPage.restPage(roleList));
    }

    @ApiOperation(value = "修改角色状态", notes = "修改角色状态", httpMethod = "POST")
    @PostMapping(value = "/updateStatus/{id}")
    public ServerResponse updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        BackendRole backendRole = new BackendRole();
        backendRole.setStatus(status);
        int count = roleService.update(id, backendRole);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "获取角色相关菜单", notes = "获取角色相关菜单", httpMethod = "GET")
    @GetMapping(value = "/listMenu/{roleId}")
    public ServerResponse<List<BackendMenu>> listMenu(@PathVariable Long roleId) {
        List<BackendMenu> roleList = roleService.listMenu(roleId);
        return ServerResponse.success(roleList);
    }


    @ApiOperation(value = "获取角色相关资源", notes = "获取角色相关资源", httpMethod = "GET")
    @GetMapping(value = "/listResource/{roleId}")
    public ServerResponse<List<BackendResource>> listResource(@PathVariable Long roleId) {
        List<BackendResource> roleList = roleService.listResource(roleId);
        return ServerResponse.success(roleList);
    }

    @ApiOperation(value = "给角色分配菜单", notes = "给角色分配菜单", httpMethod = "POST")
    @PostMapping(value = "/allocMenu")
    @ResponseBody
    public ServerResponse allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return ServerResponse.success(count);
    }

    @ApiOperation(value = "给角色分配资源",notes = "给角色分配资源",httpMethod = "POST")
    @PostMapping(value = "/allocResource")
    public ServerResponse allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return ServerResponse.success(count);
    }
}
