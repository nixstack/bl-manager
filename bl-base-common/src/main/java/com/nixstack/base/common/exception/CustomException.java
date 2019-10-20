package com.nixstack.base.common.exception;

import com.nixstack.base.common.code.IResultCode;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {
    //错误代码
    IResultCode resultCode;

    public CustomException(IResultCode resultCode){
        this.resultCode = resultCode;
    }
    public IResultCode getResultCode(){
        return resultCode;
    }

    public String getMessage() {
        return resultCode.message();
    }
}
