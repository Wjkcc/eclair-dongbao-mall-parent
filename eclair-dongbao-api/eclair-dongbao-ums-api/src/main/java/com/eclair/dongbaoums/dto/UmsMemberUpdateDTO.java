package com.eclair.dongbaoums.dto;/**
 * @author
 * @date
 **/

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @Author
 * @Time 2021/1/27 15:31
 * @Description
 **/
@Data
public class UmsMemberUpdateDTO {
    @Length(max = 10, min = 4, message = "the length of username need 4 to 10")
    private String username;
    @NotEmpty
    private Long id;
    private String icon;
    private String nickName;
    @Email
    private String email;
}
