package com.nexus.mall.pojo.bo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户登录表单对象")
public class UserLoginBO {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "不能为空")
    @Size(min = 6,message = "应大于6位数")
    private String password;
}
