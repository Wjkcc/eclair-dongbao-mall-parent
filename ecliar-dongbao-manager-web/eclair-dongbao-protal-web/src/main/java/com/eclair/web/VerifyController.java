package com.eclair.web;/**
 * @author
 * @date
 **/

import com.eclair.base.result.ResultWrapper;
import com.eclair.code.AbstractCode;
import com.eclair.code.impl.GraphCode;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * @Author
 * @Time 2021/2/4 10:16
 * @Description
 **/
@RestController
@RequestMapping("/code")
public class VerifyController {
    @GetMapping("/generate")
    public void generate(HttpServletResponse response,HttpServletRequest request) {
        GraphCode graphCode = GraphCode.getInstance();
        String code = graphCode.getCode();
        request.getSession().setAttribute("code",code);
        request.setAttribute("a","a");
        InputStream image = graphCode.getImage();
        byte[] bytes = new byte[1024];
        response.setContentType("image/jpeg");
        try(ServletOutputStream outputStream = response.getOutputStream()) {
            while (image.read(bytes) != -1) {
                outputStream.write(bytes);
            }
        }
        catch (Exception e) {
            System.out.println("error");
        }

        System.out.println("generate");
    }
    @GetMapping("/verifyCode")
    public ResultWrapper verifyCode(String code, HttpServletRequest request) {
        Assert.notNull(code, "code is null");
        String code1 = (String)request.getSession().getAttribute("code");
         HttpSession session = request.getSession();
        final Object a = request.getAttribute("a");
        if (code1 != null) {
            if (code.equals(code1)) {
                return ResultWrapper.success().msg("verify success").build();
            }
        }
        return ResultWrapper.fail().msg("verify fail").build();
    }
}
