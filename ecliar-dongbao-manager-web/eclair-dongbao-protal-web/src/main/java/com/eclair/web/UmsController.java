package com.eclair.web;

import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.entity.UmsMember;
import com.eclair.dongbaoums.service.UmsMemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * @description:
 * @author: wjk
 * @date: 2021/1/21 21:19
 **/
@RestController
public class UmsController {
    @Resource
    UmsMemberService umsMemberService;
    @PostMapping("/addUser")
    public String register(@RequestBody UmsMemberRegisterDTO umsMemberRegisterDTO) {
        String s = checkNotNull(umsMemberRegisterDTO);
        if (s != null) {
            return s;
        }
        return umsMemberService.register(umsMemberRegisterDTO);
    }
    // 通过反射获取当前类的get方法从而获取参数值，不存在就返回参数error
    private <T> String checkNotNull(T t) {
        Class<?> aClass = t.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        return null;
    }
}
