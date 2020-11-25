package com.nexus.mall.service.impl;

import com.nexus.mall.dao.protal.UsersMapper;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**

* @Description:    java类作用描述 

* @Author:         Nexus

* @CreateDate:     2020/9/6 17:53

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/6 17:53

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * getUserById
     *
     * @param id
     * @return com.nexus.mall.pojo.Users
     * @Author Noctis
     * @Description //测试
     * @Date 2020/9/6 18:02
     **/
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    @Override
    public Users getUserById(Integer id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    /**
     * saveUser
     *
     * @return void
     * @Author Noctis
     * @Description
     * @Date 2020/9/6 18:05
     **/
    @Override
    public void saveUser() {

    }

    /**
     * updateUserById
     *
     * @param id
     * @return void
     * @Author Noctis
     * @Description
     * @Date 2020/9/6 18:05
     **/
    @Override
    public void updateUserById(Integer id) {

    }

    /**
     * deleteUserById
     *
     * @param id
     * @return void
     * @Author Noctis
     * @Description
     * @Date 2020/9/6 18:05
     **/
    @Override
    public void deleteUserById(Integer id) {

    }
}
