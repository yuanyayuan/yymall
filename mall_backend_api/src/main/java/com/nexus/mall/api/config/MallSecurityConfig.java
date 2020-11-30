package com.nexus.mall.api.config;

import com.google.common.collect.Maps;
import com.nexus.mall.pojo.BackendResource;
import com.nexus.mall.security.component.DynamicSecurityService;
import com.nexus.mall.security.config.SecurityConfig;
import com.nexus.mall.service.backend.BackendAdminService;
import com.nexus.mall.service.backend.BackendResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**

* @Description:    SpringSecurity子配置
 * 只有加了@EnableGlobalMethodSecurity(prePostEnabled=true)
 *  那么在上面使用的 @PreAuthorize("hasAuthority('admin')")才会生效

* @Author:         Nexus

* @CreateDate:     2020/9/9 22:00

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 22:00

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {

    @Autowired
    private BackendAdminService adminService;

    @Autowired
    private BackendResourceService resourceService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return () -> {
            Map<String, ConfigAttribute> map = Maps.newConcurrentMap();
            List<BackendResource> resourceList = resourceService.listAll();
            for (BackendResource resource : resourceList) {
                map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }

}
