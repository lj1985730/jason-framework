package com.yoogun.chart.infrastructure.echarts;

import java.util.ArrayList;
import java.util.List;

/**
 * chart-echarts-抽象数据格式
 * @author Liu Jun
 * @since v1.0.0
 */
public abstract class Series {

    protected ChartType type;
    protected String name;
    protected Integer xAxisIndex = 0;
    protected Integer yAxisIndex = 0;
    protected String stack;

    protected List<Object[]> data = new ArrayList<>();

    public ChartType getType() {
        return type;
    }

    public void setType(ChartType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getxAxisIndex() {
        return xAxisIndex;
    }

    public void setxAxisIndex(Integer xAxisIndex) {
        this.xAxisIndex = xAxisIndex;
    }

    public Integer getyAxisIndex() {
        return yAxisIndex;
    }

    public void setyAxisIndex(Integer yAxisIndex) {
        this.yAxisIndex = yAxisIndex;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }
}
