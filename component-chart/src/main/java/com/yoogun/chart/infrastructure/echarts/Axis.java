package com.yoogun.chart.infrastructure.echarts;

/**
 *  chart-echarts-轴对象
 * @author Liu Jun
 * @since v1.0.0
 */
public class Axis {

    private String name;    //轴名称
    private AxisType type;  //轴类型

    /**
     * 构造器
     * @param type 轴类型
     * @param name 轴名称
     */
    public Axis(String name, AxisType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AxisType getType() {
        return type;
    }

    public void setType(AxisType type) {
        this.type = type;
    }
}
