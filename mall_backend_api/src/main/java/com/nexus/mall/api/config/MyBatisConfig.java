package com.nexus.mall.api.config;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.nexus.mall.dao"})
public class MyBatisConfig {
}
