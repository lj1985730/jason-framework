!function ($) {

    'use strict';

    var container = document.getElementById('netProfitRatioContainer');
    var chart;

    /**
     * chart配置
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
            data : [ '本年销售净利率', '上年销售净利率' ]
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
                name : '本年销售净利率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 3.5, 0.5, 3.5, 1, 4, 2, 8.8, 6.7 ]
            },
            {
                name : '上年销售净利率',
                type : 'line',
                smooth : true,  //这句就是让曲线变平滑的
                data : [ 7.6, 4.5, 2.9, 5.5, 4, 3.7, 9, 3.5 ]
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(option);
    });
}(window.jQuery);
