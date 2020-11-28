package com.duzhuo.common.utils;

import com.duzhuo.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: 万宏远
 * @date: 2020/5/11 10:49
 */

public class ExcelUtils {
    /**
     * 前两行固定，不填数据
     */
    public static final int HEAD_LOCK_ROW=2;

    public static final String ROW_NUM_KEY = "rownum";


    /**
     * 下载一个不带下拉选项的excel模板
     * @param title 文件名/首行标题
     * @param headList 表头
     * @param response
     */
    public static void downExcelTemplet(String title,List<String> headList,HttpServletResponse response) throws IOException {
        downExcelTemplet(title,headList,null,response);
    }

    /**
     * 下载一个带下拉选项的excel模板
     * @param title 文件名/首行标题
     * @param headList 表头
     * @param parpamtsList 可选择参数列(需与列名一比一)
     * @param response
     */
    public static void downExcelTemplet(String title,List<String> headList,
                                          List<List<String>> parpamtsList,HttpServletResponse response) throws IOException {
        String fileName = title+".xls";
        HSSFWorkbook wb = createExcel(title,headList, parpamtsList);
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
        response.setContentType("application/msexcel");
        OutputStream output = response.getOutputStream();
        wb.write(output);
        output.close();
    }

    /**
     * 下载一个不带下拉选项的excel模板
     * @param fileName 文件名，不要带后缀
     * @param headList 表头
     */
    public static ResponseEntity<byte[]> downExcelTempletByte(String fileName, List<String> headList) throws IOException {
        return downExcelTempletByte(fileName,headList,null);
    }

    /**
     * 下载一个带下拉选项的excel模板
     * @param fileName 文件名，不要带后缀
     * @param headList 表头
     * @param parpamtsList 可选择参数列(需与列名一比一)
     */
    public static ResponseEntity<byte[]> downExcelTempletByte(String fileName, List<String> headList,
                                                             List<List<String>> parpamtsList) throws IOException {
        fileName = fileName+".xls";
        HSSFWorkbook wb = createExcel("sheet1",headList, parpamtsList);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        wb.write(bos);
        byte[] barray=bos.toByteArray();
        //is第一转
        InputStream is=new ByteArrayInputStream(barray);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(wb.getBytes(),headers, HttpStatus.OK);
    }

    /**
     * @param sheetName sheet页名称,表头
     * @param titleList 列名
     * @param parpamtsList 可选择参数列(需与列名一比一)
     */
    private static HSSFWorkbook createExcel(String sheetName,List<String> titleList,List<List<String>> parpamtsList) {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(sheetName);

        //设置前两行冻结
        sheet.createFreezePane(0,HEAD_LOCK_ROW,0,HEAD_LOCK_ROW);
        //此处是设置 第一行 合并单元格的个数(标题行)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (titleList.size()-1)));

        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle = rowm.createCell(0);
        //获取列头样式对象
        HSSFCellStyle columnTopStyle = getColumnTopStyle(wb);
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(sheetName);
        rowm.setHeightInPoints(25);

        //设置第三行标题 标题列
        HSSFRow rowTitleName = sheet.createRow(1);
        HSSFCellStyle style = getColumnTopStyle(wb);
        for (int i=0;i<titleList.size();i++){
            HSSFCell cellTitleName = rowTitleName.createCell(i);
            cellTitleName.setCellValue(titleList.get(i));
            cellTitleName.setCellStyle(style);
        }

        //设置默认下拉内容
        if(parpamtsList!=null && parpamtsList.size()>0) {
            for(int i = 0; i<parpamtsList.size(); i++) {
                if(parpamtsList.get(i)==null || parpamtsList.get(i).size()<1) {
                    continue;
                }
                // 生成下拉框内容
                String[] strings = new String[parpamtsList.get(i).size()];
                parpamtsList.get(i).toArray(strings);
                // 绑定下拉框和作用区域
                HSSFDataValidation dataValidation = getDataValidationList4Col(sheet,(short) HEAD_LOCK_ROW,(short)i,(short)10000,(short)i,parpamtsList.get(i),wb);
                // 对sheet页生效
                sheet.addValidationData(dataValidation);
            }
        }
        return wb;
    }

