package com.nexus.mall.service.center;

import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.pojo.Orders;
import com.nexus.mall.pojo.vo.user.OrderStatusCountsVO;

public interface MyOrdersService {
    /**
     * 查询我的订单列表
     *
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 订单状态 --> 商家发货
     * @Author : Nexus
     * @Description : 订单状态 --> 商家发货
     * @Date : 2020/10/15 22:43
     * @Param : orderId
     * @return : void
     **/
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @Author : Nexus
     * @Description : 查询我的订单
     * @Date : 2020/10/15 22:44
     * @Param : userId
     * @Param : orderId
     * @return : Orders
     **/
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 —> 确认收货
     * @Author : Nexus
     * @Description : 更新订单状态 —> 确认收货
     * @Date : 2020/10/15 22:44
     * @Param : orderId
     * @return : boolean
     **/
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单（逻辑删除）
     * @Author : Nexus
     * @Description : 删除订单（逻辑删除）
     * @Date : 2020/10/15 22:44
     * @Param : userId
     * @Param : orderId
     * @return : boolean
     **/
    boolean deleteOrder(String userId, String orderId);

    /**
     * 查询用户订单数
     * @Author : Nexus
     * @Description : 查询用户订单数
     * @Date : 2020/10/15 22:44
     * @Param : userId
     * @return : OrderStatusCountsVO
     **/
    public OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 获得分页的订单动向
     * @Author : Nexus
     * @Description : 获得分页的订单动向
     * @Date : 2020/10/15 22:45
     * @Param : userId
     * @Param : page
     * @Param : pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     **/
    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
