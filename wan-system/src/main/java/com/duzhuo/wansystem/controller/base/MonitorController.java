package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.monitor.Server;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: wanhy
 * @date: 2020/9/12 9:25
 */
@Controller
@RequestMapping("/base/monitor/server")
@Api(tags = "系统监控")
public class MonitorController {

    /**
     * 系统监控页
     * @param model
     * @return
     * @throws Exception
     */
    @RequiresPermissions("1006")
    @GetMapping(value = "/index")
    public String server(Model model) {
        Server server = new Server();
        server.copyTo();
        model.addAttribute("server", server);
        return "/base/monitor/index";
    }


}
