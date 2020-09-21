package com.nexus.mall.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**

* @Description:    订单状态VO

* @Author:         Nexus

* @CreateDate:     2020/9/21 22:46

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/21 22:46

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusCountsVO {
    private Integer waitPayCounts;
    private Integer waitDeliverCounts;
    private Integer waitReceiveCounts;
    private Integer waitCommentCounts;
}
