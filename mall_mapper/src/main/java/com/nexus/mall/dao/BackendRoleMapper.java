package com.nexus.mall.dao;

import com.nexus.mall.my.mapper.MyMapper;
import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.pojo.BackendRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BackendRoleMapper extends MyMapper<BackendRole> {
    /**
     * 
     * getMenuList
     * 
     * @Author LiYuan
     * @Description 根据后台用户ID获取菜单
     * @Date 14:42 2020/9/11
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendMenu>
    **/
    List<BackendMenu> getMenuList(@Param("adminId") Long adminId);
}