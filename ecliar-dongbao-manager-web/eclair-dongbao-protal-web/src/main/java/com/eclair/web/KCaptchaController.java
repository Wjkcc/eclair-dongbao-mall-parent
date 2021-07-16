package com.eclair.web;/**
 * @author
 * @date
 **/

import com.baomidou.kaptcha.Kaptcha;
import com.eclair.base.result.ResultWrapper;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author
 * @Time 2021/7/16 15:34
 * @Description
 **/
@RestController
@RequestMapping("/k")
public class KCaptchaController {
    @Autowired
    private Kaptcha kaptcha;
    @GetMapping("generate")
    public void generate(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
           kaptcha.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("verify")
    public ResultWrapper verify(String code, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        final boolean validate = kaptcha.validate(code);
        if (validate) {
            return ResultWrapper.success().build();
        }
        return null;
    }
}
