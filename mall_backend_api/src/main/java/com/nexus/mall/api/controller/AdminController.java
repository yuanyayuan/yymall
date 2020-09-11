package com.nexus.mall.api.controller;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.AdminCreateBO;
import com.nexus.mall.pojo.bo.AdminLoginBO;
import com.nexus.mall.service.BackendAdminService;
import com.nexus.mall.service.BackendRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.HashMap;
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
public class AdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;   //Authorization

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
        return ServerResponse.success("该用户不存在");
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServerResponse<BackendAdmin> register(@Validated @RequestBody AdminCreateBO adminParam){
        BackendAdmin register = adminService.register(adminParam);
        if(register == null){
            return ServerResponse.failed();
        }
        return ServerResponse.success(register);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
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

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
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

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
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

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ServerResponse logout() {
        return ServerResponse.success(null);
    }




}
