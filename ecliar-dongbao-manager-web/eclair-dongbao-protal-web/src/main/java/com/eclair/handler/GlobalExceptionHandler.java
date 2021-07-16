package com.eclair.handler;/**
 * @author
 * @date
 **/

import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.eclair.base.Exception.BusinessException;
import com.eclair.base.result.ResultWrapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常捕获
 * @Author
 * @Time 2021/1/26 15:15
 * @Description
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BusinessException.class,})
    public ResultWrapper otherException(BusinessException e) {
        String message = e.getMessage();
        return ResultWrapper.fail().data(message).build();
    }

    @ExceptionHandler({Exception.class,})
    public ResultWrapper allException(Exception e) {
        e.printStackTrace();
        if (e instanceof KaptchaIncorrectException) {
            return ResultWrapper.fail().data("code is incorrect").build();
        }
        if (e instanceof KaptchaNotFoundException) {
            return ResultWrapper.fail().data("code is expire").build();
        }
        String message = e.getMessage();
        return ResultWrapper.fail().data(message).build();
    }
}
