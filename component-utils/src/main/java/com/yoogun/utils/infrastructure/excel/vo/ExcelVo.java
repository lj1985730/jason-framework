package com.yoogun.utils.infrastructure.excel.vo;

import com.yoogun.utils.infrastructure.excel.annotation.ExcelExport;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelImport;

import java.lang.reflect.Field;

/**
 *  excel属性值对象，包含excel配置注解和属性
 *  @author  Liu Jun at 2018-3-21 13:53:55
 *  @since v1.0.0
 */
public class ExcelVo {

    /**
     *  属性
     */
    private Field field;

    /**
     *  导入注解
     */
    private ExcelImport importAnnotation;

    /**
     *  导出注解
     */
    private ExcelExport exportAnnotation;

    /**
     * 构造器-导出
     * @param field 属性
     * @param annotation 导出注解
     */
    public ExcelVo(Field field, ExcelExport annotation) {
        this.field = field;
        this.exportAnnotation = annotation;
    }

    /**
     * 构造器-导入
     * @param field 属性
     * @param annotation 导入注解
     */
    public ExcelVo(Field field, ExcelImport annotation) {
        this.field = field;
        this.importAnnotation = annotation;
    }

    public Field getField() {
        return field;
    }

    public ExcelImport getImportAnnotation() {
        return importAnnotation;
    }

    public ExcelExport getExportAnnotation() {
        return exportAnnotation;
    }
}
