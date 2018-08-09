!function ($) {

    'use strict';

    var baseUrl = appPath + "/auth/person";
    var postUrl = appPath + "/auth/post";
    var $table;
    var $tree;
    var timer;

    $(function() {
        initTree();  // 初始化部门岗位树
    });

    /**
     *  刷新列表
     */
    var refreshTable = function() {
        $table.bootstrapTable('removeAll');
        $table.bootstrapTable('refresh');
    };

    /**
     *  初始化左侧部门岗位树
     */
    var initTree = function() {

        $tree = $('#departmentPostTree');

        var options = { //通用配置
            levels : 1,
            showTags : true
        };

        $.fn.http({
            method : 'GET',
            url :  postUrl,
            success : function(data) {
                $tree.treeview($.extend(options, { data : data }));   //新创建树组件
                $tree.on('nodeSelected', function() {
                    clearTimeout(timer);
                    refreshTable();
                });
                $tree.on('nodeUnselected', function() {
                    timer = setTimeout(refreshTable, 100);
                });
                initTable();    //初始化左侧人员列表
            }
        });
    };

    /**
     *  初始化右侧人员表格
     */
    var initTable = function() {

        $table = $('#personTable');

        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索姓名/部门/岗位';
                    },
                    onExpandRow: function (index, row, $detail) {
                    }
                },
                $.extend({}, generalTableOption, { queryParams : personParams })
            )
        );
    };

    /**
     * 查询条件
     * @param params 基本查询条件，包含search、sort、order、limit、offset
     */
    function personParams(params) {
        var selectedNodes = $tree.treeview('getSelected');
        var localParams = {
            name : $('#search_name').val(),
            orgId : selectedNodes.length > 0 ? selectedNodes[0].id.toString() : '', //机构Id
            orgType : selectedNodes.length > 0 ? selectedNodes[0].type.toString() : ''  //机构类型 1 部门 2 岗位
        };
        return $.extend(localParams, params);
    }
}(window.jQuery);

/**
 * 格式化-职务
 * @param value 显示值
 * @param row 数据行
 */
var departmentPostFormatter = function (value, row) {
    if(!value) {
        return overSpanFormatter('-');
    }
    if(!row.hasOwnProperty('departmentName') || !row['departmentName']) {
        return overSpanFormatter(value);
    }

    return overSpanFormatter(value + '(' + row['departmentName'] + ')');
};
