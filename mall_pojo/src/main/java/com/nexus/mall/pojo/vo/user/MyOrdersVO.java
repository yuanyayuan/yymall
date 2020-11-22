package com.nexus.mall.pojo.vo.user;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * 用户中心我的订单列表VO
 * @Author : Nexus
 * @Description : 用户中心我的订单列表VO
 * @Date : 2020/11/22 23:07
 * @Param : null
 * @return : 
 **/
@Data
@ToString
public class MyOrdersVO {
    private String orderId;
    private Date createdTime;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    private Integer orderStatus;
    private List<MySubOrderItemVO> subOrderItemList;
}
