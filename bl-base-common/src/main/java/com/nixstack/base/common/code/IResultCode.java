package com.nixstack.base.common.code;

/**
 * 10000-- 通用错误代码
 * 24000-- cms错误代码
 */
public interface IResultCode {
    //操作是否成功,true为成功，false操作失败
//    boolean isSuccess();
    int flag();
    //操作代码
    int code();
    //提示信息
    String message();

}
