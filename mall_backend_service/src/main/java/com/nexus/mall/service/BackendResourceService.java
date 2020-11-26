package com.nexus.mall.service;

import com.nexus.mall.pojo.BackendResource;

import java.util.List;
/**

* @Description:    后台资源管理Service

* @Author:         Nexus

* @CreateDate:     2020/11/26 23:03

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/26 23:03

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface BackendResourceService {
    /**
     * 查询全部资源
     * @Author : Nexus
     * @Description : 查询全部资源
     * @Date : 2020/9/10 23:09
     * @Param :
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> listAll();

    /**
     * 添加资源
     * @Author : Nexus
     * @Description : 添加资源
     * @Date : 2020/11/26 23:04
     * @Param : umsResource
     * @return : int
     **/
    int create(BackendResource umsResource);

    /**
     * 修改资源
     * @Author : Nexus
     * @Description : 修改资源
     * @Date : 2020/11/26 23:04
     * @Param : id
     * @Param : umsResource
     * @return : int
     **/
    int update(Long id, BackendResource umsResource);

    /**
     * 获取资源详情
     * @Author : Nexus
     * @Description : 获取资源详情
     * @Date : 2020/11/26 23:04
     * @Param : id
     * @return : com.nexus.mall.pojo.BackendResource
     **/
    BackendResource getItem(Long id);

    /**
     * 删除资源
     * @Author : Nexus
     * @Description : 删除资源
     * @Date : 2020/11/26 23:04
     * @Param : id
     * @return : int
     **/
    int delete(Long id);

    /**
     * 分页查询资源
     * @Author : Nexus
     * @Description : 分页查询资源
     * @Date : 2020/11/26 23:05
     * @Param : categoryId
     * @Param : nameKeyword
     * @Param : urlKeyword
     * @Param : page
     * @Param : pageSize
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer pageSize);
}
