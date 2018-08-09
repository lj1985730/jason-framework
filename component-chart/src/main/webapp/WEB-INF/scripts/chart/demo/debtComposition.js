!function ($) {

    'use strict';

    var container = document.getElementById('debtCompositionContainer');
    var chart;

    var mainDataDync = [];
    var mainDataNonCurrent = [];
    for(var i = 0; i < 12; i ++) {
        mainDataDync.push((Math.random() * 3016.87).toFixed(2));
    }
    for(var j = 0; j < 12; j ++) {
        mainDataNonCurrent.push((Math.random() * 6092.5).toFixed(2));
    }

    /**
     * chart配置
     * @type {{tooltip: {trigger: string, axisPointer: {type: string}}, legend: {align: string, data: [string,string]}, grid: {left: string, right: string, bottom: string, containLabel: boolean}, xAxis: {type: string}, yAxis: {type: string, data: [string,string]}, series: [null,null]}}
     */
    var mainOption = {
        tooltip : {
            trigger : 'axis',
            axisPointer : {
                type : 'shadow'
            }
        },
        legend : {
            align : 'right',
            data : [ '流动负债', '非流动负债' ]
        },
        grid: {
            left : '3%',
            right : '4%',
            bottom : '3%',
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
                name : '流动负债',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                data : [ 524.06, 3016.87 ]
            },
            {
                name : '非流动负债',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                data : [ 505.43, 6092.50 ]
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
            data : [ '流动负债', '非流动负债' ]
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
                name : '流动负债',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                //524.06
                data : [ 13.66, 13.8, 26.7, 40.71, 32.89, 56.48, 64.22, 91.1, 90, 114.5 ]
            },
            {
                name : '非流动负债',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                //505.43
                data : [ 11.66, 11.8, 24.7, 38.78, 30.89, 54.48, 62.52, 89.1, 88, 113.5 ]
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
            data : [ '流动负债', '非流动负债' ]
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
                name : '流动负债',
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
                name : '非流动负债',
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
        chart = echarts.init(container, null, { renderer: 'canvas' });
        chart.setOption(mainOption);

        chart.on('click', function (params) {
            if(params.name === '国资委') {
                chart.setOption(mainDownOption);
            } else if(params.name === '装备集团') {
                chart.setOption(downOption);
            }
        });

        $('#debtBackBtn').on('click', function () {
            chart.setOption(mainOption);
        });
    });
}(window.jQuery);
