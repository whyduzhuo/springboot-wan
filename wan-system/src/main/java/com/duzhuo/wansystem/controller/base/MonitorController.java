package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.monitor.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wanhy
 * @date: 2020/9/12 9:25
 */
@Controller
@RequestMapping("/base/monitor/server")
public class MonitorController {

    /**
     * 系统监控页
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/index")
    public String server(Model model) {
        Server server = new Server();
        server.copyTo();
        model.addAttribute("server", server);
        return "/base/monitor/index";
    }


}
