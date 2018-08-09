package com.yoogun.chart.infrastructure.echarts;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * chart-echarts-轴类型枚举
 * @author  Liu Jun
 * @since v1.0.0
 */
public enum ChartType {

    LINE("line"),
    BAR("bar"),
    PIE("pie");

    private String type;

    ChartType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return this.type;
    }
}
