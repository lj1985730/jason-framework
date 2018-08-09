!function ($) {

    'use strict';

    var container = document.getElementById('totalAssetRewardRatioContainer');
    var chart;

    /**
     * chart配置
     * @type {{tooltip: {trigger: string, axisPointer: {type: string, label: {backgroundColor: string}}}, legend: {align: string, data: [string,string]}, grid: {top: string, left: string, right: string, bottom: string, containLabel: boolean}, xAxis: [null], yAxis: [null], series: [null,null]}}
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
            data : [ '本年总资产报酬率', '上年总资产报酬率' ]
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
                name : '本年总资产报酬率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 0.4, 0.35, 0.5, 0.5, 0.8, 0.8, 2.3, 0.8 ]
            },
            {
                name : '上年总资产报酬率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 0.6, 0.55, 0.5, 1.1, 1, 1.2, 2.1, 0.46 ]
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(option);
    });
}(window.jQuery);
