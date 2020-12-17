package com.duzhuo.wansystem.controller.house;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.ExcelUtils;
import com.duzhuo.wansystem.dto.house.ExcelDemoDto;
import com.duzhuo.wansystem.entity.house.ExcelDemo;
import com.duzhuo.wansystem.service.house.ExcelDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.logging.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/16 10:30
 */
@Api(tags = "ExcelDemo")
@Controller
@RequestMapping("/house/excelDemo")
public class ExcelDemoController extends BaseController{
    @Resource
    private ExcelDemoService excelDemoService;


    @Log(title = "ExcelDemo",operateType = OperateType.SELECT)
    @ApiOperation(value = "ExcelDemo--首页")
    @RequiresPermissions("1104")
    @GetMapping("/index")
    public String index(HttpServletRequest request, CustomSearch<ExcelDemo> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.setPageSize(60);
        customSearch.setPagedata(excelDemoService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        return "/house/excelDemo/index";
    }

    /**
     * 下载一个带下拉选项的Excel
     * @param request
     * @param response
     */
    @Log(title = "ExcelDemo",operateType = OperateType.DOWLOAD)
    @ApiOperation(value = "ExcelDemo--下载一个带下拉选项的Excel")
    @GetMapping("/downLoadTemp")
    public void downLoadTemp(HttpServletRequest request, HttpServletResponse response,ExcelDemoDto excelDemoDto) throws IOException {
        List<String> headList = new ArrayList<>();
        headList.add("选项一");
        headList.add("选项二");
        headList.add("选项三");
        headList.add("选项四");
        List<List<String>> paramsList = new ArrayList<>();
        paramsList.add(excelDemoDto.getParm1());
        paramsList.add(excelDemoDto.getParm2());
        paramsList.add(excelDemoDto.getParm3());
        paramsList.add(excelDemoDto.getParm4());
        ExcelUtils.downExcelTemplet("下载一个带下拉选项的Excel",headList,paramsList,response);
    }

    @Log(title = "ExcelDemo",operateType = OperateType.DOWLOAD)
    @ApiOperation(value = "ExcelDemo--将数据导出excel")
    @PostMapping("/expertData")
    public void expertDemo(HttpServletRequest request, HttpServletResponse response,ExcelDemoDto excelDemoDto,String[] fields) throws Exception {
        String[] headList = {"姓名","学号","班级","学院"};
        List<Map<String,Object>> dataList = new ArrayList<>();
        List<List<String>> stringList = new ArrayList<>();
        stringList.add(excelDemoDto.getParm1());
        stringList.add(excelDemoDto.getParm2());
        stringList.add(excelDemoDto.getParm3());
        stringList.forEach(s->{
            Map<String,Object> data = new HashMap<>();
            data.put("姓名",s.get(0));
            data.put("学号",s.get(1));
            data.put("班级",s.get(2));
            data.put("学院",s.get(3));
            dataList.add(data);
        });
        ExcelUtils.exportExcel("将数据导出excel",headList,dataList,response);
    }

    @GetMapping("/importWin")
    public String importWin(){
        return "/house/excelDemo/importWin";
    }

    @Log(title = "下载导入模板",operateType = OperateType.DOWLOAD)
    @GetMapping("/downLoadExcelTemp")
    public void downLoadExcelTemp(HttpServletResponse response) throws IOException {
        excelDemoService.downLoadExcelTemp(response);
    }

    @Log(title = "导入数据",operateType = OperateType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public Message importData(MultipartFile file, @RequestParam(value = "isupload",required = false,defaultValue = "false") boolean isupload) throws Exception {
        return excelDemoService.importData(file,isupload);
    }
}
