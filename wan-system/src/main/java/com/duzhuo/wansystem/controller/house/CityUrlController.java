package  com.duzhuo.wansystem.controller.house;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.HttpUtils;
import com.duzhuo.wansystem.entity.house.CityUrl;
import com.duzhuo.wansystem.service.house.CityUrlService;
import com.duzhuo.wansystem.test.CallableAndFuture;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 城市代码--Controller
 * @author: 万宏远
 * @date: 2020/08/18 10:51:27
 */

@Slf4j
@Controller
@RequestMapping("/house/cityurl")
public class CityUrlController extends BaseController {

    @Resource
    private CityUrlService cityUrlService;

    @Log(title = "城市代码--列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "城市代码--列表")
    public String list(HttpServletRequest request,CustomSearch<CityUrl> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        customSearch.setPageSize(60);
        customSearch.setPagedata(cityUrlService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams,SEARCH_PREFIX));
        return "***";
    }


    @Log(title = "删除城市代码",operateType = OperateType.UPDATE)
    @ApiOperation(value = "删除城市代码")
    @ResponseBody
    @PostMapping("/del")
    public Message del(Long id){
        return cityUrlService.del(id);
    }


    @ApiOperation(value = "爬取链家地址")
    @GetMapping("/ljUrlPach")
    @ResponseBody
    public void ljUrlPach(){
        //<li><a href="https://dl.lianjia.com/">大连</a></li>
        String pageCode = HttpUtils.get("https://www.lianjia.com/city/");
        String reg = "<li><a href=\"https://[a-zA-z]{2,6}.lianjia\\.com/\"> [\u4e00-\u9fa5]{2,5}</a></li>";
        List<String> matchers = CallableAndFuture.getMatchers("<li><a href=\"https://[a-zA-z]{2,20}.lianjia\\.com/\">[\u4e00-\u9fa5]{2,20}</a></li>", pageCode);
        matchers.forEach(m->{
            String startStr1 = "<li><a href=\"https://";
            int start1 =m.indexOf(startStr1)+startStr1.length();
            String endStr1 = ".lianjia.com";
            int end1 = m.indexOf(endStr1);
            String code = m.substring(start1,end1).trim();

            String startStr2 = ".lianjia.com/\">";
            int start2 = m.indexOf(startStr2)+startStr2.length();
            String endStr2 = "</a></li>";
            int end2 = m.indexOf(endStr2);
            String name =m.substring(start2,end2).trim();

            CityUrl cityUrl = new CityUrl();
            cityUrl.setLjUrl("https://"+code+".lianjia.com/ershoufang/");
            cityUrl.setName(name);
            try {
                cityUrlService.addLjData(cityUrl);
            }catch (ServiceException e){
                log.info(e.getMessage());
            }

        });
    }

    @ApiOperation(value = "爬取诸葛地址")
    @GetMapping("/zgUrlPach")
    @ResponseBody
    public String zgUrlPach(){
        //<a href="//hf.ershoufang.zhuge.com/">合肥</a>
        String pageCode = HttpUtils.get("https://www.zhuge.com/areachange/?businessType=sell");
        String reg = "<a href=\"//[a-zA-z]{2,6}.ershoufang.zhuge.com/\">[\u4e00-\u9fa5]{2,6}</a>";
        List<String> matchers = CallableAndFuture.getMatchers(reg, pageCode);
        matchers.forEach(m->{
            String startStr1 = "<a href=\"//";
            int start1 =m.indexOf(startStr1)+startStr1.length();
            String endStr1 = "/\">";
            int end1 = m.indexOf(endStr1);
            String code = m.substring(start1,end1).trim();

            String startStr2 = ".com/\">";
            int start2 = m.indexOf(startStr2)+startStr2.length();
            String endStr2 = "</a>";
            int end2 = m.indexOf(endStr2);
            String name =m.substring(start2,end2).trim();

            CityUrl cityUrl = new CityUrl();
            cityUrl.setZgUrl("http://"+code);
            cityUrl.setName(name);
            try {
                cityUrlService.addZgData(cityUrl);
            }catch (ServiceException e){
                log.info(e.getMessage());
            }
        });
        return "";
    }

    @GetMapping("/lianjia")
    public String linajia(String cityName){
        CityUrl cityUrl = cityUrlService.findName(cityName);
        return "redirect:"+cityUrl.getLjUrl();
    }
}
