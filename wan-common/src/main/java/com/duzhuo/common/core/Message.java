package com.duzhuo.common.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;

/**
 * 操作消息提示
 * @author: wanhy
 * @date: 2020/1/1 15:39
 */

public class Message {
    public enum Type{
        /**成功*/
        SUCCESS,
        /**警告*/
        WARN,
        /**错误*/
        ERROR
    }

    private Type type;
    private String msg;
    private Object data;

    public Message(){

    }

    public Message(Type type, String message, Object data) {
        this.type = type;
        this.msg = message;
        this.data = data;
    }

    /**
     *
     * @return 成功消息
     */
    public static Message success(){
        return new Message(Type.SUCCESS,null,null);
    }

    /**
     *
     * @param msg
     * @return 成功消息
     */
    public static Message success(String msg){
        return new Message(Type.SUCCESS,msg,null);
    }

    /**
     * 返回成功消息
     * @param data
     * @return
     */
    public static Message success(Object data){
        return new Message(Type.SUCCESS,null,data);
    }

    /**
     * 返回成功消息
     * @param msg
     * @param data
     * @return
     */
    public static Message success(String msg,Object data){
        return new Message(Type.SUCCESS,msg,data);
    }

    /**
     * 返回警告消息
     * @return
     */
    public static Message warn(){
        return new Message(Type.WARN,null,null);
    }

    /**
     * 返回警告消息
     * @return
     */
    public static Message warn(String msg){
        return new Message(Type.WARN,msg,null);
    }

    /**
     * 返回警告消息
     * @return
     */
    public static Message warn(String msg,Object data){
        return new Message(Type.WARN,msg,data);
    }

    /**
     * 返回警告消息
     * @return
     */
    public static Message error(){
        return new Message(Type.ERROR,null,null);
    }

    /**
     * 返回警告消息
     * @return
     */
    public static Message error(String msg){
        return new Message(Type.ERROR,msg,null);
    }

    /**
     * 返回警告消息
     * @return
     */
    public static Message error(String msg,Object data){
        return new Message(Type.ERROR,msg,data);
    }

    @JsonProperty
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @JsonProperty
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JsonProperty
    @Transient
    public Integer getIcon(){
        if (this.type==Type.SUCCESS){
            return 1;
        }
        if (this.type==Type.WARN){
            return 0;
        }
        if (this.type==Type.ERROR){
            return 2;
        }
        return 3;
    }
}
