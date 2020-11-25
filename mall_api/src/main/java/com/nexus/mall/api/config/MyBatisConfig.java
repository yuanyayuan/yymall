package com.nexus.mall.api.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.nexus.mall.dao.backend","com.nexus.mall.dao.protal"})
public class MyBatisConfig {
}
