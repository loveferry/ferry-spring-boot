package cn.org.ferry.sys.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

/**
 * excel 导入导出的配置类
 */

public class ExcelConfig {
    public static final String FIELD_HEADER = "header";
    public static final String FIELD_FILENAME = "fileName";
    public static final String FIELD_SHEETNAME = "sheetName";
    public static final String FIELD_SHEETSIZE = "sheetSize";
    public static final String FIELD_COLUMNS = "columns";

    private String header;

    private String fileName;

    private String sheetName;

    private int sheetSize;

    private JSONArray columns;

    private DataFormat format;

    private List<CellStyle> styles;

    public ExcelConfig() {
        fileName = "未命名.xlsx";
        sheetName = "sheet1";
        sheetSize = 100000;
    }

    public void init(SXSSFWorkbook sheets) {
        format = sheets.createDataFormat();
        styles = new ArrayList<>(10);
        CellStyle style = sheets.createCellStyle();
        style.setDataFormat(format.getFormat("@"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.add(style);
    }

    public CellStyle getCellStyle(SXSSFWorkbook sheets, short dataFormat, HorizontalAlignment alignment){
        for (CellStyle style : styles) {
            if(style.getDataFormat() == dataFormat && style.getAlignment().getCode() == alignment.getCode()){
                return style;
            }
        }
        CellStyle style = sheets.createCellStyle();
        style.setDataFormat(dataFormat);
        style.setAlignment(alignment);
        styles.add(style);
        return style;
    }

    public static String getFieldHeader() {
        return FIELD_HEADER;
    }

    public static String getFieldFilename() {
        return FIELD_FILENAME;
    }

    public static String getFieldSheetname() {
        return FIELD_SHEETNAME;
    }

    public static String getFieldSheetsize() {
        return FIELD_SHEETSIZE;
    }

    public static String getFieldColumns() {
        return FIELD_COLUMNS;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetSize() {
        return sheetSize;
    }

    public void setSheetSize(int sheetSize) {
        this.sheetSize = sheetSize;
    }

    public JSONArray getColumns() {
        return columns;
    }

    public JSONObject getColumn(int index){
        return this.columns.getJSONObject(index);
    }

    public void setColumns(JSONArray columns) {
        this.columns = columns;
    }

    public DataFormat getFormat() {
        return format;
    }

    public void setFormat(DataFormat format) {
        this.format = format;
    }

    public List<CellStyle> getStyles() {
        return styles;
    }

    public void setStyles(List<CellStyle> styles) {
        this.styles = styles;
    }
}
