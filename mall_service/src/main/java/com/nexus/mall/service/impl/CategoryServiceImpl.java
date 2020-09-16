package com.nexus.mall.service.impl;

import com.google.common.collect.Maps;
import com.nexus.mall.common.enums.CategoryState;
import com.nexus.mall.dao.CategoryMapper;
import com.nexus.mall.dao.CategoryMapperCustom;
import com.nexus.mall.pojo.Category;
import com.nexus.mall.pojo.vo.user.CategoryVO;
import com.nexus.mall.pojo.vo.user.NewItemsVO;
import com.nexus.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    /**
     * queryAllRootLevelCat
     *
     * @return : java.util.List<com.nexus.mall.pojo.Category>
     * @Author : Nexus
     * @Description : 查询所有一级分类
     * @Date : 2020/9/13 22:41
     * @Param :
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", CategoryState.ONE_LEVEL.type);
        return categoryMapper.selectByExample(example);
    }

    /**
     * getSubCatList
     *
     * @param rootCatId
     * @return : java.util.List<com.nexus.mall.pojo.vo.user.CategoryVO>
     * @Author : Nexus
     * @Description : 根据一级分类id查询下一层子分类信息
     * @Date : 2020/9/13 22:52
     * @Param : rootCatId
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

    /**
     * getSixNewItemsLazy
     *
     * @param rootCatId
     * @return : java.util.List<NewItemsVO>
     * @Author : Nexus
     * @Description : 查询首页每个一级分类下的6条最新商品数据
     * @Date : 2020/9/13 23:24
     * @Param : rootCatId
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("rootCatId", rootCatId);

        return categoryMapperCustom.getSixNewItemsLazy(map);
    }

}
