package com.nixstack.base.common.response;

import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.code.IResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据响应，统一格式
 * 数据响应，统一格式
 * @param <T>
 */
@Data
@NoArgsConstructor
public class ResultRes<T> implements IRes {
    int flag = FLAG;
    int code = CODE;
    String message;
    T data;

    public ResultRes(IResultCode resultCode) {
        this.flag = resultCode.flag();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public ResultRes(IResultCode resultCode, T data) {
        this.flag = resultCode.flag();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public ResultRes(T data) {
//        this();
        ResultRes.SUCCESS();
        this.data = data;
    }


    public  static ResultRes SUCCESS() {
        return new ResultRes(ECommonCode.SUCCESS);
    }

    public static ResultRes FAIL() {
        return new ResultRes(ECommonCode.FAIL);
    }

    /**
     * java声明泛型方法的固定格式，在方法的返回值声明之前的位置，<T>定义该方法所拥有的泛型标识符，个数可以是多个
     * 如：
     * public static<T1,T2,T3> Response<T1> test(T2 t2,T3 t3){
     *
     * }
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultRes<T> createSuccess(T data) {
        return new ResultRes(data);
    }
}
