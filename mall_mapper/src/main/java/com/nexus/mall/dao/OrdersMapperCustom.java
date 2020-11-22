package com.nexus.mall.dao;

import com.nexus.mall.pojo.OrderStatus;
import com.nexus.mall.pojo.vo.user.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapperCustom {
    /**
     * 查询指定用户订单
     * @Author : Nexus
     * @Description : 查询指定用户订单
     * @Date : 2020/11/22 23:07
     * @Param : map
     * @return : java.util.List<com.nexus.mall.pojo.vo.user.MyOrdersVO>
     **/
    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);
    /**
     * getMyOrderStatusCounts
     * @Author : Nexus
     * @Description :
     * @Date : 2020/11/22 23:10
     * @Param : map
     * @return : int
     **/
    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);
    /**
     * getMyOrderTrend
     * @Author : Nexus
     * @Description :
     * @Date : 2020/11/22 23:10
     * @Param : map
     * @return : java.util.List<com.nexus.mall.pojo.OrderStatus>
     **/
    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);
}
