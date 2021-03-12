package com.woniuxy.exception;


import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnknownAccountException.class)
    public Result handlerNullPointException(){
        return new Result(false, StatusCode.UNKNOWNACCOUNT,"账号错误");
    }
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result handlerArithmeticException(){
        return new Result(false, StatusCode.INCORRECTCREDENTIALS,"密码错误");
    }
    @ExceptionHandler(Exception.class)
    public Result handlerException(){
        return new Result(false, StatusCode.ERROR,"未知异常");
    }
}
