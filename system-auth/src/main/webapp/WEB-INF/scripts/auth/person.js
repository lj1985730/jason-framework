!function ($) {

    'use strict';

    var baseUrl = appPath + "/auth/person";
    var $table;
    var $infoForm;
    var $editWin, $editForm;

    $(function() {

        initTable();    //初始化左侧人员列表

        $infoForm = $('#personInfoForm');   //信息面板

        /**
         * 按钮初始化
         */
        $('#createPerson').off('click').on('click', function() { showEditWin(0); });
        $('#updatePerson').off('click').on('click', function() { showEditWin(1); }).addClass('hidden');   //默认禁用修改按钮
        $('#deletePerson').off('click').on('click', function() { remove(); }).addClass('hidden');   //默认禁用修改按钮
        $('#editWinSubmitBtn').off('click').on('click', function() { submit() });

        /**
         * 编辑页初始化
         */
        $editWin = $("#editWin");
        $editForm = $("#editForm");

        /**
         * 下拉框
         */
        $('#state').comboData('COM_PSN_STATE', false, null, true);//人员状态
        $('#nature').comboData('COM_PSN_NATURE', false, null, true);//人员性质
        $('#gender').comboData('COM_PSN_GENDER', false, null, true);//人员性别
        $('#nationality').comboData('COM_PSN_NATION', false, null, true);//民族

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素
    });

    /**
     *  初始化右侧人员表格
     */
    var initTable = function() {

        $table = $('#personTable');

        $table.bootstrapTable(
            $.extend(
                {
                    url : baseUrl,
                    formatSearch : function () {
                        return '搜索姓名/部门/岗位';
                    },
                    onCheck : function(row) {
                        onSelectPerson(row);
                    },
                    onUncheck : function() {
                        onUnselectPerson();
                    },
                    onExpandRow: function (index, row, $detail) {
                    }
                },
                $.extend({}, generalTableOption, { queryParams : personParams })
            )
        );
    };

    /**
     * 查询条件
     * @param params 基本查询条件，包含search、sort、order、limit、offset
     */
    function personParams(params) {
        return $.extend({}, params);
    }

    /**
     *  选择人员
     * @param person
     */
    var onSelectPerson = function(person) {
        $infoForm.form('load', person);    //加载人员信息
        $.fn.loadPersonPosts(person.id);
        $('#updatePerson').removeClass('hidden');   //启用修改按钮
        $('#deletePerson').removeClass('hidden');   //启用删除按钮
    };

    /**
     *  取消选中人员
     */
    var onUnselectPerson = function() {
        $infoForm.form('clear');    //清空人员信息
        $.fn.clearPersonPosts();    //清空岗位信息
        $('#updatePerson').addClass('hidden');   //禁用修改按钮
        $('#deletePerson').addClass('hidden');   //禁用删除按钮
    };

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

        var method = (sOrU === 0 ? "POST" : "PUT");
        $.fn.http({
            method : method,
            url : baseUrl,
            data : JSON.stringify($editForm.serializeJson()),
            success : function() {
                $editWin.modal("hide");
                $table.bootstrapTable('refresh');
                onUnselectPerson();
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
                        onUnselectPerson();
                    }
                });
            }
        });
    };
}(window.jQuery);