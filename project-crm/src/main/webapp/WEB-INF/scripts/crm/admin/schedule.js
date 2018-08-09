/***********************************
 * CRM-日程-脚本
 * @author Sheng Baoyu at 2018-03-20 14:59:38
 * @modify Liu Jun at 2018-4-26 14:00:43
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/schedule';
    var $table;

    /**
     * 初始化方法
     */
    $(function() {
        /**
         * 初始化table
         */
        $table = $('#scheduleTable');
        $table.bootstrapTable(
            $.extend({
                    url : baseUrl,
                    formatSearch : function () { return '搜索日程标题'; }
            },
            $.extend({}, generalTableOption, { queryParams : scheduleParams }))
        );

        /* 按钮初始化 */
        $('#viewSchedule').on('click', function() { $.fn.showViewScheduleWin($table); });
        $('#createSchedule').on('click', function() { $.fn.showEditScheduleWin(0); });
        $('#updateSchedule').on('click', function() { $.fn.showEditScheduleWin(1); });
        $('#deleteSchedule').on('click', function() { remove(); });
    });

    /**
     * 查询条件
     * @param params 基本查询条件，包含search、sort、order、limit、offset
     */
    function scheduleParams(params) {
        var localParams = {
            // 查询条件
            scheduleTitle : $('search_scheduleTitle').val()
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
     * 导出excel
     */
    $.fn.export = function() {
        $.fn.download(baseUrl + '/listExcel');
    };

}(window.jQuery);
