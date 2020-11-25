package com.nexus.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.nexus.mall.common.exception.Asserts;
import com.nexus.mall.dao.backend.BackendAdminMapper;
import com.nexus.mall.dao.backend.BackendAdminRoleRelationMapper;
import com.nexus.mall.dao.backend.BackendAdminRoleRelationMapperCustom;
import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendAdminRoleRelation;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.pojo.BackendRole;
import com.nexus.mall.pojo.bo.admin.AdminCreateBO;
import com.nexus.mall.pojo.bo.admin.AdminUserDetails;
import com.nexus.mall.pojo.bo.admin.UpdateAdminPasswordParam;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**

* @Description:    BackendAdminService实现类

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

    @Autowired(required = false)
    private JwtTokenUtil jwtTokenUtil;
    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Sid sid;

    @Autowired
    private BackendAdminMapper adminMapper;

    @Autowired
    private BackendAdminRoleRelationMapper  adminRoleRelationMapper;

    @Autowired
    private BackendAdminRoleRelationMapperCustom adminRoleRelationMapperCustom;

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
        Example example = new Example(BackendAdmin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        BackendAdmin result = adminMapper.selectOneByExample(example);
        return result != null;

    }

    /**
     * getAdminByUsername
     *
     * @param username
     * @return : com.nexus.mall.pojo.BackendAdmin
     * @Author : Nexus
     * @Description : 通过用户名获取用户
     * @Date : 2020/9/10 21:48
     * @Param : username
     */
    @Override
    public BackendAdmin getAdminByUsername(String username) {
        Example example = new Example(BackendAdmin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<BackendAdmin> adminList = adminMapper.selectByExample(example);
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
        List<BackendResource> resourceList = adminRoleRelationMapperCustom.getResourceList(adminId);
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
        return adminRoleRelationMapperCustom.getRoleList(adminId);
    }

    /**
     * list
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return java.util.List<com.nexus.mall.pojo.BackendAdmin>
     * @Author LiYuan
     * @Description 根据用户名或昵称分页查询用户
     * @Date 10:47 2020/9/14
     **/
    @Override
    public List<BackendAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(BackendAdmin.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andLike("username","%" + keyword + "%");
            example.or(example.createCriteria().andLike("nickName","%" + keyword + "%"));
        }
        return adminMapper.selectByExample(example);

    }

    /**
     * getItem
     *
     * @param id
     * @return com.nexus.mall.pojo.BackendAdmin
     * @Author LiYuan
     * @Description 根据用户id获取用户
     * @Date 11:29 2020/9/14
     **/
    @Override
    public BackendAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * update
     *
     * @param id
     * @param admin
     * @return int
     * @Author LiYuan
     * @Description 修改指定用户信息
     * @Date 11:39 2020/9/14
     **/
    @Override
    public int update(Long id, BackendAdmin admin) {
        admin.setId(id);
        BackendAdmin backendAdmin = adminMapper.selectByPrimaryKey(id);
        if(backendAdmin.getPassword().equals(admin.getPassword())){
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        return adminMapper.updateByPrimaryKeySelective(admin);
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

    /**
     * updatePassword
     *
     * @param updatePasswordParam
     * @return int
     * @Author LiYuan
     * @Description 修改密码
     * @Date 11:03 2020/9/15
     **/
    @Override
    public int updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        if(StrUtil.isEmpty(updatePasswordParam.getUsername())
                ||StrUtil.isEmpty(updatePasswordParam.getOldPassword())
                ||StrUtil.isEmpty(updatePasswordParam.getNewPassword())){
            Asserts.fail("提交参数不合法");
        }
        Example example = new Example(BackendAdmin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",updatePasswordParam.getUsername());
        List<BackendAdmin> adminList = adminMapper.selectByExample(example);
        if(CollUtil.isEmpty(adminList)){
            Asserts.fail("找不到该用户");
        }
        BackendAdmin admin = adminList.get(0);
        if(!passwordEncoder.matches(updatePasswordParam.getOldPassword(),admin.getPassword())){
            Asserts.fail("旧密码错误");
        }
        admin.setPassword(passwordEncoder.encode(updatePasswordParam.getNewPassword()));
        adminMapper.updateByPrimaryKey(admin);
        return 1;
    }

    /**
     * delete
     *
     * @param id
     * @return int
     * @Author LiYuan
     * @Description 删除指定用户
     * @Date 11:27 2020/9/15
     **/
    @Override
    public int delete(Long id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    /**
     * updateRole
     *
     * @param adminId
     * @param roleIds
     * @return int
     * @Author LiYuan
     * @Description 修改用户角色关系
     * @Date 16:07 2020/9/15
     **/
    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //1.先删除原来的关系
        Example example = new Example(BackendAdminRoleRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adminId",adminId);
        adminRoleRelationMapper.deleteByExample(example);
        //2.建立新关系
        if(CollectionUtils.isEmpty(roleIds)){
            List<BackendAdminRoleRelation> list = Lists.newLinkedList();
            assert roleIds != null;
            for (Long roleId : roleIds) {
                BackendAdminRoleRelation roleRelation = new BackendAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationMapperCustom.insertList(list);
        }
        return count;
    }
}
