package com.duzhuo.wansystem.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/5/11 22:20
 */
@Slf4j
public class DateUtilsTest {
    public static void main(String[] args) throws ParseException, IOException {
        String url = "https://citybrain.yunshangnc.com/cbgw/ccno/zhsq_event/service/event/fetchEventData4Jsonp.json?listType=5&userId=99&orgId=99&orgCode=3601&jsonpcallback=jQuery09494236251050014_1615529521823";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        Map<String,String> params = new HashMap<>();
        params.put("eventType","all");
        params.put("createTimeStart","2000-02-10 14:12:06");
        params.put("createTimeEnd","2021-03-12 14:12:06");
        params.put("searchEventLabel","true");
        params.put("labelModel","002");
        params.put("patrolType","1,2");
        params.put("page","1");
        params.put("rows","10");
        params.put("eventSeq","1");
        params.put("isGitAttr","true");
        params.put("infoOrgCode","3601");
        params.put("eventLabelIds","61,62,63,64,65,66,67,68,69,70");


        Headers headers = Headers.of(headerMap);
        params.keySet().stream().forEach(key -> {
            formBody.add(key, params.get(key));
        });
        Request request = new Request.Builder().url(url).headers(headers)
                .post(formBody.build()).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string().replace("jQuery09494236251050014_1615529521823(","").replace(")","");
        JSONObject jsonObject= JSONObject.parseObject(result);
        Page page=jsonObject.toJavaObject(Page.class);
        System.err.println("");
    }

    public enum Type{
        哈哈,
        呵呵
    }
}
