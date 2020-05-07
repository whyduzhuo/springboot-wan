package com.duzhuo.common.utils;

import com.duzhuo.common.exception.ServiceException;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/1/1 20:03
 */

public class ExcelUtils {

    /**
     * 数据从第三行开始读取
     * 读取Exel文件内容，转List
     * @param file
     * @param colNumber
     * @return
     */
    public static List<List<String>> excelToList(MultipartFile file, int colNumber) throws IOException {
        InputStream inputStream = file.getInputStream();
        String fileName=file.getOriginalFilename();
        if (!fileName.contains(".xls")){
            throw new ServiceException("非Excel文件");
        }
        //判断是否是.xls还是.xlsx文件
        boolean is03excle = fileName.matches("^.+\\.(?i)(xls)$");
        Workbook workbook = is03excle ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        Sheet childSheet = workbook.getSheetAt(0);
        // 最后一行的行标，从0开始
        int rowCount = childSheet.getLastRowNum();
        if (rowCount < 3) {
            throw new ServiceException("文件无数据！");
        }
        List<List<String>> data= new ArrayList<>(rowCount-2);
        //从第三行开始读取
        for (int j = 2; j <= rowCount; j++) {
            Row row = childSheet.getRow(j);
            List<String> rowData = new ArrayList<>(colNumber);
            for (int i=0;i<colNumber;i++){
                Cell cellCode = row.getCell(i);
                String cellData = "";
                if (cellCode!=null){
                    cellCode.setCellType(CellType.STRING);
                    cellData=cellCode.getStringCellValue();
                }
                rowData.add(cellData);
            }
            data.add(rowData);
        }
        return data;
    }

    /**
     * 下载一个带下拉选项的excel模板
     * @param fileName 文件名，不要带后缀
     * @param sheetName 表格首行标题
     * @param titleList 表头
     * @param parpamtsList 可选择参数列(需与列名一比一)
     */
    public static ResponseEntity<byte[]> exportExcelTemplate(String fileName, String sheetName, List<String> titleList,
                                                             List<List<String>> parpamtsList) throws IOException {
        fileName = fileName+".xls";
        HSSFWorkbook wb = createExcel("sheet1",titleList, parpamtsList);

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
     * 下载一个带下拉选项的excel模板
     * @param fileName 文件名，不要带后缀
     * @param sheetName 表格首行标题
     * @param titleList 表头
     * @param parpamtsList 可选择参数列(需与列名一比一)
     * @param response
     */
    public static void exportExcelTemplate(String fileName,String sheetName,List<String> titleList,
                                           List<List<String>> parpamtsList,HttpServletResponse response) throws IOException {
        fileName = fileName+".xls";
        HSSFWorkbook wb = createExcel("sheet1",titleList, parpamtsList);
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
        response.setContentType("application/msexcel");
        OutputStream output = response.getOutputStream();
        wb.write(output);
        output.close();
    }

    /**
     * 下载Excel模板
     * @param fileName 文件名，不要带后缀
     * @param sheetName 表格首行标题
     * @param titleList 表头
     * @throws IOException
     */
    public static ResponseEntity<byte[]> exportExcelTemplate(String fileName, String sheetName, List<String> titleList) throws IOException {
        return exportExcelTemplate(fileName,sheetName,titleList,new ArrayList<>());
    }

    /**
     * 下载Excel模板
     * @param fileName 文件名，不要带后缀
     * @param sheetName 表格首行标题
     * @param titleList 表头
     * @param response
     * @throws IOException
     */
    public static void exportExcelTemplate(String fileName,String sheetName,List<String> titleList,
                                           HttpServletResponse response) throws IOException {
        exportExcelTemplate(fileName,sheetName,titleList,new ArrayList<>(),response);
    }


    /**
     * @param sheetName sheet页名称,表头
     * @param titleList 列名
     * @param parpamtsList 可选择参数列(需与列名一比一)
     */
    public static HSSFWorkbook createExcel(String sheetName,List<String> titleList,List<List<String>> parpamtsList) {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(sheetName);

        //设置前两行冻结
        sheet.createFreezePane(0,2,0,2);
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

        //设置第二行标题 标题列
        HSSFRow rowTitleName = sheet.createRow(1);
        HSSFCellStyle style = getStyle(wb);
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
                HSSFDataValidation dataValidation = getDataValidationList4Col(sheet,(short) 2,(short)i,(short)10000,(short)i,parpamtsList.get(i),wb);
                // 对sheet页生效
                sheet.addValidationData(dataValidation);
            }
        }
        return wb;
    }

    /**
     * 设置标题样式
     * @param workbook
     * @return
     */
    private static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)12);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        return style;
    }

    /**
     * 列表首页的大title样式
     * @param workbook
     * @return
     */
    private static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)16);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("宋体");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);

        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
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
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
        //将第sheet设置为隐藏
        wbCreat.setSheetHidden(wbCreat.getSheetIndex(sheetName), true);

        sheet.addValidationData(validation);
        return validation;
    }
}
