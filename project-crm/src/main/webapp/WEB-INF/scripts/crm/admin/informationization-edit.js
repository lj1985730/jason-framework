/***********************************
 * CRM-客户联系人-编辑页-脚本
 * @author Liu Jun at 2018-7-10 14:14:52
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/informationization';
    var $table, $editWin, $editForm;

    /**
     * 初始化方法
     */
    $(function() {

        /**
         * 按钮初始化
         */
        $('#editInfoWinSubmitBtn').on('click', function() { submit(); });

        /**
         * 编辑页初始化
         */
        $editWin = $('#editInfoWin');
        $editForm = $('#editInfoForm');

        //下拉框初始化
        $('#edit_info_brand').comboData('CRM_SOFTWARE_BRAND', false, null, true);  //品牌
        $('#edit_info_productLine').comboData('CRM_PROD_LINE', false, null, true);  //产品线
        $('#edit_contact_satisfaction').comboData('CRM_CSR_INFO_SATISFACTION', false, null, true);  //满意度
        $('#edit_contact_state').comboData('CRM_CSR_INFO_STATE', false, null, true);  //满意度

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素

    });

    /**
     * 显示编辑窗口
     * @param $outTable	数据源table
     */
    $.fn.showEditInfoWin = function($outTable) {
        $table = $outTable;
        try {
            $editForm.form('clear');    //清除表单
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            $editForm.form('load', row);    //加载表单数据
            $editWin.modal('show');
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 提交表单
     */
    var submit = function() {

        if(!$editForm.valid()) {    //表单验证
            return false;
        }

        $.fn.http({
            method : 'PUT',
            url : baseUrl,
            data : JSON.stringify($editForm.serializeJson()),
            success : function() {
                $editWin.modal('hide');
                $table.bootstrapTable('refresh');
            }
        });
    };

}(window.jQuery);
