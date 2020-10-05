package com.duzhuo.common.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量操作结果类的封装
 * @author: wanhy
 * @date: 2020/3/13 16:47
 */

public class ApproveResult {
    /**
     *操作，检查、导入、审批等
     */
    private String operate;
    /**
     * 成功数量
     */
    private int success = 0;
    /**
     * 失败数量
     */
    private int failed = 0;
    /**
     * 失败的原因集合
     */
    private List<String> failedResultList = new ArrayList<>();

    public ApproveResult(String operate){
        this.operate = operate;
    }

    public void addSuccess(){
        this.success+=1;
    }

    public void addFailed(String resean){
        this.failed+=1;
        this.failedResultList.add(resean);
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public List<String> getFailedResultList() {
        return failedResultList;
    }

    public void setFailedResultList(List<String> failedResultList) {
        this.failedResultList = failedResultList;
    }

    public String getHtml(){
        if (success==0 && failed==0){
            return "<h3>无成功/失败记录</h3>";
        }
        if (failed==0){
            return "<h3>"+success+"条全部成功！</h3>";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h3>成功：<span style = 'color:#46c37b'>" + success + "</span>条，失败：<span style = 'color:#ff0000'>" +
                failed + "</span>条。</h3>");
        for (String result:failedResultList) {
            stringBuilder.append("<span style = 'color:#ff0000'>"+result+"</span></br>");
        }
        return stringBuilder.toString();
    }

    public String getCheckHtml(){

        if (success==0 && failed==0){
            return "<h4>无数据</h4>";
        }
        if (failed==0){
            return "<h4 style = 'color:#46c37b'>检查无异常</h4>";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h4 style='color:#333'>无异常：<span style = 'color:#46c37b'>" + success + "</span>条，异常：<span style = 'color:#ff0000'>" +
                failed + "</span>条。</h4>");
        for (String result:failedResultList) {
            stringBuilder.append("<span style = 'color:#ff0000'>"+result+"</span></br>");
        }
        return stringBuilder.toString();
    }
}
