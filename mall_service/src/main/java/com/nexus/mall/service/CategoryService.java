package com.nexus.mall.service;

import com.nexus.mall.pojo.Category;
import com.nexus.mall.pojo.vo.CategoryVO;
import com.nexus.mall.pojo.vo.NewItemsVO;

import java.util.List;
/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/13 22:41

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/13 22:41

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface CategoryService {
    /**
     * queryAllRootLevelCat
     * @Author : Nexus
     * @Description : 查询所有一级分类
     * @Date : 2020/9/13 22:41
     * @Param :
     * @return : java.util.List<com.nexus.mall.pojo.Category>
     **/
    List<Category> queryAllRootLevelCat();

    /**
     * getSubCatList
     * @Author : Nexus
     * @Description : 根据一级分类id查询子分类信息
     * @Date : 2020/9/13 23:22
     * @Param : rootCatId
     * @return : java.util.List<com.nexus.mall.pojo.vo.CategoryVO>
     **/
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * getSixNewItemsLazy
     * @Author : Nexus
     * @Description : 查询首页每个一级分类下的6条最新商品数据
     * @Date : 2020/9/13 23:24
     * @Param : rootCatId
     * @return : java.util.List<NewItemsVO>
     **/
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
