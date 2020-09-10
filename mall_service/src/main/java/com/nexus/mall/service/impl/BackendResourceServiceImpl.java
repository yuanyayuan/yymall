package com.nexus.mall.service.impl;

import com.nexus.mall.dao.BackendResourceMapper;
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
}
