package com.nexus.mall.api.controller;

import com.nexus.mall.api.resource.PayConfig;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.enums.ImoocPayResultCode;
import com.nexus.mall.common.enums.OrderStatusEnum;
import com.nexus.mall.common.enums.PayMethod;
import com.nexus.mall.common.util.RedisOperator;
import com.nexus.mall.pojo.OrderStatus;
import com.nexus.mall.pojo.bo.user.ShopcartBO;
import com.nexus.mall.pojo.bo.user.SubmitOrderBO;
import com.nexus.mall.pojo.vo.user.MerchantOrdersVO;
import com.nexus.mall.pojo.vo.user.OrderVO;
import com.nexus.mall.service.OrderService;
import com.nexus.mall.util.CookieUtils;
import com.nexus.mall.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/21 22:35

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/21 22:35

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController  extends BaseController{
    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PayConfig payConfig;

    @Autowired
    private RedisOperator redisOperator;


    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public ServerResponse create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)
                && !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return ServerResponse.failed("支付方式不支持！");
        }

        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId());
        if (StringUtils.isBlank(shopcartJson)) {
            return ServerResponse.failed("购物数据不正确");
        }

        List<ShopcartBO> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);

        // 1. 创建订单
        OrderVO orderVO = orderService.createOrder(shopcartList, submitOrderBO);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        /*
          1001
          2002 -> 用户购买
          3003 -> 用户购买
          4004
         */
        // 清理覆盖现有的redis汇总的购物数据
        assert shopcartList != null;
        shopcartList.removeAll(orderVO.getToBeRemovedShopcatList());
        redisOperator.set(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId(), JsonUtils.objectToJson(shopcartList));
        // 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartList), true);

        /**
         * 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
         **/
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId",payConfig.getUserId());
        headers.add("password",payConfig.getPassword());

        HttpEntity<MerchantOrdersVO> entity =
                new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<ServerResponse> responseEntity =
                restTemplate.postForEntity(paymentUrl,
                        entity,
                        ServerResponse.class);
        ServerResponse paymentResult = responseEntity.getBody();
        assert paymentResult != null;
        if (paymentResult.getCode() != ImoocPayResultCode.success.code) {
            log.error("发送错误：{}", paymentResult.getMessage());
            return ServerResponse.failed("支付中心订单创建失败，请联系管理员！");
        }

        return ServerResponse.success(orderId);
    }
    /**
     * 支付中心回调地址
     * @Author : Nexus
     * @Description : 支付中心回调地址
     * @Date : 2020/11/21 20:37
     * @Param : merchantOrderId
     * @return : java.lang.Integer
     **/
    @ApiOperation(value = "修改订单状态", notes = "修改订单状态", httpMethod = "POST")
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @ApiOperation(value = "获取订单信息", notes = "获取订单信息", httpMethod = "POST")
    @PostMapping("getPaidOrderInfo")
    public ServerResponse getPaidOrderInfo(String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return ServerResponse.success(orderStatus);
    }

}
