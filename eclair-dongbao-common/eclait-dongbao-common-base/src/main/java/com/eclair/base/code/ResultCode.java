package com.eclair.base.code;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public enum ResultCode {

    SUCCESS("200","SUCCESS"),
    FAIL("500","FAIL");
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String msg;

}
