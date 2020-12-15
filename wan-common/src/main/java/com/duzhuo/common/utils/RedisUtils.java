package com.duzhuo.common.utils;

import com.alibaba.fastjson.JSON;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.enums.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/25 17:57
 */

@Component
@Slf4j
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object>   redisTemplate;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    /**  默认过期时长，单位：秒 */
    public final static long  DEFAULT_EXPIRE = 60 * 60 * 24;

    /**  不设置过期时长 */
    public final static long  NOT_EXPIRE = -1;

    public static String buildEntityKey(BaseEntity baseEntity, OperateType operateType){
        return baseEntity.getClass().getName().replace(".",":")+":"+operateType.toString()+"@"+baseEntity.getId();
    }

    public static String buildEntityKey(Class<?> tClass,Long id, OperateType operateType){
        return tClass.getName().replace(".",":")+":"+operateType.toString()+"@"+id;
    }

    /**
     * 插入缓存默认时间
     * @param key 键
     * @param value 值
     * @author zmr
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 插入缓存
     * @param key 键
     * @param value 值
     * @param expire 过期时间(s)
     * @author zmr
     */
    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value,expire,TimeUnit.SECONDS);
    }

    /**
     * 获取缓存
     * @param key 键
     * @return
     * @author zmr
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 返回指定类型结果
     * @param key 键
     * @param clazz 类型class
     * @return
     * @author zmr
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return  fromJson(value, clazz);
    }

    /**
     * 删除缓存
     * @param key 键
     * @author zmr
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    public Boolean exits(String key){
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
                || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return com.alibaba.fastjson.JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 查询某前缀开头的key的值
     * @param prefix key 前缀
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String prefix, Class<T> clazz) {
        Set<String> keys = redisTemplate.keys(prefix);
        List<T> list = new ArrayList<>();
        keys.forEach(k->list.add(get(k,clazz)));
        return list;
    }
}