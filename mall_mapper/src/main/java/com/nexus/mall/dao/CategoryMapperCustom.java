package com.nexus.mall.dao;

import com.nexus.mall.pojo.vo.CategoryVO;
import com.nexus.mall.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CategoryMapperCustom {
    /**
     * getSubCatList
     * @Author : Nexus
     * @Description : 根据一级分类id查询下一层子分类信息
     * @Date : 2020/9/15 21:32
     * @Param : rootCatId
     * @return : java.util.List<com.nexus.mall.pojo.vo.CategoryVO>
     **/
    List<CategoryVO> getSubCatList(Integer rootCatId);
    /**
     * getSixNewItemsLazy
     * @Author : Nexus
     * @Description : 查询首页每个一级分类下的6条最新商品数据
     * @Date : 2020/9/15 21:32
     * @Param : map
     * @return : java.util.List<com.nexus.mall.pojo.vo.NewItemsVO>
     **/
    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}
