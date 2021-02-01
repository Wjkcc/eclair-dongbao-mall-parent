package com.eclair.base.Exception;

/**
 * @author
 * @date
 **/

public class BusinessException extends RuntimeException{
    String msg;
    public BusinessException(String msg) {
        super(msg);
    }
}
