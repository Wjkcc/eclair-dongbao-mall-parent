package com.eclair.token.Third;

/**
 * @author
 * @date
 **/

public interface AbstractTokenSave {
    String getStrategyName(String name);
    Boolean checkToken(String key);
    void addTime(String key);
    void addKey(String key, String username);
    void deleteToken(String key,String username);
    void deleteToken(String username);
}
