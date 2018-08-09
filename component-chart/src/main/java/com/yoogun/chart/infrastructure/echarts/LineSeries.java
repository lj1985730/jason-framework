package com.yoogun.chart.infrastructure.echarts;

/**
 * chart-echarts-线性数据格式
 * @author Liu Jun
 * @since v1.0.0
 */
public class LineSeries extends Series {

    private String symbol = "emptyCircle";

    public LineSeries() {
        super();
        type = ChartType.LINE;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
