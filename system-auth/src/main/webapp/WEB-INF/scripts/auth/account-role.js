!function ($) {

    'use strict';

    var $table, $accountTable;

    /**
     *  初始化
     */
    $(function () {

        /* role table初始化 */
        $table = $("#roleSelectorTable");
        $table.bootstrapTable(
            $.extend(
                {
                    url : appPath + '/auth/account/roles',
                    formatSearch : function () {
                        return '搜索角色名称或编码';
                    }
                },
                $.extend({}, generalTableOption, { pagination : true, pageList: [1000], pageSize : 1000, queryParams : roleParams })
            )
        );

        /* account table初始化 */
        $accountTable = $("#accountTable");
        /* 点击账户列表刷新已绑定的角色 */
        $accountTable.on('check.bs.table', function (event, row) {
            $table.bootstrapTable('uncheckAll');
            if(!row.roles) {
                $table.bootstrapTable('checkBy', { field : "id", values : [] }); //勾选已绑定的角色
            } else {
                $table.bootstrapTable('checkBy', { field : "id", values : row.roles }); //勾选已绑定的角色
            }
        });

        /* 取消点击账户列表清除已绑定的角色 */
        $accountTable.on('uncheck.bs.table', function () {
            $table.bootstrapTable('uncheckAll');
            $table.bootstrapTable('checkBy', { field : "id", values : [] }); //勾选已绑定的角色
        });

        /* 刷新表格后，自动选中当前账户的角色 */
        $table.on('load-success.bs.table', function () {
            var selectedAccount = $accountTable.bootstrapTable('getSelections')[0];
            if(selectedAccount && selectedAccount.roles) {
                $table.bootstrapTable('checkBy', { field : "id", values : selectedAccount.roles }); //勾选已绑定的角色
            }
        });
    });

    /**
     * roleTable的查询参数
     * @param params:包含基本查询条件，包含search、sort、order、limit、offset
     */
    var roleParams = function (params) {
        //预留条件查询：name、code,现在支持模糊查询，search
        var localParams = {};
        return $.extend(localParams, params);
    };

    /**
     * 保存绑定角色到账户上
     */
    $.fn.bindRoleToAccount = function () {
        var selectedAccount = $accountTable.bootstrapTable('getSelections')[0];
        if(!selectedAccount) {
            SysMessage.alertInfo('请选择要操作的账户！');
            return false;
        }

        var selectedRoles = $table.bootstrapTable('getSelections');
        if(!selectedRoles || selectedRoles.length === 0) {
            SysMessage.alertInfo('请选择要绑定的角色！');
            return false;
        }

        // 拼接角色Id
        var roleIds = '';
        for(var i = 0; i < selectedRoles.length; i ++) {
            roleIds += selectedRoles[i].id + ',';
        }

        var request = { // 请求体
            method : 'PUT',
            url : appPath + '/auth/account/roles',
            data : JSON.stringify({
                accountId : selectedAccount.id,
                roleIds : roleIds
            }),
            success : function () {
                selectedAccount.roles = roleIds.split(',');
                $accountTable.bootstrapTable('updateByUniqueId', { id : selectedAccount.id, row : selectedAccount });
            }
        };
        $.fn.http(request);
    };

}(window.jQuery);