package com.yoogun.initialize.infrastructure;

import org.apache.commons.lang3.StringUtils;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 脚本处理器
 * @author Liu Jun
 * @since v2.1.2
 */
public class SqlProcessor {

    public static Map<String, List<Integer>> lobIndexCache = new Hashtable<>();

    /**
     * 读取插入值集合
     * @param insertSql 插入sql
     * @return 插入值集合
     */
    public static String[] readValues(String insertSql) {
        if(!insertSql.startsWith( "INSERT INTO " )) {
            return null;
        }

        String subStr = StringUtils.substringAfter(insertSql, "VALUES (");
        String valuesStr = StringUtils.substringBeforeLast(subStr, ");");
        return valuesStr.split(",");
    }

    /**
     * 判断UUID格式（022B575C-FF66-486A-AF7E-94E43F061D6C）
     * @param value 2017-01-01 00:00:00
     * @return true 是；false 否
     */
    public static boolean isUUID(String value) {
        return !StringUtils.isBlank( value ) && value.length() == 36 && value.charAt( 8 ) == '-' && value.charAt( 13 ) == '-' && value.charAt( 18 ) == '-' && value.charAt( 23 ) == '-';
    }

    /**
     * 判断时间格式
     * @param value 2017-01-01 00:00:00
     * @return true 是；false 否
     */
    public static boolean isTime(String value) {
        return !StringUtils.isBlank( value ) && value.length() == 19 && value.startsWith( "20" ) && value.charAt( 4 ) == '-' && value.charAt( 7 ) == '-';
    }

    /**
     * 判断LOB格式
     * @param value lob
     * @return true 是；false 否
     */
    static boolean isBlob(String value) {
        return !StringUtils.isBlank( value ) && value.startsWith( "0x" );
    }
}
