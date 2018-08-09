/**
 * 格式化-显示序号
 * @param value 显示值
 * @param row 行数据
 * @param index 序号
 */
var indexFormatter = function (value, row, index) {
    if(value) {
        value = index + 1;
    } else {
        //value ='等待查询..';
        value = index + 1;
    }
    return overSpanFormatter(value);
};

/**
 * 格式化-账户
 * @param value 显示值
 * @param row 行数据
 */
var accountFormatter = function (value, row) {
    if(row['modifierName']) {
        value = value + '(' + row['modifierName'] + ')';
    }
    return overSpanFormatter(value);
};

/**
 * 格式化-显示是否
 * @param value 显示值
 */
var booleanFormatter = function (value) {
    if(value !== null && typeof(value) !== 'undefined') {
        value = value ? '是' : '否';
    } else {
        value = '-';
    }
    return overSpanFormatter(value);
};

/**
 * 格式化-显示鼠标提示
 * @param value 值
 */
var overSpanFormatter = function(value) {
    if($.fn.isNull(value)) {
        return '';
    } else {
        return '<span title="' + value + '">' + value + '</span>';
    }
};

/**
 * 格式化-订单状态
 * @param value 显示值
 */
var stateFormatter = function (value) {
    if(value === null || typeof(value) === 'undefined') {
        value = '-';
    } else {
        switch (value) {
            case 1 :
                return overSpanFormatter('新增');
            case 2 :
                return overSpanFormatter('已确认');
            case 3 :
                return overSpanFormatter('完成');
            case 4 :
                return overSpanFormatter('取消');
        }
    }
    return overSpanFormatter(value);
};

/**
 * 格式化-订单明细小计
 * @param value 显示值
 * @param row 数据
 */
var totalFormatter = function (value, row) {
    if(value === null || typeof(value) === 'undefined') {
        return overSpanFormatter('-');
    } else {
        return overSpanFormatter(row.count * row.price);
    }
};
/**
 * 格式化-文件大小格式化
 * @param value 显示值
 * @param row 数据
 */
var fileSizeFormatter = function (value, row) {
    if(value === null || typeof(value) === 'undefined') {
        return overSpanFormatter('-');
    } else {

        var kSize = 1024;
        var mSize = 1024 * kSize;
        var gSize = 1024 * mSize;

        if(value >= gSize) {
            return  overSpanFormatter(Number(value / gSize).toFixed(2) + ' GB');
        } else if(value >= mSize) {
            return  overSpanFormatter(Number(value / mSize).toFixed(2) + ' MB');
        } else if(value >= kSize) {
            return  overSpanFormatter(Number(value / kSize).toFixed(2) + ' KB');
        } else {
            return  overSpanFormatter(Number(value).toFixed(2) + ' B');
        }
    }
};

/**
 * 格式化-参与人姓名
 * @param value 显示值
 */
var partinFormatter = function (value) {
    var names = '-';
    if(value !== null && typeof(value) !== 'undefined' && value !== "") {
        var result = JSON.parse(value);
        var name = [];
        for(var i=0;i<result.length;i++){
            name[i] = result[i].text;
        }
        names = name.join(',');
    }
    return overSpanFormatter(names);
};