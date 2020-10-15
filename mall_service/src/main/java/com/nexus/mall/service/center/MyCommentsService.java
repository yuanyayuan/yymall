package com.nexus.mall.service.center;

import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.pojo.OrderItems;
import com.nexus.mall.pojo.bo.user.center.OrderItemsCommentBO;

import java.util.List;

public interface MyCommentsService {
    /**
     * 根据订单id查询关联的商品
     * @Author : Nexus
     * @Description : 根据订单id查询关联的商品
     * @Date : 2020/10/15 22:41
     * @Param : orderId
     * @return : java.util.List<com.nexus.mall.pojo.OrderItems>
     **/
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @Author : Nexus
     * @Description : 保存用户的评论
     * @Date : 2020/10/15 22:41
     * @Param : orderId
     * @Param : userId
     * @Param : commentList
     * @return : void
     **/
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     * @Author : Nexus
     * @Description : 我的评价查询 分页
     * @Date : 2020/10/15 22:41
     * @Param : userId
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     **/
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
