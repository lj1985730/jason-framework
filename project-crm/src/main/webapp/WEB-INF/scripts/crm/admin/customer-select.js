/***********************************
 * CRM-选择客户-脚本
 * @modify by Liu Jun at 2018-4-26 16:50:34
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
        $table = $('#customerTable-select');
        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索客户名称';
                    }
                },
                $.extend({}, generalTableOption, { queryParams : customerParams })
            )
        );

        /**
         * 按钮初始化
         */
        $('#select-viewCustomer').off('click').on('click', function() { $.fn.showViewCustomerWin($table); });
        $('#select-createCustomer').off('click').on('click', function() { $.fn.showEditCustomerWin(0, $table); });
        $('#select-updateCustomer').off('click').on('click', function() { $.fn.showEditCustomerWin(1, $table); });
        $('#select-deleteCustomer').off('click').on('click', function() { remove(); });
        $('#select-commitCustomer').off('click').on('click', function() { workflowCommit(); });
    });

    /**
     * 查询条件
     * @param params 基本查询条件，包含search、sort、order、limit、offset
     */
    function customerParams(params) {
        var localParams = {
        };
        return $.extend(localParams, params);
    }

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
     * 导出excel
     */
    $.fn.export = function() {
        $.fn.download(baseUrl + '/listExcel');
    };

}(window.jQuery);
