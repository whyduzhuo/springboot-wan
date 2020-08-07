package ${data.controllerpackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxyunge.common.SysMessage;
import com.jxyunge.controller.BaseController;
import com.jxyunge.mybatis.dto.specialperson.${data.entityName}Dto;
import com.jxyunge.mybatis.dto.system.ApiResult;
import com.jxyunge.mybatis.dto.system.TableListResult;
import com.jxyunge.mybatis.entity.specialperson.${data.entityName};
import com.jxyunge.mybatis.service.specialperson.${data.entityName}Service;
import com.jxyunge.page.PageBody;
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
@RequestMapping("/grid/spec/aids_person")
public class Grid${data.entityName}Controller extends BaseController {

    @Resource
    private ${data.entityName}Service ${data.lowEntityName}Service;

    @ResponseBody
    @PostMapping(value = "/getList")
    public TableListResult<${data.entityName}Dto> getList(IPage<${data.entityName}Dto> page, HttpServletRequest request){
        Map<String,Object> searchParams = Tools.getParametersStartingWith(request,"search_");
        searchParams.put("eq_unit_id",super.getCurrentAdmin().getUnitId());
        ${data.lowEntityName}Service.page(page,searchParams);
        return new TableListResult<>(page);
    }

    /**
    * 页面
    * @return
    */
    @GetMapping("/list")
    public String list(){
        return "/admin_tmpl/specialperson/${data.lowEntityName}/list";
    }

    @GetMapping("/edit")
    public String edit(Long id,Model model){
        ${data.entityName}Dto ${data.lowEntityName}Dto = new ${data.entityName}Dto();
        if (id!=null){
            ${data.lowEntityName}Dto = ${data.lowEntityName}Service.getById(id);
        }
        model.addAttribute("data",${data.lowEntityName}Dto);
        return "/admin_tmpl/specialperson/${data.lowEntityName}/detail";
    }


    @ResponseBody
    @PostMapping("/save")
    public SysMessage addData(${data.entityName} ${data.lowEntityName}VO){
        if (${data.lowEntityName}VO.getUnitId()==null){
            ${data.lowEntityName}VO.setUnitId(super.getCurrentAdmin().getUnitId());
        }else{
            ${data.lowEntityName}Service.save(${data.lowEntityName}VO);
        }
        return SysMessage.success("保存成功！");
    }


    @ResponseBody
    @PostMapping("/delete")
    public SysMessage del(@RequestParam("ids") Long... ids){
        ${data.lowEntityName}Service.delete(ids);
        return SysMessage.success("删除成功！");
    }

    @ResponseBody
    @PostMapping("/delete")
    public SysMessage del(Long id){
        ${data.lowEntityName}Service.delete(id);
        return SysMessage.success("删除成功！");
    }

}
