package com.nexus.mall.pojo.vo;

import lombok.Data;
/**

* @Description:    用于展示商品搜索列表结果的VO

* @Author:         Nexus

* @CreateDate:     2020/9/14 21:43

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/14 21:43

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
public class SearchItemsVO {
    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;
}
