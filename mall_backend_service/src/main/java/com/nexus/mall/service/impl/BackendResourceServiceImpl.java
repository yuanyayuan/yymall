package com.nexus.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.nexus.mall.dao.backend.BackendResourceMapper;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.service.backend.BackendAdminCacheService;
import com.nexus.mall.service.backend.BackendResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
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

    @Autowired
    private BackendAdminCacheService adminCacheService;
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
     * @param resource
     * @return : int
     * @Author : Nexus
     * @Description : 添加资源
     * @Date : 2020/11/26 23:04
     */
    @Override
    public int create(BackendResource resource) {
        resource.setCreateTime(new Date());
        return resourceMapper.insert(resource);
    }

    /**
     * 修改资源
     *
     * @param id
     * @param resource
     * @return : int
     * @Author : Nexus
     * @Description : 修改资源
     * @Date : 2020/11/26 23:04
     */
    @Override
    public int update(Long id, BackendResource resource) {
        resource.setId(id);
        int count = resourceMapper.updateByPrimaryKeySelective(resource);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    /**
     * 获取资源详情
     *
     * @param id
     * @return : com.nexus.mall.pojo.BackendResource
     * @Author : Nexus
     * @Description : 获取资源详情
     * @Date : 2020/11/26 23:04
     */
    @Override
    public BackendResource getItem(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除资源
     *
     * @param id
     * @return : int
     * @Author : Nexus
     * @Description : 删除资源
     * @Date : 2020/11/26 23:04
     */
    @Override
    public int delete(Long id) {
        int count = resourceMapper.deleteByPrimaryKey(id);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    /**
     * 分页查询资源
     * @Author : Nexus
     * @Description : 分页查询资源
     * @Date : 2020/11/26 23:05
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param page
     * @param pageSize
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     */
    @Override
    public List<BackendResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        Example example = new Example(BackendResource.class);
        Example.Criteria criteria = example.createCriteria();
        if (categoryId != null){
            criteria.andEqualTo("categoryId",categoryId);
        }
        if (StrUtil.isNotEmpty(nameKeyword)){
            criteria.andLike("name",'%'+nameKeyword+'%');
        }
        if (StrUtil.isNotEmpty(urlKeyword)){
            criteria.andLike("url",'%'+urlKeyword+'%');
        }
        return resourceMapper.selectByExample(example);
    }
}
