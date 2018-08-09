!function ($) {

    'use strict';

	var baseUrl = appPath + "/base/sysDict";
    var $table, $editWin, $editForm;

	$(function() {

        /**
         * 表格初始化
         */
        $table = $('#dictTable');
        $table.bootstrapTable(
			$.extend(
			    {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索字典分类/名称';
                    }
                },
                $.extend({}, generalTableOption, { queryParams : commonParams })
			)
		);

        /**
         * 按钮初始化
         */
        $('#createDict').on('click', function() { showEditWin(0); });
        $('#updateDict').on('click', function() { showEditWin(1); });
        $('#deleteDict').on('click', function() { remove(); });
        $('#editDictWinSubmitBtn').on('click', function() { submit() });

        /**
		 * 编辑页初始化
         */
        $editWin = $("#editDictWin");
        $editForm = $("#editDictForm");

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素

	});

    /**
     * 显示编辑窗口
     * @param saveOrUpdate	0 新增；1 修改
     */
    var sOrU;
    var showEditWin = function(saveOrUpdate) {
        sOrU = saveOrUpdate;
        try {
            $editForm.form('clear');
            if(sOrU === 1) {
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }
                $editForm.form('load', row);
            }
            $editWin.modal("show");
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

        $.fn.http({ //发送请求
            method : sOrU === 0 ? 'POST' : 'PUT',
            url : baseUrl,
            data : JSON.stringify($editForm.serializeJson()),
            success : function() {
                $editWin.modal("hide");
                $table.bootstrapTable('refresh');
            }
        });
    };

	/**
	 * 执行删除动作的操作
	 */
	var remove = function() {
        var row = $table.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }

        bootbox.confirm("确定要删除吗？", function(callback) {
            if (callback) {
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
}(window.jQuery);
