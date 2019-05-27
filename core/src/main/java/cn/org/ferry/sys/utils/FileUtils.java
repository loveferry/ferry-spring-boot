package cn.org.ferry.sys.utils;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.exception.ExcelException;
import cn.org.ferry.system.utils.BeanUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static final int ROW_ACCESS_WINDOW_SIZE = 100;

    /**
     * excel导出
     * 使用 apache 的 poi 进行 Excel 的导出
     * SXSSFWorkbook 对象支持大数据量的导入导出,但是只支持.xlsx格式的导出,不支持.xls格式
     * 导出的数据对象为BaseDTO的子类集合
     * @param list     导出的数据集合
     * @param config   excel 导出的配置信息
     */
    public static void exportExcelSheetByBaseDTO(String sheetName, List<? extends BaseDTO> list, ExcelConfig config, SXSSFWorkbook sheets) {
        if (null == config) {
            config = new ExcelConfig();
        }
        int sheetCount = list.size() % config.getSheetSize() > 0 ? list.size() / config.getSheetSize() + 1 : list.size() / config.getSheetSize();
        for (int page = 0; page < sheetCount; page++) {
            int lastIndex = config.getSheetSize()*(page+1);
            if(lastIndex>list.size()){
                lastIndex = list.size();
            }
            SXSSFSheet sheet;
            try {
                sheet = sheets.createSheet(sheetName);
            }catch (IllegalArgumentException e){
                sheet = sheets.createSheet(config.getSheetName()+(page+1));
            }
            createSheetByBaseDTO(list.subList(config.getSheetSize()*page, lastIndex), sheet, config);
        }
    }

    /**
     * excel导出
     * 使用 apache 的 poi 进行 Excel 的导出
     * SXSSFWorkbook 对象支持大数据量的导入导出,但是只支持.xlsx格式的导出,不支持.xls格式
     * 导出的数据对象为BaseDTO的子类集合
     * @param list     导出的数据集合
     * @param config   excel 导出的配置信息
     */
    public static void exportExcelSheetByMap(String sheetName, List<Map<String, Object>> list, ExcelConfig config, SXSSFWorkbook sheets) {
        if (null == config) {
            config = new ExcelConfig();
        }
        int sheetCount = list.size() % config.getSheetSize() > 0 ? list.size() / config.getSheetSize() + 1 : list.size() / config.getSheetSize();
        for (int page = 0; page < sheetCount; page++) {
            int lastIndex = config.getSheetSize()*(page+1);
            if(lastIndex>list.size()){
                lastIndex = list.size();
            }
            SXSSFSheet sheet;
            try {
                sheet = sheets.createSheet(sheetName);
            }catch (IllegalArgumentException e){
                sheet = sheets.createSheet(config.getSheetName()+(page+1));
            }
            createSheetByMap(list.subList(config.getSheetSize()*page, lastIndex), sheet, config);
        }
    }

    /**
     * excel导出
     * 使用 apache 的 poi 进行 Excel 的导出
     * SXSSFWorkbook 对象支持大数据量的导入导出,但是只支持.xlsx格式的导出,不支持.xls格式
     * 导出的数据对象为BaseDTO的子类集合
     * @param response http响应对象
     * @param list     导出的数据集合
     * @param config   excel 导出的配置信息
     */
    public static void exportExcelForXLSXByBaseDTO(HttpServletResponse response, List<? extends BaseDTO> list, ExcelConfig config) {
        if(null == list){
            throw new ExcelException("数据集为空!");
        }
        if (null == config) {
            config = new ExcelConfig();
        }
        // 设置内存中保存的最大值,超过则将内存中数据刷入磁盘中
        SXSSFWorkbook sheets = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
        config.init(sheets);
        int sheetCount = list.size() % config.getSheetSize() > 0 ? list.size() / config.getSheetSize() + 1 : list.size() / config.getSheetSize();
        for (int page = 0; page < sheetCount; page++) {
            int lastIndex = config.getSheetSize()*(page+1);
            if(lastIndex>list.size()){
                lastIndex = list.size();
            }
            SXSSFSheet sheet;
            try {
                sheet = sheets.createSheet(config.getSheetName());
            }catch (IllegalArgumentException e){
                sheet = sheets.createSheet(config.getSheetName()+(page+1));
            }
            createSheetByBaseDTO(list.subList(config.getSheetSize()*page, lastIndex), sheet, config);
        }
        outputResponse(response, sheets, config.getFileName());
    }

    /**
     * excel导出
     * 使用 apache 的 poi 进行 Excel 的导出
     * SXSSFWorkbook 对象支持大数据量的导入导出,但是只支持.xlsx格式的导出,不支持.xls格式
     * 导出的数据对象为Map的集合
     * @param response http响应对象
     * @param list     导出的数据集合
     * @param config   excel 导出的配置信息
     */
    public static void exportExcelForXLSXByMap(HttpServletResponse response, List<Map<String, Object>> list, ExcelConfig config) {
        if (null == list) {
            list = new ArrayList<>();
        }
        if (null == config) {
            config = new ExcelConfig();
        }
        // 设置内存中保存的最大值,超过则将内存中数据刷入磁盘中
        SXSSFWorkbook sheets = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
        config.init(sheets);

        int sheetCount = list.size() % config.getSheetSize() > 0 ? list.size() / config.getSheetSize() + 1 : list.size() / config.getSheetSize();
        for (int page = 0; page < sheetCount; page++) {
            int lastIndex = config.getSheetSize()*(page+1);
            if(lastIndex>list.size()){
                lastIndex = list.size();
            }
            SXSSFSheet sheet;
            try {
                sheet = sheets.createSheet(config.getSheetName());
            }catch (IllegalArgumentException e){
                sheet = sheets.createSheet(config.getSheetName()+(page+1));
            }
            createSheetByMap(list.subList(config.getSheetSize()*page, lastIndex), sheet, config);
        }
        outputResponse(response, sheets, config.getFileName());
    }

    public static void outputResponse(HttpServletResponse response, SXSSFWorkbook sheets, String fileName){
        try (ServletOutputStream os = response.getOutputStream()){
            response.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            response.setContentType(sheets.getXSSFWorkbook().getWorkbookType().getContentType()+";charset=" + "UTF-8");
            response.setHeader("Accept-Ranges", "bytes");
            sheets.write(os);
            sheets.dispose();
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的字符转化格式");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("excel导出错误!");
            e.printStackTrace();
        }
    }

    private static void createSheetByBaseDTO(List<? extends BaseDTO> list, SXSSFSheet sheet, ExcelConfig config){
        int columnsSize = config.getColumns().size();
        int rowIndex = setHeaderRow(config, sheet, columnsSize);
        // 行循环
        try {
            for (BaseDTO dto : list) {
                SXSSFRow row = sheet.createRow(++rowIndex);
                // 列循环
                for(int cellIndex = 0; cellIndex < columnsSize; cellIndex++){
                    // 当前列的配置信息
                    JSONObject jsonObject = config.getColumn(cellIndex);
                    String fieldName = jsonObject.getString("field");
                    Object value = dto.getClass().getDeclaredMethod(BeanUtils.getMethodName(fieldName)).invoke(dto);
                    CellStyle cellStyle = getCellStyleOrSetDefaultCellStyle(row, config, value, jsonObject.getString("align"), jsonObject.getString("type"));
                    createCell(row.createCell(cellIndex), value, cellStyle);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void createSheetByMap(List<Map<String, Object>> list, SXSSFSheet sheet, ExcelConfig config){
        int columnsSize = config.getColumns().size();
        int rowIndex = setHeaderRow(config, sheet, columnsSize);
        // 行循环
        for (Map<String, Object> map : list) {
            SXSSFRow row = sheet.createRow(++rowIndex);
            // 列循环
            for(int cellIndex = 0; cellIndex < columnsSize; cellIndex++){
                // 当前列的配置信息
                JSONObject jsonObject = config.getColumn(cellIndex);
                // 定位当前单元格
                for(Map.Entry<String, Object> entry : map.entrySet()){
                    if(StringUtils.equalsIgnoreCase(jsonObject.getString("field"), entry.getKey())){
                        CellStyle cellStyle = getCellStyleOrSetDefaultCellStyle(row, config, entry.getValue(), jsonObject.getString("align"), jsonObject.getString("type"));
                        createCell(row.createCell(cellIndex), entry.getValue(), cellStyle);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 设置sheet页的标题，表头
     * @return 返回当前行的行下标
     */
    private static int setHeaderRow(ExcelConfig config, SXSSFSheet sheet, int columnsSize){
        int rowIndex = 0;
        // 字段头的样式
        CellStyle fieldStyle = config.getCellStyle(sheet.getWorkbook(), config.getFormat().getFormat("@"), HorizontalAlignment.CENTER);
        SXSSFRow fieldRow;
        if(StringUtils.isNotEmpty(config.getHeader())){
            sheet.addMergedRegion(new CellRangeAddress(0,0, 0, columnsSize-1));
            SXSSFRow headerRow = sheet.createRow(rowIndex++);
            createCell(headerRow.createCell(0), config.getHeader(), fieldStyle);
            fieldRow = sheet.createRow(rowIndex);
        }else {
            fieldRow = sheet.createRow(rowIndex);
        }
        // 设置字段头
        for(int cellIndex = 0; cellIndex < columnsSize; cellIndex++){
            JSONObject jsonObject = config.getColumn(cellIndex);
            createCell(fieldRow.createCell(cellIndex), jsonObject.getString("description"), fieldStyle);
            if(jsonObject.getInteger("width") != null){
                sheet.setColumnWidth(cellIndex, jsonObject.getIntValue("width")*30);
            }
        }
        return rowIndex;
    }

    private static CellStyle getCellStyleOrSetDefaultCellStyle(SXSSFRow row, ExcelConfig config, Object value, String align, String type){
        HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
        short dataFormat = config.getFormat().getFormat("General");
        if(StringUtils.equalsIgnoreCase("CENTER",align)){
            horizontalAlignment = HorizontalAlignment.CENTER;
        }else if(StringUtils.equalsIgnoreCase("RIGHT", align)){
            horizontalAlignment = HorizontalAlignment.RIGHT;
        }
        if(StringUtils.equalsIgnoreCase("STRING", type)){
            dataFormat = config.getFormat().getFormat("@");
        } else if(StringUtils.equalsIgnoreCase("NUMERIC", type)){
            if(BeanUtils.isIntegerNumber(value)){
                dataFormat = config.getFormat().getFormat("0");
            }else if(BeanUtils.isFloatNumber(value)){
                dataFormat = config.getFormat().getFormat("#,##0.00");
            }else if(BeanUtils.isDate(value)){
                dataFormat = config.getFormat().getFormat("m/d/yy");
            }
        }else if(StringUtils.equalsIgnoreCase("DATE", type)){
            dataFormat = config.getFormat().getFormat("m/d/yy");
        }
        return config.getCellStyle(row.getSheet().getWorkbook(), dataFormat, horizontalAlignment);
    }

    private static void createCell(SXSSFCell cell, Object value, CellStyle style){
        if(value == null){
            return ;
        }
        if(value instanceof String){
            cell.setCellValue(value.toString());
        }else if(BeanUtils.isIntegerNumber(value)){
            cell.setCellValue(Long.parseLong(value.toString()));
        }else if(BeanUtils.isFloatNumber(value)){
            cell.setCellValue(Double.parseDouble(value.toString()));
        }else if(BeanUtils.isDate(value)){
            cell.setCellValue((Date) value);
        }else{
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }
}
