package com.eclair.token;/**
 * @author
 * @date
 **/

import com.eclair.dto.UmsTokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt生成
 * @Author
 * @Time 2021/1/27 13:18
 * @Description
 **/
public class JWTUtils {
    public static final String SECRET = "hello";
    // 一个小时
    public static final Long ONE_HOUR = 60 * 60 * 1000L;
    public static String generateToken(String subject, String ip, Boolean exp) {
        Map<String,Object> map = new HashMap<>();
        map.put("ip",ip);
        map.put("username",subject);
        JwtBuilder haha = Jwts.builder()
                .setClaims(map)
                .setSubject("haha")
                .signWith(SignatureAlgorithm.HS256, SECRET);
        if (exp) {
            Date d = new Date(System.currentTimeMillis() + ONE_HOUR);
            haha.setExpiration(d);
        }
        return haha.compact();
    }
    public static UmsTokenDTO decodeToken(String token) {
        Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String ip = (String)body.get("ip");
        String username = (String)body.get("username");
        UmsTokenDTO umsTokenDTO = new UmsTokenDTO();
        umsTokenDTO.setIp(ip);
        umsTokenDTO.setUsername(username);
        System.out.println(body.getSubject());
        return umsTokenDTO;
    }

    public static void main(String[] args) {
        String s = generateToken("123", "234",false);
        System.out.println(s);
        decodeToken(s);
    }
}
