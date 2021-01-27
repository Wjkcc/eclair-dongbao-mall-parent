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
            Long aLong = map.get(key);
            Long l = System.currentTimeMillis();
            if (l < aLong) {
                Long value = l - aLong;
                if (value < 60 * 1000) {
                    addTime(key);
                }
                return true;
            }
            deleteToken(key);
        }
        return false;
    }

    @Override
    public void deleteToken(String key) {
        map.remove(key);
    }

    @Override
    public void addTime(String key) {
        map.replace(key,System.currentTimeMillis() + 60 * 30 * 1000);
    }

    @Override
    public void addKey(String key) {
        if(!checkToken(key)) {
            map.put(key,System.currentTimeMillis() + 60 * 30 * 1000);
        }
    }
}
