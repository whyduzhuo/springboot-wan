package com.duzhuo.wansystem.controller.base;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.duzhuo.common.core.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis.encoding.Base64;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: 万宏远
 * @email:1434495271@qq.com
 * @date: 2020/11/14 11:18
 */

@Slf4j
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {

    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @RequestMapping("/kaptcha")
    @ResponseBody
    public Message kaptcha(){
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);
        String base64 = getBase64(image);
        String key  = "kaptcha:"+UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(key,text,3, TimeUnit.MINUTES);
        Map<String,String> map = new HashMap<>(2);
        map.put("key",key);
        map.put("img",base64);
        return Message.success(map);
    }


    public String getBase64(BufferedImage image){
        String base64 = null;
        try {
            //输出流
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", stream);
            base64 = Base64.encode(stream.toByteArray());
            System.out.println(base64);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return "data:image/jpg;base64,"+base64;
    }
}
