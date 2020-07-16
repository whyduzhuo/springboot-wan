package ${data.servicepackage};

import com.diange.common.Filter;
import com.diange.common.ApproveResult;
import com.diange.common.Message;
import ${data.entityPackages};
import com.diange.service.BaseService;
import ${data.daopackage}.${data.entityName}Dao;
import com.diange.util.ExcelCommonUtil;
import com.diange.util.Tools;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStream;


/**
 * ${data.module}
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */
@Service
public class ${data.entityName}Service extends BaseService< ${data.entityName} ,Long>{
    @Resource
    private ${data.entityName}Dao ${data.lowEntityName}Dao;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    public void setBaseDao(${data.entityName}Dao ${data.lowEntityName}Dao) {
        super.setBaseDao(${data.lowEntityName}Dao);
    }

    /**
     * ${data.module} -- 新增
     * @param ${data.lowEntityName}VO
     * @return
     */
    public Message addData(${data.entityName} ${data.lowEntityName}VO) {
        throw new  ServiceException("功能暂未完成！");
    }

    private Message check(${data.entityName} ${data.lowEntityName}VO){
        throw new  ServiceException("功能暂未完成！");
    }

    /**
     * ${data.module} -- 删除
     * @param id
     * @return
     */
    public Message del(Long id) {
        throw new  ServiceException("功能暂未完成！");
    }

    /**
     * ${data.module} -- 修改
     * @param ${data.lowEntityName}VO
     * @return
     */
    public Message edit(${data.entityName} ${data.lowEntityName}VO) {
        throw new  ServiceException("功能暂未完成！");
    }

    /**
     * ${data.module} -- 导出excel
     * @param response
     * @param filters
     */
    public void exportData(HttpServletResponse response, List<Filter> filters) throws Exception{
        String fileName="${data.module}导出";
        String fileTitle="${data.module}导出列表";
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC,"createDate"));
        List<${data.entityName}> ${data.lowEntityName}List = super.searchList(filters,new Sort(orders));
        long count = super.count(filters);
        if (count>1000){
            throw new ServiceException("数据量过大，请增加筛选条件，降至1000条以下！");
        }
        List<Map<String,Object>> data = new ArrayList<>(${data.lowEntityName}List.size());
        for (${data.entityName} ${data.lowEntityName}:${data.lowEntityName}List) {
            Map<String,Object> map = new HashMap(n);
//            map.put("字段1",${data.lowEntityName}.getcolumn1());
//            map.put("字段2",${data.lowEntityName}.getcolumn2());
//            map.put("字段3",${data.lowEntityName}.getcolumn3());
            data.add(map);
        }
        String[] head= new String[]{"字段1","字段2","字段3"};
        ExcelCommonUtil.doExportExcel3(fileName, head, data, response);
    }


    /**
    * ${data.module} -- 下载Excel模板
    * @param response
    */
    public void downLoadExcel(HttpServletResponse response) throws Exception{
    String fileName = "${data.module}导入模板";
        //首行固定表头
        List<String> titleList;
        titleList = new ArrayList<>();
        titleList.add("字段1");
        titleList.add("字段2");
        titleList.add("字段3");
        //下拉选择列表,传null表示此列为输入列，不需要下拉
        List<List<String>> parpamtsList;
        parpamtsList = new ArrayList<>(3);
        parpamtsList.add(null);
        parpamtsList.add(null);
        parpamtsList.add(null);

        ExcelCommonUtil.ExportExcelTemplet(fileName, fileName, titleList, parpamtsList, response);
    }

    /**
    * ${data.module} -- 批量审批
    * @param ids
    * @param status
    * @param option
    * @return 返回审批结果
    */
    public Message batchApprove(Long[] ids,String status,String option){
        if (StringUtils.isBlank(status)){
            return Message.warn("请选择审批状态");
        }
        if (ids==null || ids.length==0){
            return Message.warn("请先勾选数据");
        }
        ApproveResult approveResult = new ApproveResult();
        for (int i = 1;i<=ids.length;i++){
            ${data.entityName} ${data.lowEntityName} = new ${data.entityName}();
            ${data.lowEntityName}.setId(ids[i-1]);
            ${data.lowEntityName}.setOpinion(option);
            if ("-1".equals(status)){
                try {
                    this.approveWidthNoStatus(${data.lowEntityName});
                    approveResult.addSuccess();
                }catch (ServiceException e){
                    approveResult.addFailed("第"+i+"条审批失败："+e.getMessage());
                }
            }else {
                try {
                    ${data.lowEntityName}.setFtwLevel(${data.entityName}.Status.valueOf(status));
                    this.approve(${data.lowEntityName});
                    approveResult.addSuccess();
                }catch (ServiceException e){
                    approveResult.addFailed("第"+i+"条审批失败："+e.getMessage());
                }
            }
        }
        return Message.success(approveResult.getHtml());
    }


    /**
    * ${data.module} -- Excel数据导入
    * @param file 文件流
    * @param isupload 上传/检查， 上传为true，
    * @return 返回检查/上传结果
    */
    public Message importMem(MultipartFile file, boolean isupload)throws Exception{
        List<List<String>> dataListStr = ExcelCommonUtil.readExelData(file,ExcelCommonUtil.HEAD_LOCK_ROW,47);
        ApproveResult approveResult = new ApproveResult();
        for (int i = 0;i<dataListStr.size();i++){
            try {
                ${data.entityName} ${data.lowEntityName}VO = this.listStrToEntity(dataListStr.get(i));
                if (isupload) {
                this.addData(${data.lowEntityName}VO);
            } else {
                this.check(${data.lowEntityName}VO);
            }
                approveResult.addSuccess();
            }catch (ServiceException e){
                approveResult.addFailed("第"+(i+ExcelCommonUtil.HEAD_LOCK_ROW+1)+"行"+(isupload?"导入":"检查")+"失败:"+e.getMessage());
            }
        }
        return Message.success(isupload?approveResult.getHtml():approveResult.getCheckHtml());
    }

    /**
    * Stringlist转实体
    * @param data
    * @return
    */
    private ${data.entityName} listStrToEntity(List<String> data){
        ${data.entityName} ${data.lowEntityName}VO = new ${data.entityName}();
        return ${data.lowEntityName}VO;
    }
}
