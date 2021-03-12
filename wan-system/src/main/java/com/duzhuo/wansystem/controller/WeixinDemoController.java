package com.duzhuo.wansystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/12 10:51
 */
@Controller
@RequestMapping("/weixin")
public class WeixinDemoController {


    @GetMapping("/pay")
    public String pay(Model model){
        return "/weixin/pay";
    }
}
