package com.nexus.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.common.enums.CommentLevel;
import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.common.util.DesensitizationUtil;
import com.nexus.mall.dao.*;
import com.nexus.mall.pojo.*;
import com.nexus.mall.pojo.vo.user.CommentLevelCountsVO;
import com.nexus.mall.pojo.vo.user.ItemCommentVO;
import com.nexus.mall.pojo.vo.user.SearchItemsVO;
import com.nexus.mall.pojo.vo.user.ShopcartVO;
import com.nexus.mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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
     * @Author : Nexus
     * @Description : 根据商品ID查询详情
     * @Date : 2020/9/16 20:40
     * @Param : itemId
     * @return : com.nexus.mall.pojo.Items
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * queryItemImgList
     * @Author : Nexus
     * @Description : 根据商品id查询商品图片列表
     * @Date : 2020/9/14 21:44
     * @Param : itemId
     * @return : java.util.List<com.nexus.mall.pojo.ItemsImg>
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
     * @Author : Nexus
     * @Description : 根据商品id查询商品规格
     * @Date : 2020/9/16 20:40
     * @Param : itemId
     * @return : java.util.List<com.nexus.mall.pojo.ItemsSpec>
     **/
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
     * @Author : Nexus
     * @Description : 根据商品id查询商品参数
     * @Date : 2020/9/16 20:39
     * @Param : itemId
     * @return : com.nexus.mall.pojo.ItemsParam
     **/
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
     * @Author : Nexus
     * @Description : 根据商品id查询商品的评价等级数量
     * @Date : 2020/9/16 20:38
     * @Param : itemId
     * @return : com.nexus.mall.pojo.vo.user.CommentLevelCountsVO
     **/
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
     * @Author : Nexus
     * @Description : 据商品id查询商品的评价（分页）
     * @Date : 2020/9/16 20:38
     * @Param : itemId
     * @Param : level
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     **/
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

    /**
     * searchItems
     * @Author : Nexus
     * @Description : 搜索商品列表
     * @Date : 2020/9/16 20:41
     * @Param : keywords
     * @Param : sort
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("keywords", keywords);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);

        return setterPagedGrid(list, page);
    }

    /**
     * searchItems
     * @Author : Nexus
     * @Description : 根据分类id搜索商品列表
     * @Date : 2020/9/16 20:34
     * @Param : catId
     * @Param : sort
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);

        return setterPagedGrid(list, page);
    }

    /**
     * queryItemsBySpecIds
     * @Author : Nexus
     * @Description : 根据规格ids查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @Date : 2020/9/16 20:55
     * @Param : specIds
     * @return : java.util.List<com.nexus.mall.pojo.vo.user.ShopcartVO>
     */
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);

        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    /**
     * queryItemSpecById
     *
     * @param specId
     * @return : com.nexus.mall.pojo.ItemsSpec
     * @Author : Nexus
     * @Description : 根据商品规格id获取规格对象的具体信息
     * @Date : 2020/9/21 22:53
     * @Param : specId
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    @Override
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    /**
     * queryItemMainImgById
     *
     * @param itemId
     * @return : java.lang.String
     * @Author : Nexus
     * @Description : 根据商品id获得商品图片主图url
     * @Date : 2020/9/21 22:53
     * @Param : itemId
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result != null ? result.getUrl() : "";
    }

    /**
     * decreaseItemSpecStock
     *
     * @param specId
     * @param buyCounts
     * @return : void
     * @Author : Nexus
     * @Description : 减少库存
     * @Date : 2020/9/21 22:53
     * @Param : specId
     * @Param : buyCounts
     */
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        // synchronized 不推荐使用，集群下无用，性能低下
        // 锁数据库: 不推荐，导致数据库性能低下
        // 分布式锁 zookeeper redis

        // lockUtil.getLock(); -- 加锁

        // 1. 查询库存
        // int stock = 10;

        // 2. 判断库存，是否能够减少到0以下
        // if (stock - buyCounts < 0) {
        // 提示用户库存不够
        // 10 - 3 -3 - 5 = -1
        // }

        // lockUtil.unLock(); -- 解锁


        int result = itemsMapperCustom.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足!");
        }
    }
}
