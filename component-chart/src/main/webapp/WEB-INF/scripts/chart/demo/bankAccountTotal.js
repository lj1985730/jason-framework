!function ($) {

    'use strict';

    var container = document.getElementById('bankAccountTotal');
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
            data : [ '账户']
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
                name : '账户',
                type : 'bar',
                stack : '总量',
                label : {
                    normal : {
                        show : false,
                        position : 'top'
                    }
                },
                data : ['7', '11', '11', '15', '18', '20'],
                itemStyle:{
                    normal:{
                        color: function (params){
                            var colorList = ['#e65100'];
                            return colorList[params.dataIndex];
                        }
                    }
                }
            }
        ]
    };

    $(function() {
        chart = echarts.init(container, null, {renderer: 'canvas'});
        chart.setOption(mainOption);
    });
}(window.jQuery);
