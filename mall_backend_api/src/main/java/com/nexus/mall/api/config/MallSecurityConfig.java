package com.nexus.mall.api.config;

import com.nexus.mall.security.SecurityConfig;
import com.nexus.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**

* @Description:    SpringSecurity子配置

* @Author:         Nexus

* @CreateDate:     2020/9/9 22:00

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 22:00

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Configuration
@EnableWebSecurity
/*
 只有加了@EnableGlobalMethodSecurity(prePostEnabled=true)
 那么在上面使用的 @PreAuthorize("hasAuthority('admin')")才会生效
*/
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
    @Autowired
    private UserService userService;

}
