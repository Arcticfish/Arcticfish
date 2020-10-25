package com.itheima.health.exception;

/**
 * 自定义异常：
 * 1.区分系统与业务异常
 * 2.给用户有好的提示信息
 * 3.终止已知不符合业务逻辑代码的执行
 */
public class MyException extends RuntimeException{
    public MyException(String message){
        super(message);

    }
}
