!function ($) {

    'use strict';

    var container = document.getElementById('netAssetYieldContainer');
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
            data : [ '本年净资产收益率', '上年净资产收益率' ]
        },
        // toolbox : {
        //     feature : {
        //         saveAsImage : {}
        //     }
        // },
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
                name : '本年净资产收益率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 0.3, 0.1, 0.35, 0.2, 0.5, 0.4, 2, 0.7 ]
            },
            {
                name : '上年净资产收益率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 0.4, 0.3, 0.2, 0.7, 0.5, 0.5, 1.6, 0.3 ]
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, { renderer : 'canvas' });
        chart.setOption(option);
    });
}(window.jQuery);
