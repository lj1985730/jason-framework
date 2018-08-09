!function ($) {

    'use strict';

    var months = ['','1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月',''];

    /**
     *  下属机构
     */
    var orgs = [ '重工起重', '国投集团', '会展集团', '瓦轴集团', '热电集团', '资源集团', '国讯科技', '装备资讯', '装备小额贷', '辽无二电' ];

    var container = document.getElementById('largeAmountContainer');
    var chart;

    var mainOption = {
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
            data : [ '2017年' ]
        },
        grid: {
            top : '10%',
            left : '1%',
            right : '1%',
            bottom : '1%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : months
            }
        ],
        yAxis : [{
            type : 'value',
            axisLabel : {
                show : true,
                interval : 'auto'
            }
        }],
        series : [{
            name : '2017年',
            type : 'line',
            smooth : true,  //这句就是让曲线变平滑的
            data : [ 0, 0, 3410 ,0, 6546, 0, 0, 0, 0, 4932 ]
        }]
    };

    /**
     * chart配置
     */
    var innerOption = {
        // tooltip : {
        //     trigger : 'axis',
        //     axisPointer: {
        //         type : 'shadow'
        //     }
        // },
        legend : {
            align : 'right',
            data : [
                '大额资金发生'
            ]
        },
        grid: {
            left : 'left',
            right : '1px',
            bottom : '1%',
            containLabel : true
        },
        yAxis : [
            {
                type : 'category'
            }
        ],
        xAxis : [
            {
                type : 'value',
                position:'top'
            }
        ],
        series : [
            {
                name : '大额资金发生',
                type : 'bar'
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(mainOption);

        chart.on('click', function (params) {
            innerOption.yAxis[0].data = randomOrgs();
            innerOption.series[0].data = randomNumber(params.value);
            echarts.dispose(container);
            chart = echarts.init(container, null, { renderer : 'canvas' });
            chart.setOption(innerOption);
            $('#month').text(params.name + '大额资金排名');
            chart.on('click', function () {
                $('#dtlTable').bootstrapTable('load', [
                    {'type' : '大连银行东港支行', 'people' : '大连银行', 'data' : '2017-05-11', 'amount' : '3560', 'forwhat' : '存款转理财'},
                    {'type' : '大连西岗支行-债券户', 'people' : '大连银行', 'data' : '2017-05-20', 'amount' : '3100', 'forwhat' : '增持股权'}
                ]);
                $('#dtlWin').modal('show');
            });
        });

        $('#incomeBackBtn').on('click', function () {
            echarts.dispose(container);
            chart = echarts.init(container, null, { renderer : 'canvas' });
            chart.setOption(mainOption);
            $('#month').text('大额资金情况');

            chart.on('click', function (params) {
                innerOption.yAxis[0].data = randomOrgs();
                innerOption.series[0].data = randomNumber(params.value);
                echarts.dispose(container);
                chart = echarts.init(container, null, { renderer : 'canvas' });
                chart.setOption(innerOption);
                $('#month').text(params.name + '大额资金排名');
                chart.on('click', function () {
                    $('#dtlTable').bootstrapTable('load', [
                        {'type' : '大连银行东港支行', 'people' : '大连银行', 'data' : '2017-05-11', 'amount' : '3560', 'forwhat' : '存款转理财'},
                        {'type' : '大连西岗支行-债券户', 'people' : '大连银行', 'data' : '2017-05-20', 'amount' : '3100', 'forwhat' : '增持股权'}
                    ]);
                    $('#dtlWin').modal('show');
                });
            });
        });

        $('#dtlTable').bootstrapTable({
            pagination : false,
            locale : 'zh_CN',
            queryParamsType : "limit"
        });
    });

    /**
     * 企业随机排序
     */
    var randomOrgs = function() {
        var result = [];
        for (var i = 0; i < 10; i++) {
            var temp = parseInt(Math.random() * (10 - i));
            result.push(orgs[temp]);
            orgs.splice(temp, 1);
        }

        orgs = result;
        return result;
    };

    /**
     * 生成随机数字
     */
    var randomNumber = function(total) {
        var result = [];
        var current = total;
        for (var i = 0; i < 10; i++) {
            current = Math.ceil(current * (Math.floor(Math.random() * 50 + 50) / 100));
            result.push(current);
        }
        return result.reverse();
    };

    Array.prototype.contains = function ( needle ) {
        for (var i in this) {
            if (this[i].toString() === needle.toString()) return true;
        }
        return false;
    }

}(window.jQuery);
