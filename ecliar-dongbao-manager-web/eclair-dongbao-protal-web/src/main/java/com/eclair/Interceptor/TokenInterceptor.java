package com.eclair.Interceptor;/**
 * @author
 * @date
 **/

import com.eclair.annotation.Token;
import com.eclair.config.ValueConfig;
import com.eclair.dto.UmsTokenDTO;
import com.eclair.token.JWTUtils;
import com.eclair.token.Third.AbstractTokenSave;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * token拦截器
 * @Author
 * @Time 2021/1/27 14:37
 * @Description
 **/
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    AbstractTokenSave abstractTokenSave;
    // 校验方法是否需要@Token注解，获取贴可能进行校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        System.out.println(2222);
        Method method = handlerMethod.getMethod();
        //检查是否有Token注解，无则跳过认证
        if (!method.isAnnotationPresent(Token.class)) {
                return true;
        }
        String token = request.getHeader("assess-token");
        // 校验token
        if (Objects.nonNull(token)) {
            UmsTokenDTO umsTokenDTO = JWTUtils.decodeToken(token);
            // 获取token，进行校验
            if (!umsTokenDTO.isEmpty()) {
                // 校验过期时间
                // 在线程中添加用户信息
                if (ValueConfig.exp) {
                    Boolean aBoolean = abstractTokenSave.checkToken(umsTokenDTO.getUsername());
                }
                return true;
            }
        }
        System.out.println(11111);
        response.getWriter().println("token 不存在或者失效");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
