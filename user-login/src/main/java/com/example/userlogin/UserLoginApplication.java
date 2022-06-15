package com.example.userlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 * 运行当前类开始
 */
@SpringBootApplication

@ComponentScan(basePackages = {"com.example.common","com.example"})
@EnableSwagger2
@EnableTransactionManagement
@EnableAsync
public class UserLoginApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserLoginApplication.class, args);
    }

}
