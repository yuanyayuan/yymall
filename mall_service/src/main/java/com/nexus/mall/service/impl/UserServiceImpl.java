package com.nexus.mall.service.impl;

import com.nexus.mall.common.enums.Sex;
import org.n3r.idworker.Sid;
import com.nexus.mall.dao.UsersMapper;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.user.UserCreatBO;
import com.nexus.mall.service.UserService;
import com.nexus.mall.util.DateUtils;
import com.nexus.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Autowired
    private Sid sid;

    @Autowired
    private UsersMapper usersMapper;

    /**
     * queryUsernameIsExist
     *
     * @param username
     * @return : boolean
     * @Author : Nexus
     * @Description : 判断用户名是否重复
     * @Date : 2020/9/6 21:39
     * @Param : username 用户名
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result != null;
    }

    /**
     * createUser
     *
     * @param userBO
     * @return : com.nexus.mall.pojo.Users
     * @Author : Nexus
     * @Description : 新增用户
     * @Date : 2020/9/6 23:02
     * @Param : userBO 用户注册表单对象
     */
    @Override
    public Users createUser(UserCreatBO userBO) {
        String userId = sid.nextShort();
        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称等同于用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtils.strToDate("1900-01-01"));
        // 默认性别为保密
        user.setSex(Sex.secret.type);
        // 系统时间
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;
    }

    /**
     * queryUserForLogin
     *
     * @param username
     * @param password
     * @return com.nexus.mall.pojo.Users
     * @Author LiYuan
     * @Description 检索用户名与密码与数据库是否匹配
     * @Date 17:09 2020/9/11
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password",password);

        return usersMapper.selectOneByExample(userCriteria);
    }


}
