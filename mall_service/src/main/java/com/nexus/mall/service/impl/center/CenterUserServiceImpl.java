package com.nexus.mall.service.impl.center;

import com.nexus.mall.dao.UsersMapper;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.user.center.CenterUserBO;
import com.nexus.mall.service.center.CenterUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired
    public UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return : com.nexus.mall.pojo.Users
     * @Author : Nexus
     * @Description : 根据用户id查询用户信息
     * @Date : 2020/10/15 21:53
     */
    @Override
    public Users queryUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    /**
     * 修改用户信息
     *
     * @param userId
     * @param centerUserBO
     * @return : com.nexus.mall.pojo.Users
     * @Author : Nexus
     * @Description : 修改用户信息
     * @Date : 2020/10/15 21:54
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return queryUserInfo(userId);
    }

    /**
     * 用户头像更新
     *
     * @param userId
     * @param faceUrl
     * @return : com.nexus.mall.pojo.Users
     * @Author : Nexus
     * @Description : 用户头像更新
     * @Date : 2020/10/15 21:54
     * @Param : userId
     * @Param : faceUrl
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return queryUserInfo(userId);
    }
}
