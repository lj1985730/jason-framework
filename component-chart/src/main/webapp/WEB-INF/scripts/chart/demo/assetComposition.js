!function ($) {

    'use strict';

    var container = document.getElementById('assetCompositionContainer');
    var chart;

    var mainDataDync = [];
    var mainDataNonCurrent = [];
    for(var i = 0; i < 12; i ++) {
        mainDataDync.push((Math.random() * 10000).toFixed(2));
    }
    for(var j = 0; j < 12; j ++) {
        mainDataNonCurrent.push((Math.random() * 65000).toFixed(2));
    }

    var mainOption = {
        tooltip : {
            trigger : 'axis',
            axisPointer : {
                type : 'shadow'
            }
        },
        legend : {
            align : 'right',
            data : [ '流动资产', '非流动资产' ]
        },
        grid: {
            left : '1%',
            right : '3%',
            bottom : '1%',
            containLabel : true
        },
        xAxis : {
            type : 'value'
        },
        yAxis : {
            type : 'category',
            data : [ '装备集团', '国资委' ]
        },
        series : [
            {
                name : '流动资产',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                data : [ 863.69, 4878.77 ]
            },
            {
                name : '非流动资产',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                data : [ 3394.97, 12878.31 ]
            }
        ]
    };

    var downOption = {
        tooltip : {
            trigger : 'axis',
            axisPointer: {
                type : 'shadow'
            }
        },
        legend : {
            align : 'right',
            data : [ '流动资产', '非流动资产' ]
        },
        grid: {
            left : '1%',
            right : '3%',
            bottom : '1%',
            containLabel : true
        },
        xAxis : {
            type : 'value'
        },
        yAxis : {
            type : 'category',
            data : [ '辽无二电', '装备小额贷', '装备资讯', '国讯科技', '资源集团', '热电集团', '瓦轴集团', '会展集团', '国投集团', '重工起重' ]
        },
        series : [
            {
                name : '流动资产',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                //863.69
                data : [ 29.66, 43.8, 36.7, 20.71, 32.89, 126.48, 128.22, 201.8, 90, 163.5 ]
            },
            {
                name : '非流动资产',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                //3394.97
                data : [ 94.25, 93.13, 88.6, 163.33, 267.38, 288.67, 582.94, 358.3, 688.4, 769.97 ]
            }
        ]
    };

    var mainDownOption = {
        tooltip : {
            trigger : 'axis',
            axisPointer: {
                type : 'shadow'
            }
        },
        legend : {
            align : 'right',
            data : [ '流动资产', '非流动资产' ]
        },
        grid: {
            left : '1%',
            right : '3%',
            bottom : '1%',
            containLabel : true
        },
        xAxis : {
            type : 'value'
        },
        yAxis : {
            type : 'category',
            data : [ '公交集团', '大连港', '机场集团', '盐化集团', '染化集团', '三寰控股', '装备集团', '建投集团', '水务集团', '城建投', '资源集团', '大化集团' ]
        },
        series : [
            {
                name : '流动资产',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                //863.69
                data : mainDataDync
            },
            {
                name : '非流动资产',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                //3394.97
                data : mainDataNonCurrent
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(mainOption);

        chart.on('click', function (params) {
            if(params.value) {
                if(params.name === '国资委') {
                    chart.setOption(mainDownOption);
                } else if(params.name === '装备集团') {
                    chart.setOption(downOption);
                }
            }
        });

        $('#backBtn').on('click', function () {
            chart.setOption(mainOption);
        });
    });
}(window.jQuery);
