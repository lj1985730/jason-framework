/***********************************
 * CRM-客户联系人-编辑页-脚本
 * @author Liu Jun at 2018-7-10 14:14:52
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var $table, $win, $form;

    /**
     * 初始化方法
     */
    $(function() {

        /**
         * 查看页初始化
         */
        $win = $('#viewContactWin');
        $form = $('#viewContactForm');
    });

    /**
     * 显示编辑窗口
     * @param $outTable	数据源table
     */
    $.fn.showViewContactWin = function($outTable) {
        $table = $outTable;
        try {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            $form.form('clear');    //清除表单
            $form.form('load', row);    //加载表单数据
            $win.modal('show');
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

}(window.jQuery);
