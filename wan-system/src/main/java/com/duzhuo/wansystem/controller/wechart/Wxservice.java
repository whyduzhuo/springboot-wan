package com.duzhuo.wansystem.controller.wechart;

import com.duzhuo.common.config.Global;
import com.duzhuo.common.config.OauthConfig;
import com.duzhuo.common.utils.HttpUtils;
import com.duzhuo.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/2/24 9:46
 */
@Slf4j
@Service
public class Wxservice {
    @Resource
    private OauthConfig oauthConfig;
    @Resource
    private RedisUtils redisUtils;
    /**
     * 获取微信token
     * @return
     */
    public String getAccessToken() throws Exception {
        String accessToke = redisUtils.get(Global.WX_TOKEN_REDIS_KEY,String.class);
        if (accessToke!=null){
            return accessToke;
        }
        //拼装获取access_token的url请求
        String tokenUrl = oauthConfig.getWxGetTokenUrl().replace("ID", oauthConfig.getWechartAppid()).replace("SECRET",oauthConfig.getWechartSecret());
        //获取访问用户身份的access_token
        Map map =  HttpUtils.getForObject(tokenUrl, Map.class);
        String accessToken = map.get("access_token").toString();
        Long expiresIn = Long.valueOf(map.get("expires_in").toString());
        log.info("【获取access_token】,access_token={}",map.toString());
        redisUtils.set(Global.WX_TOKEN_REDIS_KEY,accessToken,expiresIn-20);
        return accessToken;
    }
}
