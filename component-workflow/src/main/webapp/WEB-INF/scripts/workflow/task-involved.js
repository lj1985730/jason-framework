!function ($) {

    'use strict';

    var baseUrl = appPath + '/workflow';

    var $table;

    /**
     *  初始化
     */
    $(function () {

       /* 默认查询未完成的数据 */
        $("[name='involvedFinished']", "#involvedToolbar").each(function() {
            if($(this).val() === 'false') {
                $(this).attr('checked', true);
                $(this).parent().addClass('checked');
            }
        });

        /* role table初始化 */
        $table = $("#involvedTable");
        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl + '/involvedProcesses',
                    formatSearch : function () {
                        return '搜索流程名称或流程编号';
                    }
                },
                $.extend({}, generalTableOption, { queryParams : involvedParams })
            )
        );

        /* 点击账户列表 */
        $table.on('check.bs.table', function (event, row) {
        });

        /* 取消点击账户列表 */
        $table.on('uncheck.bs.table', function () {
        });

        /* 刷新表格后 */
        $table.on('load-success.bs.table', function () {
        });

        /* 切换是否完成条件 */
        $("[name='involvedFinished']", "#involvedToolbar").on('ifChecked', function() {
            $table.bootstrapTable('removeAll');
            $table.bootstrapTable('refresh');
        });
    });

    /**
     * table查询参数
     */
    var involvedParams = function (params) {
        var localParams = {
            finished : $("[name='involvedFinished']:checked", "#involvedToolbar").val()
        };
        return $.extend(localParams, params);
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