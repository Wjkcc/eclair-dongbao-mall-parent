package com.eclair.dongbaoums.config;/**
 * @author
 * @date
 **/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author
 * @Time 2021/1/25 13:43
 * @Description
 **/
@Configuration
public class EncodeConfiguration {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
