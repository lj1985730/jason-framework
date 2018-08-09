!function ($) {

    'use strict';

    var container = document.getElementById('closedAccount');
    var chart;

    /**
     * chart配置
     */

    var option2 = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b} : {c} ({d}%)"
        },
        legend : {
            orient : 'vertical',
            left : 'left',
            data : [ '建设银行', '招商银行' ]
        },
        series : [
            {
                name : '统计数量',
                type : 'pie',
                radius : '55%',
                center : [ '50%', '60%' ],
                data : [
                    { value : 3, name : '建设银行' },
                    { value : 2, name : '招商银行' }
                ],
                itemStyle : {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };


    $(function() {
        chart = echarts.init(container, null, { renderer : 'canvas' });
        chart.setOption(option2);

        chart.on('click', function (params) {
            if(params.name === '招商银行') {
                chart.setOption();
            } else if(params.name === '建设银行') {
                chart.setOption();
            }
        });

        $('#totalBackBtn2').on('click', function () {
            echarts.dispose(container);
            chart = echarts.init(container, null, { renderer : 'canvas' });
            chart.setOption(option2);

            chart.on('click', function (params) {
                if(params.name === '招商银行') {
                    chart.setOption();
                } else if(params.name === '建设银行') {
                    chart.setOption();
                }
            });
        });
    });
}(window.jQuery);
