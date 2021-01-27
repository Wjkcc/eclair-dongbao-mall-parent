package com.eclair.handler;/**
 * @author
 * @date
 **/

import com.eclair.base.result.ResultWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 *
 * 参数校验异常统一捕获
 * @Author
 * @Time 2021/1/26 15:07
 * @Description
 **/
@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuffer sb = new StringBuffer();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        allErrors.forEach(e -> {
            sb.append(" ").append(e.getDefaultMessage());
        });
        return new ResponseEntity(ResultWrapper.fail().data(sb.toString()).build(), HttpStatus.OK);
    }
}
