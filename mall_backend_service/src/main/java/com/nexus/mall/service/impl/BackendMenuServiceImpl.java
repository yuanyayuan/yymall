package com.nexus.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.nexus.mall.dao.backend.BackendMenuMapper;
import com.nexus.mall.pojo.BackendMenu;
import com.nexus.mall.pojo.dto.admin.BackendMenuNode;
import com.nexus.mall.service.backend.BackendMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BackendMenuServiceImpl implements BackendMenuService {


    @Autowired
    private BackendMenuMapper menuMapper;

    /**
     * 创建后台菜单
     *
     * @param backendMenu
     * @return : int
     * @Author : Nexus
     * @Description : 创建后台菜单
     * @Date : 2020/11/26 21:47
     * @Param : backendMenu
     */
    @Override
    public int create(BackendMenu backendMenu) {
        backendMenu.setCreateTime(new Date());
        updateLevel(backendMenu);
        return menuMapper.insert(backendMenu);
    }
    /**
     * 修改菜单层级
     * @Author : Nexus
     * @Description : 修改菜单层级
     * @Date : 2020/11/26 22:00
     * @Param : backendMenu
     * @return : void
     **/
    private void updateLevel(BackendMenu backendMenu){
        if (backendMenu.getParentId() == 0){
            //没有父菜单时为一级菜单
            backendMenu.setLevel(0);
        }else {
            //有父菜单时选择根据父菜单level设置
            BackendMenu parentMenu = menuMapper.selectByPrimaryKey(backendMenu.getParentId());
            if(parentMenu != null){
                backendMenu.setLevel(parentMenu.getLevel() + 1);
            }else {
                backendMenu.setLevel(0);
            }
        }
    }

    /**
     * 修改后台菜单
     *
     * @param id
     * @param backendMenu
     * @return : int
     * @Author : Nexus
     * @Description : 修改后台菜单
     * @Date : 2020/11/26 21:47
     * @Param : id
     * @Param : backendMenu
     */
    @Override
    public int update(Long id, BackendMenu backendMenu) {
        backendMenu.setId(id);
        updateLevel(backendMenu);
        return menuMapper.updateByPrimaryKeySelective(backendMenu);
    }

    /**
     * 根据ID获取菜单详情
     *
     * @param id
     * @return : com.nexus.mall.pojo.BackendMenu
     * @Author : Nexus
     * @Description : 根据ID获取菜单详情
     * @Date : 2020/11/26 21:48
     * @Param : id
     */
    @Override
    public BackendMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据ID删除菜单
     *
     * @param id
     * @return : int
     * @Author : Nexus
     * @Description : 根据ID删除菜单
     * @Date : 2020/11/26 21:49
     * @Param : id
     */
    @Override
    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页查询后台菜单
     *
     * @param parentId
     * @param page
     * @param pageSize
     * @return : java.util.List<com.nexus.mall.pojo.BackendMenu>
     * @Author : Nexus
     * @Description : 分页查询后台菜单
     * @Date : 2020/11/26 21:49
     * @Param : parentId
     * @Param : pageSize
     * @Param : pageNum
     */
    @Override
    public List<BackendMenu> list(Long parentId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Example example = new Example(BackendMenu.class);
        example.createCriteria().andEqualTo("parentId", parentId);
        return menuMapper.selectByExample(example);
    }

    /**
     * 树形结构返回所有菜单列表
     *
     * @return : java.util.List<com.nexus.mall.pojo.dto.admin.BackendMenuNode>
     * @Author : Nexus
     * @Description : 树形结构返回所有菜单列表
     * @Date : 2020/11/26 21:49
     * @Param :
     **/
    @Override
    public List<BackendMenuNode> treeList() {
        List<BackendMenu> backendMenuList = menuMapper.selectByExample(new Example(BackendMenu.class));
        return backendMenuList.stream()
                .filter(backendMenu -> backendMenu.getParentId().equals(0L))
                .map(backendMenu -> coverMenuNode(backendMenu, backendMenuList)).collect(Collectors.toList());
    }

    private BackendMenuNode coverMenuNode(BackendMenu backendMenu,List<BackendMenu> menuList){
        BackendMenuNode node = new BackendMenuNode();
        BeanUtils.copyProperties(backendMenu, node);
        List<BackendMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(backendMenu.getId()))
                .map(subMenu -> coverMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * 修改菜单显示状态
     *
     * @param id
     * @param hidden
     * @return : int
     * @Author : Nexus
     * @Description : 修改菜单显示状态
     * @Date : 2020/11/26 21:49
     * @Param : id
     * @Param : hidden
     */
    @Override
    public int updateHidden(Long id, Integer hidden) {
        BackendMenu backendMenu = new BackendMenu();
        backendMenu.setId(id);
        backendMenu.setHidden(hidden);
        return menuMapper.updateByPrimaryKeySelective(backendMenu);
    }
}
