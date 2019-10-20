package com.nixstack.base.common.exception;

import com.nixstack.base.common.code.IResultCode;

/**
 * 抛出异常
 */
public class ExceptionCast {
    public static void cast(IResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
