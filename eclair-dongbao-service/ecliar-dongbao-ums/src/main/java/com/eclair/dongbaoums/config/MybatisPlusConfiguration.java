package com.eclair.dongbaoums.config;

import com.eclair.dongbaoums.Handler.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: wjk
 * @date: 2021/1/21 21:26
 **/
@Configuration
public class MybatisPlusConfiguration{
    @Bean
    public MyHandler myHandler() {
        System.out.println(11111);
        return new MyHandler();
    }
    public MybatisPlusConfiguration() {
        System.out.println(22222222);
    }
}
