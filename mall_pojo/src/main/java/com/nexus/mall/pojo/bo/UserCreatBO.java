package com.nexus.mall.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "不能为空")
    @Size(min = 6,message = "应大于6位数")
    private String password;

    @ApiModelProperty(value = "确认密码")
    @NotBlank(message = "不能为空")
    @Size(min = 6,message = "应大于6位数")
    private String confirmPassword;
}
