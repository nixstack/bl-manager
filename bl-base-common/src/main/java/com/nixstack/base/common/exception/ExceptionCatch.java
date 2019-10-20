package com.nixstack.base.common.exception;

import com.google.common.collect.ImmutableMap;
import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.code.IResultCode;
import com.nixstack.base.common.response.ResultRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 捕获异常
 */
@ControllerAdvice // 控制器增强,要添加@ComponentScan(basePackages= "com.nixstack.base")，否则500错语
public class ExceptionCatch {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionCatch.class);

    //定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>,IResultCode> EXCEPTIONS;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>,IResultCode> builder = ImmutableMap.builder();

    // 捕获自定义异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody // 要添加，否则404错误
    public ResultRes handlerCustom(CustomException customException) {
        // 记录日志
        logger.error("自定义异常信息：{}", customException.getMessage());

        IResultCode resultCode = customException.getResultCode();

        return new ResultRes(resultCode);
    }

    // 捕获未知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultRes handler(Exception exception) {
        exception.printStackTrace();
        // 记录日志
        logger.error("异常信息：{}", exception.getMessage());

        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();//EXCEPTIONS构建成功
        }

        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        IResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if(resultCode !=null){
            return new ResultRes(resultCode);
        }else{
            //返回99999异常
            return new ResultRes(ECommonCode.SERVER_ERROR);
        }
    }

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class,ECommonCode.INVALID_PARAM);
    }
}
