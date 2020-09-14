package com.nexus.mall.dao;

import com.nexus.mall.pojo.vo.ItemCommentVO;
import com.nexus.mall.pojo.vo.SearchItemsVO;
import com.nexus.mall.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {
    /**
     * queryItemComments
     * @Author : Nexus
     * @Description :
     * @Date : 2020/9/14 21:50
     * @Param : map
     * @return : java.util.List<com.nexus.mall.pojo.vo.ItemCommentVO>
     **/
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);
    /**
     * searchItems
     * @Author : Nexus
     * @Description :
     * @Date : 2020/9/14 21:50
     * @Param : map
     * @return : java.util.List<com.nexus.mall.pojo.vo.SearchItemsVO>
     **/
    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);
    /**
     * searchItemsByThirdCat
     * @Author : Nexus
     * @Description :
     * @Date : 2020/9/14 21:50
     * @Param : map
     * @return : java.util.List<com.nexus.mall.pojo.vo.SearchItemsVO>
     **/
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);
    /**
     * queryItemsBySpecIds
     * @Author : Nexus
     * @Description :
     * @Date : 2020/9/14 21:50
     * @Param : specIdsList
     * @return : java.util.List<com.nexus.mall.pojo.vo.ShopcartVO>
     **/
    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);
    /**
     * decreaseItemSpecStock
     * @Author : Nexus
     * @Description :
     * @Date : 2020/9/14 21:50
     * @Param : specId
     * @Param : pendingCounts
     * @return : int
     **/
    int decreaseItemSpecStock(@Param("specId") String specId,
                                     @Param("pendingCounts") int pendingCounts);
}
