package com.nexus.mall.pojo.vo.user;

import com.nexus.mall.pojo.bo.user.ShopcartBO;
import lombok.Data;

import java.util.List;

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
    private List<ShopcartBO> toBeRemovedShopcatList;
}
