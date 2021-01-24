package com.eclair.dongbaoums.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @description:
 * @author: wjk
 * @date: 2021/1/24 21:26
 **/
@Data
@ToString
public class UmsMemberRegisterDTO {
    private String userName;
    private String icon;
    private String password;
    private String nickName;

}
