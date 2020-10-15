package com.nexus.mall.pojo.vo.user;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class MySubOrderItemVO {
    private String itemId;
    private String itemImg;
    private String itemName;
    private String itemSpecName;
    private Integer buyCounts;
    private Integer price;
}
