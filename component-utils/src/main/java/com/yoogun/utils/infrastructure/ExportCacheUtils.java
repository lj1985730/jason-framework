package com.yoogun.utils.infrastructure;

public class ExportCacheUtils {

    public static final String EXPORT_CACHE_NAME = "exportExcelCache";

    /**
     * 获取导出缓存，用于通用导出excel
     * @param key 缓存KEY
     * @return 系统缓存值
     */
    public static Object get(String key) {
        return EhCacheUtils.get(EXPORT_CACHE_NAME, key);
    }

    /**
     * 写入导出缓存，通用导出excel
     * @param key 缓存KEY
     */
    public static void put(String key, Object value) {
        EhCacheUtils.put(EXPORT_CACHE_NAME, key, value);
    }

    /**
     * 删除导出缓存，通用导出excel
     * @param key 缓存KEY
     */
    public static void remove(String key) {
        EhCacheUtils.remove(EXPORT_CACHE_NAME, key);
    }
}
