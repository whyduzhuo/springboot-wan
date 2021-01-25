package com.duzhuo.wansystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/1/7 10:18
 */
@Controller
public class IndexController {


    @RequestMapping({"/","/index"})
    public String index(){
        return "redirect:/base/login";
    }

    @GetMapping("/system/main")
    public String main(Model model){

        return "/base/main";
    }
}
