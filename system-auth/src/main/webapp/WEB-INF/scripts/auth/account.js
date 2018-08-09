!function ($) {

    'use strict';

    /**
     * 基础路径
     */
    var baseUrl = appPath + "/auth/account";
    var $table, $editWin, $editForm, $infoForm;
    var $personSelectorWin, $personSelectorTable;

    $(function() {

        $table = $('#accountTable');    //账户列表

        $infoForm = $('#accountInfoForm');  //账户信息面板

        /**
         * 表格初始化
         */
        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索用户名/姓名';
                    },
                    onCheck : function(row) {
                        onSelectAccount(row);
                    },
                    onUncheck : function() {
                        onUnSelectAccount();
                    }
                },
                $.extend({}, generalTableOption, { queryParams : accountParams })
            )
        );

        /**
         * 按钮事件初始化
         */
        /* 新增、修改、删除 */
        $("#createAccount").on("click", function () { showEditWin(0); });
        $("#updateAccount").on("click",function () { showEditWin(1); }).addClass('hidden');   //默认禁用按钮
        $("#deleteAccount").on("click",function () { remove(); }).addClass('hidden');   //默认禁用按钮
        /* 解锁 */
        $("#unlockAccount").on("click",function () {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            if (!row.locked) {
                SysMessage.alertWarning('账户"' + row.name + '"并未锁定！');
                return false;
            }
            $.fn.http({
                method : 'PUT',
                url : baseUrl + '/' + row.id + '/unlock',
                success : function() {
                    SysMessage.alertSuccess('账户"' + row.name + '"已解锁！');
                    $table.bootstrapTable('refresh');
                }
            });
        }).addClass('hidden');   //默认禁用按钮
        /* 启用/禁用 */
        $("#enableAccount").on("click",function () {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            $.fn.http({
                method : 'PUT',
                url : baseUrl + '/' + row.id + '/toggleEnable',
                success : function() {
                    SysMessage.alertSuccess('账户"' + row.name + '"已' + (row.enabled ? '禁用' : '启用') + '！');
                    $table.bootstrapTable('refresh');
                }
            });
        }).addClass('hidden');   //默认禁用按钮
        /* 密码重置 */
        $("#resetPassword").on("click",function () {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            $.fn.http({
                method : 'PUT',
                url : baseUrl + '/' + row.id + '/resetPassword',
                success : function() {
                    SysMessage.alertSuccess('账户"' + row.name + '"已重置为默认密码！');
                }
            });
        }).addClass('hidden');   //默认禁用按钮

        /**
         * 关联人员点击弹窗
         */
        $personSelectorWin = $("#personWin");
        $personSelectorTable = $("#personTable");

        /* 点击弹出人员选择窗体 */
        $("#personSelector").on("click",function () { showPersonWin(); });
        /* 选中人员 */
        $("#personSelectorCheckBtn").on("click",function () { selectPerson(); });

        $('#editAccountWinSubmitBtn').on('click', function() { submit(); });

        /**
         * 编辑页初始化
         */
        $editWin = $("#editAccountWin");
        $editForm = $("#editAccountForm");

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
    });

    /**
     *  查询参数
     * @param params:包含基本查询条件，包含search、sort、order、limit、offset
     */
    function accountParams(params) {
        //预留条件查询,现在支持模糊查询(用户名)，search
        var localParams = {};
        return $.extend(localParams, params);
    }

    /**
     * 清空账户信息面板
     */
    var clearInfoForm = function() {
        $infoForm.form('clear');
    };

    /**
     * 加载账户信息面板
     * @param account
     */
    var loadInfoForm = function(account) {
        if(!account) {
            return false;
        }
        $infoForm.form('load', account);
    };

    /**
     * 选择账户事件
     */
    var onSelectAccount = function(account) {
        loadInfoForm(account);
        $('#updateAccount').removeClass('hidden');   //启用修改按钮
        $('#deleteAccount').removeClass('hidden');   //启用删除按钮
        if(account.locked) {
            $('#unlockAccount').removeClass('hidden');   //启用解锁按钮
        } else {
            $('#unlockAccount').addClass('hidden');   //禁用解锁按钮
        }
        $('#enableAccount').removeClass('hidden');   //启用生效按钮
        $('#resetPassword').removeClass('hidden');   //启用重置密码按钮
    };

    /**
     * 不选择账户事件
     */
    var onUnSelectAccount = function() {
        clearInfoForm();
        $('#updateAccount').addClass('hidden');   //禁用修改按钮
        $('#deleteAccount').addClass('hidden');   //禁用删除按钮
        $('#unlockAccount').addClass('hidden');   //启用解锁按钮
        $('#enableAccount').addClass('hidden');   //启用生效按钮
        $('#resetPassword').addClass('hidden');   //启用重置密码按钮
    };

    /**
     * 显示编辑窗口，定义保存或修改的标识符
     * @param saveOrUpdate 0:新增，1：修改
     */
    var sOrU;
    var showEditWin = function(saveOrUpdate) {
        sOrU = saveOrUpdate;    //新增、修改标识的全局变量
        try {
            $editForm.form('clear');
            if (sOrU === 1) {   //修改
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }
                $editForm.form('load', row);
                $("#personName").val(row.personName);
            }
            $editWin.modal('show');
        } catch (e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 表单保存方法,根据修改或保存的全局标识，判断ajax提交方法
     */
    var submit = function () {

        if(!$editForm.valid()) {    //表单验证
            return false;
        }

        if(sOrU === 0) {    //新增，需要判断已有账户的情况
            $.fn.http({
                method : 'GET',
                url : baseUrl + '/person/' + $('#personId').val() + '/accountNames',
                success : function(data) {
                    if(data) {
                        var confirmStr = sprintf('人员“%s”已拥有%s个账户：%s，确定还要创建新账户吗？',
                            $('#personName').val(),
                            data.split('/').length,
                            data);

                        bootbox.confirm(confirmStr, function(callback) {
                            if (callback) {
                                sendSave();
                            }
                        });
                    } else {
                        sendSave();
                    }
                }
            });
        } else {
            sendSave();
        }
    };

    /**
     * 保存操作
     */
    var sendSave = function() {
        var method = (sOrU === 0 ? "POST" : "PUT");
        $.fn.http({
            method : method,
            url : baseUrl,
            data : JSON.stringify($editForm.serializeJson()),
            success : function() {
                $editWin.modal("hide");
                $table.bootstrapTable('refresh');
                onUnSelectAccount();
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
            bootbox.confirm({
                message : "确定要删除吗？",
                size : 'small',
                callback : function(callback) {
                    if (callback) {
                        $.fn.http({
                            method : "DELETE",
                            url : baseUrl + '/' + row.id,
                            success : function() {
                                $table.bootstrapTable('refresh');
                                onUnSelectAccount();
                            }
                        });
                    }
                }
            });
        } catch (e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 显示人员列表
     */
    var showPersonWin = function() {
       try {
           $personSelectorWin.modal('show');
       } catch (e) {
           SysMessage.alertError(e.message);
       }
    };

    /**
     * 选择人员
     */
    var selectPerson = function () {
        var row = $personSelectorTable.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        $("#personId").val(row.id);
        $("#personName").val(row.name);
        $('#phone').val(row.phone);
        $("#email").val(row.email);

        $personSelectorWin.modal('hide');
    };

}(window.jQuery);