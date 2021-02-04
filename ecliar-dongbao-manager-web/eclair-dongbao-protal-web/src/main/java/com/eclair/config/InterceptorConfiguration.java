package com.eclair.config;/**
 * @author
 * @date
 **/

import com.eclair.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.rmi.registry.Registry;

/**
 * @Author
 * @Time 2021/1/27 15:00
 * @Description
 **/
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Resource
    TokenInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**")
                .excludePathPatterns("/code/**");;
    }
}
