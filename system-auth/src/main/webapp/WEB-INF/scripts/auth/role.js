!function ($) {

    'use strict';

    /**
     * 基础路径
     */
    var baseUrl = appPath + "/auth/role";
    var $table, $editWin, $editForm, $infoForm;

    $(function() {

        $table = $('#roleTable');    //角色列表
        $infoForm = $('#roleInfoForm');  //角色信息面板

        /**
         * 表格初始化
         */
        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索角色名称/编码';
                    },
                    onCheck : function(row) {	//选中表某一行时触发刷新账户信息面板事件
                        onSelectRole(row);
                        $.fn.RoleRlatObj.setRoleRlat(row);
                    },
                    onUncheck : function() {	    //取消选中表某一行时触发clear账户信息面板事件
                        onUnSelectRole();
                        $.fn.RoleRlatObj.cancelICheckState();
                    }
                },
                $.extend({}, generalTableOption, { queryParams : roleParams })
            )
        );

        /**
         * 按钮事件初始化
         */
        /* 新增、修改、删除 */
        $("#createRole").on("click", function () { showEditWin(0); });
        $("#updateRole").on("click",function () { showEditWin(1); });
        $("#deleteRole").on("click",function () { remove(); });
        /* 启用/禁用 */
        $("#enableRole").on("click",function () {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            $.fn.http({
                method : 'PUT',
                url : baseUrl + '/' + row.id + '/toggleEnable',
                success : function() {
                    SysMessage.alertSuccess('角色"' + row.name + '"已' + (row.enabled ? '禁用' : '启用') + '！');
                    $table.bootstrapTable('refresh');
                }
            });
        });

        /* 提交表单 */
        $('#editRoleWinSubmitBtn').on('click', function() { submit(); });

        /**
         * 编辑页初始化
         */
        $editWin = $("#editRoleWin");
        $editForm = $("#editRoleForm");

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素

    });

    /**
     * roleTable的查询参数
     * @param params:包含基本查询条件，包含search、sort、order、limit、offset
     */
    function roleParams(params) {
        //预留条件查询：name、code,现在支持模糊查询，search
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
     * 加载角色信息面板
     * @param role
     */
    var loadInfoForm = function(role) {
        if(!role) {
            return false;
        }

        $infoForm.form('load', role);
    };

    /**
     * 选择角色事件
     */
    var onSelectRole = function(role) {
        loadInfoForm(role);
        $('#updateRole').removeClass('hidden');   //启用修改按钮
        $('#deleteRole').removeClass('hidden');   //启用删除按钮
        $('#enableRole').removeClass('hidden');   //启用生效按钮
    };

    /**
     * 不选择角色事件
     */
    var onUnSelectRole = function() {
        clearInfoForm();
        $('#updateRole').addClass('hidden');   //禁用修改按钮
        $('#deleteRole').addClass('hidden');   //禁用删除按钮
        $('#enableRole').addClass('hidden');   //禁用生效按钮
    };

    /**
     * 显示编辑窗口
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
            }
            $editWin.modal('show');
        } catch (e){
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 保存操作
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
                $.fn.RoleRlatObj.$roleRlatTable.bootstrapTable('refresh');
                onUnSelectRole();
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
                                $.fn.RoleRlatObj.$roleRlatTable.bootstrapTable('refresh');
                                onUnSelectRole();
                            }
                        });
                    }
                }
            });
        } catch (e) {
            SysMessage.alertError(e.message);
        }
    };

}(window.jQuery);
