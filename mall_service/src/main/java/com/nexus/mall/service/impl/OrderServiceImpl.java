package com.nexus.mall.service.impl;


import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.exception.Asserts;
import com.nexus.mall.pojo.bo.user.ShopcartBO;
import com.nexus.mall.service.component.CancelOrderSender;
import com.nexus.mall.util.DateUtils;
import com.nexus.mall.common.enums.OrderStatusEnum;
import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.dao.protal.OrderItemsMapper;
import com.nexus.mall.dao.protal.OrderStatusMapper;
import com.nexus.mall.dao.protal.OrdersMapper;
import com.nexus.mall.pojo.*;
import com.nexus.mall.pojo.bo.user.SubmitOrderBO;
import com.nexus.mall.pojo.vo.user.MerchantOrdersVO;
import com.nexus.mall.pojo.vo.user.OrderVO;
import com.nexus.mall.service.AddressService;
import com.nexus.mall.service.ItemService;
import com.nexus.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @className OrderServiceImpl
 * @description 订单业务类
 * @author LiYuan
 * @date 2020/10/29
**/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private Sid sid;

    @Autowired
    private CancelOrderSender cancelOrderSender;

    /**
     * 用于创建订单相关信息
     *
     * @param submitOrderBO
     * @return : OrderVO
     * @Author : Nexus
     * @Description : 用于创建订单相关信息
     * @Date : 2020/9/21 22:41
     * @Param : submitOrderBO
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public OrderVO createOrder(List<ShopcartBO> shopcartList, SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        int postAmount = 0;

        String orderId = sid.nextShort();

        UserAddress address = addressService.queryUserAddress(userId, addressId);

        /*
          1. 新订单数据保存
         */
        Orders newOrder = saveNewOrder(orderId, userId, address, postAmount, payMethod, leftMsg);

        /*
          2. 循环根据itemSpecIds保存订单商品信息表
         */
        String[] itemSpecIdArr = itemSpecIds.split(",");
        // 商品原价累计
        int totalAmount = 0;
        // 优惠后的实际支付价格累计
        int realPayAmount = 0;
        List<ShopcartBO> toBeRemovedShopcatList = new ArrayList<>();
        for (String itemSpecId : itemSpecIdArr) {
            ShopcartBO cartItem = getBuyCountsFromShopcart(shopcartList, itemSpecId);
            // 整合redis后，商品购买的数量重新从redis的购物车中获取
            if (cartItem == null){
                Asserts.fail("服务器异常，请稍后再试");
            }
            int buyCounts = cartItem.getBuyCounts();
            toBeRemovedShopcatList.add(cartItem);

            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemSpec.getPriceDiscount() * buyCounts;

            // 2.2 根据商品id，获得商品信息以及商品图片
            String itemId = itemSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            // 2.3 循环保存子订单数据到数据库
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            // 2.4 在用户提交订单以后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }

        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);

        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        //postAmount——运费
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        // 5. 构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setToBeRemovedShopcatList(toBeRemovedShopcatList);

        sendDelayMessageCancelOrder(orderId);
        return orderVO;
    }

    /**
     * 新订单数据保存
     * @Author : Nexus
     * @Description : 新订单数据保存
     * @Date : 2020/12/3 22:41
     * @Param : orderId
     * @Param : userId
     * @Param : address
     * @Param : postAmount
     * @Param : payMethod
     * @Param : leftMsg
     * @return : com.nexus.mall.pojo.Orders
     **/
    private Orders saveNewOrder(String orderId,
                                String userId,
                                UserAddress address,
                                int postAmount,
                                Integer payMethod,
                                String leftMsg){
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " " + address.getCity() + " " + address.getDistrict() + " " + address.getDetail());
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        return newOrder;
    }

    private void sendDelayMessageCancelOrder(String orderId) {
        //获取订单超时时间，假设为60分钟(测试用的30秒)
        long delayTimes = 30 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }


    /**
     * 从redis中的购物车里获取商品，目的：counts
     * @Author : Nexus
     * @Description :从redis中的购物车里获取商品，目的：counts
     * @Date : 2020/11/30 20:52
     * @Param : shopcartList
     * @Param : specId
     * @return : com.nexus.mall.pojo.bo.user.ShopcartBO
     **/
    private ShopcartBO getBuyCountsFromShopcart(List<ShopcartBO> shopcartList, String specId) {
        for (ShopcartBO cart : shopcartList) {
            if (cart.getSpecId().equals(specId)) {
                return cart;
            }
        }
        return null;
    }

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     * @return : void
     * @Author : Nexus
     * @Description : 修改订单状态
     * @Date : 2020/9/21 22:46
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    /**
     * 查询订单状态
     *
     * @param orderId
     * @return : com.nexus.mall.pojo.OrderStatus
     * @Author : Nexus
     * @Description : 查询订单状态
     * @Date : 2020/9/21 22:47
     * @Param : orderId
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    /**
     * closeOrder
     *
     * @return : void
     * @Author : Nexus
     * @Description : 关闭超时未支付订单
     * @Date : 2020/9/21 22:48
     * @Param :
     **/
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public void closeOrder() {
        // 查询所有未付款订单，判断时间是否超时（30min），超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        log.info("所有待支付："+list);
        for (OrderStatus os : list) {
            // 获得订单创建时间
            Date createdTime = os.getCreatedTime();
            // 和当前时间进行对比
            Long min = DateUtils.getMinPoor(new Date(),createdTime);
            if (min > 30) {
                // 超过30min，关闭订单
                closeOrderByOrderId(os.getOrderId());
            }
        }
    }
    /**
     * 根据orderId关闭订单
     * @Author : Nexus
     * @Description : 根据orderId关闭订单
     * @Date : 2020/12/3 22:37
     * @Param : orderId
     * @return : void
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    public void closeOrderByOrderId(String orderId) {
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }



}
