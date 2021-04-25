package com.nexus.mall.common.service;

import com.nexus.mall.util.BloomFilterHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;
/**

* @Description:    redis操作Service

* @Author:         Nexus

* @CreateDate:     2020/11/26 23:12

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/26 23:12

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface RedisService {
    /**
     * 保存属性
     * @Author : Nexus
     * @Description : 保存属性
     * @Date : 2020/11/26 23:12
     * @Param : key
     * @Param : value
     * @Param : time
     * @return : void
     **/
    void set(String key, Object value, long time);

    /**
     * 保存属性
     * @Author : Nexus
     * @Description : 保存属性
     * @Date : 2020/11/26 23:12
     * @Param : key
     * @Param : value
     * @return : void
     **/
    void set(String key, Object value);

    /**
     * 获取属性
     * @Author : Nexus
     * @Description : 获取属性
     * @Date : 2020/11/26 23:12
     * @Param : key
     * @return : java.lang.Object
     **/
    Object get(String key);

    /**
     * 删除属性
     * @Author : Nexus
     * @Description : 删除属性
     * @Date : 2020/11/26 23:12
     * @Param : key
     * @return : java.lang.Boolean
     **/
    Boolean del(String key);

    /**
     * 批量删除属性
     * @Author : Nexus
     * @Description : 批量删除属性
     * @Date : 2020/11/26 23:13
     * @Param : keys
     * @return : java.lang.Long
     **/
    Long del(List<String> keys);

    /**
     * 设置过期时间
     * @Author : Nexus
     * @Description : 设置过期时间
     * @Date : 2020/11/26 23:13
     * @Param : key
     * @Param : time
     * @return : java.lang.Boolean
     **/
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     * @Author : Nexus
     * @Description : 获取过期时间
     * @Date : 2020/11/26 23:13
     * @Param : key
     * @return : java.lang.Long
     **/
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     * @Author : Nexus
     * @Description : 判断是否有该属性
     * @Date : 2020/11/26 23:13
     * @Param : key
     * @return : java.lang.Boolean
     **/
    Boolean hasKey(String key);

    /**
     * 按delta递增
     * @Author : Nexus
     * @Description :按delta递增
     * @Date : 2020/11/26 23:14
     * @Param : key
     * @Param : delta
     * @return : java.lang.Long
     **/
    Long incr(String key, long delta);

    /**
     * 按delta递减
     * @Author : Nexus
     * @Description : 按delta递减
     * @Date : 2020/11/26 23:15
     * @Param : key
     * @Param : delta
     * @return : java.lang.Long
     **/
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     * @Author : Nexus
     * @Description : 获取Hash结构中的属性
     * @Date : 2020/11/26 23:15
     * @Param : key
     * @Param : hashKey
     * @return : java.lang.Object
     **/
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     * @Author : Nexus
     * @Description : 向Hash结构中放入一个属性
     * @Date : 2020/11/26 23:15
     * @Param : key
     * @Param : hashKey
     * @Param : value
     * @Param : time
     * @return : java.lang.Boolean
     **/
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     * @Author : Nexus
     * @Description : 向Hash结构中放入一个属性
     * @Date : 2020/11/26 23:15
     * @Param : key
     * @Param : hashKey
     * @Param : value
     * @return : void
     **/
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     * @Author : Nexus
     * @Description : 直接获取整个Hash结构
     * @Date : 2020/11/26 23:15
     * @Param : key
     * @return : java.util.Map<java.lang.Object,java.lang.Object>
     **/
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     * @Author : Nexus
     * @Description : 直接设置整个Hash结构
     * @Date : 2020/11/26 23:16
     * @Param : key
     * @Param : map
     * @Param : time
     * @return : java.lang.Boolean
     **/
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     * @Author : Nexus
     * @Description : 直接设置整个Hash结构
     * @Date : 2020/11/26 23:16
     * @Param : key
     * @Param : map
     * @return : void
     **/
    void hSetAll(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     * @Author : Nexus
     * @Description : 删除Hash结构中的属性
     * @Date : 2020/11/26 23:16
     * @Param : key
     * @Param : hashKey
     * @return : void
     **/
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     * @Author : Nexus
     * @Description : 判断Hash结构中是否有该属性
     * @Date : 2020/11/26 23:16
     * @Param : key
     * @Param : hashKey
     * @return : java.lang.Boolean
     **/
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     * @Author : Nexus
     * @Description : Hash结构中属性递增
     * @Date : 2020/11/26 23:16
     * @Param : key
     * @Param : hashKey
     * @Param : delta
     * @return : java.lang.Long
     **/
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     * @Author : Nexus
     * @Description : Hash结构中属性递减
     * @Date : 2020/11/26 23:17
     * @Param : key
     * @Param : hashKey
     * @Param : delta
     * @return : java.lang.Long
     **/
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     * @Author : Nexus
     * @Description : 获取Set结构
     * @Date : 2020/11/26 23:17
     * @Param : key
     * @return : java.util.Set<java.lang.Object>
     **/
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     * @Author : Nexus
     * @Description : 向Set结构中添加属性
     * @Date : 2020/11/26 23:17
     * @Param : key
     * @Param : values
     * @return : java.lang.Long
     **/
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     * @Author : Nexus
     * @Description : 向Set结构中添加属性
     * @Date : 2020/11/26 23:17
     * @Param : key
     * @Param : time
     * @Param : values
     * @return : java.lang.Long
     **/
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     * @Author : Nexus
     * @Description : 是否为Set中的属性
     * @Date : 2020/11/26 23:17
     * @Param : key
     * @Param : value
     * @return : java.lang.Boolean
     **/
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     * @Author : Nexus
     * @Description : 获取Set结构的长度
     * @Date : 2020/11/26 23:18
     * @Param : key
     * @return : java.lang.Long
     **/
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     * @Author : Nexus
     * @Description : 删除Set结构中的属性
     * @Date : 2020/11/26 23:18
     * @Param : key
     * @Param : values
     * @return : java.lang.Long
     **/
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     * @Author : Nexus
     * @Description : 获取List结构中的属性
     * @Date : 2020/11/26 23:18
     * @Param : key
     * @Param : start
     * @Param : end
     * @return : java.util.List<java.lang.Object>
     **/
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     * @Author : Nexus
     * @Description : 获取List结构的长度
     * @Date : 2020/11/26 23:18
     * @Param : key
     * @return : java.lang.Long
     **/
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     * @Author : Nexus
     * @Description : 根据索引获取List中的属性
     * @Date : 2020/11/26 23:18
     * @Param : key
     * @Param : index
     * @return : java.lang.Object
     **/
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     * @Author : Nexus
     * @Description : 向List结构中添加属性
     * @Date : 2020/11/26 23:18
     * @Param : key
     * @Param : value
     * @return : java.lang.Long
     **/
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     * @Author : Nexus
     * @Description : 向List结构中添加属性
     * @Date : 2020/11/26 23:19
     * @Param : key
     * @Param : value
     * @Param : time
     * @return : java.lang.Long
     **/
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     * @Author : Nexus
     * @Description : 向List结构中批量添加属性
     * @Date : 2020/11/26 23:19
     * @Param : key
     * @Param : values
     * @return : java.lang.Long
     **/
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     * @Author : Nexus
     * @Description : 向List结构中批量添加属性
     * @Date : 2020/11/26 23:19
     * @Param : key
     * @Param : time
     * @Param : values
     * @return : java.lang.Long
     **/
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     * @Author : Nexus
     * @Description : 从List结构中移除属性
     * @Date : 2020/11/26 23:19
     * @Param : key
     * @Param : count
     * @Param : value
     * @return : java.lang.Long
     **/
    Long lRemove(String key, long count, Object value);
    /**
     * 布隆过滤器
     * @Author : Nexus
     * @Description : //TODO
     * @Date : 2021/4/3 21:29
     * @Param : bloomFilterHelper
     * @Param : key
     * @Param : value
     * @return : void
     **/
    <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value);
    /**
     * 布隆过滤器
     * @Author : Nexus
     * @Description : //TODO
     * @Date : 2021/4/3 21:29
     * @Param : bloomFilterHelper
     * @Param : key
     * @Param : value
     * @return : boolean
     **/
    <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value);
}
