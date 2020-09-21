package com.nexus.mall.pojo.vo.user;

import lombok.Data;
/**

* @Description:    订单页面展示VO

* @Author:         Nexus

* @CreateDate:     2020/9/21 22:46

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/21 22:46

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
public class OrderVO {
    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
}
