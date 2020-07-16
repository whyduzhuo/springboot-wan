package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.manager.AsyncManager;
import com.duzhuo.common.utils.EmailSending;
import com.duzhuo.common.utils.ExcelUtils;
import com.duzhuo.wansystem.entity.base.Coding;
import com.duzhuo.wansystem.service.base.CodingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author: wanhy
 * @date: 2020/1/13 15:35
 */

@Api(tags = "代码生成")
@Controller
@RequestMapping("/base/coding")
public class CodingController {
    @Resource
    private CodingService codingService;
    @Resource
    private EmailSending emailSending;

    @Log(title = "代码生成首页",operateType = OperateType.SELECT )
    @ApiOperation(value = "首页")
    @GetMapping("/index")
    public String index(Model model){
        List<Coding> codingList = codingService.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
        model.addAttribute("dataList",codingList);
        return "base/coding/list";
    }

    @Log(title = "查询Service代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "")
    @GetMapping("/getService")
    public String getService(Coding coding, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/service.java.vm";
    }

    @Log(title = "新增数据",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增数据")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Coding codingVO){
        return codingService.addData(codingVO);
    }

    @Log(title = "获取controller代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取controller代码")
    @GetMapping("/getController")
    public String getController(HttpServletRequest request,Coding coding, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/controller.java.vm";
    }
    
    @Log(title = "获取dao代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取dao代码")
    @GetMapping("/getDao")
    public String getDao(HttpServletRequest request,Coding coding, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/dao.java.vm";
    }

    @Log(title = "获取Html代码",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取Html代码")
    @GetMapping("/getHtml")
    public String getHtml(HttpServletRequest request,Coding coding, Model model){
        coding.cal();
        model.addAttribute("data",coding);
        return "vm/html.ftl.vm";
    }

    @Log(title = "获取单条数据",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取单条数据")
    @GetMapping("/findById")
    @ResponseBody
    public Coding findById(HttpServletRequest request, Long id){
        return codingService.find(id);
    }

    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        String title = "卧槽";
        List<String> headList = new ArrayList<>();
        headList.add("字段1");
        headList.add("字段2");
        headList.add("字段3");
        headList.add("字段4");
        List<List<String>> params = new ArrayList<>();
        params.add(Arrays.asList(new String[]{"选项1","选项2","选项3","选项4"}));
        params.add(Arrays.asList(new String[]{"选项1","选项2","选项3","选项4"}));
        params.add(Arrays.asList(new String[]{"选项1","选项2","选项3","选项4"}));
        params.add(Arrays.asList(new String[]{"选项1","选项2","选项3","选项4"}));
        ExcelUtils.downExcelTemplet(title,headList,params,response);
    }
}
