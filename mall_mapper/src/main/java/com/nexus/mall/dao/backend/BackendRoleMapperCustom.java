package com.nexus.mall.dao.backend;

import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.pojo.BackendResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**

* @Description:    自定义后台角色管理

* @Author:         Nexus

* @CreateDate:     2020/11/25 22:36

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/25 22:36

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface BackendRoleMapperCustom {
    /**
     * 根据后台用户ID获取菜单
     * @Author : Nexus
     * @Description : 根据后台用户ID获取菜单
     * @Date : 2020/11/25 22:37
     * @Param : adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendMenu>
     **/
    List<BackendMenu> getMenuList(@Param("adminId") Long adminId);
    /**
     * 根据角色ID获取菜单
     * @Author : Nexus
     * @Description : 根据角色ID获取菜单
     * @Date : 2020/11/25 22:37
     * @Param : roleId
     * @return : java.util.List<com.nexus.mall.pojo.BackendMenu>
     **/
    List<BackendMenu> getMenuListByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据角色ID获取资源
     * @Author : Nexus
     * @Description : 根据角色ID获取资源
     * @Date : 2020/11/25 22:37
     * @Param : roleId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
