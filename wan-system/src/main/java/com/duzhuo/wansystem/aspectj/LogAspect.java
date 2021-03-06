package com.duzhuo.wansystem.aspectj;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.config.SettingConfig;
import com.duzhuo.common.core.EmailSendService;
import com.duzhuo.common.enums.YesOrNo;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.thread.ThreadPoolService;
import com.duzhuo.common.utils.JSON;
import com.duzhuo.common.utils.ServletUtils;
import com.duzhuo.common.utils.StringUtils;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.SysOperLog;
import com.duzhuo.wansystem.service.base.SysOperLogService;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 * 
 * @author 万宏远
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private ThreadPoolService threadPoolService;
    @Resource
    private SysOperLogService sysOperLogService;
    @Resource
    private EmailSendService emailSendService;
    @Resource
    private SettingConfig settingConfig;

    /**
     * 配置织入点
     */
    @Pointcut(value = "@annotation(com.duzhuo.common.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            Admin admin = ShiroUtils.getCurrAdmin();

            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(YesOrNo.是);
            // 请求的地址
            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (admin != null) {
                operLog.setAdmin(admin);
            }
            String stackTrace="";
            if (e != null) {
                stackTrace=ExceptionUtils.getStackTrace(e);
                operLog.setErrorMsg(e.getMessage());
                if (!(e instanceof ServiceException)){
                    operLog.setHaveException(YesOrNo.是);
                    operLog.setErrorMsg(StringUtils.substring(stackTrace, 0, 3000));
                }
                operLog.setStatus(YesOrNo.否);

            }
            // 设置请求方式
            operLog.setMethod(ServletUtils.getRequest().getMethod());
            // 保存响应结果,get请求不保存
            if (!"GET".equalsIgnoreCase(operLog.getMethod())){
                operLog.setJsonResult(JSON.marshal(jsonResult));
            }else {
                operLog.setJsonResult("GET 请求不记录响应结果");
            }
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 异步保存数据库
            threadPoolService.execute(() -> {
                sysOperLogService.addData(operLog);
            });
            //
            if (operLog.getHaveException()==YesOrNo.是 && settingConfig.getErrMsgEmailReminder()){
                threadPoolService.execute(()->{
                    emailSendService.simpleMailSend(settingConfig.getEmail(),settingConfig.getName()+settingConfig.getErrMsgEmailTitle(),operLog.getErrorMsg());
                });
            }

        }
        catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, SysOperLog operLog) throws Exception {
        // 设置action动作
        operLog.setOperateType(log.operateType());
        // 设置标题
        operLog.setTitle(log.title());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(SysOperLog operLog) throws Exception {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        Map map = request.getParameterMap();
        String params = JSON.marshal(map);
        operLog.setOperParm(StringUtils.substring(params, 0, 2000));
        operLog.setOs(os);
        operLog.setBrowser(browser);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
