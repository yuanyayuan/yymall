package com.nexus.mall.pojo.bo.user;

import lombok.Data;
import lombok.ToString;

/**

* @Description:    用于创建订单的BO对象

* @Author:         Nexus

* @CreateDate:     2020/9/21 22:46

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/21 22:46

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
@ToString
public class SubmitOrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
