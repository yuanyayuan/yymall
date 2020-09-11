package com.nexus.mall.service;

import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.user.UserCreatBO;

public interface UserService {
    /**
     * queryUsernameIsExist
     * @Author : Nexus
     * @Description : 校验用户名是否唯一
     * @Date : 2020/9/6 21:39
     * @Param : username 用户名
     * @return : boolean
     **/
    boolean queryUsernameIsExist(String username);
    /**
     * createUser
     * @Author : Nexus
     * @Description : 创建用户
     * @Date : 2020/9/6 23:02
     * @Param : userBO 用户注册表单对象
     * @return : com.nexus.mall.pojo.Users
     **/
    Users createUser(UserCreatBO userBO);
    /**
     *
     * queryUserForLogin
     *
     * @Author LiYuan
     * @Description 检索用户名与密码与数据库是否匹配
     * @Date 17:09 2020/9/11
     * @param username
     * @param password
     * @return com.nexus.mall.pojo.Users
    **/
    Users queryUserForLogin(String username,String password);
}
