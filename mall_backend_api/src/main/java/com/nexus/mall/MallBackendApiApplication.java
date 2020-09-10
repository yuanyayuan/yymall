package com.nexus.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.nexus.mall.dao")
@ComponentScan(basePackages = {"com.nexus.mall", "org.n3r.idworker"})
@SpringBootApplication
public class MallBackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallBackendApiApplication.class, args);
	}

}
