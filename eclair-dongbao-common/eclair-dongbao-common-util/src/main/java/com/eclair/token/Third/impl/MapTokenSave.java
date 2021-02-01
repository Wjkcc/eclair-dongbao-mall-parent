package com.eclair.token.Third.impl;/**
 * @author
 * @date
 **/

import com.eclair.token.Third.AbstractTokenSave;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author
 * @Time 2021/1/27 14:07
 * @Description
 **/
@Component
public class MapTokenSave implements AbstractTokenSave {
    public static final String strategyName = "map";
    public static Map<String,Long> map = new ConcurrentHashMap<>(64);
    public static Map<String,String> mapUser = new ConcurrentHashMap<>(64);
    // 对象锁
    public static Object lock = new Object();

    @Override
    public String getStrategyName(String name) {
        return strategyName;
    }

    /**
     * 验证token是否过期
     * @param key
     * @return
     */
    @Override
    public Boolean checkToken(String key) {
        if (map.get(key) != null) {
            Long expire = map.get(key);
            Long now = System.currentTimeMillis();
            // 未过期
            if (expire > now) {
                // 比较剩余时间
                long value = expire - now;
                // 剩余时间小于60秒就延长时间
                if (value < 60 * 1000) {
                    addTime(key);
                }
                return true;
            }
            // 过期删除，返回false
            deleteToken(key);
        }
        return false;
    }

    public void deleteToken(String username) {
        String s = mapUser.get(username);
        deleteToken(s,username);
    }

    @Override
    public void addTime(String key) {
        map.replace(key,System.currentTimeMillis() + 60 * 30 * 1000);
    }


    public void addKey(String key, String username) {
        if(!checkToken(key)) {
            // 第一次可能会有过期的key存在情况
            map.put(key,System.currentTimeMillis() + 60 * 30 * 1000);
            mapUser.put(username,key);
        }else{
            addKey(key,username);
        }
    }
    public void deleteToken(String key,String username) {
        deleteToken(key);
        mapUser.remove(username);
    }

}
