/**
 * 通用chart配置
 * @type {{}}
 */
var generalChartOption = {
    tooltip : {
        trigger : 'axis',
        axisPointer : {
            type : 'shadow'
        }
    },
    xAxis : {
        nameLocation : 'center',
        minInterval : 1
    },
    yAxis : {
        minInterval : 1
    },
    legend : {
        align : 'center'
    },
    grid : {
        left : '2%',
        right : '2%',
        bottom : '2%',
        containLabel : true
    },
    color : ['#1CAF9A','#2F4554', '#61A0A8', '#D48265', '#91C7AE','#749F83',  '#CA8622', '#BDA29A','#6E7074', '#546570', '#C4CCD3'],
    animation : true,
    animationThreshold : 200
};