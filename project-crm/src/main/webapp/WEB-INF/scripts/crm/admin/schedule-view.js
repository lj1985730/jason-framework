/***********************************
 * 日程管理-查看功能-脚本
 * @author Liu Jun at 2018-4-28 14:42:38
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var $win, $form;
    var $fileTable;

    /**
     * 初始化方法
     */
    $(function() {

        /**
         * 编辑页初始化
         */
        $win = $('#view-scheduleWin');
        $form = $('#view-scheduleForm');

        $fileTable = $('#view-cuScheduleDoc');
    });


    /**
     * 显示查看窗口
     */
    $.fn.showViewScheduleWin = function($outTable) {
        try {
            var row = $outTable.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            clearForm();    //清空表单
            fillForm(row);
            $win.modal('show');
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     *  清空全部表单
     */
    var clearForm = function() {
        $form.form('clear');    //清除商机信息
        $fileTable.fileTable('clear');    //清除附件
    };

    /**
     *  填充表单
     *  @param data 数据
     */
    var fillForm = function(data) {
        $form.form('load', data);    //加载商机信息
        var participantStr = data.schedulePartin;
        if(participantStr) {
            var participants = JSON.parse(participantStr);
            var participantsNames = '';
            for(var i = 0; i < participants.length; i++) {
                participantsNames += participants[i].text + ',';
            }
            $('#view-schedulePartin').text(participantsNames.substring(0, participantsNames.length - 1));
        }

        $fileTable.fileTable('load', data.id);    //附件组件绑定数据
    };

}(window.jQuery);