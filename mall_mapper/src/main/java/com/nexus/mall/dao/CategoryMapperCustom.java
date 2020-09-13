package com.nexus.mall.dao;

import com.nexus.mall.pojo.vo.CategoryVO;
import com.nexus.mall.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CategoryMapperCustom {
    List<CategoryVO> getSubCatList(Integer rootCatId);

    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}
