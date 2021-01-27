package com.eclair.dongbaoums.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @description:
 * @author: wjk
 * @date: 2021/1/24 21:26
 **/
@Data
@ToString
public class UmsMemberRegisterDTO {
    @Size(min = 4, max = 10, message = "username length need 4 to 10")
    private String username;
    private String icon;
    @NotEmpty(message = "password can not empty")
    private String password;
    private String nickName;
    @Email
    private String email;

}
