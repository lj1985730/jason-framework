!function ($) {

    'use strict';

    var container = document.getElementById('openAccount');
    var chart;

    /**
     * chart配置
     */
    var option = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b} : {c} ({d}%)"
        },
        legend : {
            orient : 'vertical',
            left : 'left',
            data : [ '工商银行', '农业银行', '建设银行' ]
        },
        series : [
            {
                name : '统计数量',
                type : 'pie',
                radius : '55%',
                center : [ '50%', '60%' ],
                data : [
                    { value : 3, name : '工商银行' },
                    { value : 5, name : '农业银行' },
                    { value : 2, name : '建设银行' }
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
        chart.setOption(option);

        chart.on('click', function (params) {
            if(params.name === '工商银行') {
                chart.setOption(assetOption);
            } else if(params.name === '农业银行') {
                chart.setOption(ownerOption);
            } else if(params.name === '建设银行') {
                chart.setOption(debtOption);
            }
        });

        $('#totalBackBtn').on('click', function () {
            echarts.dispose(container);
            chart = echarts.init(container, null, { renderer : 'canvas' });
            chart.setOption(option);

            chart.on('click', function (params) {
                if(params.name === '工商银行') {
                    chart.setOption(assetOption);
                } else if(params.name === '农业银行') {
                    chart.setOption(ownerOption);
                } else if(params.name === '建设银行') {
                    chart.setOption(debtOption);
                }
            });
        });
    });
}(window.jQuery);
