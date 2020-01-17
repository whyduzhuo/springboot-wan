package ${data.servicepackage};

import com.diange.common.Filter;
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
     * 新增
     * @param ${data.lowEntityName}VO
     * @return
     */
    public Message addData(${data.entityName} ${data.lowEntityName}VO) {
        return Message.error("功能暂未完成！");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public Message del(Long id) {
        return Message.error("功能暂未完成！");
    }

    /**
     * 修改
     * @param ${data.lowEntityName}VO
     * @return
     */
    public Message edit(${data.entityName} ${data.lowEntityName}VO) {
        return Message.error("功能暂未完成！");
    }

    /**
     * 导出excel
     * @param response
     * @param exp_column1
     * @param exp_column2
     * @param exp_column3
     */
    public void exportData(HttpServletResponse response,String exp_column1,String exp_column2,String exp_column3) throws Exception{
        String fileName="${data.module}导出";
        String fileTitle="${data.module}导出列表";
        List<Filter> filters = Lists.newArrayList();
        if (Tools.vaildeParam(exp_column1)){

        }
        if (Tools.vaildeParam(exp_column1)){

        }
        if (Tools.vaildeParam(exp_column1)){

        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"createDate"));
        List<${data.entityName}> ${data.lowEntityName}List = super.searchList(filters,sort);
        List<Map<String,Object>> data = new ArrayList<>(${data.lowEntityName}List.size());
        for (${data.entityName} ${data.lowEntityName}:${data.lowEntityName}List) {
//            Map<String,Object> map = new HashMap(n);
//            map.put("column1",music.getcolumn1());
//            map.put("column2",music.getcolumn2());
//            map.put("column3",music.getcolumn3());
        }
        List<String> properties = Lists.newArrayList(new String[]{"column1","column2","column3"});
        String[]  head= new String[]{"字段1","字段2","字段3"};
        CellType[] cellTypes = new CellType[]{CellType.STRING,CellType.STRING, CellType.STRING};
        ExcelCommonUtil.doExportExcel3(fileName, head, data, properties, fileTitle, response, cellTypes);
    }


    /**
    * 下载Excel模板
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
    * 导入${data.module}
    * @param inputStream 文件流
    * @param isupload 上传/检查， 上传为true，
    * @param fileName 文件名
    * @return 返回检查/上传结果
    */
    public Message importMem(InputStream inputStream, String fileName, boolean isupload)throws Exception{
        StringBuilder ERROR_MESSAGE = new StringBuilder();
        int success = 0;
        int failed = 0;
        if (!fileName.contains(".xls")){
            return Message.error("请上传Excel文件！");
        }
        //判断是否是.xls还是.xlsx文件
        boolean is03excle = fileName.matches("^.+\\.(?i)(xls)$");
        Workbook workbook=null;;
        try {
            workbook = is03excle ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);

            Sheet childSheet = workbook.getSheetAt(0);
            int rowCount = childSheet.getLastRowNum(); // 最后一行的行标，从0开始
            if (rowCount < 1) {
                return Message.error("表中无内容！");
            }

            for (int j = 2; j <= rowCount; j++) {
                //读取单元格
                Row row = childSheet.getRow(j);
                if (row == null){
                    continue;
                }
                Cell cellCode = row.getCell(0);
                if (cellCode==null){
                ERROR_MESSAGE.append("第" + (j + 1) + "行中：字段1为空，请检查<br/>");
                failed+=1;
                    continue;
                }
                cellCode.setCellType(CellType.STRING);
                String column = StringUtils.deleteWhitespace((cellCode.getStringCellValue()));





                ${data.entityName} ${data.lowEntityName}VO = new ${data.entityName}();
                if (isupload){
                    Message message = this.addData(${data.lowEntityName}VO);
                    if (!Message.Type.success.equals(message.getType())){
                        failed+=1;
                        ERROR_MESSAGE.append("第" + (j + 1) + "行："+message.getContent()+"</br>");
                    }else {
                        success+=1;
                    }
                }else {
                    success+=1;
                }
            }
        }catch (Exception e){
            throw e;
        }finally {
            workbook.close();
            inputStream.close();
        }
        return Message.success(ERROR_MESSAGE.toString());
    }
}
