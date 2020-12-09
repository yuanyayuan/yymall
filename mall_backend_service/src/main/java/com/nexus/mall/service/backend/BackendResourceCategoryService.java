package com.nexus.mall.service.backend;

import com.nexus.mall.pojo.BackendResourceCategory;

import java.util.List;
/**
 * @className BackendResourceCategoryService
 * @description
 * @author LiYuan
 * @date 2020/12/7
**/
public interface BackendResourceCategoryService {
    /**
     *
     * 获取所有资源分类
     *
     * @Author LiYuan
     * @Description 获取所有资源分类
     * @Date 9:56 2020/12/7
     * @param
     * @return java.util.List<com.snpas.pmo.pojo.UmsResourceCategory>
     **/
    List<BackendResourceCategory> listAll();

    /**
     *
     * 创建资源分类
     *
     * @Author LiYuan
     * @Description 创建资源分类
     * @Date 9:56 2020/12/7
     * @param resourceCategory
     * @return int
     **/
    int create(BackendResourceCategory resourceCategory);

    /**
     *
     * 修改资源分类
     *
     * @Author LiYuan
     * @Description 修改资源分类
     * @Date 9:56 2020/12/7
     * @param id
     * @param resourceCategory
     * @return int
     **/
    int update(Long id, BackendResourceCategory resourceCategory);

    /**
     *
     * 删除资源分类
     *
     * @Author LiYuan
     * @Description 删除资源分类
     * @Date 9:56 2020/12/7
     * @param id
     * @return int
     **/
    int delete(Long id);
}
