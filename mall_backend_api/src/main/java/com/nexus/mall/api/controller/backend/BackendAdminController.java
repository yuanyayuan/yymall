package com.nexus.mall.api.controller.backend;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import com.nexus.mall.common.api.CommonPage;
import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.pojo.bo.admin.AdminCreateBO;
import com.nexus.mall.pojo.bo.admin.AdminLoginBO;
import com.nexus.mall.pojo.bo.admin.UpdateAdminPasswordParam;
import com.nexus.mall.service.backend.BackendAdminService;
import com.nexus.mall.service.backend.BackendRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**

* @Description:    用户操作相关接口

* @Author:         Nexus

* @CreateDate:     2020/9/9 22:01

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 22:01

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "后台用户相关接口",tags = {"用于后台用户的相关接口"})
@Validated
@RestController
@RequestMapping("/admin")
public class BackendAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    /**
     * Authorization
     **/
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private BackendAdminService adminService;
    @Autowired
    private BackendRoleService roleService;


    @ApiOperation(value = "用户名唯一校验", notes = "用户名唯一校验", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ServerResponse usernameIsExist(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username){
        //查找注册的用户名是否存在
        boolean usernameIsExist = adminService.queryUsernameIsExist(username);
        if(usernameIsExist){
            return ServerResponse.failed(ResultCode.REGISTER_DUP_FAIL);
        }
        //请求成功，用户名没有重复
        return ServerResponse.success(null,"该用户不存在");
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public ServerResponse<BackendAdmin> register(@Validated @RequestBody AdminCreateBO adminParam){
        BackendAdmin register = adminService.register(adminParam);
        if(register == null){
            return ServerResponse.failed();
        }
        return ServerResponse.success(register);
    }

    @ApiOperation(value = "登录以后返回token",notes = "用户登录", httpMethod = "POST")
    @PostMapping(value = "/login")
    public ServerResponse login(@Validated @RequestBody AdminLoginBO adminLoginParam){
        String token = adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
        if(token == null){
            return ServerResponse.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = Maps.newHashMap();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ServerResponse.success(tokenMap);
    }

    @ApiOperation(value = "刷新token",notes = "刷新token", httpMethod = "GET")
    @GetMapping(value = "/refreshToken")
    public ServerResponse refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String newToken = adminService.refreshToken(token);
        if(newToken == null){
            return ServerResponse.failed("Token已过期");
        }
        Map<String, String> tokenMap = Maps.newHashMap();
        tokenMap.put("token", newToken);
        tokenMap.put("tokenHead", tokenHead);
        return ServerResponse.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息",notes = "获取当前登录用户信息", httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse getAdminInfo(Principal principal){
        if(principal == null){
            return ServerResponse.unauthorized(null);
        }
        String username = principal.getName();
        BackendAdmin admin = adminService.getAdminByUsername(username);
        Map<String, Object> data = Maps.newHashMap();
        data.put("username",admin.getUsername());
        data.put("menus",roleService.getMenuList(admin.getId()));
        data.put("icon",admin.getIcon());
        List<BackendRole> roleList = adminService.getRoleList(admin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(BackendRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return ServerResponse.success(data);
    }

    @ApiOperation(value = "登出功能",notes = "登出功能", httpMethod = "POST")
    @PostMapping(value = "/logout")
    public ServerResponse logout() {
        return ServerResponse.success(null);
    }

    @ApiOperation(value = "根据用户名或姓名分页获取用户列表",notes = "根据用户名或姓名分页获取用户列表", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ServerResponse<CommonPage<BackendAdmin>> list(
            @ApiParam(name = "keyword", value = "关键字", required = true)
            @RequestParam(value = "keyword", required = false)
                    String keyword,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page", defaultValue = "1")
                    Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize", defaultValue = "10")
                    Integer pageSize){
        List<BackendAdmin> result = adminService.list(keyword, page, pageSize);
        return ServerResponse.success(CommonPage.restPage(result));
    }

    @ApiOperation(value = "获取指定用户信息",notes = "获取指定用户信息",httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public ServerResponse<BackendAdmin> getItem(@PathVariable Long id) {
        BackendAdmin admin = adminService.getItem(id);
        return ServerResponse.success(admin);
    }

    @ApiOperation(value = "修改指定用户信息",notes = "修改指定用户信息",httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id, @RequestBody BackendAdmin admin) {
        int count = adminService.update(id, admin);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }
    @ApiOperation(value = "修改指定用户密码",notes = "修改指定用户密码",httpMethod = "POST")
    @PostMapping(value = "/updatePassword")
    public ServerResponse updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if(status > 1){
            return ServerResponse.success(status);
        }else {
            return ServerResponse.failed();
        }
    }
    @ApiOperation(value = "删除指定用户信息",notes = "删除指定用户信息",httpMethod = "POST")
    @PostMapping(value = "/delete/{id}")
    public ServerResponse delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "修改帐号状态",notes = "修改帐号状态",httpMethod = "POST")
    @PostMapping(value = "/updateStatus/{id}")
    public ServerResponse updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        BackendAdmin admin = new BackendAdmin();
        admin.setStatus(status);
        int count = adminService.update(id,admin);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "给用户分配角色",notes = "给用户分配角色",httpMethod = "POST")
    @PostMapping(value = "/role/update")
    public ServerResponse updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "获取指定用户的角色",notes = "获取指定用户的角色",httpMethod = "GET")
    @GetMapping(value = "role/{adminId}")
    public ServerResponse<List<BackendRole>> getRoleList(@PathVariable Long adminId) {
        List<BackendRole> roleList = adminService.getRoleList(adminId);
        return ServerResponse.success(roleList);
    }

}