    /**
     * 列表首页的大title样式
     * @param workbook
     * @return
     */
    private static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("Courier New");
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(false);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        return style;
    }

    private static HSSFDataValidation getDataValidationList4Col(HSSFSheet sheet, short firstRow,
                                                                short firstCol, short endRow,
                                                                short endCol, List<String> colName,
                                                                HSSFWorkbook wbCreat) {
        String sheetName = "hidden"+firstCol;
        String[] dataArray = colName.toArray(new String[0]);
        HSSFSheet hidden = wbCreat.createSheet(sheetName);
        HSSFCell cell;
        for (int i = 0, length = dataArray.length; i < length; i++) {
            String name = dataArray[i];
            HSSFRow row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(name);
        }
        Name namedCell = wbCreat.createName();
        namedCell.setNameName(sheetName);
        namedCell.setRefersToFormula(sheetName+"!$A$1:$A$" + dataArray.length);
        //加载数据,将名称为hidden的
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(sheetName);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol,
                endCol);
        HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
        //将第sheet设置为隐藏
        wbCreat.setSheetHidden(wbCreat.getSheetIndex(sheetName), true);
        sheet.addValidationData(validation);
        return validation;
    }


    public static List<List<String>> readExelData(MultipartFile file,int headLockRow,int endCol) throws IOException {
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        return readExelData(sheet, headLockRow, endCol);
    }

    /**
     * 读取Excel文件内容
     * @param headLockRow 表中固定的行
     * @param endCol 多少列
     * @param sheet
     * @return
     */
    public static List<List<String>> readExelData(Sheet sheet,int headLockRow,int endCol) {
        List<List<String>> dataList = new ArrayList<>();
        // 读取excel有多少行内容
        int rowCount = sheet.getLastRowNum();
        if (rowCount<headLockRow){
            throw new ServiceException("表中无数据！");
        }
        for (int i = headLockRow; i <= rowCount; i++) {
            List<String> data = new ArrayList<>();
            Row row = sheet.getRow(i);
            if (row == null){
                continue;
            }
            for (int j=0;j<endCol;j++){
                Cell cellCode = row.getCell(j);
                if (cellCode==null){
                    data.add("");
                    continue;
                }
                if (cellCode.getCellTypeEnum()==CellType.NUMERIC){
                    if (HSSFDateUtil.isCellDateFormatted(cellCode)){
                        Date date = cellCode.getDateCellValue();
                        data.add(new SimpleDateFormat("yyyy-MM-dd").format(date));
                        continue;
                    }
                }
                cellCode.setCellType(CellType.STRING);
                String cellValue =  StringUtils.deleteWhitespace((cellCode.getStringCellValue()));
                data.add(cellValue);

            }
            dataList.add(data);
        }
        dataList.removeIf(ExcelUtils::isEmpty);
        return dataList;
    }

    /**
     * 读取Excel数据
     * 将每一列数据转成Map形式，Map的key就是表头
     * @param file
     * @param titleRow 表头在那行，Excel行号，从1开始
     * @param dataRowStart 数据从那行开始
     * @param endCol 多少列，从1开始
     * @return
     * @throws IOException
     */
    public static List<Map<String,String>> readExelDataMap(MultipartFile file, int titleRow, int dataRowStart, int endCol, String needCol) throws IOException {
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<List<String>> stringList = ExcelUtils.readExelData(sheet, dataRowStart-1,endCol);
        List<String> titleList = ExcelUtils.readExelRowData(sheet,titleRow-1,endCol);
        if (needCol!=null){
            hasNeedCol(titleList,needCol);
        }
        List<Map<String,String>> dataMap= new ArrayList<>();
        for (int i=0;i<stringList.size();i++) {
            List<String> strings = stringList.get(i);
            Map<String,String> map = new HashMap<>();
            map.put(ROW_NUM_KEY,(i+dataRowStart)+"");
            for (int j = 0;j<strings.size();j++){
                map.put(titleList.get(j),strings.get(j));
            }
            dataMap.add(map);
        }
        return dataMap;
    }

    /**
     * 读取某行数据
     * @param sheet
     * @param rowIndex 行号，0开始
     * @param endCol 多少列，从1开始
     * @return
     */
    public static List<String> readExelRowData(Sheet sheet, int rowIndex, int endCol) {
        List<String> data = new ArrayList<>();
        Row row = sheet.getRow(rowIndex);
        for (int j=0;j<endCol;j++){
            Cell cellCode = row.getCell(j);
            if (cellCode==null){
                data.add("");
                continue;
            }
            cellCode.setCellType(CellType.STRING);
            String cellValue = StringUtils.deleteWhitespace(cellCode.getStringCellValue());
            data.add(cellValue);
        }
        return data;
    }

    private static boolean isEmpty(List<String> data){
        for (String string: data) {
            if (StringUtils.isNotBlank(string)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断表头有没有对上
     * @param titleList excel 读取的表头
     * @param col 因该存在的字段用逗号分开  如 学号,姓名,身份证号
     */
    public static void hasNeedCol(List<String> titleList,String col){
        String[] cols = col.split(",");
        Arrays.stream(cols).forEach(c->{
            if (!hasCol(titleList,c)){
                throw new ServiceException("没有找到表头："+c+"，请检查！");
            }
        });
    }

    private static boolean hasCol(List<String> titleList,String col){
        for (String aTitleList : titleList) {
            if (aTitleList.equals(col)) {
                return true;
            }
        }
        return false;
    }

    /**
     * MultipartFile，获取Workbook
     * @param multipartFile
     * @return
     */
    public static Workbook getWorkBook(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.getOriginalFilename().contains(".xls")){
            throw new ServiceException("请上传Excel文件！");
        }
        Workbook workbook ;
        try {
            workbook=new HSSFWorkbook(multipartFile.getInputStream());
        }catch (OfficeXmlFileException e){
            try {
                workbook=new XSSFWorkbook(multipartFile.getInputStream());
            }catch (Exception e1){
                throw new IOException("文件解析失败",e);
            }

        }
        return workbook;
    }

}
