!function ($) {

    'use strict';

    var container = document.getElementById('assetTotalContainer');
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
            data : [ '资产总额', '所有者权益总额', '负债总额' ]
        },
        series : [
            {
                name : '资产比例',
                type : 'pie',
                radius : '55%',
                center : [ '50%', '60%' ],
                data : [
                    { value : 92015.75, name : '资产总额' },
                    { value : 46138.86, name : '负债总额' },
                    { value : 45876.89, name : '所有者权益总额' }
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

    var assetOption = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b} : {c} ({d}%)"
        },
        legend : {
            orient : 'horizontal',
            left : 'left',
            data : [ '重工起重', '国投集团', '会展集团', '瓦轴集团', '热电集团', '资源集团', '国讯科技', '装备资讯', '装备小额贷', '辽无二电' ]
        },
        series : [
            {
                name : '资产比例',
                type : 'pie',
                radius : '55%',
                center : ['50%', '60%'],
                roseType : 'radius',
                data : [
                    { value : 6201.75, name : '重工起重' },
                    { value : 4613.86, name : '国投集团' },
                    { value : 4587.89, name : '会展集团' },
                    { value : 4576.89, name : '瓦轴集团' },
                    { value : 2876.89, name : '热电集团' },
                    { value : 1576.89, name : '资源集团' },
                    { value : 2776.89, name : '国讯科技' },
                    { value : 1876.89, name : '装备资讯' },
                    { value : 1536.89, name : '装备小额贷' },
                    { value : 3876.89, name : '辽无二电' }
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

    var ownerOption = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b} : {c} ({d}%)"
        },
        legend : {
            orient : 'horizontal',
            // orient : 'vertical',
            align : 'right',
            data : [ '重工起重', '国投集团', '会展集团', '瓦轴集团', '热电集团', '资源集团', '国讯科技', '装备资讯', '装备小额贷', '辽无二电' ]
        },
        series : [
            {
                name : '资产比例',
                type : 'pie',
                radius : '55%',
                center : ['50%', '60%'],
                roseType : 'radius',
                data : [
                    { value : (Math.random() * 4500).toFixed(2), name : '重工起重' },
                    { value : (Math.random() * 4500).toFixed(2), name : '国投集团' },
                    { value : (Math.random() * 4500).toFixed(2), name : '会展集团' },
                    { value : (Math.random() * 4500).toFixed(2), name : '瓦轴集团' },
                    { value : (Math.random() * 4500).toFixed(2), name : '热电集团' },
                    { value : (Math.random() * 4500).toFixed(2), name : '资源集团' },
                    { value : (Math.random() * 4500).toFixed(2), name : '国讯科技' },
                    { value : (Math.random() * 4500).toFixed(2), name : '装备资讯' },
                    { value : (Math.random() * 4500).toFixed(2), name : '装备小额贷' },
                    { value : (Math.random() * 4500).toFixed(2), name : '辽无二电' }
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

    var debtOption = {
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b} : {c} ({d}%)"
        },
        legend : {
            orient : 'horizontal',
            left : 'left',
            data : [ '重工起重', '国投集团', '会展集团', '瓦轴集团', '热电集团', '资源集团', '国讯科技', '装备资讯', '装备小额贷', '辽无二电' ]
        },
        series : [
            {
                name : '资产比例',
                type : 'pie',
                radius : '55%',
                center : ['50%', '60%'],
                roseType : 'radius',
                data : [
                    { value : (Math.random() * 5000).toFixed(2), name : '重工起重' },
                    { value : (Math.random() * 5000).toFixed(2), name : '国投集团' },
                    { value : (Math.random() * 5000).toFixed(2), name : '会展集团' },
                    { value : (Math.random() * 5000).toFixed(2), name : '瓦轴集团' },
                    { value : (Math.random() * 5000).toFixed(2), name : '热电集团' },
                    { value : (Math.random() * 5000).toFixed(2), name : '资源集团' },
                    { value : (Math.random() * 5000).toFixed(2), name : '国讯科技' },
                    { value : (Math.random() * 5000).toFixed(2), name : '装备资讯' },
                    { value : (Math.random() * 5000).toFixed(2), name : '装备小额贷' },
                    { value : (Math.random() * 5000).toFixed(2), name : '辽无二电' }
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
            if(params.name === '资产总额') {
                chart.setOption(assetOption);
            } else if(params.name === '所有者权益总额') {
                chart.setOption(ownerOption);
            } else if(params.name === '负债总额') {
                chart.setOption(debtOption);
            }
        });

        $('#totalBackBtn').on('click', function () {
            echarts.dispose(container);
            chart = echarts.init(container, null, { renderer : 'canvas' });
            chart.setOption(option);

            chart.on('click', function (params) {
                if(params.name === '资产总额') {
                    chart.setOption(assetOption);
                } else if(params.name === '所有者权益总额') {
                    chart.setOption(ownerOption);
                } else if(params.name === '负债总额') {
                    chart.setOption(debtOption);
                }
            });
        });
    });
}(window.jQuery);
