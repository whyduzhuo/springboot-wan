package com.duzhuo.wansystem.controller.daily;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/1/9 14:38
 */
@CrossOrigin
@Controller
public class DemoController {

    /**
     * https://open.weixin.qq.com/connect/oauth2/authorize?
     * appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&connect_redirect=1#wechat_redirect"
     * @return
     */
    @GetMapping("/connect/oauth2/authorize")
    public String authorize(String appid,String redirect_uri,String response_type,String scope,String state,String connect_redirect){

        return "redirect:"+redirect_uri+"?"+response_type+"=8008208820";
    }

    /**
     * https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET
     * @param corpid APPID
     * @param corpsecret 微信密钥
     * @return access_token
     */
    @GetMapping("/cgi-bin/gettoken")
    @ResponseBody
    public Map gettoken(String corpid, String corpsecret){
        Map map = new HashMap(1);
        map.put("access_token","access_token8888");
        map.put("expires_in", 7200);
        return map;
    }

    /**
     * https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE
     * @return userid
     */
    @GetMapping("/cgi-bin/user/getuserinfo")
    @ResponseBody
    public Map getuserinfo(String access_token,String code){
        Map map = new HashMap(7);
        map.put("errcode","0");
        map.put("errmsg","");
        map.put("UserId","wanhy");
        map.put("DeviceId","dwadafa");
        map.put("user_ticket","dwadwada");
        map.put("expires_in","200");
        map.put("usertype","0");
        return map;
    }

    /**
     * https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID
     * @return userinfo
     */
    @GetMapping("/cgi-bin/user/get")
    @ResponseBody
    public Map get(){
        Map map = new HashMap(10);
        map.put("errcode","0");
        map.put("errmsg","");
        map.put("userid","80");
        map.put("name","万宏远");
        map.put("mobile","15079185135");
        map.put("hide_mobile","0");
        map.put("department","5,88");
        map.put("order","0");
        map.put("position","程序员");
        map.put("positions","程序员,工程师");
        map.put("gender","0");
        map.put("email","1434495271@qq.com");
        map.put("status","1");
        map.put("enable","1");
        return map;
    }
}
