package com.yoogun.chart.infrastructure.echarts;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * chart-echarts-轴类型枚举
 * @author  Liu Jun
 * @since v1.0.0
 */
public enum  AxisType {

    VALUE("value"),
    CATEGORY("category"),
    TIME("time"),
    LOG("log");

    private String type;

    AxisType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return this.type;
    }
}
