package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.EmailSendService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.thread.ThreadPoolService;
import com.duzhuo.common.thread.Threads;
import com.duzhuo.wansystem.entity.base.Coding;
import com.duzhuo.wansystem.service.base.CodingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: 万宏远
 * @date: 2020/1/13 15:35
 */

@Api(tags = "代码生成")
@Controller
@RequestMapping("/base/coding")
public class CodingController {
    @Resource
    private CodingService codingService;
    @Resource
    private EmailSendService emailSendService;
    @Resource
    private ThreadPoolService threadPoolService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;


    @Log(title = "代码生成首页",operateType = OperateType.SELECT )
    @ApiOperation(value = "首页")
    @RequiresPermissions("1101")
    @GetMapping("/index")
    public String index(Model model){
        List<Coding> codingList = codingService.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
        model.addAttribute("dataList",codingList);
        return "/base/coding/list";
    }

    @Log(title = "查询Service代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "")
    @GetMapping("/getService/{v}")
    public String getService(Coding coding,@PathVariable String v, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/"+v+"/service.java.vm";
    }

    @Log(title = "新增数据",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增数据")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Coding codingVO){
        codingService.addData(codingVO);
        return Message.success("添加成功！");
    }

    @Log(title = "获取controller代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取controller代码")
    @GetMapping("/getController/{v}")
    public String getController(HttpServletRequest request, Coding coding, @PathVariable String v, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/"+v+"/controller.java.vm";
    }
    
    @Log(title = "获取dao代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取dao代码")
    @GetMapping("/getDao/{v}")
    public String getDao(Coding coding, @PathVariable String v, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/"+v+"/dao.java.vm";
    }

    @Log(title = "获取mapper代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取dao代码")
    @GetMapping("/getMapper/{v}")
    public String getMapper(Coding coding, @PathVariable String v, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/"+v+"/mapper.vm";
    }

    @Log(title = "获取Html代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取Html代码")
    @GetMapping("/getHtml/{v}")
    public String getHtml(Coding coding, @PathVariable String v, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/"+v+"/html.ftl.vm";
    }

    @Log(title = "获取Html代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取Html代码")
    @GetMapping("/getDetail/{v}")
    public String getDetail(Coding coding, @PathVariable String v, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/"+v+"/detail.vm";
    }

    @Log(title = "获取单条数据",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取单条数据")
    @GetMapping("/findById")
    @ResponseBody
    public Coding findById(Long id){
        return codingService.find(id);
    }

    @GetMapping("/test")
    public void test(HttpServletResponse response) throws UnsupportedEncodingException, MessagingException, ExecutionException, InterruptedException {
        //emailSendService.sendAttachmentMail("1434495271@qq.com","通知","哈哈哈",new File("D:\\wan\\万宏远.pdf"));
        Long timeMillis = System.currentTimeMillis();
        Future<String> a = threadPoolExecutor.submit(() -> {
            Threads.sleep(3000);
            return "a";
        });
        Future<String> b = threadPoolExecutor.submit(() -> {
            Threads.sleep(6000);
            return "b";
        });
        Future<String> c = threadPoolExecutor.submit(() -> {
            Threads.sleep(9000);
            return "c";
        });
        String astr = a.get();
        String bstr = b.get();
        String cstr = c.get();
        System.err.println("耗时："+(System.currentTimeMillis()-timeMillis));
    }
}
