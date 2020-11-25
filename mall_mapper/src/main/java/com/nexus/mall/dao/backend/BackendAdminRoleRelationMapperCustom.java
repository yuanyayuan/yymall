package com.nexus.mall.dao.backend;

import com.nexus.mall.pojo.BackendAdminRoleRelation;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className BackendAdminRoleRelationMapperCustom
 * @description 自定义后台用户与角色管理Mapper
 * @author LiYuan
 * @date 2020/9/16
**/
public interface BackendAdminRoleRelationMapperCustom {
    /**
     *
     * insertList
     *
     * @Author LiYuan
     * @Description 批量插入用户角色关系
     * @Date 11:27 2020/9/16
     * @param adminRoleRelationList
     * @return int
    **/
    int insertList(@Param("list") List<BackendAdminRoleRelation> adminRoleRelationList);

    /**
     * getResourceList
     * @Author : Nexus
     * @Description : //获取用户所有可访问资源
     * @Date : 2020/9/10 22:42
     * @Param : adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> getResourceList(@Param("adminId") Long adminId);
    /**
     *
     * getRoleList
     *
     * @Author LiYuan
     * @Description 获取用于所有角色
     * @Date 14:47 2020/9/11
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
     **/
    List<BackendRole> getRoleList(@Param("adminId") Long adminId);
}
