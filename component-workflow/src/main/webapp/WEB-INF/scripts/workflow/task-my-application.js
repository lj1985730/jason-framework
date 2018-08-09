!function ($) {

    'use strict';

    var baseUrl = appPath + '/workflow';

    var $table;

    /**
     *  初始化
     */
    $(function () {

        // 默认查询未完成的数据
        $("[name='myApplicationFinished']", "#myApplicationToolbar").each(function() {
            if($(this).val() === 'false') {
                $(this).attr('checked', true);
                $(this).parent().addClass('checked');
            }
        });

        /* role table初始化 */
        $table = $("#myApplicationTable");

        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl + '/appliedProcesses',
                    formatSearch : function () {
                        return '搜索流程名称或流程编号';
                    }
                },
                $.extend({}, generalTableOption, { queryParams : appliedParams })
            )
        );

        /* 点击账户列表 */
        $table.on('check.bs.table', function (event, row) {
        });

        /* 取消点击账户列表 */
        $table.on('uncheck.bs.table', function () {
        });

        /* 刷新表格后 */
        $table.on('load-success.bs.table', function () {
        });

        /* 切换是否完成条件 */
        $("[name='myApplicationFinished']", "#myApplicationToolbar").on('ifChecked', function() {

            var finished = $("[name='myApplicationFinished']:checked", "#myApplicationToolbar").val();
            if(finished === true || finished === 'true') { //根据完成与否显示不同的列
                $table.bootstrapTable('showColumn', 'endDate');
                $table.bootstrapTable('showColumn', 'durationTime');
                $table.bootstrapTable('hideColumn', 'taskName');
                $table.bootstrapTable('hideColumn', 'assigneeName');
            } else {
                $table.bootstrapTable('hideColumn', 'endDate');
                $table.bootstrapTable('hideColumn', 'durationTime');
                $table.bootstrapTable('showColumn', 'taskName');
                $table.bootstrapTable('showColumn', 'assigneeName');
            }

            $table.bootstrapTable('removeAll');
            $table.bootstrapTable('refresh');
        });

    });

    /**
     * table查询参数
     */
    var appliedParams = function (params) {
        var localParams = {
            finished : $("[name='myApplicationFinished']:checked", "#myApplicationToolbar").val()
        };
        return $.extend(localParams, params);
    };

    /**
     * 取消流程
     * @param processInstanceId 流程实例ID
     */
    $.fn.cancelWorkflowProcess = function (processInstanceId) {
        bootbox.confirm('确定要撤销审批吗？', function (callback) {
            if(callback) {
                $.fn.http({
                    method : 'DELETE',
                    url : baseUrl + '/processInstance/' + processInstanceId,
                    data : {
                        reason : '发起人撤销申请'
                    },
                    success : function() {
                        $table.bootstrapTable('refresh');
                    }
                });
            }
        });
    };

}(window.jQuery);

/**
 * 工作流任务操作-我的申请
 */
var myApplicationOperation = function (value, row) {
    var content = '';

    content +=
        '<a href="javascript:$.fn.viewWorkflowProcess(\'' + row.processInstanceId + '\');" class="btn green btn-xs">' +
        '<i class="fa fa-eye"></i> 查看' +
        '</a>';

    if(row.processState === '' || row.processState === 'null'
        || (row.processState !== '审核中' && row.processState !== '被挂起' && row.processState !== '已删除' && row.processState !== '结束')) {
        content +=
            '<a href="javascript:$.fn.cancelWorkflowProcess(\'' + row.processInstanceId + '\');" class="btn red btn-xs">' +
            '<i class="fa fa-trash-o"></i> 取消' +
            '</a>';
    }

    return content;
};