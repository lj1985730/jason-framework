package com.yoogun.auth.infrastructure;

import java.util.Properties;

/**
 * 配置加载器
 */
public class PropertiesLoader {

    public static Properties properties;	//配置对象

    public static void setProperties(Properties properties) {
        PropertiesLoader.properties = properties;
    }
}
