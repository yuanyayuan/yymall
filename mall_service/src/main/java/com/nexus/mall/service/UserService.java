package com.nexus.mall.service;

import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.UserCreatBO;

public interface UserService {
    /**
     * queryUsernameIsExist
     * @Author : Nexus
     * @Description : //TODO
     * @Date : 2020/9/6 21:39
     * @Param : username 用户名
     * @return : boolean
     **/
    boolean queryUsernameIsExist(String username);
    /**
     * createUser
     * @Author : Nexus
     * @Description : //TODO
     * @Date : 2020/9/6 23:02
     * @Param : userBO 用户注册表单对象
     * @return : com.nexus.mall.pojo.Users
     **/
    Users createUser(UserCreatBO userBO);
}
