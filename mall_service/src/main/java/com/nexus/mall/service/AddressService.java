package com.nexus.mall.service;

import com.nexus.mall.pojo.UserAddress;
import com.nexus.mall.pojo.bo.user.AddressBO;

import java.util.List;

public interface AddressService {
    /**
     * queryAll
     * @Author : Nexus
     * @Description : 根据用户id查询用户的收货地址列表
     * @Date : 2020/9/20 16:21
     * @Param : userId
     * @return : java.util.List<com.nexus.mall.pojo.UserAddress>
     **/
    List<UserAddress> queryAll(String userId);

    /**
     * addNewUserAddress
     * @Author : Nexus
     * @Description : 用户新增地址
     * @Date : 2020/9/20 16:21
     * @Param : addressBO
     * @return : void
     **/
    void addNewUserAddress(AddressBO addressBO);

    /**
     * updateUserAddress
     * @Author : Nexus
     * @Description : 用户修改地址
     * @Date : 2020/9/20 16:22
     * @Param : addressBO
     * @return : void
     **/
    void updateUserAddress(AddressBO addressBO);

    /**
     * deleteUserAddress
     * @Author : Nexus
     * @Description : 根据用户id和地址id，删除对应的用户地址信息
     * @Date : 2020/9/20 16:22
     * @Param : userId
     * @Param : addressId
     * @return : void
     **/
    void deleteUserAddress(String userId, String addressId);

    /**
     * updateUserAddressToBeDefault
     * @Author : Nexus
     * @Description : 修改默认地址
     * @Date : 2020/9/20 16:22
     * @Param : userId
     * @Param : addressId
     * @return : void
     **/
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * queryUserAddres
     * @Author : Nexus
     * @Description : 根据用户id和地址id，查询具体的用户地址对象信息
     * @Date : 2020/9/20 16:23
     * @Param : userId
     * @Param : addressId
     * @return : com.nexus.mall.pojo.UserAddress
     **/
    UserAddress queryUserAddress(String userId, String addressId);
}
