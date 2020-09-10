package com.nexus.mall.service;

import com.nexus.mall.pojo.BackendResource;

import java.util.List;

public interface BackendResourceService {
    /**
     * listAll
     * @Author : Nexus
     * @Description : 查询全部资源
     * @Date : 2020/9/10 23:09
     * @Param :
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> listAll();
}
