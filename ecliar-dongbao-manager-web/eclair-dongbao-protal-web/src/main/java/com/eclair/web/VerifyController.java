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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
        graphCode.doWork();
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

    /**
     * 生成验证码图片，转化成base64形式返回
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/generateBase64")
    @ResponseBody
    public Map<String,String> generateToBase64(HttpServletResponse response, HttpServletRequest request) throws IOException {
        GraphCode graphCode = GraphCode.getInstance();
        graphCode.doWork();
        String code = graphCode.getCode();
        request.getSession().setAttribute("code",code);
        InputStream image = graphCode.getImage();

        response.setContentType("image/jpeg");
        // 将图片流转化成base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int r = 0;
        while (image.read(bytes,0,1024) > 0) {
            byteArrayOutputStream.write(bytes,0,1024);
        }
        final byte[] bytes1 = byteArrayOutputStream.toByteArray();
        final String s = Base64.getEncoder().encodeToString(bytes1);
        Map<String,String> map = new HashMap<>(2);
        map.put("pic",s);

        System.out.println("generate:  "+code);
        return map;
    }

}
