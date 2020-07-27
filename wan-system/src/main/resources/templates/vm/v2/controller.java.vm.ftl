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
import org.springframework.web.bind.WebDataBinder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ${data.module}--Controller
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */

@RequestMapping("/admin/${data.lowEntityName}")
@RestController
@Api(value = "${data.module}")
public class ${data.entityName}Controller extends BaseController {

    @Resource
    private ${data.entityName}Service ${data.lowEntityName}Service;

    /**
     * string转Date
     * @param dataBinder
     */
    @Override
    @InitBinder
    public void  initBinder(WebDataBinder dataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @AutoLog(value = "${data.module}--分页")
    @ApiOperation(value = "${data.module}--分页")
    @GetMapping("/pages")
    public ApiResult<IPage<${data.entityName}>> pages(PageBody<${data.entityName}> page){
        QueryWrapper<${data.entityName}> queryWrapper = Tools.getWrapperBySearchParams(page.getSearchParams());
        ${data.lowEntityName}Service.page(page,queryWrapper);
        return ApiResultUtil.success(page);
    }


    @AutoLog(value = "${data.module}--新增")
    @ApiOperation(value = "${data.module}--新增")
    @PostMapping("/addData")
    public ApiResult<${data.entityName}> addData(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.addData(${data.lowEntityName}VO);
    }


    @AutoLog(value = "${data.module}--修改")
    @ApiOperation(value = "${data.module}--修改")
    @PostMapping("/edit")
    public ApiResult<${data.entityName}> edit(${data.entityName} ${data.lowEntityName}VO){
        return ${data.lowEntityName}Service.edit(${data.lowEntityName}VO);
    }


    @AutoLog(value = "${data.module}--新增/修改")
    @ApiOperation(value = "${data.module}--新增/修改")
    @PostMapping("/saveOrUpdate")
    public ApiResult<${data.entityName}> saveOrUpdate(${data.entityName} ${data.lowEntityName}VO){
        if (${data.lowEntityName}VO.getId()==null){
            return ${data.lowEntityName}Service.addData(${data.lowEntityName}VO);
        }
        return ${data.lowEntityName}Service.edit(${data.lowEntityName}VO);
    }


    @AutoLog(value = "${data.module}--删除")
    @ApiOperation(value = "${data.module}--删除")
    @DeleteMapping("/del")
    public ApiResult del(@RequestParam("id") Long id){
        ${data.lowEntityName}Service.del(id);
        return ApiResultUtil.success();
    }


    @AutoLog(value = "${data.module}--批量删除")
    @ApiOperation(value = "${data.module}--批量删除")
    @GetMapping("/delete")
    public ApiResult delete(@RequestParam("id") Long... ids){
        ${data.lowEntityName}Service.delete(ids);
        return ApiResultUtil.success();
    }


    @AutoLog(value = "${data.module}--查询单个对象")
    @ApiOperation(value = "${data.module}--查询单个对象")
    @GetMapping("/get/{id}")
    public ApiResult<${data.entityName}> findById(@PathVariable Long id){
        return ApiResultUtil.success(${data.lowEntityName}Service.getById(id));
    }

}
