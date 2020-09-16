package com.nexus.mall.pojo.vo.user;

import lombok.Data;
/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/14 21:43

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/14 21:43

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
public class ShopcartVO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
