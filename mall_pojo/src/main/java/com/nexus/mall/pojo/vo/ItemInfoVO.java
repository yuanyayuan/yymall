package com.nexus.mall.pojo.vo;

import com.nexus.mall.pojo.Items;
import com.nexus.mall.pojo.ItemsImg;
import com.nexus.mall.pojo.ItemsParam;
import com.nexus.mall.pojo.ItemsSpec;
import lombok.Data;
/**

* @Description:    商品详情VO

* @Author:         Nexus

* @CreateDate:     2020/9/14 22:04

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/14 22:04

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
import java.util.List;
@Data
public class ItemInfoVO {
    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;
}
