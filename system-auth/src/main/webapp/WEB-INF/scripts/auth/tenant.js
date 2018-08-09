!function ($) {

    'use strict';

	var baseUrl = appPath + "/auth/tenant";
    var $table, $editWin, $editForm;

	$(function() {

        /**
         * 表格初始化
         */
        $table = $('#tenantTable');
        $table.bootstrapTable(
			$.extend(
			    {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索企业名称';
                    },
                    onCheck : function(row) {	//选中规划表某一行时触发指标表刷新事件
                    },
                    onExpandRow: function (index, row, $detail) {
                    },
                    onUncheck : function() {
                    }
                },
                $.extend({}, generalTableOption, { queryParams : tenantParams })
			)
		);

        /**
         * 按钮事件初始化
         */
        /* 启用/禁用 */
        $("#enableTenant").on("click",function () {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            $.fn.http({
                method : 'PUT',
                url : baseUrl + '/' + row.id + '/toggleEnable',
                success : function() {
                    SysMessage.alertSuccess('企业"' + row.name + '"已' + (row.enabled ? '禁用' : '启用') + '！');
                    $table.bootstrapTable('refresh');
                }
            });
        });

        /* 新增、修改、删除 */
        $('#createTenant').on('click', function() { showEditWin(0); });
        $('#updateTenant').on('click', function() { showEditWin(1); });
        $('#deleteTenant').on('click', function() { remove(); });

        /* 提交表单 */
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
	 * 查询条件
	 * @param params 基本查询条件，包含search、sort、order、limit、offset
	 */
	function tenantParams(params) {
		var localParams = {
			category : $('search_category').val()
		};
		return $.extend(localParams, params);
	}

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
                $("#editWinTitle").html("修改企业信息");
                $editWin.modal("show");
            } else if(sOrU === 0) {
                $("#editWinTitle").html("新增企业信息");
                $editWin.modal("show");
            }
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

        var method = (sOrU === 0 ? "POST" : "PUT");
	    $.fn.http({
            method : method,
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
                    method : "DELETE",
                    url : baseUrl + '/' + row.id,
                    success : function() {
                        $table.bootstrapTable('refresh');
                    }
                });
            }
        });
	};

}(window.jQuery);
