package com.nexus.mall.security.config;

import com.nexus.mall.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
/**

* @Description:    Redis配置类

* @Author:         Nexus

* @CreateDate:     2020/11/27 0:00

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/27 0:00

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
