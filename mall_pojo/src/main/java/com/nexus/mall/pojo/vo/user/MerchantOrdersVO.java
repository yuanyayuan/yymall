package com.nexus.mall.pojo.vo.user;

import lombok.Data;
/**

* @Description:    商户

* @Author:         Nexus

* @CreateDate:     2020/11/11 21:39

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/11 21:39

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
public class MerchantOrdersVO {
    /**
    商户订单号
     */
    private String merchantOrderId;
    /**
    商户方的发起用户的用户主键id
     */
    private String merchantUserId;
    /**
    实际支付总金额（包含商户所支付的订单费邮费总额）
    */
    private Integer amount;
    /**
    支付方式 1:微信   2:支付宝
    */
    private Integer payMethod;
    /**
    支付成功后的回调地址
    */
    private String returnUrl;
}
