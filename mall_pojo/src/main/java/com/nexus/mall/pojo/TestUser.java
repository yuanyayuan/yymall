package com.nexus.mall.pojo;

import com.nexus.mall.group.GroupA;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class TestUser {
    @NotNull( groups = {GroupA.class}, message = "id cannot be null")
    private Integer id;

    @NotNull(message = "username cannot be null")
    private String name;

    @NotNull(message = "sex cannot be null")
    private String sex;

    @Max(value = 99L)
    private Integer age;
}
