package com.eclair.web;/**
 * @author
 * @date
 **/

import com.eclair.base.result.ResultWrapper;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author
 * @Time 2021/7/16 14:08
 * @Description
 **/
@RestController
@RequestMapping("/easy")
public class EasyCaptchaCpntroller {
    @GetMapping("generate")
    public void generate(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            CaptchaUtil.out(httpServletRequest,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("verify")
    public ResultWrapper verify(String code,HttpServletRequest httpServletRequest, HttpServletResponse response) {
        final boolean ver = CaptchaUtil.ver(code, httpServletRequest);
        if (ver) {
            CaptchaUtil.clear(httpServletRequest);
            return ResultWrapper.success().build();
        }
        return null;
    }
}
