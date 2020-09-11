package com.nexus.mall.service.impl;

import com.nexus.mall.common.exception.Asserts;
import com.nexus.mall.dao.BackendAdminMapper;
import com.nexus.mall.dao.BackendAdminRoleRelationMapper;
import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.pojo.bo.AdminCreateBO;
import com.nexus.mall.pojo.bo.AdminUserDetails;
import com.nexus.mall.security.util.JwtTokenUtil;
import com.nexus.mall.service.BackendAdminService;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/9 23:43

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 23:43

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
@Slf4j
public class BackendAdminServiceImpl implements BackendAdminService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Sid sid;

    @Autowired
    private BackendAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BackendAdminRoleRelationMapper  adminRoleRelationMapper;

    /**
     * login
     *
     * @param username
     * @param password
     * @return : java.lang.String
     * @Author : Nexus
     * @Description : 用户登录
     * @Date : 2020/9/10 23:44
     * @Param : username
     * @Param : password
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        }catch(AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * register
     *
     * @param adminParam
     * @return : com.nexus.mall.pojo.BackendAdmin
     * @Author : Nexus
     * @Description : 后台用户注册
     * @Date : 2020/9/10 23:24
     * @Param : adminParam
     */
    @Override
    public BackendAdmin register(AdminCreateBO adminParam) {
        BackendAdmin admin = new BackendAdmin();
        BeanUtils.copyProperties(adminParam, admin);
        admin.setCreateTime(new Date());
        admin.setStatus(1);
        //查询是否有相同用户名的用户
        if(queryUsernameIsExist(admin.getUsername())){
            return null;
        }
        String encodePassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePassword);
        adminMapper.insertSelective(admin);
        return admin;
    }

    /**
     * queryUsernameIsExist
     *
     * @param username
     * @return : boolean
     * @Author : Nexus
     * @Description : 判断用户名是否唯一
     * @Date : 2020/9/10 23:28
     * @Param : username
     */
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example adminExample = new Example(BackendAdmin.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("username",username);
        BackendAdmin result = adminMapper.selectOneByExample(adminExample);
        return result != null;

    }

    /**
     * getAdminByUsername
     *
     * @param username
     * @return : com.nexus.mall.pojo.BackendAdmin
     * @Author : Nexus
     * @Description : //通过用户名获取用户
     * @Date : 2020/9/10 21:48
     * @Param : username
     */
    @Override
    public BackendAdmin getAdminByUsername(String username) {
        Example adminExample = new Example(BackendAdmin.class);
        Example.Criteria criteria = adminExample.createCriteria();
        criteria.andEqualTo("username",username);
        List<BackendAdmin> adminList = adminMapper.selectByExample(criteria);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    /**
     * refreshToken
     *
     * @param oldToken
     * @return java.lang.String
     * @Author LiYuan
     * @Description 刷新token的功能
     * @Date 9:33 2020/9/11
     **/
    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    /**
     * getResourceList
     *
     * @param adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     * @Author : Nexus
     * @Description : //获取指定用户的可访问资源
     * @Date : 2020/9/10 21:57
     * @Param : adminId
     */
    @Override
    public List<BackendResource> getResourceList(Long adminId) {
        List<BackendResource> resourceList = adminRoleRelationMapper.getResourceList(adminId);
        return resourceList;
    }

    /**
     * getRoleList
     *
     * @param adminId
     * @return java.util.List<com.nexus.mall.pojo.BackendRole>
     * @Author LiYuan
     * @Description 获取用户对于角色
     * @Date 14:44 2020/9/11
     **/
    @Override
    public List<BackendRole> getRoleList(Long adminId) {
        return adminRoleRelationMapper.getRoleList(adminId);
    }

    /**
     * loadUserByUsername
     *
     * @param username
     * @return : org.springframework.security.core.userdetails.UserDetails
     * @Author : Nexus
     * @Description : //获取用户信息
     * @Date : 2020/9/10 21:46
     * @Param : username
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        BackendAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<BackendResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw  new UsernameNotFoundException("用户名或密码错误");
    }


}
