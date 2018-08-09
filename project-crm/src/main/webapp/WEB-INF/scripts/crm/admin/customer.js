/***********************************
 * CRM-客户管理-主页-脚本
 * @modify by Liu Jun at 2018年7月12日
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/customer';
    var $table;

    /**
     * 初始化方法
     */
    $(function() {

        /* 初始化主table */
        $table = $('#customerTable');
        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索客户名称';
                    }
                },
                $.extend({}, generalTableOption, { queryParams : commonParams })
            )
        );

        /**
         * 按钮初始化
         */
        $('#viewCustomer').off('click').on('click', function() { $.fn.showViewCustomerWin($table); });
        $('#createCustomer').off('click').on('click', function() { $.fn.showEditCustomerWin(0, $table); });
        $('#updateCustomer').off('click').on('click', function() { $.fn.showEditCustomerWin(1, $table); });
        $('#deleteCustomer').off('click').on('click', function() { remove(); });
        $('#applyCustomer').off('click').on('click', function() { workflowCommit(); });
        $('#undoCommitCustomer').off('click').on('click', function() { workflowUndoCommit(); });
    });

    /**
     * 执行删除动作的操作
     */
    var remove = function() {
        var row = $table.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        bootbox.confirm('确定要删除吗？', function (callback) {
            if(callback) {
                $.fn.http({
                    method : 'DELETE',
                    url : baseUrl + '/' + row.id,
                    success : function() {
                        $table.bootstrapTable('refresh');
                    }
                });
            }
        });
    };

    /**
     * 提交工作流审批
     */
    var workflowCommit = function() {
        var row = $table.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        bootbox.confirm('确定要提交审批吗？', function (callback) {
            if(callback) {
                $.fn.http({
                    method : 'PUT',
                    url : baseUrl + '/' + row.id + '/workflowCommit',
                    success : function() {
                        $table.bootstrapTable('refresh');
                    }
                });
            }
        });
    };

    /**
     * 提交工作流审批
     */
    var workflowUndoCommit = function() {
        var row = $table.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        bootbox.confirm('确定要撤销审批吗？', function (callback) {
            if(callback) {
                $.fn.http({
                    method : 'PUT',
                    url : baseUrl + '/' + row.id + '/workflowUndoCommit',
                    success : function() {
                        $table.bootstrapTable('refresh');
                    }
                });
            }
        });
    };

    /**
     * 导出excel
     */
    $.fn.export = function() {
        $.fn.download(baseUrl + '/listExcel');
    };

}(window.jQuery);
