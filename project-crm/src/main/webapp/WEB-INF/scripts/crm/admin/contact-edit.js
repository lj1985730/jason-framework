/***********************************
 * CRM-客户联系人-编辑页-脚本
 * @author Liu Jun at 2018-7-10 14:14:52
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/contact';
    var $table, $editWin, $editForm;

    /**
     * 初始化方法
     */
    $(function() {

        /**
         * 按钮初始化
         */
        $('#editContactWinSubmitBtn').on('click', function() { submit(); });

        /**
         * 编辑页初始化
         */
        $editWin = $('#editContactWin');
        $editForm = $('#editContactForm');

        //下拉框初始化
        $('#edit_contact_gender').comboData('COM_PSN_GENDER', false, null, true);  //性别
        $('#edit_contact_department').comboData('CRM_CONTACT_DEPT', false, null, true);  //部门
        $('#edit_contact_post').comboData('CRM_CONTACT_POST', false, null, true);  //岗位

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素

    });

    /**
     * 显示编辑窗口
     * @param saveOrUpdate	0 新增；1 修改
     * @param $outTable	数据源table
     */
    var sOrU;
    $.fn.showEditContactWin = function(saveOrUpdate, $outTable) {
        sOrU = saveOrUpdate;
        $table = $outTable;
        try {
            $editForm.form('clear');    //清除表单
            if(sOrU === 1) {
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }
                $editForm.form('load', row);    //加载表单数据
            } else {
            }
            $('#customerId').val($('#id').val());
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

        var method = (sOrU === 0 ? 'POST' : 'PUT');
        var contact = $editForm.serializeJson();   //取form表单内填写数据

        $.fn.http({
            method : method,
            url : baseUrl,
            data : JSON.stringify(contact),
            success : function() {
                $editWin.modal('hide');
                $table.bootstrapTable('refresh');
            }
        });
    };

}(window.jQuery);
