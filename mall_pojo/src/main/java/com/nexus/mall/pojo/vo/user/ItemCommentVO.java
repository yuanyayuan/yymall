package com.nexus.mall.pojo.vo.user;

import lombok.Data;

import java.util.Date;
/**

* @Description:    用于展示商品评价的VO

* @Author:         Nexus

* @CreateDate:     2020/9/14 21:42

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/14 21:42

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
public class ItemCommentVO {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;
}
