!function ($) {

    'use strict';

    var baseUrl = appPath + "/auth/person";
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

        $tree.treeSelect({
            entity : 'com.yoogun.auth.domain.model.Department',
            levels : 1, //默认只打开2层
            multiSelect : false,
            onSelect : function() {  //选中节点,刷新人员表单
                refreshTable();
            },
            onUnselect : function() {   //取消选中节点,隐藏表单
                timer = setTimeout(refreshTable, 100);
            }
        });
        initTable();    //初始化左侧人员列表
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
                        return '搜索姓名';
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
        var selectedNodes = $tree.treeSelect('getSelectedId');
        var localParams = {
            orgId : selectedNodes.length > 0 ? selectedNodes[0] : ''//机构Id
        };
        return $.extend(localParams, params);
    }
}(window.jQuery);
