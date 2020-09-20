package com.nexus.mall.pojo.bo.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AddressBO {
    private String addressId;
    private String userId;
    @NotBlank(message = "收货人不能为空")
    @Size(max = 12,message = "收货人姓名不能太长,最长为12")
    private String receiver;
    @NotBlank(message = "收货人手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$",message = "请输入正确的手机号")
    @Length(max = 11,min = 11)
    private String mobile;
    @NotBlank(message = "收货地址信息不能为空")
    private String province;
    @NotBlank(message = "收货地址信息不能为空")
    private String city;
    @NotBlank(message = "收货地址信息不能为空")
    private String district;
    @NotBlank(message = "收货地址信息不能为空")
    private String detail;
}
