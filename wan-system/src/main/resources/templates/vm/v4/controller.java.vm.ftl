package ${data.controllerpackage};

import ${data.entityPackages};
import ${data.servicepackage}.${data.entityName}Service;
import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
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
@RequestMapping("**")
public class ${data.entityName}Controller extends BaseController {

    @Resource
    private ${data.entityName}Service ${data.lowEntityName}Service;

    @Log(title = "${data.module}--列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "${data.module}--列表")
    public String list(HttpServletRequest request,CustomSearch<${data.entityName}> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.setPageSize(60);
        customSearch.setPagedata(${data.lowEntityName}Service.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        return "***";
    }

    @Log(title = "新增${data.module}",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增${data.module}")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.addData(${data.lowEntityName}VO);
    }


    @Log(title = "修改${data.module}",operateType = OperateType.UPDATE)
    @ApiOperation(value = "修改${data.module}")
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.edit(${data.lowEntityName}VO);
    }


    @Log(title = "删除${data.module}",operateType = OperateType.UPDATE)
    @ApiOperation(value = "删除${data.module}")
    @ResponseBody
    @PostMapping("/del")
    public Message del(Long id){
        return ${data.lowEntityName}Service.del(id);
    }

}
