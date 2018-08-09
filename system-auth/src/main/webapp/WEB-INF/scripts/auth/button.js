!function ($) {

    'use strict';

    var baseUrl = appPath + "/auth/button";
    var $tree;
    var $table, $editWin, $editForm;

    $(function() {

        /**
         * 初始化tree组件
         */
        var timer;
        $tree = $('#treePanel');
        $tree.treeSelect({
            entity : 'com.yoogun.auth.domain.model.Menu',
            multiSelect : false,
            onSelect : function() {  //选中节点,显示表单
                clearTimeout(timer);
                refreshTable(); //加载选中的数据
            },
            onUnselect : function() {   //取消选中节点,加载数据
                timer = setTimeout(refreshTable, 100);
            }
        });

        /**
         *  初始化按钮表格
         */
        $table = $('#buttonTable');
        $table.bootstrapTable(
            $.extend(
                { url : baseUrl, formatSearch : function () { return '搜索按钮名称'; } },
                $.extend({}, generalTableOption, { queryParams : tableParams })
            )
        );

        /**
         * 按钮初始化
         */
        /* 启用/禁用 */
        $("#enableButton").on("click",function () {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            $.fn.http({
                method : 'PUT',
                url : baseUrl + '/' + row.id + '/toggleEnable',
                success : function() {
                    SysMessage.alertSuccess('按钮"' + row.name + '"已' + (row.enabled ? '禁用' : '启用') + '！');
                    $table.bootstrapTable('refresh');
                }
            });
        });
        $('#createButton').on('click', function() { showEditWin(0); });
        $('#updateButton').on('click', function() { showEditWin(1); });
        $('#deleteButton').on('click', function() { remove(); });
        $('#editWinSubmitBtn').on('click', function() { submit() });

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
    function tableParams(params) {
        var localParams = {
            menuId : $tree.treeSelect('getSelectedId')[0]
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
            if(sOrU === 1) {    //update
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }
                $editForm.form('load', row);
                $("#editWinTitle").html('修改按钮');
                $editWin.modal("show");
            } else if(sOrU === 0) { //create new

                var menuIds = $tree.treeSelect('getSelectedId');
                if(menuIds.length === 0) {
                    SysMessage.alertInfo('请选择一个菜单！');
                    return false;
                }

                $('#menuId').val(menuIds[0]);   //赋值菜单Id

                $("#editWinTitle").html('新增按钮');
                $editWin.modal("show");
            }
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 刷新表格数据
     */
    var refreshTable = function() {
        $table.bootstrapTable('refresh');
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