package com.nexus.mall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
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
@MapperScan(basePackages = "com.nexus.mall.dao")
@ComponentScan(basePackages = {"com.nexus.mall", "org.n3r.idworker"})
@SpringBootApplication
public class MallApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApiApplication.class, args);
	}

}
