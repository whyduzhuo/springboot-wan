package com.duzhuo.common.exception;

import com.duzhuo.common.core.Message;
import com.duzhuo.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
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

/**
 * controller全局异常处理
 * @author: wanhy
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
        log.error(message, e);
        String per = message.substring(message.lastIndexOf('['),message.indexOf(']')+1);
        if (ServletUtils.isAjaxRequest(request)) {
            return Message.error("您没有权限：权限编号："+per);
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("content", "您没有权限：权限编号："+per);
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }


    /**
     * 请求方式不支持
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public Message handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Message.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Object notFount(HttpServletRequest request,RuntimeException e) {
        log.error("运行时异常:", e);
        if (ServletUtils.isAjaxRequest(request)) {
            return Message.error("运行时异常:"+e.getMessage());
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("content", "运行时异常:"+e.getMessage());
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request,Exception e) {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request)) {
            return Message.error("服务器错误，请联系管理员。"+e.getMessage());
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("content","服务器错误，请联系管理员。"+e.getMessage());
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Object businessException(HttpServletRequest request, ServiceException e) {
        log.info(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request)) {
            return Message.error(e.getMessage());
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("content", e.getMessage());
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Message validatedBindException(BindException e) {
        log.info(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Message.error(message);
    }

    /**
     * 文件上传过大异常
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object maxUploadSizeExceededException(HttpServletRequest request,MaxUploadSizeExceededException e){
        log.error(e.getMessage(), e);
        String message = "超过文件限制大小:"+maxSize;
        if (ServletUtils.isAjaxRequest(request)) {
            return Message.error(message);
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("content",message);
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }

    /**
     * 登录失效异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(UnknownSessionException.class)
    public Object unknownSessionExceptionHandle(HttpServletRequest request,UnknownSessionException e){
        log.info(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request)) {
            return Message.error("登录失效，请重新登录");
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("content","登录失效，请重新登录");
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }
}
