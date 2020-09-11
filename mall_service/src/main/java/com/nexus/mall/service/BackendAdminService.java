package com.nexus.mall.service;

import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.pojo.bo.AdminCreateBO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**

* @Description:    后台管理员Service

* @Author:         Nexus

* @CreateDate:     2020/9/10 21:47

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/10 21:47

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface BackendAdminService {
    /**
     * login
     * @Author : Nexus
     * @Description : 用户登录
     * @Date : 2020/9/10 23:44
     * @Param : username
     * @Param : password
     * @return : java.lang.String
     **/
    String login(String username,String password);

    /**
     * register
     * @Author : Nexus
     * @Description : 后台用户注册
     * @Date : 2020/9/10 23:24
     * @Param : adminParam
     * @return : com.nexus.mall.pojo.BackendAdmin
     **/
    BackendAdmin register(AdminCreateBO adminParam);

    /**
     * queryUsernameIsExist
     * @Author : Nexus
     * @Description : 判断用户名是否唯一
     * @Date : 2020/9/10 23:28
     * @Param : username
     * @return : boolean
     **/
    boolean queryUsernameIsExist(String username);

    /**
     * getAdminByUsername
     * @Author : Nexus
     * @Description : 根据用户名获取用户
     * @Date : 2020/9/10 21:48
     * @Param : username
     * @return : com.nexus.mall.pojo.BackendAdmin
     **/
    BackendAdmin getAdminByUsername(String username);

    /**
     *
     * refreshToken
     *
     * @Author LiYuan
     * @Description 刷新token的功能
     * @Date 9:33 2020/9/11
     * @param oldToken
     * @return java.lang.String
    **/
    String refreshToken(String oldToken);

    /**
     * loadUserByUsername
     * @Author : Nexus
     * @Description : 获取用户信息
     * @Date : 2020/9/10 21:46
     * @Param : username
     * @return : org.springframework.security.core.userdetails.UserDetails
     **/
    UserDetails loadUserByUsername(String username);

    /**
     * getResourceList
     * @Author : Nexus
     * @Description : 获取指定用户的可访问资源
     * @Date : 2020/9/10 21:57
     * @Param : adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> getResourceList(Long adminId);
    /**
     *
     * getRoleList
     *
     * @Author LiYuan
     * @Description 获取用户对于角色
     * @Date 14:44 2020/9/11
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
    **/
    List<BackendRole> getRoleList(Long adminId);
}
