package com.nexus.mall.service.backend;

import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.pojo.bo.admin.AdminCreateBO;
import com.nexus.mall.pojo.bo.admin.UpdateAdminPasswordParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**

* @Description:    后台管理员Service

* @Author:         Nexus

* @CreateDate:     2020/9/10 21:47

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/10 21:47

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface BackendAdminService {
    /**
     * login
     * @Author : Nexus
     * @Description : 用户登录
     * @Date : 2020/9/10 23:44
     * @Param : username
     * @Param : password
     * @return : java.lang.String
     **/
    String login(String username,String password);

    /**
     * register
     * @Author : Nexus
     * @Description : 后台用户注册
     * @Date : 2020/9/10 23:24
     * @Param : adminParam
     * @return : com.nexus.mall.pojo.BackendAdmin
     **/
    BackendAdmin register(AdminCreateBO adminParam);

    /**
     * 判断用户名是否唯一
     * @Author : Nexus
     * @Description : 判断用户名是否唯一
     * @Date : 2020/9/10 23:28
     * @Param : username
     * @return : boolean
     **/
    boolean queryUsernameIsExist(String username);

    /**
     * 根据用户名获取用户
     * @Author : Nexus
     * @Description : 根据用户名获取用户
     * @Date : 2020/9/10 21:48
     * @Param : username
     * @return : com.nexus.mall.pojo.BackendAdmin
     **/
    BackendAdmin getAdminByUsername(String username);

    /**
     * 刷新token的功能
     * @Author LiYuan
     * @Description 刷新token的功能
     * @Date 9:33 2020/9/11
     * @param oldToken
     * @return java.lang.String
    **/
    String refreshToken(String oldToken);

    /**
     * 获取用户信息
     * @Author : Nexus
     * @Description : 获取用户信息
     * @Date : 2020/9/10 21:46
     * @Param : username
     * @return : org.springframework.security.core.userdetails.UserDetails
     **/
    UserDetails loadUserByUsername(String username);

    /**
     * getResourceList
     * @Author : Nexus
     * @Description : 获取指定用户的可访问资源
     * @Date : 2020/9/10 21:57
     * @Param : adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<BackendResource> getResourceList(Long adminId);

    /**
     * getRoleList
     * @Author LiYuan
     * @Description 获取用户对于角色
     * @Date 14:44 2020/9/11
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
    **/
    List<BackendRole> getRoleList(Long adminId);

    /**
     * list
     * @Author LiYuan
     * @Description 根据用户名或昵称分页查询用户
     * @Date 10:47 2020/9/14
     * @param keyword
     * @param page
     * @param pageSize
     * @return java.util.List<com.nexus.mall.pojo.BackendAdmin>
    **/
    List<BackendAdmin> list(String keyword, Integer page, Integer pageSize);
    /**
     * getItem
     * @Author LiYuan
     * @Description 根据用户id获取用户
     * @Date 11:29 2020/9/14
     * @param id
     * @return com.nexus.mall.pojo.BackendAdmin
    **/
    BackendAdmin getItem(Long id);
    /**
     *
     * update
     *
     * @Author LiYuan
     * @Description 修改指定用户信息
     * @Date 11:39 2020/9/14
     * @param id
     * @param admin
     * @return int
    **/
    int update(Long id, BackendAdmin admin);
    /**
     *
     * updatePassword
     *
     * @Author LiYuan
     * @Description 修改密码
     * @Date 11:03 2020/9/15
     * @param updatePasswordParam
     * @return int
    **/
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);
    /**
     *
     * delete
     *
     * @Author LiYuan
     * @Description 删除指定用户
     * @Date 11:27 2020/9/15
     * @param id
     * @return int
    **/
    int delete(Long id);
    /**
     *
     * updateRole
     *
     * @Author LiYuan
     * @Description 修改用户角色关系
     * @Date 16:07 2020/9/15
     * @param adminId
     * @param roleIds
     * @return int
    **/
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);
}
