package com.nexus.mall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**

* @Description:    启动类

* @Author:         Nexus

* @CreateDate:     2020/9/3 21:59

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/3 21:59

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@EnableScheduling
@ComponentScan(basePackages = {"com.nexus.mall", "org.n3r.idworker"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class MallApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApiApplication.class, args);
	}

}
