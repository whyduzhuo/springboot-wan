package com.duzhuo.common.exception;

import com.duzhuo.common.core.Message;
import com.duzhuo.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * controller全局异常处理
 * @author: 万宏远
 * @date: 2020/1/1 15:57
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR_VIEW = "/common/error/error";

    @Value("${wan.profile.max-size}")
    private String maxSize;

    /**
     * 权限校验异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e){
        String message = e.getMessage();
        String msg = "您没有权限：权限编号："+message.substring(message.lastIndexOf('['),message.indexOf(']')+1);
        log.warn(msg, e);
        return retError(request,e,msg);
    }


    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleException(HttpServletRequest request,HttpRequestMethodNotSupportedException e) {
        String msg = getErrorMsg(e);
        log.error(msg, e);
        return retError(request,e,msg);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Object notFount(HttpServletRequest request,RuntimeException e) {
        String msg = getErrorMsg(e);
        log.error(msg, e);
        return retError(request,e,msg);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request,Exception e) {
        String msg = getErrorMsg(e);
        log.error(msg, e);
        return retError(request,e,msg);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Object businessException(HttpServletRequest request, ServiceException e) {
        String msg = e.getMessage();
        log.info(msg, e);
        return retError(request,e,msg);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Object validatedBindException(HttpServletRequest request,BindException e) {
        String msg = e.getMessage();
        log.info(msg, e);
        return retError(request,e,msg);
    }

    /**
     * 文件上传过大异常
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object maxUploadSizeExceededException(HttpServletRequest request,MaxUploadSizeExceededException e){
        String msg = "超过文件限制大小:"+maxSize;
        log.warn(msg, e);
        return retError(request,e,msg);
    }

    /**
     * 登录失效异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(UnknownSessionException.class)
    public Object unknownSessionExceptionHandle(HttpServletRequest request,UnknownSessionException e){
        String msg = "登录失效，请重新登录"+e.getMessage();
        log.info(msg, e);
        return retError(request,e,msg);
    }

    private Object retError(HttpServletRequest request,Exception e,String msg){
        if (ServletUtils.isAjaxRequest(request)) {
            return Message.error(msg);
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("content",msg);
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }

    private String getErrorMsg(Exception e){
        return "系统错误【"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"】"+e.getMessage();
    }
}
