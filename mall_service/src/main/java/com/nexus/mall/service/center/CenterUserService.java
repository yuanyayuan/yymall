package com.nexus.mall.service.center;

import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.user.center.CenterUserBO;

public interface CenterUserService {
    /**
     * 根据用户id查询用户信息
     * @Author : Nexus
     * @Description : 根据用户id查询用户信息
     * @Date : 2020/10/15 21:53
     * @Param : userId
     * @return : com.nexus.mall.pojo.Users
     **/
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @Author : Nexus
     * @Description : 修改用户信息
     * @Date : 2020/10/15 21:54
     * @Param : userId
     * @Param : centerUserBO
     * @return : com.nexus.mall.pojo.Users
     **/
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @Author : Nexus
     * @Description : 用户头像更新
     * @Date : 2020/10/15 21:54
     * @Param : userId
     * @Param : faceUrl
     * @return : com.nexus.mall.pojo.Users
     **/
    Users updateUserFace(String userId, String faceUrl);
}
