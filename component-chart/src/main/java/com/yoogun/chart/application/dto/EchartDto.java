package com.yoogun.chart.application.dto;

import com.yoogun.chart.infrastructure.echarts.*;

import java.util.ArrayList;
import java.util.List;

/**
 * chart-echarts-DTO
 * @author Liu Jun
 * @since v1.0.0
 */
public class EchartDto {

    private Axis xAxis; //x轴
    private Axis yAxis; //y轴

    private List<Series> series = new ArrayList<>();    //数据

    /**
     * 构造器
     * @param xAxis X轴
     * @param yAxis Y轴
     */
    public EchartDto(Axis xAxis, Axis yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    /**
     * 构造器
     * @param xName X轴名称
     * @param xType X轴类型
     * @param yName Y轴名称
     * @param yType Y轴类型
     */
    public EchartDto(String xName, AxisType xType, String yName, AxisType yType) {
        this.xAxis = new Axis(xName, xType);
        this.yAxis = new Axis(yName, yType);
    }

    /**
     * 构造器
     * @param xAxis X轴
     * @param yAxis Y轴
     * @param series 数据集
     */
    public EchartDto(Axis xAxis, Axis yAxis, List<Series> series) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.series = series;
    }

    /**
     * 增加线性数据
     * @param data 数据
     * @return 对象自身
     */
    public EchartDto appendLineData(List<Object[]> data) {
        LineSeries lineSeries = new LineSeries();
        lineSeries.setData(data);
        series.add(lineSeries);
        return this;
    }

    /**
     * 增加柱状数据
     * @param data 数据
     * @return 对象自身
     */
    public EchartDto appendBarData(List<Object[]> data) {
        BarSeries barSeries = new BarSeries();
        barSeries.setData(data);
        series.add(barSeries);
        return this;
    }

    /**
     * 增加数据集
     * @param appended 数据集
     * @return 对象自身
     */
    public EchartDto append(Series appended) {
        series.add(appended);
        return this;
    }

    public Axis getxAxis() {
        return xAxis;
    }

    public void setxAxis(Axis xAxis) {
        this.xAxis = xAxis;
    }

    public Axis getyAxis() {
        return yAxis;
    }

    public void setyAxis(Axis yAxis) {
        this.yAxis = yAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
