package com.nexus.mall.service;

import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendResource;

import java.util.List;

/**

* @Description:    后台用户缓存操作类

* @Author:         Nexus

* @CreateDate:     2020/11/26 23:06

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/26 23:06

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface BackendAdminCacheService {
    /**
     * 删除后台用户缓存
     * @Author : Nexus
     * @Description : 删除后台用户缓存
     * @Date : 2020/11/26 23:07
     * @Param : adminId
     * @return : void
     **/
    void delAdmin(Long adminId);

    /**
     * 删除后台用户资源列表缓存
     * @Author : Nexus
     * @Description : 删除后台用户资源列表缓存
     * @Date : 2020/11/26 23:07
     * @Param : adminId
     * @return : void
     **/
    void delResourceList(Long adminId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     * @Author : Nexus
     * @Description : 当角色相关资源信息改变时删除相关后台用户缓存
     * @Date : 2020/11/26 23:07
     * @Param : roleId
     * @return : void
     **/
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     * @Author : Nexus
     * @Description :当角色相关资源信息改变时删除相关后台用户缓存
     * @Date : 2020/11/26 23:09
     * @Param : roleIds
     * @return : void
     **/
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     * @Author : Nexus
     * @Description : 当资源信息改变时，删除资源项目后台用户缓存
     * @Date : 2020/11/26 23:09
     * @Param : resourceId
     * @return : void
     **/
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     * @Author : Nexus
     * @Description : 获取缓存后台用户信息
     * @Date : 2020/11/26 23:10
     * @Param : username
     * @return : com.nexus.mall.pojo.BackendAdmin
     **/
    BackendAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     * @Author : Nexus
     * @Description : 设置缓存后台用户信息
     * @Date : 2020/11/26 23:10
     * @Param : admin
     * @return : void
     **/
    void setAdmin(BackendAdmin admin);

    /**
     * 获取缓存后台用户资源列表
     * @Author : Nexus
     * @Description : 获取缓存后台用户资源列表
     * @Date : 2020/11/26 23:10
     * @Param : adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> getResourceList(Long adminId);

    /**
     * 设置后台后台用户资源列表
     * @Author : Nexus
     * @Description : 设置后台后台用户资源列表
     * @Date : 2020/11/26 23:10
     * @Param : adminId
     * @Param : resourceList
     * @return : void
     **/
    void setResourceList(Long adminId, List<BackendResource> resourceList);
}
