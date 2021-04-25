package com.nexus.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.nexus.mall.common.service.RedisService;
import com.nexus.mall.dao.backend.BackendAdminRoleRelationMapper;
import com.nexus.mall.dao.backend.BackendAdminRoleRelationMapperCustom;
import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendAdminRoleRelation;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.service.backend.BackendAdminCacheService;
import com.nexus.mall.service.backend.BackendAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**

* @Description:    BackendAdminCacheService实现类

* @Author:         Nexus

* @CreateDate:     2020/11/26 23:21

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/26 23:21

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
@Slf4j
public class BackendAdminCacheServiceImpl implements BackendAdminCacheService {

    @Autowired
    private BackendAdminService adminService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BackendAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private BackendAdminRoleRelationMapperCustom adminRoleRelationDao;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    /**
     * 删除后台用户缓存
     *
     * @param adminId
     * @return : void
     * @Author : Nexus
     * @Description : 删除后台用户缓存
     * @Date : 2020/11/26 23:07
     * @Param : adminId
     */
    @Override
    public void delAdmin(Long adminId) {
        BackendAdmin admin = adminService.getItem(adminId);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    /**
     * 删除后台用户资源列表缓存
     *
     * @param adminId
     * @return : void
     * @Author : Nexus
     * @Description : 删除后台用户资源列表缓存
     * @Date : 2020/11/26 23:07
     * @Param : adminId
     */
    @Override
    public void delResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.del(key);
    }

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     *
     * @param roleId
     * @return : void
     * @Author : Nexus
     * @Description : 当角色相关资源信息改变时删除相关后台用户缓存
     * @Date : 2020/11/26 23:07
     * @Param : roleId
     */
    @Override
    public void delResourceListByRole(Long roleId) {
        Example example = new Example(BackendAdminRoleRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",roleId);
        List<BackendAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     *
     * @param roleIds
     * @return : void
     * @Author : Nexus
     * @Description :当角色相关资源信息改变时删除相关后台用户缓存
     * @Date : 2020/11/26 23:09
     * @Param : roleIds
     */
    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        Example example = new Example(BackendAdminRoleRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("roleId",roleIds);
        List<BackendAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     *
     * @param resourceId
     * @return : void
     * @Author : Nexus
     * @Description : 当资源信息改变时，删除资源项目后台用户缓存
     * @Date : 2020/11/26 23:09
     * @Param : resourceId
     */
    @Override
    public void delResourceListByResource(Long resourceId) {
        List<Long> adminIdList = adminRoleRelationDao.getAdminIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 获取缓存后台用户信息
     *
     * @param username
     * @return : com.nexus.mall.pojo.BackendAdmin
     * @Author : Nexus
     * @Description : 获取缓存后台用户信息
     * @Date : 2020/11/26 23:10
     * @Param : username
     */
    @Override
    public BackendAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (BackendAdmin) redisService.get(key);
    }

    /**
     * 设置缓存后台用户信息
     *
     * @param admin
     * @return : void
     * @Author : Nexus
     * @Description : 设置缓存后台用户信息
     * @Date : 2020/11/26 23:10
     * @Param : admin
     */
    @Override
    public void setAdmin(BackendAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    /**
     * 获取缓存后台用户资源列表
     *
     * @param adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     * @Author : Nexus
     * @Description : 获取缓存后台用户资源列表
     * @Date : 2020/11/26 23:10
     * @Param : adminId
     */
    @Override
    public List<BackendResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<BackendResource>) redisService.get(key);
    }

    /**
     * 设置后台后台用户资源列表
     *
     * @param adminId
     * @param resourceList
     * @return : void
     * @Author : Nexus
     * @Description : 设置后台后台用户资源列表
     * @Date : 2020/11/26 23:10
     * @Param : adminId
     * @Param : resourceList
     */
    @Override
    public void setResourceList(Long adminId, List<BackendResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }
}
