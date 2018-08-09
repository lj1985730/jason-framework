!function ($) {

    'use strict';

    var container = document.getElementById('costProfitRatioContainer');
    var chart;

    /**
     * chart配置
     * @type {{tooltip: {trigger: string, axisPointer: {type: string, label: {backgroundColor: string}}}, legend: {align: string, data: [string,string]}, grid: {left: string, right: string, bottom: string, containLabel: boolean}, xAxis: [null], yAxis: [null], series: [null,null]}}
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
            data : [ '本年成本费用利润率', '上年成本费用利润率' ]
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
                name : '本年成本费用利润率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 4, 0.5, 4.4, 1.8, 5.5, 3, 18, 13 ]
            },
            {
                name : '上年成本费用利润率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 15.5, 9, 6, 9, 7.5, 7, 17.5, 4 ]
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(option);
    });
}(window.jQuery);
