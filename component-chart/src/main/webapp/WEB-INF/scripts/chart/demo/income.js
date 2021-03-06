!function ($) {

    'use strict';

    var months = ['','1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月',''];

    /**
     *  下属机构
     */
    var orgs = [ '重工起重', '国投集团', '会展集团', '瓦轴集团', '热电集团', '资源集团', '国讯科技', '装备资讯', '装备小额贷', '辽无二电' ];

    var container = document.getElementById('incomeContainer');
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
            data : [,2116, 2341, 2764, 3133, 3421, 2117, 3632, 1531, 1741, 1980, 1100]
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
                '预测', '实际'
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
            },
            {
                type : 'category',
                axisLine : {show:false},
                axisTick : {show:false},
                axisLabel : {show:false},
                splitArea : {show:false},
                splitLine : {show:false}
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
                name : '实际',
                type : 'bar',
                itemStyle : {normal: {color:'rgba(232,79,16,1)', label : { show : false }}},
                data : [ 250, 350, 410, 510, 545, 650, 710, 810, 845, 932 ]
            },
            {
                name : '预测',
                type : 'bar',
                yAxisIndex : 1,
                itemStyle : {normal : {color : 'rgba(232,79,16, 0.3)', label : {show:false,formatter:function(p){return p.value > 0 ? (p.value +'+'):'';}}}},
                data : [550,450,510,670,800,790,810,940,925,1132]
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(mainOption);

        chart.on('click', function (params) {
            innerOption.yAxis[0].data = randomOrgs();
            innerOption.yAxis[1].data = innerOption.yAxis[0].data;
            innerOption.series[0].data = randomNumber(params.value);
            innerOption.series[1].data = randomNumber(params.value);
            echarts.dispose(container);
            chart = echarts.init(container, null, { renderer : 'canvas' });
            chart.setOption(innerOption);
            $('#month').text(params.name + '销售收入排名');
            chart.on('click', function () {
                $('#dtlTable').bootstrapTable('load', [
                    {'type' : '主营业务收入', 'amount' : '391003', 'planIncome' : '400000'},
                    {'type' : '材料销售', 'amount' : '223231', 'planIncome' : '200000'},
                    {'type' : '代购代销', 'amount' : '348464', 'planIncome' : '300000'},
                    {'type' : '包装物出租', 'amount' : '231003', 'planIncome' : '300000'},
                ]);
                $('#dtlWin').modal('show');
            });
        });

        $('#incomeBackBtn').on('click', function () {
            echarts.dispose(container);
            chart = echarts.init(container, null, { renderer : 'canvas' });
            chart.setOption(mainOption);
            $('#month').text('销售收入情况');

            chart.on('click', function (params) {
                innerOption.yAxis[0].data = randomOrgs();
                innerOption.yAxis[1].data = innerOption.yAxis[0].data;
                innerOption.series[0].data = randomNumber(params.value);
                innerOption.series[1].data = randomNumber(params.value);
                echarts.dispose(container);
                chart = echarts.init(container, null, { renderer : 'canvas' });
                chart.setOption(innerOption);
                $('#month').text(params.name + '销售收入排名');
                chart.on('click', function () {
                    $('#dtlTable').bootstrapTable('load', [
                        {'type' : '主营业务收入', 'amount' : '391003', 'planIncome' : '400000'},
                        {'type' : '材料销售', 'amount' : '223231', 'planIncome' : '200000'},
                        {'type' : '代购代销', 'amount' : '348464', 'planIncome' : '300000'},
                        {'type' : '包装物出租', 'amount' : '231003', 'planIncome' : '300000'},
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
