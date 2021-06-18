package com.eclair.web;/**
 * @author
 * @date
 **/

import com.eclair.base.result.ResultWrapper;
import com.eclair.code.impl.GraphCode;
import com.eclair.code.impl.JcaptchaCode;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Time 2021/4/19 14:31
 * @Description
 **/
@RestController
@RequestMapping("/jcaptcha")
public class JcaptchaController {
    @GetMapping("/generate")
    public void generate(HttpServletResponse response, HttpServletRequest request) {
        try {
            System.out.println("begin");
        final String id = request.getSession().getId();
        // 把sessionId放入第三方验证码容器中，确保唯一性
        BufferedImage imageChallengeForID = JcaptchaCode.getService().getImageChallengeForID(id);
        // 图片缓存编码成jpeg
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(byteArrayOutputStream);

        jpegEncoder.encode(imageChallengeForID);
        // 写到返回输出流
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control","no-store");
        final ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @GetMapping("/verifyCode")
    public ResultWrapper verifyCode(String code, HttpServletRequest request) {
     return null;
    }

}
