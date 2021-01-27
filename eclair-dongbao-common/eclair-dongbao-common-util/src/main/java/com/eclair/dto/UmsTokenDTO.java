package com.eclair.dto;/**
 * @author
 * @date
 **/

import lombok.Data;

/**
 * @Author
 * @Time 2021/1/27 13:50
 * @Description
 **/
@Data
public class UmsTokenDTO {
    private String username;
    private String ip;
    public boolean isEmpty() {
        return this.getIp() == null && this.getUsername() == null;
    }
}
