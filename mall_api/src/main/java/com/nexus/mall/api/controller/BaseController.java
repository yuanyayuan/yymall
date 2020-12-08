package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.util.RedisOperator;
import com.nexus.mall.pojo.Orders;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.vo.user.UsersVO;
import com.nexus.mall.service.center.MyOrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMON_PAGE = 1;
    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;


    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     *   支付中心的调用地址  produce
     **/
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    /**
     *   微信支付成功 -> 支付中心 -> 天天吃货平台
     *                         -> 回调通知的url
     **/
    String payReturnUrl = "http://ghost.natapp1.cc/orders/notifyMerchantOrderPaid";
    //String payReturnUrl = "http://api.z.mukewang.com/foodie-dev-api/orders/notifyMerchantOrderPaid";
    /**
     * 用户上传头像的位置
     **/
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces" +
            File.separator + "images" +
            File.separator + "foodie" +
            File.separator + "faces";
    //public static final String IMAGE_USER_FACE_LOCATION = "/workspaces/images/foodie/faces";

    @Autowired
    public MyOrdersService myOrdersService;
    @Autowired
    private RedisOperator redisOperator;
    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @Author : Nexus
     * @Description : 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @Date : 2020/12/8 23:51
     * @Param : userId
     * @Param : orderId
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    public ServerResponse checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return ServerResponse.failed("订单不存在！");
        }
        return ServerResponse.success(order);
    }

    /**
     * pojo转vo
     * @Author : Nexus
     * @Description : pojo转vo
     * @Date : 2020/12/8 23:51
     * @Param : user
     * @return : com.nexus.mall.pojo.vo.user.UsersVO
     **/
    public UsersVO conventUsersVO(Users user) {
        // 实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(),
                uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
