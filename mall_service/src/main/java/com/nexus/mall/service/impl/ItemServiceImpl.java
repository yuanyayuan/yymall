package com.nexus.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.common.enums.CommentLevel;
import com.nexus.mall.common.util.DesensitizationUtil;
import com.nexus.mall.dao.*;
import com.nexus.mall.pojo.*;
import com.nexus.mall.pojo.vo.CommentLevelCountsVO;
import com.nexus.mall.pojo.vo.ItemCommentVO;
import com.nexus.mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;


    /**
     * queryItemById
     *
     * @param itemId
     * @return : com.nexus.mall.pojo.Items
     * @Author : Nexus
     * @Description : 根据商品ID查询详情
     * @Date : 2020/9/14 21:44
     * @Param : itemId
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * queryItemImgList
     *
     * @param itemId
     * @return : java.util.List<com.nexus.mall.pojo.ItemsImg>
     * @Author : Nexus
     * @Description : 根据商品id查询商品图片列表
     * @Date : 2020/9/14 21:44
     * @Param : itemId
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExp = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    /**
     * queryItemSpecList
     *
     * @param itemId
     * @return : java.util.List<com.nexus.mall.pojo.ItemsSpec>
     * @Author : Nexus
     * @Description : 根据商品id查询商品规格
     * @Date : 2020/9/14 21:44
     * @Param : itemId
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemsSpecExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemsSpecExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsSpecMapper.selectByExample(itemsSpecExp);
    }


    /**
     * queryItemParam
     *
     * @param itemId
     * @return : com.nexus.mall.pojo.ItemsParam
     * @Author : Nexus
     * @Description : 根据商品id查询商品参数
     * @Date : 2020/9/14 21:45
     * @Param : itemId
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public ItemsParam queryItemParam(String itemId) {
        Example itemsParamExp = new Example(ItemsParam.class);
        Example.Criteria criteria = itemsParamExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(itemsParamExp);
    }

    /**
     * queryCommentCounts
     *
     * @param itemId
     * @return : com.nexus.mall.pojo.vo.CommentLevelCountsVO
     * @Author : Nexus
     * @Description : 根据商品id查询商品的评价等级数量
     * @Date : 2020/9/14 22:07
     * @Param : itemId
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);

        return countsVO;
    }
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    /**
     * queryPagedComments
     *
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     * @Author : Nexus
     * @Description : 根据商品id查询商品的评价（分页）
     * @Date : 2020/9/14 22:37
     * @Param : itemId
     * @Param : level
     * @Param : page
     * @Param : pageSize
     */
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("itemId", itemId);
        map.put("level", level);

        // mybatis-pagehelper
        /*
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO vo : list) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return setterPagedGrid(list, page);
    }
    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
