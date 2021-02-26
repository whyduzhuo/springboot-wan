package com.duzhuo.wansystem.controller.wechart;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.config.OauthConfig;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.HttpUtils;
import com.duzhuo.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/1/8 16:18
 */
@Controller
@RequestMapping(value = "/api/wechat")
@Slf4j
public class ApiWeChatOauthController{
    @Resource
    private OauthConfig oauthConfig;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private Wxservice wxservice;


    @Log(title = "微信授权页面",operateType = OperateType.OTHER)
    @GetMapping("/auth")
    public String auth() throws IOException {
        log.info("进入到微信页面自定义方法地址...");
        //拼接微信网页授权地址
        String redirectURL = String.format(oauthConfig.getWxConnectOath2AuthorizeUrl(), oauthConfig.getWechartAppid(),
                URLEncoder.encode(oauthConfig.getRequestUrl(), StandardCharsets.UTF_8.toString()),
                oauthConfig.getWxSnsapiBase(), StringUtils.trimToEmpty(oauthConfig.getWxState()));
        log.info("【微信网页授权】获取redirectURL,redirectURL={}", redirectURL);
        return "redirect:" + redirectURL;
    }

    @ResponseBody
    @RequestMapping("/haha")
    public String haha(String signature,String timestamp,String nonce,String echostr){
        log.error(signature,timestamp,nonce,echostr);
        return echostr;
    }

    @Log(title = "根据code获取userinfo",operateType = OperateType.OTHER)
    @GetMapping("/userinfo")
    @ResponseBody
    public Message userinfo(String code) throws Exception {
        log.info("进入到回调地址...");
        log.info("【回调地址】获取的code值为{}",code);
        if(!"authdeny".equals(code)){
            try {
                //根据code获取用户Access_token和openid,注：access_token 会有7200的失效时间，建议获取到用户信息之后，存在本地数据库，根据openid查询用户信息，防止频繁获取用户信息
                String url = oauthConfig.getUserToken().replace("APPID",oauthConfig.getWechartAppid()).replace("SECRET",oauthConfig.getWechartSecret()).replace("CODE",code);
                Map userAccessToken = HttpUtils.getForObject(url, Map.class);
                String openid = userAccessToken.get("openid").toString();
                String access_token =  userAccessToken.get("access_token").toString();
                //根据Access_token和openid获取用户的基本信息
                String userinfoUrl = oauthConfig.getUserInfo().replace("ACCESS_TOKEN",access_token).replace("OPENID",openid);
                Map userInfo = HttpUtils.getForObject(userinfoUrl,Map.class);
                log.info("【访问用户身份基本信息】,obj={}",userInfo);
                return Message.success(userInfo);
            }catch (Exception e){
                throw new ServiceException("code失效");
            }
        }
        return Message.error("用户信息获取失败");
    }

}
