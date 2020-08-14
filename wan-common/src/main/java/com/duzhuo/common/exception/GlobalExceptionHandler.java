package com.duzhuo.common.exception;

import com.duzhuo.common.core.Message;
import com.duzhuo.common.utils.ServletUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR_VIEW = "/common/error/error";

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
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
        log.error(e.getMessage(), e);
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
    public Message notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return Message.error("运行时异常:" + e.getMessage());
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
    public Message maxUploadSizeExceededException(MaxUploadSizeExceededException e){
        log.error(e.getMessage(), e);
        String message = "超过文件限制大小:"+maxSize;
        return Message.error(message);
    }
}
