package ${data.controllerpackage};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxyunge.annotation.AutoLog;
import com.jxyunge.enums.autoLog.AutoLogTypeEnum;
import com.jxyunge.mybatis.dto.system.ApiResult;
import ${data.entityPackages};
import ${data.servicepackage}.${data.entityName}Service;
import com.jxyunge.utils.ApiResultUtil;
import com.jxyunge.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ${data.module}
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */

@RequestMapping("/test")
@Controller
@Api(value = "${data.module}")
public class ${data.entityName}Controller extends BaseController {

    @Resource
    private ${data.entityName}Service ${data.lowEntityName}Service;

    @AutoLog(value = "${data.module}--分页",logType = AutoLogTypeEnum.OPER_LOG)
    @ApiOperation(value = "${data.module}--分页")
    @GetMapping("/pages")
    public ApiResult<IPage<${data.entityName}>> pages(IPage<${data.entityName}> page, HttpServletRequest request){
        Map<String,Object> searchParams = Tools.getParametersStartingWith(request,"search_");
        QueryWrapper<${data.entityName}> queryWrapper = Tools.getWrapperBySearchParams(searchParams);
        ${data.lowEntityName}Service.page(page,queryWrapper);
        return ApiResultUtil.success(page);
    }


    @AutoLog(value = "${data.module}--新增",logType = AutoLogTypeEnum.OPER_LOG)
    @ApiOperation(value = "${data.module}--新增")
    @PostMapping("/addData")
    @ResponseBody
    public ApiResult addData(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.addData(${data.lowEntityName}VO);
    }


    @AutoLog(value = "${data.module}--修改",logType = AutoLogTypeEnum.OPER_LOG)
    @ApiOperation(value = "${data.module}--修改")
    @PostMapping("/edit")
    @ResponseBody
    public ApiResult edit(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.edit(${data.lowEntityName}VO);
    }


    @AutoLog(value = "${data.module}--删除",logType = AutoLogTypeEnum.OPER_LOG)
    @ApiOperation(value = "${data.module}--删除")
    @PostMapping("/del")
    @ResponseBody
    public ApiResult del(Long id){
        return ${data.lowEntityName}Service.del(id);
    }

    @AutoLog(value = "${data.module}--查询单个对象",logType = AutoLogTypeEnum.OPER_LOG)
    @ApiOperation(value = "${data.module}--查询单个对象")
    @GetMapping("/get/{id}")
    @ResponseBody
    public ApiResult findById(@PathVariable Long id){
        return ApiResultUtil.success(${data.lowEntityName}Service.getById(id));
    }

}
