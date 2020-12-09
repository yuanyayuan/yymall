package com.nexus.mall.pojo.dto.admin;

import com.nexus.mall.pojo.BackendMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**

* @Description:    后台菜单节点封装

* @Author:         Nexus

* @CreateDate:     2020/11/26 21:46

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/26 21:46

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Getter
@Setter
public class BackendMenuNode extends BackendMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<BackendMenuNode> children;
}
