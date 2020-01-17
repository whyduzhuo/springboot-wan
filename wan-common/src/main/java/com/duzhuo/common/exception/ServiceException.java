package com.duzhuo.common.exception;

/**
 * 业务异常
 * @author: wanhy
 * @date: 2020/1/1 15:26
 */

public class ServiceException extends RuntimeException{

    protected final String message;

    public ServiceException(String message){
        this.message = message;
    }

    public ServiceException(String message,Throwable e){
        super(message,e);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
