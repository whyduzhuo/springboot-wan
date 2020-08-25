package  com.duzhuo.wansystem.controller.house;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.HttpUtils;
import com.duzhuo.common.utils.StringUtils;
import com.duzhuo.wansystem.entity.house.HouseCount;
import com.duzhuo.wansystem.service.house.CityUrlService;
import com.duzhuo.wansystem.service.house.HouseCountService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
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
    @RequiresPermissions("1102")
    public String list(Model model,String date){
        if (StringUtils.isBlank(date)){
            date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        model.addAttribute("data",houseCountService.statisticDate(date));
        model.addAttribute("date",date);
        return "/house/houseCount/date";
    }

    @Log(title = "二手房走势--列表",operateType = OperateType.SELECT)
    @GetMapping("/city")
    @ApiOperation(value = "二手房走势--列表")
    @RequiresPermissions("1103")
    public String cityLsit(Model model){
        Long start = System.currentTimeMillis();
        model.addAttribute("dataList",houseCountService.statisticCity());
        model.addAttribute("contry",houseCountService.statisticContry());
        System.err.println(System.currentTimeMillis()-start);
        return "/house/houseCount/city";
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

    @Log(title = "爬取当天二手房挂牌量",operateType = OperateType.INSERT)
    @ApiOperation("爬取当天二手房挂牌量")
    @GetMapping("/addBatch")
    @ResponseBody
    @RequiresPermissions("110200")
    public Message addBatch(){
        return houseCountService.addBatch();
    }

    @GetMapping("ceshi")
    @ResponseBody
    public Message ceshi(String url,String charSet) throws UnsupportedEncodingException {
        String pageCode = HttpUtils.get(url,charSet);
        return Message.success("",pageCode);
    }

}
