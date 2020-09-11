package com.nexus.mall.service;

import com.nexus.mall.pojo.BackendMenu;

import java.util.List;

/**
 * @className BackendRoleService
 * @description 后台角色管理Service
 * @author LiYuan
 * @date 2020/9/11
**/
public interface BackendRoleService {
    /**
     *
     * getMenuList
     *
     * @Author LiYuan
     * @Description 根据用户id获取对应菜单
     * @Date 10:19 2020/9/11
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendMenu>
    **/
    List<BackendMenu> getMenuList(Long adminId);
}
