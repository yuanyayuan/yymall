package com.nexus.mall.pojo.vo;

import lombok.Data;

import java.util.List;
/**

* @Description:    二级分类VO

* @Author:         Nexus

* @CreateDate:     2020/9/13 23:27

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/13 23:27

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
public class CategoryVO {
    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    /**
     * @Author : Nexus
     * @Description :三级分类vo list
     * @Date : 2020/9/13 22:58
     **/
    private List<SubCategoryVO> subCatList;

}
