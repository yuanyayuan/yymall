package com.nexus.mall.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**

* @Description:    测试用Controller

* @Author:         Nexus

* @CreateDate:     2020/9/3 22:01

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/3 22:01

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }
}
