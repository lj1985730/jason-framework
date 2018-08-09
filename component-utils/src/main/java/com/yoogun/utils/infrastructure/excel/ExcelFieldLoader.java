package com.yoogun.utils.infrastructure.excel;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelExport;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelImport;
import com.yoogun.utils.infrastructure.excel.vo.ExcelVo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * excel属性加载器
 *  支持按组加载，安导入导出加载
 *  @author  Liu Jun at 2018-3-21 14:00:04
 *  @since v1.0.0
 */
public class ExcelFieldLoader {

    /**
     * 读取全部导出excel属性
     * @param clazz 属性
     * @param groups 分组
     * @return 全部导出excel属性集合
     */
    public static <E extends BaseEntity> List<ExcelVo> loadExport(Class<E> clazz, String... groups) {
        Field[] fields = clazz.getDeclaredFields(); //读取实体全部属性

        List<ExcelVo> results = new ArrayList<>();

        for (Field field : fields) {
            ExcelExport annotation = field.getAnnotation(ExcelExport.class);
            // 忽略掉没有ExcelExport注解的属性
            if(annotation == null) {
                continue;
            }

            if (groups == null || groups.length == 0) { //没分组的话，直接加入结果集
                results.add(new ExcelVo(field, annotation));
            } else { //有分组的话，按照分组过滤
                boolean inGroup = false;
                for(String group : groups) {
                    if (inGroup) {
                        break;
                    }
                    for (String annotationGroup : annotation.groups()) {   //比较注解中的分组设置和需要的分组
                        if (group.equals(annotationGroup)) {
                            inGroup = true;
                            results.add(new ExcelVo(field, annotation));
                            break;
                        }
                    }
                }
            }
        }

        // 最终按照注解中的sort值排序
        results.sort(Comparator.comparingInt(vo -> vo.getExportAnnotation().sort()));

        return results;
    }

    /**
     * 读取全部导入excel属性
     * @param clazz 属性
     * @param groups 分组
     * @return 全部导入excel属性集合
     */
    public static <E extends BaseEntity> List<ExcelVo> loadImport(Class<E> clazz, String... groups) {
        Field[] fields = clazz.getDeclaredFields(); //读取实体全部属性

        List<ExcelVo> results = new ArrayList<>();

        for (Field field : fields) {
            ExcelImport annotation = field.getAnnotation(ExcelImport.class);
            // 忽略掉没有ExcelExport注解的属性
            if(annotation == null) {
                continue;
            }

            if (groups == null || groups.length == 0) { //没分组的话，直接加入结果集
                results.add(new ExcelVo(field, annotation));
            } else {    //有分组的话，按照分组过滤
                boolean inGroup = false;
                for(String group : groups) {
                    if (inGroup) {
                        break;
                    }
                    for (String annotationGroup : annotation.groups()) {   //比较注解中的分组设置和需要的分组
                        if (group.equals(annotationGroup)) {
                            results.add(new ExcelVo(field, annotation));
                            inGroup = true;
                            break;
                        }
                    }
                }
            }
        }

        // 最终按照注解中的sort值排序
        results.sort(Comparator.comparingInt(vo -> vo.getImportAnnotation().sort()));

        return results;
    }

}
