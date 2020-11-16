package ${data.controllerpackage};

import com.diange.common.CustomSearch;
import com.diange.common.Message;
import com.diange.controller.BaseController;
import com.diange.util.CommonUtil;
import com.diange.util.Tools;
import ${data.entityPackages};
import ${data.servicepackage}.${data.entityName}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * ${data.module}
 * @author: ${data.author}
 * @email: 1434495271@qq.com
 * @date: ${data.createDateStr}
 */
@Controller
@RequestMapping("/admin/${data.lowEntityName}")
public class ${data.entityName}Controller extends BaseController{
   private static final Logger logger = LoggerFactory.getLogger(${data.entityName}Controller.class);

    @Resource
    private ${data.entityName}Service ${data.lowEntityName}Service;

    /**
     * ${data.module} -- 首页
     */
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<${data.entityName}> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String, Object> searchParams = getParametersStartingWith(request,"search_");
        CommonUtil.paramTrim(searchParams);


        customSearch.setPagedata(${data.lowEntityName}Service.search(searchParams,customSearch));
        model.addAttribute("searchParams", mapKeyAddPre(searchParams, "search_"));
        model.addAttribute("customSearch", customSearch);
        return "/admin/${data.system}/****/list";
    }

    /**
     * ${data.module} -- 新增
     */
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.addData(${data.lowEntityName}VO);
    }

    /**
     * ${data.module} -- 编辑修改
     */
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.edit(${data.lowEntityName}VO);
    }

    /**
     * ${data.module} -- 删除
     */
    @PostMapping("/del")
    @ResponseBody
    public Message del(@NotNull Long id){
        return ${data.lowEntityName}Service.del(id);

    }

    /**
     * ${data.module} -- 查询单个对象
     */
    @GetMapping("/findById")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        return Message.successful(${data.lowEntityName}Service.find(id));
    }

    /**
     * ${data.module} -- 数据导出Excel
     * @param response
     * @throws Exception
     */
    @GetMapping("/exportData")
    @ResponseBody
    public void exportData(HttpServletRequest request,HttpServletResponse response, String[] fields)throws Exception{
        if (fields==null || fields.length==0){
            throw new ServiceException("请选择需要导出的字段!");
        }
        Map<String, Object> searchParams = getParametersStartingWith(request,"exp_");
        List<Filter> filters = ${data.lowEntityName}Service.mapToFilters(searchParams);
        ${data.lowEntityName}Service.exportData(response,filters,fields);
    }

    /**
     * ${data.module} --下载导入模板
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/downLoadExcel")
    public void downLoadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ${data.lowEntityName}Service.downLoadExcel(response);
        }catch (Exception e){
            logger.error("文件下载失败",e);
            throw e;
        }
    }

    /**
     * ${data.module} -- Excel数据导入
     * @param request
     * @param isUpload 是否是上传，
     * @param file 文件
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/importData")
    @ResponseBody
    public Message importData(HttpServletRequest request, Boolean isUpload,
                             @RequestParam("filePath") MultipartFile file) throws Exception {
        try {
            return ${data.lowEntityName}Service.importData(file, isUpload);
        } catch (Exception e) {
            logger.error("文件上传失败！");
            throw new Exception("文件上传失败！",e);
        }
    }

    /**
    * ${data.module} -- 批量审批
    */
    @PostMapping("/batchApprove")
    @ResponseBody
    public Message batchApprove(HttpServletRequest request,@RequestParam(name = "ids[]")Long[] ids,String option,String status){
        return ${data.lowEntityName}Service.batchApprove(ids,status,option);
    }
}
