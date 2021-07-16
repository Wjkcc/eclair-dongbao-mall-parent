package com.eclair.web;/**
 * @author
 * @date
 **/

import com.eclair.base.result.ResultWrapper;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author
 * @Time 2021/7/16 14:04
 * @Description
 **/
@RestController
@RequestMapping("/happy")
public class HappyCaptchaController {
    @GetMapping("generate")
    public void generate(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        HappyCaptcha.require(httpServletRequest,response)
                .style(CaptchaStyle.ANIM)
                .type(CaptchaType.ARITHMETIC)
                .build().finish();
    }
    @GetMapping("verify")
    public ResultWrapper verify(String code,HttpServletRequest httpServletRequest, HttpServletResponse response) {
        Assert.notNull(code,"code is null");
//        final String attribute = (String)httpServletRequest.getSession().getAttribute(HappyCaptcha.SESSION_KEY);
//        // 区分大小写
//        if (code.equals(attribute)) {
//            return ResultWrapper.success().build();
//        }
        final boolean verification = HappyCaptcha.verification(httpServletRequest, code, true);
        if (verification) {
            return ResultWrapper.success().build();
        }
        return null;
    }
}
