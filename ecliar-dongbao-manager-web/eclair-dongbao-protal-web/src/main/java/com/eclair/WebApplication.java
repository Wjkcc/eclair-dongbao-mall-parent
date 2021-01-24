package com.eclair;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: wjk
 * @date: 2021/1/21 21:03
 **/
//@SpringBootApplication(scanBasePackages = {"com.eclair"})
@SpringBootApplication
@MapperScan("com.eclair.dongbaoums.mapper")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
