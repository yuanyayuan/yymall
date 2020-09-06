package com.nexus.mall.service;

import com.nexus.mall.pojo.Users;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/6 17:53

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/6 17:53

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface TestService {
    /**
     * getUserById
     * @Author Noctis
     * @Description //测试
     * @Date 2020/9/6 18:02
     * @param id
     * @return com.nexus.mall.pojo.Users
     **/
    Users getUserById(Integer id);
    /**
     * saveUser
     * @Author Noctis
     * @Description //TODO
     * @Date 2020/9/6 18:05
     * @param
     * @return void
     **/
    void saveUser();
    /**
     * updateUserById
     * @Author Noctis
     * @Description //TODO
     * @Date 2020/9/6 18:05
     * @param id
     * @return void
     **/
    void updateUserById(Integer id);
    /**
     * deleteUserById
     * @Author Noctis
     * @Description //TODO
     * @Date 2020/9/6 18:05
     * @param id
     * @return void
     **/
    void deleteUserById(Integer id);
}
