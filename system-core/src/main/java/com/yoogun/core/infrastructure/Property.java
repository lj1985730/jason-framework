package com.yoogun.core.infrastructure;

import java.io.Serializable;

public class Property implements Serializable {

    /**
     * 类属性名
     */
    private String field;

    /**
     * 数据库字段名
     */
    private String column;

    /**
     * 属性类型
     */
    private Class<?> type;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * 指定类型处理器
     * @return 类型处理器字符串
     */
    public String getTypeHandler() {
        if(this.type == null) {
            return null;
        }

        //boolean型使用自定义
        if(Boolean.class.isAssignableFrom(this.type)) {
            return ",typeHandler = " + BooleanTypeHandler.class.getName();
        }

        //其他类型使用默认值
        return "";
    }
}
