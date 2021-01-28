package ${data.controllerpackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxyunge.common.SysMessage;
import com.jxyunge.controller.BaseController;
import com.jxyunge.mybatis.dto.system.TableListResult;
import ${data.entityPackages};
import ${data.servicepackage}.${data.entityName}Service;
import com.jxyunge.mybatis.dto.system.ApiResult;
import com.jxyunge.utils.ApiResultUtil;
import com.jxyunge.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ${data.module}--Controller
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */

@Controller
@RequestMapping("${data.uri}")
public class ${data.entityName}Controller extends BaseController {

    @Resource
    private ${data.entityName}Service ${data.lowEntityName}Service;

    @PermissionAnt("")
    @ResponseBody
    @PostMapping(value = "/getList")
    public TableListResult<${data.entityName}Dto> getList(IPage<${data.entityName}Dto> page, HttpServletRequest request){
        Map<String,Object> searchParams = Tools.getParametersStartingWith(request,"search_");
        ${data.lowEntityName}Service.pageDto(page,searchParams);
        return new TableListResult<>(page);
    }

    /**
    * 页面
    * @return
    */
    @PermissionAnt("")
    @GetMapping("/list")
    public String list(){
        return "/admin_tmpl${data.uri}/list";
    }

    @GetMapping("/addWin")
    public String addWin(Model model){
        ${data.entityName}Dto ${data.lowEntityName}Dto = new ${data.entityName}Dto();
        model.addAttribute("data",${data.lowEntityName}Dto);
        return "/admin_tmpl${data.uri}/addWin";
    }

    @GetMapping("/editWin")
    public String editWin(Long id,Model model){
        ${data.entityName}Dto  ${data.lowEntityName}Dto = ${data.lowEntityName}Service.getById(id);
        model.addAttribute("data",${data.lowEntityName}Dto);
        return "/admin_tmpl${data.uri}/editWin";
    }


    @ResponseBody
    @PostMapping("/add")
    public ApiResult add(${data.entityName} ${data.lowEntityName}VO){
        ${data.lowEntityName}Service.add(${data.lowEntityName}VO);
        return ApiResult.success("保存成功！");
    }

    @ResponseBody
    @PostMapping("/edit")
    public ApiResult edit(${data.entityName} ${data.lowEntityName}VO){
        ${data.lowEntityName}Service.edit(${data.lowEntityName}VO);
        return ApiResult.success("保存成功！");
    }


    @ResponseBody
    @PostMapping("/delete")
    public ApiResult del(Long id){
        ${data.lowEntityName}Service.delete(id);
        return ApiResult.success("删除成功！");
    }

    @ApiOperation(value = "下载导入模板")
    @GetMapping("/downloadTemplate")
        public void downloadTemplate(HttpServletResponse response) throws IOException {
        ${data.lowEntityName}Service.downloadTemplate(response);
    }

    @ApiOperation(value = "数据导入")
    @PostMapping(value = "/importData")
    @ResponseBody
    public ApiResult importData(Boolean isUpload, MultipartFile file) throws Exception {
        ApproveResult approveResult = ${data.lowEntityName}Service.importData(file, isUpload);
        return ApiResult.success(approveResult.getHtml(),approveResult);
    }
}
