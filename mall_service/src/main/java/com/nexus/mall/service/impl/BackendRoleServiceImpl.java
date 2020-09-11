package com.nexus.mall.service.impl;

import com.nexus.mall.dao.BackendRoleMapper;
import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.service.BackendRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/9 23:43

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 23:43

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
public class BackendRoleServiceImpl implements BackendRoleService {

    @Autowired
    private BackendRoleMapper roleMapper;

    /**
     * getMenuList
     *
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendMenu>
     * @Author LiYuan
     * @Description 根据用户id获取对应菜单
     * @Date 10:19 2020/9/11
     **/
    @Override
    public List<BackendMenu> getMenuList(Long adminId) {
        return roleMapper.getMenuList(adminId);
    }
}
