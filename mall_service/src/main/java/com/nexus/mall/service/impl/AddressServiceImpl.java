package com.nexus.mall.service.impl;

import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.dao.UserAddressMapper;
import com.nexus.mall.pojo.UserAddress;
import com.nexus.mall.pojo.bo.user.AddressBO;
import com.nexus.mall.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/20 16:19

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/20 16:19

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    /**
     * queryAll
     *
     * @param userId
     * @return : java.util.List<com.nexus.mall.pojo.UserAddress>
     * @Author : Nexus
     * @Description : 根据用户id查询用户的收货地址列表
     * @Date : 2020/9/20 16:21
     * @Param : userId
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        return userAddressMapper.select(ua);
    }

    /**
     * addNewUserAddress
     *
     * @param addressBO
     * @return : void
     * @Author : Nexus
     * @Description : 用户新增地址
     * @Date : 2020/9/20 16:21
     * @Param : addressBO
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        // 1. 判断当前用户是否存在地址，如果没有，则新增为‘默认地址’
        int isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty()) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        // 2. 保存地址到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }

    /**
     * updateUserAddress
     *
     * @param addressBO
     * @return : void
     * @Author : Nexus
     * @Description : 用户修改地址
     * @Date : 2020/9/20 16:22
     * @Param : addressBO
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    /**
     * deleteUserAddress
     *
     * @param userId
     * @param addressId
     * @return : void
     * @Author : Nexus
     * @Description : 根据用户id和地址id，删除对应的用户地址信息
     * @Date : 2020/9/20 16:22
     * @Param : userId
     * @Param : addressId
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);
        userAddressMapper.delete(address);
    }

    /**
     * updateUserAddressToBeDefault
     *
     * @param userId
     * @param addressId
     * @return : void
     * @Author : Nexus
     * @Description : 修改默认地址
     * @Date : 2020/9/20 16:22
     * @Param : userId
     * @Param : addressId
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 1. 查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list  = userAddressMapper.select(queryAddress);
        for (UserAddress ua : list) {
            ua.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }

        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    /**
     * queryUserAddres
     *
     * @param userId
     * @param addressId
     * @return : com.nexus.mall.pojo.UserAddress
     * @Author : Nexus
     * @Description : 根据用户id和地址id，查询具体的用户地址对象信息
     * @Date : 2020/9/20 16:23
     * @Param : userId
     * @Param : addressId
     */
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        UserAddress singleAddress = new UserAddress();
        singleAddress.setId(addressId);
        singleAddress.setUserId(userId);
        return userAddressMapper.selectOne(singleAddress);
    }
}
