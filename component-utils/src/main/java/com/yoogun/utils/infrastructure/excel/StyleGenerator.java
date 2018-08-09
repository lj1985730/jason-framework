package com.yoogun.utils.infrastructure.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

public class StyleGenerator {

    private Workbook workbook;    //标题样式
    private CellStyle title;    //标题样式
    private CellStyle head; //列头样式
    private CellStyle data; //数据样式
    private CellStyle leftData; //左对齐数据样式
    private CellStyle centerData; //居中数据样式
    private CellStyle rightData; //右对齐数据样式
    private Map<String, CellStyle> styleCache;

    /**
     * 构造器
     * @param workbook excel工作簿
     */
    public StyleGenerator(Workbook workbook) {
        this.workbook = workbook;
        this.title = this.createTitleStyle();
        this.head = this.createHeadStyle();
        this.data = this.createDataStyle(null);
        this.leftData = this.createDataStyle(CellStyle.ALIGN_LEFT);
        this.centerData = this.createDataStyle(CellStyle.ALIGN_CENTER);
        this.rightData = this.createDataStyle(CellStyle.ALIGN_RIGHT);
        this.styleCache = new HashMap<>();
    }

    /**
     * 获取标题样式
     * @return 标题样式
     */
    public CellStyle titleStyle() {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(this.title);
        return style;
    }

    /**
     * 获取列头样式
     * @return 列头样式
     */
    public CellStyle headStyle() {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(this.head);
        return style;
    }

    /**
     * 获取数据样式
     * @return 数据样式
     */
    public CellStyle dataStyle() {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(this.data);
        return style;
    }

    /**
     * 获取左对齐数据样式
     * @return 左对齐数据样式
     */
    public CellStyle leftDataStyle() {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(this.leftData);
        return style;
    }

    /**
     * 获取居中对齐数据样式
     * @return 居中对齐数据样式
     */
    public CellStyle centerDataStyle() {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(this.centerData);
        return style;
    }

    /**
     * 获取右对齐数据样式
     * @return 右对齐数据样式
     */
    public CellStyle rightDataStyle() {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(this.rightData);
        return style;
    }

    /**
     * 创建标题样式
     * @return 标题样式
     */
    private CellStyle createTitleStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);	//水平居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);	//垂直居中
        Font titleFont = workbook.createFont();	//标题字体
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        return style;
    }

    /**
     * 创建列头样式
     * @return 列头样式
     */
    private CellStyle createHeadStyle() {
        CellStyle style = workbook.createCellStyle();

        this.addCommonStyle(style);

//		style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);

        return style;
    }

    /**
     * 创建数据样式
     * @param alignment	水平对齐方式
     * @return 数据样式
     */
    private CellStyle createDataStyle(Short alignment) {
        CellStyle style = workbook.createCellStyle();

        this.addCommonStyle(style);

        if(alignment != null) {	//水平对齐方式
            style.setAlignment(alignment);
        }

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        return style;
    }

    /**
     * 增加通用样式
     * @param style 样式对象
     */
    private void addCommonStyle(CellStyle style) {
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);	//垂直居中
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
    }

    /**
     *  缓存样式
     * @param name 缓存名称
     * @param style 样式
     */
    public void putStyle(String name, CellStyle style) {
        this.styleCache.put(name, style);
    }

    /**
     *  读取缓存中样式
     * @param name 缓存名称
     * @return 样式
     */
    public CellStyle getStyle(String name) {
        return this.styleCache.get(name);
    }
}
