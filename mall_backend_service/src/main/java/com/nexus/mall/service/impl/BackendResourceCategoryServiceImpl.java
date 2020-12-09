package com.nexus.mall.service.impl;

import com.nexus.mall.dao.backend.BackendResourceCategoryMapper;
import com.nexus.mall.pojo.BackendResourceCategory;
import com.nexus.mall.service.backend.BackendResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
@Service
public class BackendResourceCategoryServiceImpl implements BackendResourceCategoryService {
    @Autowired
    private BackendResourceCategoryMapper resourceCategoryMapper;
    /**
     * 获取所有资源分类
     *
     * @return java.util.List<com.snpas.pmo.pojo.UmsResourceCategory>
     * @Author LiYuan
     * @Description 获取所有资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public List<BackendResourceCategory> listAll() {
        Example example = new Example(BackendResourceCategory.class);
        example.setOrderByClause("sort desc");
        return resourceCategoryMapper.selectByExample(example);
    }

    /**
     * 创建资源分类
     *
     * @param resourceCategory
     * @return int
     * @Author LiYuan
     * @Description 创建资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public int create(BackendResourceCategory resourceCategory) {
        resourceCategory.setCreateTime(new Date());
        return resourceCategoryMapper.insert(resourceCategory);
    }

    /**
     * 修改资源分类
     *
     * @param id
     * @param resourceCategory
     * @return int
     * @Author LiYuan
     * @Description 修改资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public int update(Long id, BackendResourceCategory resourceCategory) {
        resourceCategory.setId(id);
        return resourceCategoryMapper.updateByPrimaryKeySelective(resourceCategory);
    }

    /**
     * 删除资源分类
     *
     * @param id
     * @return int
     * @Author LiYuan
     * @Description 删除资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public int delete(Long id) {
        return resourceCategoryMapper.deleteByPrimaryKey(id);
    }
}
