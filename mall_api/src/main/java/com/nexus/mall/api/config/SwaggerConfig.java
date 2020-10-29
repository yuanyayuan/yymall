package com.nexus.mall.api.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.nexus.mall.common.config.BaseSwaggerConfig;
import com.nexus.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**

* @Description:    Swagger API文档相关配置

* @Author:         Nexus

* @CreateDate:     2020/9/3 22:05

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/3 22:05

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Configuration
@EnableSwagger2
@EnableKnife4j
@Profile({"dev"})
public class SwaggerConfig extends BaseSwaggerConfig {
    /**
     * @Author Noctis
     * @Description Swagger API文档相关配置
     * @Date 2020/9/3 22:06
     * @param
     * @return com.nexus.mall.common.domain.SwaggerProperties
     **/
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.nexus.mall.api.controller")
                .enableSecurity(true)
                .title("mall-api模块")
                .description("mall-api相关接口文档")
                .contactName("nexus")
                .contactEmail("liyuan0707@outlook.com")
                .contactUrl("https://github.com/yuanyayuan/yymall")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
