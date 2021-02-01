package com.eclair.base.result;

import com.eclair.base.code.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: wjk
 * @date: 2021/1/25 20:44
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultWrapper<T> implements Serializable {
    private String code;
    private String msg;
    private T data;
    private String errorMsg;
    public static ResultWrapper.ResultWrapperBuilder success() {
        return ResultWrapper.builder().code(ResultCode.SUCCESS.getCode()).msg(ResultCode.SUCCESS.getMsg());
    }

    public static ResultWrapper.ResultWrapperBuilder fail() {
        return new ResultWrapperBuilder().code(ResultCode.FAIL.getCode()).msg(ResultCode.FAIL.getMsg());
    }
}
