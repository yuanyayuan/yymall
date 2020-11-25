package com.nexus.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.nexus.mall.dao.backend.BackendRoleMapper;
import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.service.BackendRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**

* @Description:    后台角色Service实现类

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
     * 根据用户id获取对应菜单
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


    /**
     * 创建角色
     *
     * @param role
     * @return int
     * @Author LiYuan
     * @Description 创建角色
     * @Date 14:41 2020/9/25
     **/
    @Override
    public int create(BackendRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    /**
     * 修改角色信息
     *
     * @param id
     * @param role
     * @return int
     * @Author LiYuan
     * @Description 修改角色信息
     * @Date 14:46 2020/9/25
     **/
    @Override
    public int update(Long id, BackendRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 批量删除角色
     *
     * @param ids
     * @return int
     * @Author LiYuan
     * @Description 批量删除角色
     * @Date 14:47 2020/9/25
     **/
    @Override
    public int delete(List<Long> ids) {
        Example roleExample = new Example(BackendRole.class);
        roleExample.createCriteria().andIn("id",ids);
        return roleMapper.deleteByExample(roleExample);
    }

    /**
     * 获取所有角色信息
     *
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
     * @Author LiYuan
     * @Description 获取所有角色信息
     * @Date 14:49 2020/9/25
     **/
    @Override
    public List<BackendRole> list() {
        return roleMapper.selectByExample(new Example((BackendRole.class)));
    }

    /**
     * 分页获取角色列表
     *
     * @param keyword
     * @param page
     * @param pageSize
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
     * @Author LiYuan
     * @Description 分页获取角色列表
     * @Date 14:53 2020/9/25
     **/
    @Override
    public List<BackendRole> list(String keyword, Integer page, Integer pageSize) {
        Example example = new Example(BackendRole.class);
        PageHelper.startPage(page, pageSize);
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andLike("name","%" + keyword + "%");
        }
        return roleMapper.selectByExample(example);
    }

    /**
     * 获取角色相关菜单
     *
     * @param roleId
     * @return java.util.List<com.nexus.mall.pojo.BackendMenu>
     * @Author LiYuan
     * @Description 根据管理员ID获取对应菜单
     * @Date 15:01 2020/9/25
     **/
    @Override
    public List<BackendMenu> listMenu(Long roleId) {
        return null;
    }

    /**
     * 获取角色相关资源
     *
     * @param roleId
     * @return java.util.List<com.nexus.mall.pojo.BackendResource>
     * @Author LiYuan
     * @Description 获取角色相关资源
     * @Date 15:04 2020/9/25
     **/
    @Override
    public List<BackendResource> listResource(Long roleId) {
        return null;
    }

    /**
     * 给角色分配菜单
     *
     * @param roleId
     * @param menuIds
     * @return int
     * @Author LiYuan
     * @Description 给角色分配菜单
     * @Date 15:04 2020/9/25
     **/
    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        return 0;
    }

    /**
     * 给角色分配资源
     *
     * @param roleId
     * @param resourceIds
     * @return int
     * @Author LiYuan
     * @Description 给角色分配资源
     * @Date 15:12 2020/9/25
     **/
    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        return 0;
    }
}
