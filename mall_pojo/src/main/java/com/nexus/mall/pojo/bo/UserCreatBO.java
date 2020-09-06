package com.nexus.mall.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**

* @Description:    用户注册表单对象

* @Author:         Nexus

* @CreateDate:     2020/9/6 22:57

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/6 22:57

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户注册表单对象")
public class UserCreatBO {
    @ApiModelProperty(value = "用户名")
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank
    private String password;

    @ApiModelProperty(value = "确认密码")
    @NotBlank
    private String confirmPassword;
}
