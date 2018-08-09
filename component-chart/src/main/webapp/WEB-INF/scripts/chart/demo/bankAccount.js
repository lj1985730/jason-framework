!function ($) {

    'use strict';

    var container = document.getElementById('bankAccount');
    var chart;



    var builderDataN = function () {
        var mainDataN = [];
        for(var i = 0; i < 6; i ++) {
            mainDataN.push((Math.random() * 10).toFixed(0));
        }
        return mainDataN;
    };
    var builderDataF = function () {
        var mainDataF = [];
        for(var j = 0; j < 6; j ++) {
            mainDataF.push((Math.random() * 10).toFixed(0));
        }
        return mainDataF;
    };


    var mainOption = {
        tooltip : {
            trigger : 'axis',
            axisPointer : {
                type : 'shadow'
            }
        },
        legend : {
            align : 'right',
            data : [ '正常', '冻结' ]
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
            data : [ '工行', '建行', '农行', '招行', '中国银行', '大连银行' ]
        },
        series : [
            {
                name : '正常',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                data : builderDataN()
            },
            {
                name : '冻结',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                data : builderDataF()
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(mainOption);
        $('#enterprise').on('change',function () {
            $('#title').html($(this).find("option:selected").text());
            var option = chart.getOption();
            option.series[0].data = builderDataN();
            option.series[1].data = builderDataF();
            chart.setOption(option);
        });
        $('#signDate').on('blur',function () {
            var option = chart.getOption();
            option.series[0].data = builderDataN();
            option.series[1].data = builderDataF();
            chart.setOption(option);
        });
    });
}(window.jQuery);
