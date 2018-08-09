!function ($) {

    'use strict';

	var baseUrl = appPath + "/meeting/meeting";
    var $table, $editWin, $editForm;
    var $startTime = $("#startTime");
    var $endTime = $("#endTime");

	$(function() {

        /**
         * 表格初始化
         */
        $table = $('#meetingTable');
        $table.bootstrapTable(
			$.extend(
			    {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索会议名称';
                    },
                    onCheck : function(row) {	//选中规划表某一行时触发指标表刷新事件
                    },
                    onExpandRow: function (index, row, $detail) {
                    },
                    onUncheck : function() {
                    }
                },
                $.extend({}, generalTableOption, { queryParams : meetingParams })
			)
		);

        /**
         * 按钮初始化
         */
        $('#createMeeting').on('click', function() { showEditWin(0); });
        $('#updateMeeting').on('click', function() { showEditWin(1); });
        $('#deleteMeeting').on('click', function() { remove(); });
        $('#editWinSubmitBtn').on('click', function() { submit() });

        $('#searchDate').datepicker().on('changeDate', function () {
            $table.bootstrapTable("refresh");
        });


        /**
		 * 编辑页初始化
         */
        $editWin = $("#editWin");
        $editForm = $("#editForm");
        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
	});

	/**
	 * 查询条件
	 * @param params 基本查询条件，包含search、sort、order、limit、offset
	 */
	function meetingParams(params) {
		var localParams = {
			name : $('#search_name').val(),
            startDate : $('#searchDate').val()
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
            $startTime.datetimepicker('setEndDate', null);
            $endTime.datetimepicker('setStartDate', null);
            $editForm.form('clear');
            if(sOrU === 1) {
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }
                $editForm.form('load', row);
                $("#editWinTitle").html("修改会议信息");
                $editWin.modal("show");
            } else if(sOrU === 0) {
                $("#editWinTitle").html("新增会议信息");
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
        $.ajax({
            method : method,
            url : baseUrl,
            dataType : "json",
            contentType : "application/json",
            data : JSON.stringify($editForm.serializeJson()),
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

    $startTime.datetimepicker({
        autoclose:true,
        startView : 1,
        maxView : 1,
        language:"zh-CN",
        format:'yyyy-mm-dd hh:ii'
        }).on('changeDate', function(selected) {	//设置时间约束-结束时间为最大时间
        $startTime.datetimepicker('setDate', $startTime.datetimepicker('getDate'));
        $endTime.datetimepicker('setStartDate', $startTime.datetimepicker('getDate'));
    }).on('clearDate', function() {	//清除时间时取消对结束时间的约束
        $endTime.datetimepicker('setStartDate', null);
    });
    //结束时间控件
    $endTime.datetimepicker({
        autoclose:true,
        startView : 1,
        maxView : 1,
        language:"zh-CN",
        format:'yyyy-mm-dd hh:ii'
    }).on('changeDate', function(selected) {	//设置时间约束-结束时间为最大时间
        $endTime.datetimepicker('setDate', $endTime.datetimepicker('getDate'));
        $startTime.datetimepicker('setEndDate', $endTime.datetimepicker('getDate'));
    }).on('clearDate', function() {	//清除时间时取消对结束时间的约束
        $startTime.datetimepicker('setEndDate', null);
    });


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
