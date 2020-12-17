package  com.duzhuo.wansystem.service.house;

import com.duzhuo.common.core.ApproveResult;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.ExcelUtils;
import com.duzhuo.wansystem.dao.house.ExcelDemoDao;
import com.duzhuo.wansystem.entity.house.ExcelDemo;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ExcelDemo--Service
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2020/12/16 18:04:40
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExcelDemoService extends BaseService<ExcelDemo, Long> {

    private static final String[] mzList = {"汉族","藏族","回族","维吾尔族"};
    private static final String[] sexList = {"男","女"};
    private static final String[] orgList = {"国际学院","工商管理学院","统计学院","会计学院"};

    @Resource
    private ExcelDemoDao excelDemoDao;

    @Resource
    public void setBaseDao(ExcelDemoDao excelDemoDao){
        super.setBaseDao(excelDemoDao);
    }

    /**
     * ExcelDemo--数据校验
     * @param excelDemoVO
     */
    private void check(ExcelDemo excelDemoVO){
        super.validation(excelDemoVO);
        if (ArrayUtils.indexOf(mzList,excelDemoVO.getMz())==-1){
            throw new ServiceException("民族填写有误！");
        }
        if (ArrayUtils.indexOf(sexList,excelDemoVO.getSex())==-1){
            throw new ServiceException("性别填写有误！");
        }
        if (ArrayUtils.indexOf(orgList,excelDemoVO.getOrg())==-1){
            throw new ServiceException("学院填写有误！");
        }
    }

    /**
     * ExcelDemo--新增
     * @param excelDemoVO
     * @return
     */
    public Message addData(ExcelDemo excelDemoVO) {
        super.validation(excelDemoVO);
        this.check(excelDemoVO);
        super.save(excelDemoVO);
        return Message.success("添加成功!");
    }


    /**
     * ExcelDemo--修改
     * @param excelDemoVO
     * @return
     */
    public Message edit(ExcelDemo excelDemoVO) {
        super.validation(excelDemoVO);
        this.check(excelDemoVO);
        ExcelDemo excelDemo = super.find(excelDemoVO.getId());
        super.update(excelDemo);
        return Message.success("修改成功!");
    }

    /**
     * ExcelDemo--删除
     * @param id
     * @return
     */
    public Message del(Long id) {
        if (id==null){
            throw new ServiceException("请选择数据！");
        }
        super.delete(id);
        return Message.success("删除成功！");
    }

    /**
     * 姓名,性别,民族,学院
     * ExcelDemo -- Excel数据导入
     * @param file 文件流
     * @param isupload 上传/检查， 上传为true，
     * @return 返回检查/上传结果
     */
    public Message importData(MultipartFile file, boolean isupload)throws Exception{
        List<Map<String,String>> dataListStr = ExcelUtils.readExelDataMap(file,2,3,20,"姓名,性别,民族,学院");
        ApproveResult approveResult = new ApproveResult(isupload?"导入":"检查");
        for (int i = 0;i<dataListStr.size();i++){
            try {
                ExcelDemo excelDemoVO = this.listStrToEntity(dataListStr.get(i));
                if (isupload) {
                    this.addData(excelDemoVO);
                } else {
                    this.check(excelDemoVO);
                }
                approveResult.addSuccess();
            }catch (ServiceException e){
                approveResult.addFailed("第"+(i+3)+"行"+(isupload?"导入":"检查")+"失败:"+e.getMessage());
            }
        }
        return Message.success(isupload?approveResult.getHtml():approveResult.getCheckHtml());
    }


    /**
     * 姓名,性别,民族,学院
     * Map转实体
     * @param data
     * @return
     */
    private ExcelDemo listStrToEntity(Map<String,String> data){
        ExcelDemo excelDemoVO = new ExcelDemo();
        excelDemoVO.setMz(data.get("民族"));
        excelDemoVO.setName(data.get("姓名"));
        excelDemoVO.setOrg(data.get("学院"));
        excelDemoVO.setSex(data.get("性别"));
        return excelDemoVO;
    }

    public void downLoadExcelTemp(HttpServletResponse response) throws IOException {
        List<String> headList = new ArrayList<>();
        headList.add("姓名");
        headList.add("性别");
        headList.add("民族");
        headList.add("学院");
        List<List<String>> paramList = new ArrayList<>();
        paramList.add(null);
        paramList.add(Arrays.asList(sexList));
        paramList.add(Arrays.asList(mzList));
        paramList.add(Arrays.asList(orgList));
        ExcelUtils.downExcelTemplet("ExcelDemo导入模板",headList,paramList,response);
    }
}
