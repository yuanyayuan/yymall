package com.nexus.mall.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.common.enums.OrderStatusEnum;
import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.dao.protal.OrderStatusMapper;
import com.nexus.mall.dao.protal.OrdersMapper;
import com.nexus.mall.dao.protal.OrdersMapperCustom;
import com.nexus.mall.pojo.OrderStatus;
import com.nexus.mall.pojo.Orders;
import com.nexus.mall.pojo.vo.user.MyOrdersVO;
import com.nexus.mall.pojo.vo.user.OrderStatusCountsVO;
import com.nexus.mall.service.center.MyOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {
    @Autowired
    public OrdersMapperCustom ordersMapperCustom;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;


    /**
     * 查询我的订单列表
     *
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);

        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(map);

        return setterPagedGrid(list, page);
    }



    /**
     * 订单状态 --> 商家发货
     *
     * @param orderId
     * @return : void
     * @Author : Nexus
     * @Description : 订单状态 --> 商家发货
     * @Date : 2020/10/15 22:43
     * @Param : orderId
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    /**
     * 查询我的订单
     *
     * @param userId
     * @param orderId
     * @return : Orders
     * @Author : Nexus
     * @Description : 查询我的订单
     * @Date : 2020/10/15 22:44
     * @Param : userId
     * @Param : orderId
     */
    @Override
    @Transactional(propagation=Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    public Orders queryMyOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(orders);
    }

    /**
     * 更新订单状态 —> 确认收货
     *
     * @param orderId
     * @return : boolean
     * @Author : Nexus
     * @Description : 更新订单状态 —> 确认收货
     * @Date : 2020/10/15 22:44
     * @Param : orderId
     */
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        int result = orderStatusMapper.updateByExampleSelective(updateOrder, example);

        //return result == 1 ? true : false;
        return result == 1;
    }

    /**
     * 删除订单（逻辑删除）
     *
     * @param userId
     * @param orderId
     * @return : boolean
     * @Author : Nexus
     * @Description : 删除订单（逻辑删除）
     * @Date : 2020/10/15 22:44
     * @Param : userId
     * @Param : orderId
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        int result = ordersMapper.updateByExampleSelective(updateOrder, example);

        //return result == 1 ? true : false;
        return result == 1;
    }

    /**
     * 查询用户订单数
     *
     * @param userId
     * @return : OrderStatusCountsVO
     * @Author : Nexus
     * @Description : 查询用户订单数
     * @Date : 2020/10/15 22:44
     * @Param : userId
     */
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        return new OrderStatusCountsVO(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
    }

    /**
     * 获得分页的订单动向
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     * @Author : Nexus
     * @Description : 获得分页的订单动向
     * @Date : 2020/10/15 22:45
     * @Param : userId
     * @Param : page
     * @Param : pageSize
     */
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(map);

        return setterPagedGrid(list, page);
    }
}
