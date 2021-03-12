package com.duzhuo.wansystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.thread.ThreadPoolService;
import com.duzhuo.common.utils.IO.ExcelUtils;
import com.duzhuo.wansystem.entity.Jubao;
import com.duzhuo.wansystem.service.JubaoService;
import com.duzhuo.wansystem.test.Page;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/1/7 10:18
 */
@Slf4j
@Controller
public class IndexController {

    @Resource
    private ThreadPoolService threadPoolService;
    @Resource
    private JubaoService jubaoService;

    @RequestMapping({"/","/index"})
    public String index(){
        return "redirect:/base/login";
    }

    @GetMapping("/system/main")
    public String main(Model model){

        return "/base/main";
    }

    /**
     * 微信js接口安全域名
     * @return
     */
    @GetMapping("/MP_verify_ulwYaaCP2peBHClv.txt")
    @ResponseBody
    public String ad(){
        return "ulwYaaCP2peBHClv";
    }


//    @GetMapping("/danao")
//    @ResponseBody
    public Message danao(){
        for (int i =0;i<100;i++){
            int finalI = i+1;
            threadPoolService.execute(()->haha(finalI));
        }
        return Message.success("");
    }

    public void haha(int start){
        int i = start;
        while (true){
            List<Jubao>  dataList = getData(i);
            threadPoolService.execute(()-> jubaoService.addData(dataList));
            i=i+100;
        }
    }

    public List<Jubao> getData(int pageNumber) {
        String url = "https://citybrain.yunshangnc.com/cbgw/ccno/zhsq_event/service/event/fetchEventData4Jsonp.json?listType=5&userId=99&orgId=99&orgCode=3601&jsonpcallback=jQuery09494236251050014_1615529521823";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        Map<String,String> params = new HashMap<>();
        params.put("eventType","all");
        params.put("createTimeStart","2021-02-10 14:12:06");
        params.put("createTimeEnd","2021-03-12 14:12:06");
        params.put("searchEventLabel","true");
        params.put("labelModel","002");
        params.put("patrolType","1,2");
        params.put("page",pageNumber+"");
        params.put("rows","10");
        params.put("eventSeq","1");
        params.put("isGitAttr","true");
        params.put("infoOrgCode","3601");
        params.put("eventLabelIds","61,62,63,64,65,66,67,68,69,70");

        Headers headers = Headers.of(headerMap);
        params.keySet().stream().forEach(key -> formBody.add(key, params.get(key)));
        Request request = new Request.Builder().url(url).headers(headers)
                .post(formBody.build()).build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string().replace("jQuery09494236251050014_1615529521823(","").replace(")","");
            JSONObject jsonObject= JSONObject.parseObject(result);
            Page page=jsonObject.toJavaObject(Page.class);
            return page.getList();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return new ArrayList<>();
    }
}
