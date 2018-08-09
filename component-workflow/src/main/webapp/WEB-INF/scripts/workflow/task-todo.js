!function ($) {

    'use strict';

    var baseUrl = appPath + '/workflow';

    var $table;

    /**
     *  初始化
     */
    $(function () {

        /* role table初始化 */
        $table = $("#todoTaskTable");
        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl + '/todoTasks',
                    formatSearch : function () {
                        return '搜索流程名称或流程编号';
                    }
                },
                $.extend({}, generalTableOption, { queryParams : todoParams })
            )
        );

        /* 点击列表 */
        $table.on('check.bs.table', function (event, row) {
            $('#viewTaskBtn').workflowHistoricView('setProcessInstanceId', row.processInstanceId);
        });

        /* 取消点击列表 */
        $table.on('uncheck.bs.table', function () {
            $('#viewTaskBtn').workflowHistoricView('setProcessInstanceId', '');
        });

        /* 刷新表格后 */
        $table.on('load-success.bs.table', function () {
        });

        $('#viewTaskBtn').workflowHistoricView({});
    });

    /**
     * todoTable 的查询参数
     */
    var todoParams = function (params) {
        var localParams = {};
        return $.extend(localParams, params);
    };

    /**
     * 签收任务
     * @param taskId 任务ID
     */
    $.fn.claimTask = function(taskId) {
        bootbox.confirm({
            title : '<i class="glyphicon glyphicon-import"></i>&nbsp;签收确认',
            message : '确认签收此任务？',
            buttons : {
                confirm : { label : '<i class="glyphicon glyphicon-ok"></i>&nbsp;确认', className: 'btn-success' },
                cancel : { label : '<i class="glyphicon glyphicon-remove"></i>&nbsp;取消', className : 'btn-default' }
            },
            callback : function(result) {
                if(!result) {
                    return;
                }
                $.ajax({
                    type : 'PUT',
                    url : basePath + '/task/' + taskId + '/claim',
                    dataType : 'json',
                    contentType : 'application/json',
                    success : function(data) {
                        toastr.success('签收完成！');
                        refreshTodoTable();
                    },
                    error : function(data) {
                        toastr.error('签收失败：' + data.responseText);
                        refreshTodoTable();
                    }
                });
            }
        });
    };

    /**
     * 处理任务
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionKey 任务定义KEY
     * @param taskId 任务ID
     * @param processState 流程状态
     */
    $.fn.disposeTask = function(processInstanceId, taskDefinitionKey, taskId, processState) {
        if(!processInstanceId || !taskDefinitionKey) {
            toastr.error('未找到对应资源！');
            return false;
        }

        // selectedTaskId = taskId;    //记录选中的任务ID
        // selectedProcessInstanceId = processInstanceId;   //记录流程实例ID

        if(processState && processState === 'REJECTED') {    //驳回的数据
            showRetry(selectedProcessInstanceId, taskDefinitionKey);
        } else {
            showDispose(selectedProcessInstanceId, taskDefinitionKey);
        }
    };

    /**
     * 显示再次提交窗口
     */
    var showRetry = function(processInstanceId, taskDefinitionKey) {
        $retryContainer.load(
            sprintf('%s/editableBusinessFormView?processInstanceId=%s&taskDefinitionKey=%s',
                basePath, processInstanceId, taskDefinitionKey
            ) + " .container-fluid",
            { },
            function(html) {
                // $retryContainer.find('button').remove();
                if($retryContainer.find('#buttonToolbar').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                    $retryContainer.find('#buttonToolbar').remove();
                }
                // if($retryContainer.find('.btn-group').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                //     $retryContainer.find('.btn-group').remove();
                // }
                $(html).find("script").appendTo($retryContainer);
                //流程查看按钮
                $('#retryPanel_historicView').workflowHistoricView('setProcessInstanceId', processInstanceId);
                $.fn.switchPanel('retry');
            }
        );
    };

    /**
     * 初始化处理窗体
     */
    // var initDisposePanel = function() {
    //     $disposePanel = $('#disposePanel');
    //     $disposeContainer = $disposePanel.find('.formContainer');
    //     $('#disposePanel_approvalBtn').on('click', function() {
    //         doDispose();
    //     });
    // };

    /**
     * 显示处理窗口
     */
    // var showDispose = function(processInstanceId, taskDefinitionKey) {
    //     $disposeContainer = $disposePanel.find('.formContainer');
    //     $disposeContainer.load(
    //         sprintf('%s/businessFormView?processInstanceId=%s&taskDefinitionKey=%s',
    //             basePath, processInstanceId, taskDefinitionKey
    //         ) + " .container-fluid",
    //         { },
    //         function(html) {
    //             $disposeContainer.find('button').remove();
    //             if($disposeContainer.find('#buttonToolbar').length > 0) {  //删除表单页面中的按钮，采用通用按钮
    //                 $disposeContainer.find('#buttonToolbar').remove();
    //             }
    //             if($disposeContainer.find('.btn-group').length > 0) {  //删除表单页面中的按钮，采用通用按钮
    //                 $disposeContainer.find('.btn-group').remove();
    //             }
    //             $(html).find("script").appendTo($disposeContainer);
    //             //流程查看按钮
    //             $('#disposePanel_historicView').workflowHistoricView('setProcessInstanceId', processInstanceId);
    //             $.fn.switchPanel('dispose');
    //         }
    //     );
    // };

    /**
     * 提交审批结果
     */
    var doDispose = function() {
        var result = $('[name="approvalResult"]:checked').val();    //radio选项
        if(!selectedTaskId) {
            bootbox.alert({
                title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;提示',
                message : '请选择操作的任务！'
            });
            return false;
        }
        var operate, url;
        if(result === 1) {
            operate = '同意';
            url = sprintf('%s/task/%s/pass', basePath, selectedTaskId);
        } else {
            if(!$('#comment').val()) {
                bootbox.alert({
                    title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;提示',
                    message : '驳回任务请填写意见！'
                });
                return false;
            }
            operate = '驳回';
            url = sprintf('%s/task/%s/reject', basePath, selectedTaskId);
        }

        bootbox.confirm({
            title : sprintf('<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;确认%s', operate),
            message : sprintf('确认%s此任务？', operate),
            buttons : {
                confirm : { label : '<i class="glyphicon glyphicon-ok"></i>&nbsp;确认', className: 'btn-success' },
                cancel : { label : '<i class="glyphicon glyphicon-remove"></i>&nbsp;取消', className : 'btn-danger' }
            },
            callback : function(result) {
                if(!result) {
                    return;
                }

                $.ajax({
                    type : 'PUT',
                    url : url,
                    dataType : 'json',
                    contentType : 'application/json',
                    data : JSON.stringify({
                        processInstanceId : selectedProcessInstanceId,
                        comment : $('#comment').val()
                    }),
                    success : function(data) {
                        toastr.success('处理成功！');
                        refreshTodoTable();
                        $.fn.switchPanel('home');
                    },
                    error : function(data) {
                        toastr.error('处理失败：' + data.responseText);
                    }
                });
            }
        });
    };

    /**
     * 初始化再次提交窗体
     */
    // var initRetryPanel = function() {
    //     $retryPanel = $('#retryPanel');
    //     $retryContainer = $retryPanel.find('.formContainer');
    //     $('#retryPanel_saveBtn').on('click', function() {
    //         $.fn.formSubmit(function() {
    //             toastr.success('保存成功！');
    //         });
    //     });
    //     $('#retryPanel_submitBtn').on('click', function() {
    //         $.fn.formSubmit($.fn.doRetry());
    //     });
    // };

    /**
     * 显示再次提交窗口
     */
    var showRetry = function(processInstanceId, taskDefinitionKey) {
        $retryContainer.load(
            sprintf('%s/editableBusinessFormView?processInstanceId=%s&taskDefinitionKey=%s',
                basePath, processInstanceId, taskDefinitionKey
            ) + " .container-fluid",
            { },
            function(html) {
                // $retryContainer.find('button').remove();
                if($retryContainer.find('#buttonToolbar').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                    $retryContainer.find('#buttonToolbar').remove();
                }
                // if($retryContainer.find('.btn-group').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                //     $retryContainer.find('.btn-group').remove();
                // }
                $(html).find("script").appendTo($retryContainer);
                //流程查看按钮
                $('#retryPanel_historicView').workflowHistoricView('setProcessInstanceId', processInstanceId);
                $.fn.switchPanel('retry');
            }
        );
    };

    /**
     * 再次提交审批结果
     */
    $.fn.doRetry = function() {
        if(!selectedTaskId) {
            bootbox.alert({
                title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;提示',
                message : '请选择操作的任务！'
            });
            return false;
        }
        bootbox.confirm({
            title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;确认再次提交',
            message : '确认再次提交此任务？',
            buttons : {
                confirm : { label : '<i class="glyphicon glyphicon-ok"></i>&nbsp;确认', className: 'btn-success' },
                cancel : { label : '<i class="glyphicon glyphicon-remove"></i>&nbsp;取消', className : 'btn-danger' }
            },
            callback : function(result) {
                $.ajax({
                    type : 'PUT',
                    url : sprintf('%s/task/%s/pass', basePath, selectedTaskId),
                    dataType : 'json',
                    contentType : 'application/json',
                    data : JSON.stringify({
                        processInstanceId : selectedProcessInstanceId,
                        comment : '再次提交'
                    }),
                    success : function(data) {
                        toastr.success('处理成功！');
                        refreshTodoTable();
                        $.fn.switchPanel('home');
                    },
                    error : function(data) {
                        toastr.error('处理失败：' + data.responseText);
                        refreshTodoTable();
                    }
                });
            }
        });
    };

    //
    // /**
    //  * 保存绑定角色到账户上
    //  */
    // $.fn.bindRoleToAccount = function () {
    //     var selectedAccount = $accountTable.bootstrapTable('getSelections')[0];
    //     if(!selectedAccount) {
    //         SysMessage.alertInfo('请选择要操作的账户！');
    //         return false;
    //     }
    //
    //     var selectedRoles = $table.bootstrapTable('getSelections');
    //     if(!selectedRoles || selectedRoles.length === 0) {
    //         SysMessage.alertInfo('请选择要绑定的角色！');
    //         return false;
    //     }
    //
    //     // 拼接角色Id
    //     var roleIds = '';
    //     for(var i = 0; i < selectedRoles.length; i ++) {
    //         roleIds += selectedRoles[i].id + ',';
    //     }
    //
    //     var request = { // 请求体
    //         method : 'PUT',
    //         url : appPath + '/auth/account/roles',
    //         data : JSON.stringify({
    //             accountId : selectedAccount.id,
    //             roleIds : roleIds
    //         }),
    //         success : function () {
    //             selectedAccount.roles = roleIds.split(',');
    //             $accountTable.bootstrapTable('updateByUniqueId', { id : selectedAccount.id, row : selectedAccount });
    //         }
    //     };
    //     $.fn.http(request);
    // };

}(window.jQuery);

/**
 * 工作流任务操作
 */
var todoTaskOperation = function (value, row, index) {
    var content = '';
    if(!row.assigneeId) {
        content += sprintf('<a style="cursor: pointer;" onclick="$.fn.claimTask(\'%s\');">签收</a>', row.taskId);
    } else {
        content += sprintf('<a style="cursor: pointer;" onclick="$.fn.disposeTask(\'%s\',\'%s\',\'%s\',\'%s\');">办理</a>',
            row.processInstanceId, row.taskDefinitionKey, row.taskId, row.processState);
    }

    if(row.processState === ''|| row.processState === 'null'
        || (row.processState !== 'PENDING' && row.processState !== 'SUSPENDED' && row.processState !== 'FINISHED')) {
        content += sprintf(
            '&nbsp;&nbsp;<a style="cursor: pointer;" href="javascript:void(0);" onclick="$.fn.removeProcessInstance(\'%s\', \'%s\', \'%s\', \'%s\')">删除</a>',
            row.processInstanceId, row.businessTable, row.businessColumn, row.businessId);
    }

    // content += sprintf('&nbsp;&nbsp;<a style="cursor: pointer;" onclick="$.fn.showHistoricModal(\'%s\')">跟踪</a>', row.processInstanceId);
    return content;
};