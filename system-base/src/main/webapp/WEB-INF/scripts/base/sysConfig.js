!function ($) {

    'use strict';

	var baseUrl = appPath + "/base/sysConfig";
    var $table, $editWin, $editForm;

	$(function() {

        /**
         * 表格初始化
         */
        $table = $('#configTable');
        $table.bootstrapTable(
			$.extend(
			    {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索企业名称';
                    }
                },
                $.extend({}, generalTableOption, { queryParams : tenantParams })
			)
		);

        /**
         * 按钮初始化
         */
        $('#createSysConfig').on('click', function() { showEditWin(0); });
        $('#updateSysConfig').on('click', function() { showEditWin(1); });
        $('#deleteSysConfig').on('click', function() { remove(); });
        $('#editConfigWinSubmitBtn').on('click', function() { submit() });

        /**
		 * 编辑页初始化
         */
        $editWin = $("#editConfigWin");
        $editForm = $("#editConfigForm");

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素

	});

	/**
	 * 查询条件
	 * @param params 基本查询条件，包含search、sort、order、limit、offset
	 */
	function tenantParams(params) {
		var localParams = {
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
                $("#editConfigWinTitle").html("修改配置");
                $('#attachment').fileTable('load', row.id);    //附件组件绑定数据
                $editWin.modal("show");
            } else if(sOrU === 0) {
                var uuid = Math.uuid();
                $("#editConfigWinTitle").html("新增配置");
                $('#id').val(uuid);  //生成新主键
                $('#attachment').fileTable('load', uuid);    //附件组件绑定数据
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

	    var data = $editForm.serializeJson();
        data['enabled'] = $('[name="enabled"]').bootstrapSwitch('state');

        $.ajax({
            method : method,
            url : baseUrl,
            dataType : "json",
            contentType : "application/json",
            data : JSON.stringify(data),
            success : function(data, status) {
                switch(status) {
                    case "timeout" :
                        SysMessage.alertError('请求超时！请稍后再次尝试。');
                        break;
                    case "error" :
                        SysMessage.alertError('请求错误！请稍后再次尝试。');
                        break;
                    case "success" :
                        if (data.success) {
                            if(data.message) {
                                SysMessage.alertSuccess(data.message);
                            }
                            $editWin.modal("hide");
                            $table.bootstrapTable('refresh');
                        } else {
                            if(data.message) {
                                SysMessage.alertInfo(data.message);
                            }
                        }
                        break;
                    default:
                        SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                        return;
                }
            }
        });
    };

	/**
	 * 执行删除动作的操作
	 */
	var remove = function() {
        try {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            bootbox.confirm("确定要删除吗？", function(callback) {
                if (callback) {
                    $.ajax({
                        method : "DELETE",
                        url : baseUrl + '/' + row.id,
                        dataType : "json",
                        contentType : "application/json",
                        data : $editForm.serializeJson(),
                        success : function(data, status) {
                            switch(status) {
                                case "timeout" :
                                    SysMessage.alertError('请求超时！请稍后再次尝试。');
                                    break;
                                case "error" :
                                    SysMessage.alertError('请求错误！请稍后再次尝试。');
                                    break;
                                case "success" :
                                    if (data.success) {
                                        if(data.message) {
                                            SysMessage.alertSuccess(data.message);
                                        }
                                    } else {
                                        if(data.message) {
                                            SysMessage.alertInfo(data.message);
                                        }
                                    }
                                    $table.bootstrapTable('refresh');
                                    break;
                                default:
                                    SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                                    return;
                            }
                        }
                    });
                }
            });
        } catch (e) {
            SysMessage.alertError(e.message);
        }
	};
}(window.jQuery);
