/***********************************
 * 商机管理-查看功能-脚本
 * @author lan Dexiang at 2018-4-26 14:40:12
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
        $win = $('#view-chanceWin');
        $form = $('#view-chanceForm');

        $fileTable = $('#view-cuChanceDoc');
    });

    /**
     * 显示查看窗口
     */
    $.fn.showViewChanceWin = function($outTable) {
        try {
            var row = $outTable.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            clearForm();    //清空表单
            fillForm(row);
            // $('#chanceExtCon-view').data('entityId',row.id);
            // $('#chanceExtCon-view').entityExt('setEntityId');
            // $('#chanceExtCon-view').entityExt('initOfView');
            $.fn.ChanceStepObj.getChanceStep(row.chanceStepJson,$('#chance_step_view'));
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
        $fileTable.fileTable('load', data.id);    //附件组件绑定数据
    };

}(window.jQuery);