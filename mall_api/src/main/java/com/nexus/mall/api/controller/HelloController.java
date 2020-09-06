package com.nexus.mall.api.controller;

import com.nexus.mall.pojo.Users;
import com.nexus.mall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TestService testService;

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("test")
    public Users getUserById(Integer id){
        return testService.getUserById(id);
    }
}
