package com.nexus.mall.service.backend;

import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
     * 创建角色
     *
     * @Author LiYuan
     * @Description 创建角色
     * @Date 14:41 2020/9/25
     * @param role
     * @return int
    **/
    int create(BackendRole role);

    /**
     *
     * 修改角色信息
     *
     * @Author LiYuan
     * @Description 修改角色信息
     * @Date 14:46 2020/9/25
     * @param id
     * @param role
     * @return int
    **/
    int update(Long id,BackendRole role);

    /**
     *
     * 批量删除角色
     *
     * @Author LiYuan
     * @Description 批量删除角色
     * @Date 14:47 2020/9/25
     * @param ids
     * @return int
    **/
    int delete(List<Long> ids);

    /**
     *
     * 获取所有角色信息
     *
     * @Author LiYuan
     * @Description  获取所有角色信息
     * @Date 14:49 2020/9/25
     * @param
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
    **/
    List<BackendRole> list();

    /**
     *
     * 分页获取角色列表
     *
     * @Author LiYuan
     * @Description 分页获取角色列表
     * @Date 14:53 2020/9/25
     * @param keyword
     * @param page
     * @param pageSize
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
    **/
    List<BackendRole> list(String keyword,Integer page,Integer pageSize);

    /**
     *
     * 根据用户id获取对应菜单
     *
     * @Author LiYuan
     * @Description 根据用户id获取对应菜单
     * @Date 10:19 2020/9/11
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendMenu>
     **/
    List<BackendMenu> getMenuList(Long adminId);

    /**
     *
     * 获取角色相关菜单
     *
     * @Author LiYuan
     * @Description 根据管理员ID获取对应菜单
     * @Date 15:01 2020/9/25
     * @param roleId
     * @return java.util.List<com.nexus.mall.pojo.BackendMenu>
    **/
    List<BackendMenu> listMenu(Long roleId);

    /**
     *
     * 获取角色相关资源
     *
     * @Author LiYuan
     * @Description 获取角色相关资源
     * @Date 15:04 2020/9/25
     * @param roleId
     * @return java.util.List<com.nexus.mall.pojo.BackendResource>
    **/
    List<BackendResource> listResource(Long roleId);

    /**
     *
     * 给角色分配菜单
     *
     * @Author LiYuan
     * @Description 给角色分配菜单
     * @Date 15:04 2020/9/25
     * @param roleId
     * @param menuIds
     * @return int
    **/
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     *
     * 给角色分配资源
     *
     * @Author LiYuan
     * @Description 给角色分配资源
     * @Date 15:12 2020/9/25
     * @param roleId
     * @param resourceIds
     * @return int
    **/
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    int allocResource(Long roleId, List<Long> resourceIds);


}
