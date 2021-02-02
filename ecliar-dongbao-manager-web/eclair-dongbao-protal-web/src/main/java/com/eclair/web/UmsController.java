package com.eclair.web;

import com.eclair.annotation.Token;
import com.eclair.base.result.ResultWrapper;
import com.eclair.dongbaoums.dto.UmsMemberChangePwdDTO;
import com.eclair.dongbaoums.dto.UmsMemberLoginDTO;
import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.dto.UmsMemberUpdateDTO;
import com.eclair.dongbaoums.service.UmsMemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    public ResultWrapper register(@RequestBody @Valid UmsMemberRegisterDTO umsMemberRegisterDTO) {
        String s = checkNotNull(umsMemberRegisterDTO);
        System.out.println(00000);
        if (s != null) {
            return null;
        }
//        try {
//            int i = 1/0;
//        }catch (Exception e) {
//            throw new BusinessException("lllllll");
//        }
        String register = umsMemberService.register(umsMemberRegisterDTO);
        ResultWrapper objectResultWrapper = new ResultWrapper();;
        if (register.equals("ok")) {

            objectResultWrapper.setMsg("success");
            return ResultWrapper.success().build();
        }
       objectResultWrapper.setMsg(register);
        return ResultWrapper.fail().code("400").msg(register).build();
    }
    // 通过反射获取当前类的get方法从而获取参数值，不存在就返回参数error
    private <T> String checkNotNull(T t) {
        Class<?> aClass = t.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        return null;
    }

    @PostMapping("/login")
    public ResultWrapper login(@RequestBody UmsMemberLoginDTO umsMemberLoginDTO) {
        String s = checkNotNull(umsMemberLoginDTO);
        if (s != null) {
            return ResultWrapper.fail().data(s).build();
        }
        return umsMemberService.login(umsMemberLoginDTO);
    }

    @PostMapping("/updatePwd")
    @Token
    public ResultWrapper updatePwd(@RequestBody UmsMemberChangePwdDTO umsMemberChangePwdDTO) {
        String s = checkNotNull(umsMemberChangePwdDTO);
        if (s != null) {
            return ResultWrapper.fail().data(s).build();
        }
        return umsMemberService.changePassword(umsMemberChangePwdDTO);
    }

    @PostMapping("/update")
    @Token
    public ResultWrapper update(@RequestBody UmsMemberUpdateDTO umsMemberUpdateDTO) {
        String s = checkNotNull(umsMemberUpdateDTO);
        if (s != null) {
            return ResultWrapper.fail().data(s).build();
        }
        return umsMemberService.updateUser(umsMemberUpdateDTO);
    }

    @GetMapping("/getUser")
    @Token
    public ResultWrapper getUser() {
        return umsMemberService.getUser();
    }

    @PostMapping("/loginOut")
    @Token
    public ResultWrapper loginOut() {
        return umsMemberService.loginOut();
    }
}
