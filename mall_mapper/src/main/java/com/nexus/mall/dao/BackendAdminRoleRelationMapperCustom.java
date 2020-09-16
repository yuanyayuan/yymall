package com.nexus.mall.dao;

import com.nexus.mall.pojo.BackendAdminRoleRelation;
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
}
