package  com.duzhuo.wansystem.controller.house;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.StringUtils;
import com.duzhuo.wansystem.entity.house.CityUrl;
import com.duzhuo.wansystem.entity.house.HouseCount;
import com.duzhuo.wansystem.service.house.CityUrlService;
import com.duzhuo.wansystem.service.house.HouseCountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 二手房统计--Controller
 * @author: 万宏远
 * @date: 2020/08/18 12:51:09
 */

@Controller
@RequestMapping("/house/housecount")
public class HouseCountController extends BaseController {

    @Resource
    private HouseCountService houseCountService;
    @Resource
    private CityUrlService cityUrlService;


    @Log(title = "二手房统计--列表",operateType = OperateType.SELECT)
    @GetMapping("/date")
    @ApiOperation(value = "二手房统计--列表")
    public String list(Model model,String date){
        if (StringUtils.isBlank(date)){
            date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        model.addAttribute("data",houseCountService.statisticDate(date));
        model.addAttribute("date",date);
        return "/house/houseCount/date";
    }

    @Log(title = "新增二手房统计",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增二手房统计")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(HouseCount houseCountVO){
        return houseCountService.addData(houseCountVO);
    }


    @Log(title = "修改二手房统计",operateType = OperateType.UPDATE)
    @ApiOperation(value = "修改二手房统计")
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(HouseCount houseCountVO){
        return houseCountService.edit(houseCountVO);
    }


    @Log(title = "删除二手房统计",operateType = OperateType.UPDATE)
    @ApiOperation(value = "删除二手房统计")
    @ResponseBody
    @PostMapping("/del")
    public Message del(Long id){
        return houseCountService.del(id);
    }

    @GetMapping("/addBatch")
    @ResponseBody
    public Message addBatch(){
        return houseCountService.addBatch();
    }

    @GetMapping("/ceshi")
    @ResponseBody
    public Integer ceshi(){
        CityUrl cityUrl = cityUrlService.find(80382L);
        return houseCountService.getZg(cityUrl);
    }

}
