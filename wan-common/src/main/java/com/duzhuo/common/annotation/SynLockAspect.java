package com.duzhuo.common.annotation;

import com.duzhuo.common.config.SettingConfig;
import com.duzhuo.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * 
 * @author 万宏远
 */
@Slf4j
@Aspect
@Component
public class SynLockAspect {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SettingConfig settingConfig;

    /**
     * 配置织入点
     */
    @Pointcut(value = "@annotation(com.duzhuo.common.annotation.SynLock)")
    public void logPointCut() {
    }

    @Before(value = "logPointCut()")
    public void doBefor(JoinPoint joinPoint){
        SynLock synLock = getAnnotationLog(joinPoint);
        String lockKey =settingConfig.getName()+":synLock:"+synLock.key();
        Boolean exits = redisTemplate.opsForValue().setIfAbsent(lockKey,"1",30, TimeUnit.SECONDS);
        if (!exits){
            throw new ServiceException("系统正忙，请稍后重试！");
        }
    }


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        SynLock synLock = getAnnotationLog(joinPoint);
        String lockKey =settingConfig.getName()+":synLock:"+synLock.key();
        redisTemplate.delete(lockKey);
    }

    /**
     * 拦截异常操作
     * @param joinPoint 切点
     */
    @AfterThrowing(value = "logPointCut()")
    public void doAfterThrowing(JoinPoint joinPoint) {
        SynLock synLock = getAnnotationLog(joinPoint);
        String lockKey =settingConfig.getName()+":synLock:"+synLock.key();
        redisTemplate.delete(lockKey);
    }


    /**
     * 是否存在注解，如果存在就获取
     */
    private SynLock getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(SynLock.class);
        }
        return null;
    }
}
