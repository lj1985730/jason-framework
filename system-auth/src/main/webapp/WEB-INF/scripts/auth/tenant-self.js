!function ($) {

    'use strict';

	var baseUrl = appPath + "/auth/tenant";
    var $editWin, $editForm;

	$(function() {

        /**
         * 按钮初始化
         */
        $('#updateSelfTenant').on('click', function() { showEditWin(); });
        $('#editWinSubmitBtn').on('click', function() { submit() });

        /**
		 * 编辑页初始化
         */
        $editWin = $("#editWin");
        $editForm = $("#editForm");
        /**
         * 下拉框
         */
        $('#category').comboData('COM_ENT_NATURE', false, null, true);

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
	});

    /**
     * 显示编辑窗口
     */
    var showEditWin = function() {
        try {
            $editForm.form('clear');
            $.fn.http({
                method : 'GET',
                url : baseUrl + '/selfTenant',
                success : function(data) {
                    $editForm.form('load', data);
                    $("#editWinTitle").html("修改企业信息");
                    $editWin.modal("show");
                }
            });
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
                $editWin.modal("hide");
                refreshForm();
            }
        });
    };

    /**
     * 刷新展示表单显示内容
     */
	var refreshForm = function() {
        var $readonlyForm = $('#readonlyForm');
        var data = $editForm.serializeJson();

        $readonlyForm.form('load', data);

        // 处理营业期限的拼接内容
        var businessTerm = (data['businessTermStart'] ? data['businessTermStart'] : '')
            + ' ~ '
            + (data['businessTermEnd'] ? data['businessTermEnd'] : '');
        $('#readonly-businessTerm').text(businessTerm);

        //处理企业类型显示文本
        var category = $('#category').find("option:selected").text();
        $('#readonly-category').text(category);
    }

}(window.jQuery);
