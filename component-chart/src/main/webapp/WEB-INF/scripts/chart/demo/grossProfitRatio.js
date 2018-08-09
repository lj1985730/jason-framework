!function ($) {

    'use strict';

    var container = document.getElementById('grossProfitRatioContainer');
    var chart;

    /**
     * chart配置
     * @type {{tooltip: {trigger: string, axisPointer: {type: string, label: {backgroundColor: string}}}, legend: {data: [string,string]}, grid: {left: string, right: string, bottom: string, containLabel: boolean}, xAxis: [null], yAxis: [null], series: [null,null]}}
     */
    var option = {
        tooltip : {
            trigger : 'axis',
            axisPointer: {
                type : 'cross',
                label : {
                    backgroundColor : '#6a7985'
                }
            }
        },
        legend : {
            align : 'right',
            data : [ '本年销售毛利率', '上年销售毛利率' ]
        },
        grid: {
            top : '10%',
            left : '1%',
            right : '3%',
            bottom : '1%',
            containLabel: true
        },
        xAxis : [{
            type : 'category',
            boundaryGap : false,
            data : [ '2017-01', '2017-02', '2017-03', '2017-04', '2017-05', '2017-06', '2017-07', '2017-08' ]
        }],
        yAxis : [{
            type : 'value',
            axisLabel : {
                show : true,
                interval : 'auto',
                formatter : '{value} %'
            }
        }],
        series : [
            {
                name : '本年销售毛利率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 42, 39, 37, 28, 29, 28, 35, 26 ]
            },
            {
                name : '上年销售毛利率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 28, 27, 30, 29, 31, 24, 33, 29 ]
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(option);
    });
}(window.jQuery);
