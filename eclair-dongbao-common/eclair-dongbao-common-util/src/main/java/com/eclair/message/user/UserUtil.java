package com.eclair.message.user;/**
 * @author
 * @date
 **/

import org.springframework.stereotype.Component;

/**
 * @Author
 * @Time 2021/2/1 16:44
 * @Description
 **/
public class UserUtil {
    private static ThreadLocal<String> userMessage = new ThreadLocal<>();
    public static String getUser() {
        return userMessage.get();
    }
    public static void setUser(String username) {
        userMessage.set(username);
    }
    public static void remove() {
        userMessage.remove();
    }
}
