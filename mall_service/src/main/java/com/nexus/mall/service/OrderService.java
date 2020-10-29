package com.nexus.mall.service;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.OrderStatus;
import com.nexus.mall.pojo.bo.user.SubmitOrderBO;
import com.nexus.mall.pojo.vo.user.OrderVO;

public interface OrderService {
    /**
     * 用于创建订单相关信息
     * @Author : Nexus
     * @Description : 用于创建订单相关信息
     * @Date : 2020/9/21 22:41
     * @Param : submitOrderBO
     * @return : OrderVO
     **/
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * updateOrderStatus
     * @Author : Nexus
     * @Description : 修改订单状态
     * @Date : 2020/9/21 22:46
     * @Param : orderId
     * @Param : orderStatus
     * @return : void
     **/
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * queryOrderStatusInfo
     * @Author : Nexus
     * @Description : 查询订单状态
     * @Date : 2020/9/21 22:47
     * @Param : orderId
     * @return : com.nexus.mall.pojo.OrderStatus
     **/
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * closeOrder
     * @Author : Nexus
     * @Description : 关闭超时未支付订单
     * @Date : 2020/9/21 22:48
     * @Param :
     * @return : void
     **/
    void closeOrder();


    /**
     * 根据提交信息生成订单
     * @Author LiYuan
     * @Description //TODO 
     * @Date 13:55 2020/10/29
     * @param submitOrderBO
     * @return com.nexus.mall.common.api.ServerResponse
    **/
    ServerResponse generateOrder(SubmitOrderBO submitOrderBO);

    /**
     * 取消单个超时订单
     * @Author LiYuan
     * @Description 取消单个超时订单
     * @Date 13:53 2020/10/29
     * @param orderId 订单id
     * @return void
    **/
    void cancelOrder(Long orderId);
}
