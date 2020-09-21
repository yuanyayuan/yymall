package com.nexus.mall.service;

import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.pojo.Items;
import com.nexus.mall.pojo.ItemsImg;
import com.nexus.mall.pojo.ItemsParam;
import com.nexus.mall.pojo.ItemsSpec;
import com.nexus.mall.pojo.vo.user.CommentLevelCountsVO;
import com.nexus.mall.pojo.vo.user.ShopcartVO;

import java.util.List;
/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/14 21:43

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/14 21:43

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface ItemService {
    /**
     * queryItemById
     * @Author : Nexus
     * @Description : 根据商品ID查询详情
     * @Date : 2020/9/14 21:44
     * @Param : itemId
     * @return : com.nexus.mall.pojo.Items
     **/
    Items queryItemById(String itemId);

    /**
     * queryItemImgList
     * @Author : Nexus
     * @Description : 根据商品id查询商品图片列表
     * @Date : 2020/9/14 21:44
     * @Param : itemId
     * @return : java.util.List<com.nexus.mall.pojo.ItemsImg>
     **/
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * queryItemSpecList
     * @Author : Nexus
     * @Description : 根据商品id查询商品规格
     * @Date : 2020/9/14 21:44
     * @Param : itemId
     * @return : java.util.List<com.nexus.mall.pojo.ItemsSpec>
     **/
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * queryItemParam
     * @Author : Nexus
     * @Description : 根据商品id查询商品参数
     * @Date : 2020/9/14 21:45
     * @Param : itemId
     * @return : com.nexus.mall.pojo.ItemsParam
     **/
    ItemsParam queryItemParam(String itemId);

    /**
     * queryCommentCounts
     * @Author : Nexus
     * @Description : 根据商品id查询商品的评价等级数量
     * @Date : 2020/9/14 22:07
     * @Param : itemId
     * @return : com.nexus.mall.pojo.vo.user.CommentLevelCountsVO
     **/
    CommentLevelCountsVO queryCommentCounts(String itemId);
    /**
     * queryPagedComments
     * @Author : Nexus
     * @Description : 根据商品id查询商品的评价（分页）
     * @Date : 2020/9/14 22:37
     * @Param : itemId
     * @Param : level
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     **/
    PagedGridResult queryPagedComments(String itemId, Integer level,
                                       Integer page, Integer pageSize);

    /**
     * searchItems
     * @Author : Nexus
     * @Description : 搜索商品列表
     * @Date : 2020/9/16 20:30
     * @Param : keywords
     * @Param : sort
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     **/
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * searhItems
     * @Author : Nexus
     * @Description : 根据分类id搜索商品列表
     * @Date : 2020/9/16 20:34
     * @Param : catId
     * @Param : sort
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     **/
    PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * queryItemsBySpecIds
     * @Author : Nexus
     * @Description : 根据规格ids查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @Date : 2020/9/16 20:55
     * @Param : specIds
     * @return : java.util.List<com.nexus.mall.pojo.vo.user.ShopcartVO>
     **/
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * queryItemSpecById
     * @Author : Nexus
     * @Description : 根据商品规格id获取规格对象的具体信息
     * @Date : 2020/9/21 22:53
     * @Param : specId
     * @return : com.nexus.mall.pojo.ItemsSpec
     **/
    ItemsSpec queryItemSpecById(String specId);

    /**
     * queryItemMainImgById
     * @Author : Nexus
     * @Description : 根据商品id获得商品图片主图url
     * @Date : 2020/9/21 22:53
     * @Param : itemId
     * @return : java.lang.String
     **/
    String queryItemMainImgById(String itemId);

    /**
     * decreaseItemSpecStock
     * @Author : Nexus
     * @Description : 减少库存
     * @Date : 2020/9/21 22:53
     * @Param : specId
     * @Param : buyCounts
     * @return : void
     **/
    void decreaseItemSpecStock(String specId, int buyCounts);
}
