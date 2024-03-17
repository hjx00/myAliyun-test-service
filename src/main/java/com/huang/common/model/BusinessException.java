package com.huang.common.model;

import java.util.Arrays;

/**
 * @Classname BusinessException
 * @CreatedDate 2024/3/12 12:23
 * @Author Huang
 */
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -2903460851208694737L;

    public BusinessException(String msg) {
        super(msg);
        this.errorCode=400;
        this.msg = msg;
    }

    public BusinessException(int errorCode, String msg) {
        super(msg);
        this.errorCode=errorCode;
        this.msg=msg;
    }
    private int errorCode;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
