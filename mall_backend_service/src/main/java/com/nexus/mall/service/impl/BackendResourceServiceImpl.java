package com.nexus.mall.service.impl;

import com.nexus.mall.dao.backend.BackendResourceMapper;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.service.BackendResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/9 23:45

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 23:45

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
public class BackendResourceServiceImpl implements BackendResourceService {

    @Autowired
    private BackendResourceMapper resourceMapper;

    /**
     * listAll
     *
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     * @Author : Nexus
     * @Description : 查询全部资源
     * @Date : 2020/9/10 23:09
     * @Param :
     **/
    @Override
    public List<BackendResource> listAll() {
        return resourceMapper.selectAll();
    }

    /**
     * 添加资源
     *
     * @param umsResource
     * @return : int
     * @Author : Nexus
     * @Description : 添加资源
     * @Date : 2020/11/26 23:04
     * @Param : umsResource
     */
    @Override
    public int create(BackendResource umsResource) {
        return 0;
    }

    /**
     * 修改资源
     *
     * @param id
     * @param umsResource
     * @return : int
     * @Author : Nexus
     * @Description : 修改资源
     * @Date : 2020/11/26 23:04
     * @Param : id
     * @Param : umsResource
     */
    @Override
    public int update(Long id, BackendResource umsResource) {
        return 0;
    }

    /**
     * 获取资源详情
     *
     * @param id
     * @return : com.nexus.mall.pojo.BackendResource
     * @Author : Nexus
     * @Description : 获取资源详情
     * @Date : 2020/11/26 23:04
     * @Param : id
     */
    @Override
    public BackendResource getItem(Long id) {
        return null;
    }

    /**
     * 删除资源
     *
     * @param id
     * @return : int
     * @Author : Nexus
     * @Description : 删除资源
     * @Date : 2020/11/26 23:04
     * @Param : id
     */
    @Override
    public int delete(Long id) {
        return 0;
    }

    /**
     * 分页查询资源
     *
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param page
     * @param pageSize
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     * @Author : Nexus
     * @Description : 分页查询资源
     * @Date : 2020/11/26 23:05
     * @Param : categoryId
     * @Param : nameKeyword
     * @Param : urlKeyword
     * @Param : page
     * @Param : pageSize
     */
    @Override
    public List<BackendResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer pageSize) {
        return null;
    }
}
