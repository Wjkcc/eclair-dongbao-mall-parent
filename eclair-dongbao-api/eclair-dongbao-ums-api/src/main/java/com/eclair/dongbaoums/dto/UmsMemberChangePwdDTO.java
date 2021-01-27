package com.eclair.dongbaoums.dto;/**
 * @author
 * @date
 **/

import lombok.Data;

/**
 * @Author
 * @Time 2021/1/27 15:31
 * @Description
 **/
@Data
public class UmsMemberChangePwdDTO {
    private Long id;
    private String username;
    private String oldPwd;
    private String newPwd;
}
