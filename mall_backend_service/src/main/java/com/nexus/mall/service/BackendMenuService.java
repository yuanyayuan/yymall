package com.nexus.mall.service;

import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.pojo.dto.admin.BackendMenuNode;

import java.util.List;

/**

* @Description:    后台菜单管理Service

* @Author:         Nexus

* @CreateDate:     2020/11/26 21:42

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/26 21:42

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface BackendMenuService {
    /**
     * 创建后台菜单
     * @Author : Nexus
     * @Description : 创建后台菜单
     * @Date : 2020/11/26 21:47
     * @Param : backendMenu
     * @return : int
     **/
    int create(BackendMenu backendMenu);

    /**
     * 修改后台菜单
     * @Author : Nexus
     * @Description : 修改后台菜单
     * @Date : 2020/11/26 21:47
     * @Param : id
     * @Param : backendMenu
     * @return : int
     **/
    int update(Long id, BackendMenu backendMenu);

    /**
     * 根据ID获取菜单详情
     * @Author : Nexus
     * @Description : 根据ID获取菜单详情
     * @Date : 2020/11/26 21:48
     * @Param : id
     * @return : com.nexus.mall.pojo.BackendMenu
     **/
    BackendMenu getItem(Long id);

    /**
     * 根据ID删除菜单
     * @Author : Nexus
     * @Description : 根据ID删除菜单
     * @Date : 2020/11/26 21:49
     * @Param : id
     * @return : int
     **/
    int delete(Long id);

    /**
     * 分页查询后台菜单
     * @Author : Nexus
     * @Description : 分页查询后台菜单
     * @Date : 2020/11/26 21:49
     * @Param : parentId
     * @Param : page
     * @Param : pageSize
     * @return : java.util.List<com.nexus.mall.pojo.BackendMenu>
     **/
    List<BackendMenu> list(Long parentId, Integer page, Integer pageSize);

    /**
     * 树形结构返回所有菜单列表
     * @Author : Nexus
     * @Description : 树形结构返回所有菜单列表
     * @Date : 2020/11/26 21:49
     * @Param :
     * @return : java.util.List<com.nexus.mall.pojo.dto.admin.BackendMenuNode>
     **/
    List<BackendMenuNode> treeList();

    /**
     * 修改菜单显示状态
     * @Author : Nexus
     * @Description : 修改菜单显示状态
     * @Date : 2020/11/26 21:49
     * @Param : id
     * @Param : hidden
     * @return : int
     **/
    int updateHidden(Long id, Integer hidden);
}
