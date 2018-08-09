!function ($) {

    'use strict';

    var container = document.getElementById('balanceAmountContainer');
    var chart;

    var dataYxis = [ '盐化', '自来水', '资源', '国投', '大装备', '金重', '大橡塑', '三寰集团', '建投', '大连港', '大化' ];

    /**
     * chart配置
     */
    var option = {
        tooltip : {
            trigger : 'axis',
            axisPointer: {
                type : 'shadow'
            }
        },
        grid: {
            top : '1%',
            left : '1%',
            right : '4%',
            bottom : '1%',
            containLabel : true
        },
        xAxis : {
            type : 'value',
            interval : 1000000
        },
        yAxis : {
            type : 'category',
            data : dataYxis
        },
        dataZoom : [{
            type : 'inside',
            orient : "vertical"
        }],
        series : [
            {
                type : 'bar',
                name : '大额资金余额',
                itemStyle : {
                    normal : { color: 'rgba(0,0,0,0.05)' }
                },
                barGap : '-100%',
                barCategoryGap : '40%',
                label : {
                    normal: {
                        show: true,
                        position: 'right'
                    }
                },
                data : [ 2500000, 2500000, 2500000, 2500000, 2500000, 2500000, 2500000, 2500000, 2500000, 2500000, 2500000 ]
            },
            {
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 1, 0,
                            [
                                {offset : 0, color : '#83bff6'},
                                {offset : 0.5, color : '#188df0'},
                                {offset : 1, color : '#188df0'}
                            ]
                        )
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 1, 0,
                            [
                                {offset : 0, color : '#2378f7'},
                                {offset : 0.7, color : '#2378f7'},
                                {offset : 1, color : '#83bff6'}
                            ]
                        )
                    }
                },
                data : [ 13200, 19121, 33443, 37206, 57000, 61285, 131647, 234306, 503609, 906325, 2174053]
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(option);

        // Enable data zoom when user click bar.
        var zoomSize = 6;
        chart.on('click', function (params) {
            chart.dispatchAction({
                type : 'dataZoom',
                startValue : dataYxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
                endValue : dataYxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
            });
        });
    });
}(window.jQuery);
