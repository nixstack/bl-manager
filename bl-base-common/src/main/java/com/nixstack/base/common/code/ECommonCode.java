package com.nixstack.base.common.code;

public enum ECommonCode implements IResultCode {
    INVALID_PARAM(1,10003,"非法参数！"),
    SUCCESS(0,10000,"操作成功！"),
    FAIL(1,11111,"操作失败！"),
    UNAUTHENTICATED(1,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(1,10002,"权限不足，无权操作！"),
    SERVER_ERROR(1,99999,"抱歉，系统繁忙，请稍后重试！"),
    RECORD_EXISTED(1,10005,"记录已存在！"),
    ;

    //操作是否成功
//    boolean isSuccess;
    int flag;
    //操作代码
    int code;
    //提示信息
    String message;

    private ECommonCode(int flag,int code, String message){
//        this.isSuccess = isSuccess;
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public int flag() {
        return flag;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
