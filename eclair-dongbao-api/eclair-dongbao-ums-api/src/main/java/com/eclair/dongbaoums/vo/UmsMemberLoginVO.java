package com.eclair.dongbaoums.vo;/**
 * @author
 * @date
 **/

import lombok.Data;

/**
 * @Author
 * @Time 2021/1/27 15:28
 * @Description
 **/
@Data
public class UmsMemberLoginVO {
    private Long id;
    private String token;
    private String username;
    private String icon;
    private String nickName;
}
