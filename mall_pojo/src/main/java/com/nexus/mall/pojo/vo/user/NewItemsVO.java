package com.nexus.mall.pojo.vo.user;

import lombok.Data;

import java.util.List;
@Data
public class NewItemsVO {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;
}
